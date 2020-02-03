/**import * as functions from 'firebase-functions';

// // Start writing Firebase Functions
// // https://firebase.google.com/docs/functions/typescript
//
 export const helloWorld = functions.https.onRequest((request, response) => {
  response.send("Hello from Firebase!");
 });

 export const lat = functions.database
 .ref('/Latitude/{latitudeValue}')

 export const long = functions.database
 .ref('/Longitude/{longitudeValue}')

 .onCreate((snapshot, context) => {
     const latitudeValue = context.params.latitudeValue
     const longitudeValue =context.params.longitudeValue

     const latData = snapshot.val()

 }) */ 




 
// [START import]
// The Cloud Functions for Firebase SDK to create Cloud Functions and setup triggers.
import * as functions from 'firebase-functions';

// The Firebase Admin SDK to access the Firebase Realtime Database.
import * as admin from 'firebase-admin';
admin.initializeApp();
// [END import]




export const transferDatav2 = functions.https.onRequest(async (req, res) => {

    // response.send("Hello from Firebase!");

    if (!req.body || !req.body.deviceId) return res.status(400).send('Device Id required');
    const deviceId = req.body.deviceId;

    // return snapshot = await admin.database().ref('/messages').push({original: original});


    // Read ID from /transfers node to check if its there
    // If not then write the requesting device as device 1
    // If yes then transfer data bertween both devices

    //reading ID from /transfers
    return admin.database().ref('/transfers').once('value')
        .then(async (snapshot) => {


            if (snapshot.val() && snapshot.val().device1) {

                // If devices have the same unique ID then return 
                if (snapshot.val().device1 === deviceId) return res.status(200).send(`Device ${deviceId} already exists`);

                //Get the data for both devices
                const device1Data = await admin.database().ref(snapshot.val().device1).once('value');
                console.log(JSON.stringify(device1Data.val()));
                if (!device1Data || device1Data.val() === null) {
                    await admin.database().ref('transfers').set(null);
                    return res.status(400).send(`Cannot find data for device 1: ${snapshot.val().device1}`);
                }

                const device2Data = await admin.database().ref(deviceId).once('value');
                console.log(JSON.stringify(device2Data.val()));
                if (!device2Data || device2Data.val() === null) {
                    await admin.database().ref('transfers').set(null);
                    return res.status(400).send(`Cannot find data for device 2: ${deviceId}`);
                }


                // To check if devices are within accepted range with the method checkDistance(device1, device2, distanceLimit)
                const inProximity = await checkDistance(device1Data.val(), device2Data.val(), 20)

                //If in accepted range push devices info into the other devices database under /data-received
                if (inProximity === true) {

                    // Transfer the data
                    await admin.database().ref(snapshot.val().device1 + '/data-received').push({ 'mediaType': device2Data.val()['mediaType'], 'userName': device2Data.val()['userName'], 'receivedAt': admin.database.ServerValue.TIMESTAMP });
                    await admin.database().ref(deviceId + '/data-received').push({ 'mediaType': device1Data.val()['mediaType'], 'userName': device1Data.val()['userName'], 'receivedAt': admin.database.ServerValue.TIMESTAMP });


                    // To Delete the device from /transfers node
                    await admin.database().ref('transfers').set(null);

                    return res.status(200).send(`Data Transfered`);

                }
                //If device isnt in acceptedrange then delete /transfers node and return message
                else if (inProximity === false) {

                    await admin.database().ref('transfers').set(null);
                    return res.status(400).send(`In proximity: ${inProximity}. Devices should be within 20 meters of each other`);
                }
                else {

                    await admin.database().ref('transfers').set(null);
                    return res.status(400).send(`Error: ${inProximity}`);
                }

            }

            else {
                return snapshot.ref.child('device1').set(deviceId)
                    .then((data) => {

                        //Set to device 1
                        return res.status(200).send(`Device ${deviceId} set as device 1`);
                    })
                    .catch((err) => {

                        return res.status(500).send(`Error: ${err}`)
                    })
                }
            })
            
            .catch((err) => {

            return res.status(500).send(`Error: ${err}`)
        })

});

//Method to check distance between devices
async function checkDistance(device1: any, device2: any, distanceLimit: number) {

    // Check distance between 2 devices in meters
    console.log('Distance Limit', distanceLimit);

    let dist = distance(device1.latitude, device1.longitude, device2.latitude, device2.longitude, 'km');

    //convert to meters
    dist = dist * 1000; 
    // Print Calculate dist from lat and long to console
    console.log('Calculated Distance', dist);

    if (dist > distanceLimit) return false;

    return true;

}

//If lat and long are the same then return 0, no need for calculations to know this
//Else the two latitudes and longitudes are calculated using Haversines formula
function distance(latitude1: number, longitude1: number, latitude2: number, longitude2: number, unit: string) {
    if ((latitude1 === latitude2) && (longitude1 === longitude2)) {
        return 0;
    }
    else {
        const radianLat1 = Math.PI * latitude1 / 180;
        const radianLat2 = Math.PI * latitude2 / 180;
        const theta = longitude1 - longitude2;
        const radtheta = Math.PI * theta / 180;
        let dist = Math.sin(radianLat1) * Math.sin(radianLat2) + Math.cos(radianLat1) * Math.cos(radianLat2) * Math.cos(radtheta);
        if (dist > 1) {
            dist = 1;
        }

        dist = Math.acos(dist);
        dist = dist * 180 / Math.PI;
        dist = dist * 60 * 1.1515;
        if (unit === "km") { dist = dist * 1.609344 }
        return dist;
    }

}
