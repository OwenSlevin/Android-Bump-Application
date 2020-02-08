# Android-Bump-Application

This is an application that allows users to enter there social media users names to the corresponding social media website. The app will then store the users information using androids SQL abilities. When two devices with the app are within an acceptable range they can press the Bump button and then fist bump there devices together. The backend firebase functions server will then check that the bump was intended to happen and if all the criteria passes the social media usernames from each device will then be transfered to the other device where they can then be accessed later.


# Chapter 1. Introduction 
 
1.1       Android Development 
 
The application created for this job will allow the user to select one of the 3 listed social media types and enter there corresponding user name for that account. Once the user has entered there information for these accounts they can then transfer this list of information to other users by clicking on the Bump button.  This will then check if the phones have been fist bumped together and if found to be true, then contact Firebase Functions where logic will examine the devices latitude and longitude and determine if the proximity was close enough for a Bump to happen. If found to be true the function will then send the each list of information from one device to the other so that both devices will have transferred there information. 
 
 
1.2       Goals 
 
My goal was to get a well-rounded understanding of Android and other technologies associated with it. I figured that this project would be a good chance for me to experience this as it requires accessing device sensors, using GPS locations, database storage and retrieval, processing logic on a backend server, using external libraries (dependencies), creating an intuitive interface etc. 
 
 The end product would be an application that simplifies how people can swap contact information. E.g. typically in a new interaction people will just get one social media account as a form of contact from that person. Now they can all be sent seamlessly with a Bump. This would remove the inconvenience of having to write down there obscure username and then having to ask for each other account individually or searching for them through google.  
 
 
1.3       Approach 
 
For this application to be able to function correctly there were a few general step that would have to be taken to achieve this. Firstly, the user’s information must be stored in a way for them to view on their device while simultaneously being uploaded to a database under a unique id while remaining in an easy to update and retrieve format.  Then during the bump process if the bump is found to be true a server function will be contacted and analyse the latitude and longitude stored by each device in the database and determine if there proximity was close enough for a Bump to occur. If this is found to be true the server function will then push the user’s information into the other device so that they can then view it.  
 
1.4       Document Structure 
 
The rest of this document is as follows. Chapter Two provides a literature review of the area of Android Development and the sources consulted in accomplishing my project, in addition to related work. Chapter Three describes the methodology and high–level design of my project structure. In Chapter Four, I discuss the system design, and requirements/specifications including any hardware and software used. Chapter Five provides the implementation details of my project/system/etc. In Chapter Six, details of the working prototype of my project/system/etc are provided, including my testing and evaluation technique. I also discuss results including any revisions to the overall design and implementation that were deemed necessary. Finally, Chapter Seven presents conclusions and future work 
 
 
 

# Chapter 2. Background 
 
2. 1       Literature Review 
 
Introduction: Most of the sources that I used throughout the duration of this project came from websites and videos. I understand that typically academic projects like these would expect references to published papers or books as there are understandably viewed as a more reputable source. The reason for this is not laziness or unwillingness to read through these sources (because they likely would’ve made this process much smoother for me) but that nothing exists for what I wanted to achieve. When looking for these types of sources all that was found were Google patents (from when they bought the original company), theorising how you could detect road bumps while driving in a car and one paper criticizing the security flaws found it the original Bump app.  
 
The information offered online for android development has grown to be very extensive and reputable in recent years from the documentation that Android / Firebase offer themselves, to tech websites where they discuss various forms of implementations and there corresponding pros and cons or simply being able to just look and error code you inexplicably received and then having an explanation right there explaining why it occurred and how to resolve it.  
 
Lieb Not Thinking About Taking Bump Technologies Public (Source: https://www.youtube.com/watch?v=VQ9lsUSWHkE ) This video is similar to the video I seen on Bump technologies many years ago. In this video David Lieb the founder of Bump talks to Bloomberg news and describes the functionality and purpose of this application. The early stages of Bump are quite similar to what I would like to achieve. In this video he speaks about how he grew tired of getting business cards off people and having to manually enter their contacts. To circumvent this he then developed Bump so that you can instead electronically create 

business cards on the application and then transfer this to another user via bumping devices. 
 
Google I/O 2012 – Up Close and Personal: NFC and Android Beam (Source: https://www.youtube.com/watch?v=HkzPc8ZvCco ) This is a conference held by Google where there developers discuss where they discuss how there software Android Beam operates and the reasoning behind the decisions they made. While this has a different implementation than what I was trying to achieve it was still covered many topics that are relevant for data transferring and designing an intuitive user experience. 
 
Android Studio Tutorial – Firebase Tutorial – Introduction – Part 1 (Source: https://www.youtube.com/watch?v=igZAYBMuBs&list=PLGCjwl1RrtcTXrWuRTa59RyRmQ4OedWrt  ) (Used episodes 1 – 8, not the entire playlist ) This is the first episode is a series of episodes that provides a crash course of Firebase and how to use all of its various capabilities and features. Initially trying to tackle and understand Firebase as a whole was surprisingly tough. I had watched Firebase’s selfmade explanation videos and read through there documentation but it still didn’t “click” for me. In this video series the steps required to start a Firebase project, link it to your android project, import dependencies, correct use of code etc. were all broken down into bite sized easy to understand videos. Being able to see each part of the setup process and having each steps importance explained I feel helped me to get a stronger foundation early on in my projects life cycle.  
 
Android Development Documentation – SensorManager (Source: https://developer.android.com/reference/android/hardware/SensorManager.html ) This is the official SensorManager documentation provided by Android. This source was beneficial to me because initially I was unsure of which sensor I would need to implement to achieve the functionality I wanted but upon reading I quickly realised the type of sensor I required and the classes I needed to implement. Here every sensor 

method and class is listed and explains thoroughly the corresponding parameters that they require to function. 
 
Getting Started with Cloud Functions for Firebase using Typescript – Firecasts (Source: https://www.youtube.com/watch?v=DYfP-UIKxH0 ) This is the Firebase made tutorial that shows step by step how to setup Node.js, NPM and import all of the necessary dependencies for Firebase Functions to operate correctly. This video was a great guide from me to ensure that I had everything I required setup correctly and enabled all of the correct settings that I would require. It also included good descriptions at every step about how everything worked and why it was required which helped me better grasp an understanding for this technology. 
 
Earth Geometry Lesson 2 – Distance between two points on great circle (Source: https://www.youtube.com/watch?v=Mpul91Svq9w )  This video is one of the best sources I looked at when trying to figure out how latitude and longitude coordinates work and then work out the distance once between these two points. He explains there concept of where the lines come from and how they are beneficial. These lines are actually referred to as great circles. The lines placed over the Earth vertically are actually large circles that’ are represented by longitude, these circles are spread out and equal distance and all intersect each other at the same 2 points. The horizontal lines represent the longitude and start from the middle where the point of the Earth is widest. From there the circles then expand outwards at an equal distance. 
 
 
 
 
 
2.2        Related Work 
 
Introduction In terms of similar work there was only one business that I have been able to find that operated in a way that I liked and that was Bump Technologies which was the main inspiration for this project as mentioned earlier. There have been other technologies that are similar and achieve the same end goal but the implementation and functionality are quite different.  
 
Bump Technologies This application allowed users to select files on their device and then transfer them to another device after a registered bump. It was released in March 2009 and operated until bought and then consequently shutdown by Google in January 2014.  
 
AirDrop This is a service introduced by Apple for its iOS and Mac devices. This software operates by using Bluetooth to create a peer to peer connection and then transfer the selected files over Wi-Fi. This means that for a transfer to occur both devices must be apple products and be on the same Wi-Fi connection.  
 
Android Beam This is a feature that comes with the Android operating system that transfers data between devices by using NFC (Near Field Communication). For a transfer to occur devices would have to be placed back to back so that both NFC chips would be touching. Then the phone with the screen facing upwards would select the data it wanted to transfer to the other device.  
 
“Bezos Beep” Although never officially released, there was a patent issued to Amazon for a technology that would be able to transfer data between each other by using an audible beep noise. The idea is that a device within a close range would detect the signal and decode it to find a URL which could then retrieve the data from a server.  

# Chapter 3. Methodology 
 
3.1       Technologies 
 
Java I chose this as my programming language as it is the one I have most experience with and the language of choice for Android Studio. While first looking into Android development I seen Kotlin was also possible choice for Android development. While Kotlin is supposed to run faster compared to java I still decided against this language because I felt learning a new language for a speed increase wasn’t worth it as I knew this project would be relatively small in size and java could provide all the speed it would require. Java is also one of the most commonly used programming used programming languages in the world which I knew would mean that there would be plenty resource’s available online to assist for when problems arises. This language is also quite robust and is capable of running on a large variety of different platforms which appealed to me since Android itself is an operating system is used by 10’s of thousands of different devices.  
 
Typescript This was the language I chose to use for writing my backend function as it was the language recommended to be used by firebase. This language is a superset of JavaScript and made improvements in areas where JavaScript was lacking.  The main benefits of this language are that it allows for object oriented programming which helps create lighter code on the server end and has better error detection capabilities when writing code which I felt would be beneficial to me during this project. 
 
Firebase Real Time Database I choose Firebase as my database of choice for this project as it is a backend platform that has a specific purpose of making backend mobile services as simple and as intuitive as possible. As both Firebase and Android are currently owned by Google I knew that the integration between the two would be excellent and provide a better application development environment. 
 

Firebase Functions Choosing Firebase functions to compute the back end for the applications was a simple choice for me to make. As its part of Firebase it has been built specifically for simplistic interactions with the database. I still double checked for other options for back end computation and quickly realised that it would much more work intensive and complicated than Firebase and provide no additional benefits to this project.  
 
Android Studio Android Studio is the official development environment for Android applications. This software has been specifically built for the purpose of application development and provides many beneficial features combined with a simple to understand user interface. The built in Android Emulator would allow me the abilities to test my code changes in real time and use the virtual sensors to simulate movement of the phone.  
 
Visual Studio Code For writing Typescript there were various other code editors that would’ve also been suitable for this task. The reason I choose Visual Studio Code was because it seems to be quite a popular code editor and I thought now would be a good time to try it out and become familiar with it. This IDE also will provide helpful code assistance to developers which just another reason for me to choose this over a code editor like notepad++. 
 
 
 
 

3.2       Techniques 
 
Introduction For basic Android Development there is plenty of resources and tutorials available online to teach and explain how concepts such how to use a simple recycler view, how to perform a task based on a button selection or how to initiate sensors and there basic functionality provided. However for this project that I wanted to create there were practically no examples of Bump technology for me to refer to and many examples of similar technologies had different implementations that I wasn’t interested in using or just used Bluetooth/NFC to transfer data which wasn’t something I wanted to implement. So I was required to do my own research and use my own judgement to assess what I thought would be required to complete this project. 
 
Uploading to the Firebase database As a part of the Bump technology gimmick data must be uploaded to a database as opposed to NFC or Bluetooth. This is because for NFC to validate the data transmission the phones must be in contact with each other for a period of time and with Bluetooth it is known to have very low bandwidth properties and its reliability has seemed to be quite inconsistent over the years.  
 
Using the Firebase database allowed me to upload the input user data in real time and make any changes in real time. This also proved to be a useful resource as I could store other information about the device that I can access on the backend later e.g. latitude, longitude, android id etc.   
 
Accelerometers and GPS Location Using these sensors aboard the phone I was able to detect when a bump had taken place. This was done by using the x co-ordinate on the phones accelerometer to detect acceleration in a given direction along the x-axis, then checking for a negative acceleration speed which implies deceleration so we know the bump has happened and then acceleration along the x-axis in the opposite direction. The Idea behind these exact 

movements was that I wanted to try and follow the physical movements that take place when a fist bump happens.  
 
Once this has happened the server will be contacted to say that a bump has taken place and it will check the latitude and longitude of the devices using the GPS to check if they were in close enough proximity to each other for this to happen. If it was determined that they were close enough the two phones will receive each other’s social media data based on their unique identification numbers. 
 
 
 
Retrieving information from Firebase database Retrieving the user data from the database is done after the bump has been detected. The method will then contact the function with the devices Id where it will hold this object and wait until it’s contacted with other devices Id. Using the two unique Ids the function will look into their databases and retrieve the stored latitude and longitude of each device. Using the latitude and longitude from each device the distance between them is then calculated and if they are within an accepted range the social media’s and usernames from each devices database will be transferred to the others database and stored under a new node. The data is then retrieved by a method that’s watching for the addition of the required data and when found will retrieve it and then display it on the users devices screen. 
 
 
 
# Chapter 4. System Design and Specifications 
 
Introduction Here I will discuss the functionality and architecture this project was developed upon. The specific tasks carried out by the code will be discussed in chapter 5. 
 
General Process These are the core functions of the application: Data Input and Uploading to Database Bump Detection  Server Function Logic Data Retrieval and Storage Application Design 
 
The first step was to create a Firebase project that is then directly linked to your android project. This will allow your project to have access to all of the powerful resources and capabilities that Firebase provides. The next step involved in getting Firebase fully operational and running correctly was to import all of the required dependencies to gradle because in Android studio this is what provides us with the functionality our application requires.  In Android dependencies allow us to include external libraries, library modules or jar files into our project. The benefit of importing these dependencies is that it saves us the time of writing code ourselves to perform certain tasks and  allows us full use of these already created code by writing references to it. With Firebase, certain dependencies were required to allow our project to interact with Firebase services correctly.  
 
Data input and uploading to database The first step is for a user to add a social media account to the app. This is done by selection the “add” button. The user will then be presented with an alert box that allows them to select one of the 3 different social media accounts. Once selected, another box will then appear and prompt the user to enter there username for that corresponding account. Once these two types of information have been entered the windows will close 

and present this information on the home page using an Android SQLite database to locally store the information and a recycler view to display this to the screen. Meanwhile on the backend of the app the two information entries are being managed in a different way so that they can then be correctly uploaded to the database and stored under the correct parents. 
 
The reason two different databases are used instead of just pulling the information from the firebase database is because SQLite database provides means of storing information locally on the device in a SQL format. This database made the application feel less clunky as the information could be quickly pulled from the systems memory and the user is still able to view there stored information without requiring an internet connection. The purpose of Firebases database is to store user information that can be easily accessed by the function when carrying out a data transfer. [1] 
 
 
Figure 1: Storing Information 
 
During this process one of the main difficulties was figuring out a method to ensure that once a social media type was selected that the correct corresponding username would then also be uploaded to firebase when the second window closes. Initially I thought that the best way to carry out these different tasks based on different buttons would be to implement a custom onClickListener. After speaking with my mentor and discussing this 

issue he told me that the method I was trying to use was much too complex and work intensive for a relatively basic function and instead I should try a simpler technique to track the buttons.   
 
So after many different tried and failed techniques to track and manage the input data the one that operated the best was quite simple. The social media type button was used to track selections by changing the selected buttons Boolean value to true and turning the other buttons Boolean values to false. From here once the “Next” button is selected it will check the values of all the buttons using an if statement to determine which one was selected and then call the correct method that will know the correct social media type and its associated username.  
 
The next step is to upload this information is to database in a way that is easy to identify and manage. As it was my first time managing a database that would be used in a relatively uncontrolled environment there were some definite learning curves to overcome and a trail of mistakes made along the way. The most important part for managing this data was implementing a primary key that could uniquely identify each user’s device and then store all of their information within that. As I had only realised this later in this projects life cycle than I should have I choose not to implement Firebases Authentication system for users as I thought that option would be too time consuming to implement for something as basic as a unique id and not that important of a feature to my project to spend time focusing on. The method to identify users that I went with was using the unique ID that android provides with every one of its devices and assigning that string to be the parent under which all the users data would then be stored as a child. 
 
I felt that this was a good alternative choice as the android id is a 64 bit value that’s generated once when the device is first booted. This number will retain its static value for the lifetime of the device. The only possible way to possibly change this value is by factory resetting the device, which is fine as this is typically done when a device is being sold or the user wants everything completely removed. 
 

 
Bump Detection For transferring data with a bump I used the devices GPS to retrieve the current latitude and longitude and the accelerometer sensor to detect changed in movement. As there were almost no similar bodies of work for me to reference this managed to be more challenging than anticipated. The first step to detect a bump is to detect a change in acceleration above a certain threshold on the x-axis. Then once this check passes the second check will look for a negative acceleration value and if this check also passes the third check will look for acceleration again. 
 
To explain the reasoning behind this I will further break down these methodologies. The reason I am mainly concerned about acceleration on the x-axis is because this will be the main axis in uses as the devices travel vertically towards each other. Theoretically it could be possible to use a 3D model to track every axis and create an extremely accurate form of bump validation but this is a technique that I hadn’t come across in my research and would much to complex and time consuming for me to develop on my own with the uncertainty of if it’s even plausible. (During my project presentation an examiner mentioned to me that there is a formula that exists that could be implemented to track movement in 3D. It’s called the Euclidean distance formula but unfortunately this discovery was too late for me to implement as this would have been an excellent addition to my project.) 
 
The first acceleration check on the x-axis is to check for the initial movement the users arm accelerating. The second check looks for a negative acceleration speed because this indicates that the phones have decelerated due to the bump taking place. Originally the idea was to check for when the acceleration was equal to 0 because at some point in the bump taking place this will be true. In reality though with the accelerometer provided with most androids this method wasn’t feasible because even with the accelerometer checking at the data at the highest speed it still wasn’t enough to consistently register a read if 0. This is because the phone will only stay at that acceleration speed for such a short time that is was often missed by the accelerometer. So the next method was the one 

I used which checks for deceleration which essentially operates in the same manner as checking for 0. The third check for acceleration on the other direction was put in place for the movement after the bump when the users hands and retracting backwards. This was mainly included as another form of checking to see if the bump has completed. Once the bump has been successfully detected a method will then contact the firebase function with the devices unique Id.  
 
Server Function Logic When contacting the firebase function there were logic steps that I was required to tackle to get the application running correctly. Firstly, when the bump occurs how will the function be able to know what device wants to transmit data and which two devices should be paired together. The solution I came up with to solve this was use a JSON object containing the devices Id under a name that the function could search for. This was made possible by implementing the OkHttpClient dependency which provided me with the functionality required to achieve this. It allowed me to enter the https server address that I wanted to interact with and then select what body of information I wanted to send to it. In this case it was the device ID. Once received by the server function if it was able to find the desired ID under the specific header it would then create a new node in the database called transfers and store the device Id there until it was contacted again with the other devices Id. 
 
Once the second Id is received the function will compare it to the first device Id that it stored in the database, if the two values are found to be equal then the function will end there as there would be no point in transferring the data as the user already has the ability to view their data on their device. One of the benefits of this check is that it will preemptively stop any user from bumping there device by themselves and creating an unnecessarily large workload for the server to deal with. At the current tiny scale I’m working on this is a non-issue and the servers are more than capable of dealing with it but at a large scale this could create a lot of issues on the backend. 
 

This value is stored by the function in the database table by creating a new node called transfers and storing the devices Id as device1. Then when the function is contacted with the second Id from the other device that was bumped against it will reference the first Id and compare them to make sure there not the same. Once it has determined that it was two different devices the process will continue by then checking the distance between the two devices. This is done by retrieving each device’s latitude and longitude from there database which was stored when they clicked the Bump button.  
 
To work out the distance between two devices using latitude and longitude I used the Haversine formula. This formula uses the great circles of the latitude and longitude as mentioned earlier and will calculate the distance between the two points using a straight line to find the distance. The reason I choose to implement checking the distance as the crow flies (straight line distance) over using google maps api to find the distance between the points is because when google maps is calculating the distance it wants to find the shortest distance by using roads which wouldn’t be the straight line distance I was looking for. The distances I am working with are too short for google maps api to provide me the accurate results I need as it is more suited to road distance and travel time calculations. The benefit of just acquiring the latitude and longitude and then applying Haversine formula is that it’s a better suited method of getting the exact distance when the distanced is short, such as two people standing next to each other with the devices. From here this then allows me to work out there linear distance and then determine if it’s plausible or not for a bump to have occurred.  
 
Once the distance has been determined the function will take this value and check if it’s greater than or less than the accepted distance to transfer data. If the value is outside of the accepted range the function will close and print to the log that the distance was outside of the accepted range. If the distance is found to be within the accepted range the function will then move to the transferring data stage. The accepted range is a hardcoded value used to state what distance would be too far for a bump to occur. During this projects development this value was at 20 meters mainly for reliable testing purposes and because at even shorter distances many variables such as weather and walls can end up 

affecting the applications functionality. As already stated this implementation will work well with a small user base.  Data Retrieval and Storage During the data transfer the function looks at the other devices database and scans it for the required information. When the scan is complete what information that has been found will then be pushed into the devices database under the node name data-transfer.  This process is carried out for both devices and on both databases.  
 
After the information has been transferred between databases it is then able to be viewed on each user’s device. This is done by setting a database reference to the “received-data” node stored with the parent node of the users unique Id. Using the reference if a change is detected a data snapshot will be taken of data-received and all of its corresponding information stored within. To sift through this and ignore any unwanted information we use a for loop and store only the data we want to display to the user. Once completed, the user will then be presented with a list of all of the current information from the datatransfer node. This will include information from past data transfers and new data transfers.  
 
Application Design For the design of this application I went with a minimalistic approach. Minimalistic designs have become quite popular recently due to their lack of clutter and intuitive layouts. The benefit of this is that it doesn’t over whelm the user when they first are first interacting with the application which makes it less likely for them to be put off the application or just saying it’s too complicated.  
 
I choose to put all of the buttons on the right hand side of the screen because the vast majority of people are right handed and this allows them to easily and mindlessly select what they want. Being right handed I personally love when apps have this same design because with the ever changing size of phones on some devices its quite hard to reach all the way over to the left side and especially the top left menu. The only bump that isn’t on the right side is the Bump button. Instead this button is centred at the bottom of the screen 

and will stand out more to the user. This was done to signify its importance seeing as the entire application was modelled around what happens after pressing this button. 
 
For colours I went with a light coloured theme and complementing colours that pop out to the user to signify there interaction and provide them with some feedback with what they have done.  Before I added any colour I noticed how the app felt substantially worse and didn’t feel as welcoming. To resolve this I used triadic and tetradic colours that would complement my Bump blue which is the main colour of this app. When the users selecting a social media type to add upon selecting the button the colour will change to let them know that there selection has been noticed and now they can progress. For the bump button when selected it will change from blue to a powerful pink to inform the user that somethings happening and once it’s completed it will turn back to blue. The view transactions activity background was made a darker shade of grey than what’s used on the main page again to provide the user with some feedback that something has happened and potentially reduce any confusion when looking at the information if they both had the same background colour.   
 
 
 

# Chapter 5. Implementation 
 
Introduction Here I will further expand upon the concepts that were mentioned in the earlier chapter.  
 
MainActivity This is the main class where the majority of the functionality happens. This is where most of the user interaction and design layout. The first thing that will happen here is the app will ask the user for all of the appropriate permissions required for the app to run correctly.   The first action from the user should do is select the “add “social media button from the menu. From here then the alertDialogBox(); method will be called and inflate a new XML to display a menu to the user with 3 different social media options. Behind each social media button is a corresponding onClickListener for that social media type that will change the selected social media button Boolean value to true and all other buttons Boolean values to false.  
 
Once the Next button is selected the type of social media account to be displayed and uploaded to the database is saved based upon which one had the Boolean value that equalled true. If no social media types were selected but the Next button is selected anyway a toast message will display notifying the user that they haven’t made a selection. 
 
Next the user will be prompted to enter the corresponding username for this account with a new alertDialogBox displaying a new XML. This alert box will contain an EditText widget. 
 
Upon selecting the Next button again the onClickListener will check to ensure that this EditText box is not empty and if this is found to be true then the entered username and selected media type will both be uploaded to the firebase database and stored to dataBaseHelper() which is an SQLite class that will allow us to also display this to the user using a RecyclerView. 
 

Once information has been entered this will call the loadDBData() method. Here the data in the SQLite database is checked and if there are more than zero items in it the ItemsViewAdapter will be contacted for its recycler view and this will display all of the information to the user in a listed format. This information listed has buttons to allow for modification which will be further discussed in the ItemsViewAdapter section. 
 
Now that there has been information entered the user can now select the Bump button. Doing this will notify the onClickListerner which will then instantiate the both the accelerometer and GPS. The buttons colour and text displayed will change to provide the user with feedback and the Boolean value of the bump button tracker will be changed so that the program can check which state it should be in. Then the following will occur as stated below. 
 
AccSensor (Now a part of the MainActivity) This class is where the Accelerometer sensor is managed. This gathers all of the accelerometer data needed to detect bumps and gets the exact latitude and longitude of the device at that moment.  (The accelerometer and GPS previously were contained in their own class. Due to later changes in the project I ran into bugs and problems implementing the functionality I required. As a response to this I decided to merge this class with the MainActivity because it was a less complex and more time saving option while still providing all of the original functionality and everything else that I needed. ) 
 
The first task is to check if the device has an accelerometer and if this is found to be true the accelerometer is initialised and the sensor is set to the fastest reading setting to provide the most accurate data readings. If no sensor is found then display a message to the user saying there device is incompatible with this app as there is no accelerometer and this is a mandatory feature required for the app to function. Then I check if the user has allowed the necessary GPS permissions that are required to get the latitude and longitude as these co-ordinates are also a necessity for the app to function correctly. If GPS permissions have been allowed then we can use the method 

enablelocation() to provide us with GPS updates every 200ms. This will then allow us to send the devices latitude and longitude at that moment to the Firebase database for later calculations. This method has the capability of updating the value every time there’s any change in latitude or longitude. For testing, development and small user base purposes this has been changed to only update every 20 meter change in value. This is because 20 meters is the distance accepted by the function to transfer and at smaller distances the GPS struggles to have 100% accuracy and variables such as clouds, wind, roofs and walls all affect this. 
 
Now in the detectBump() method it is constantly checking the data being retrieved by the accelerometer by being called every time the sensor updates and checking to see if a certain amount of acceleration has been detected along the x-axis if found to be true it will the proceed to the next part of the method to check for deceleration which would imply the bump has taken place and if also found to be true it will look for another increase in acceleration in the opposite direction as the user should be retracting the device backwards. After all of this has completed the firebaseFunction() method is then called.  
 
FirebaseFunction() operates by taking the devices unique deviceId and putting it into a JSON object with the name “deviceId”. We convert this information into a JSON object because this is the file format that is compatible with firebase functions. This JSON object is then sent to the function by stating its http address (URL) and posting the JSON information in its body. The function will then receive this and read through the JSON object and then storing the deviceId. The onResponse() and onFailure() methods are used to see if the server has a response to the posted JSON and to check if the JSON successfully sent. As I require no important data to be returned by the function these two methods were mainly used for testing as I was developing this function as they provided me with insight to what was successful and what failed and made the debugging process much easier. 
 
 

DataBaseHelper This class is responsible for saving all user input data to a SQLite database so that it can then be displayed back to the users main screen and allow them to modify the information with the buttons provided.  
 
This works by creating columns for every piece of information that is required for this application to function. Under each column name then is where the input user information will get stored and modified as required. 
 
The addItems() method is responsible for inserting the correct user information to the SQLite database as required. This is carried out by taking the recently entered information and then inserting it into the SQLite database under the suitable column. 
 
The getAllItems() method is what gets all items from the SQLite database when called so that they can then all be displayed to the user. This works by creating an array and using an SQL statement to select all columns from the table. Then using a cursor the information is looped through and stored with its column index. Once the loop completes the information is added into the array for later use.  
 
The updateItem() method operates by overwriting the previous username by taking the ID of the previous value and then over writing it with the new value. 
 
The deleteItem() method is what’s called when the user decides to remove information that they have previously input. This works by deleting the table based upon the Key_ID which will remove it from the SQLite database and stop it from being displayed through the recycler view. 
 
 
 
ItemViewAdapter This class is responsible for dealing with the user data that’s being displayed on the home screen. It interacts with the DataBaseHelper class a lot and sends commands input by the 

user for what should be done to data in the SQLite database. It also uses a RecyclerView to then display the user’s information on the home screen in a listed format. 
 
The RecyclerView extends the ViewHolder from main and updates the information as required. It also initialises a reference to the firebase database and implements the dataBaseHelper so that it can modify information on both of these databases as required. 
 
There are two options on the right side of the user’s information, an edit button and a delete button.  For each button there is a different setOnClickListener that will each allow the user to carry out the stated task on their information. 
 
For the edit button there is an edit.setOnClickListener that will open the alertDialogBox() and prompt the user again with the option to select the social media type and its corresponding username.  This process for selecting social media accounts and then entering the username is the same as what was discussed earlier.  
 
When opening the alertDialogBox() it will also pass the adapterPosition for the social media type and username to ensure the correct set on information is modified. For the SQLite database whatever social media type is selected will overwrite the previous social media type and replace it. Then the newly entered username will also overwrite and replace the old one. For the firebase database the old values are simply replaced by updating the old information to the newly entered information. 
 
The delete button has a delete.setOnClickListener that will tell the dataBaseHelper the adapterPosition of the social media type and username to remove it from the SQLite database.  Then it will call the deleteData() method also using the adapterPositions. Here a data snapshot will be taken of the database using the database reference. A for loop will then look until it finds data in the firebase database that matches the data that the user requested to delete and remove it from the database.  

 
There was no option implemented for deleting or editing just the social media type because this would be an unnecessary feature and just increase the user’s potential of messing up the way the information is stored and managed on both the device and in the database. 
 
TransferData (Firebase Function) This is the backend firebase function that is responsible for transferring device data between there databases. When this function is initially contacted with a JSON object it will check the object for a deviceId. If it is found the function will create a new node in the database called transfers and store the deviceId there so it can be compared to the other deviceId when it comes in. If no deviceId is found within the JSON body the function will simply stop there. 
 
Once the second deviceId arrives the function will compare it to the first deviceId to make sure that there different from each other. If they are found to be the same E.g. because somebody is trying to bump by themselves (not against another device) then there would be no reason to transfer the data and the function will end. 
 
After both deviceIds have been determined to be unique from each other the function will take a data snapshot of both devices databases and retrieve each devices latitude and longitude. The distance can be calculated from the latitudes and longitudes by implementing the Haversine formula.  
 
The function will then refer to inProximity to check if the devices are within an accepted distance. If inProximity is found to be false the function will delete the node called transfers and stop there. If inProximity is found to be true then the function will proceed and use a data snapshot to push the relevant data to the to the other devices database under the data-received node.  
 

Once all of this has happened this means that the function has successfully transferred the information and then will delete the transfers node so that it can get ready to process other incoming data transfers. 
 
ReceivedData This is the class in the application that responsible for displaying the transferred data on the user’s device. This is done by instantiating the RecyclerView.LayoutManager and using a database reference to point to the data-received node.  
 
Now the loadTransferData() method is called and it uses a database reference addValueEventListener to check for any changes in the data-received node. 
 
When a change is detected the onDataChange() method takes a data snapshot of the database reference and returns an object representation of the information currently being stored in there.  
 
To retrieve the information I am interested in I then use a for loop on the data snapshot to look through the data snapshot children. With every loop iteration the snapshot objects are compared to the objects in the Items.class.  If a relevant object is found from the snapshot it is added to the dataArrayList.  
 
Once the loop completes if 0 objects have been added then nothing will be displayed because the required information wasn’t found.  If 1 or more objects have been added to the dataArrayList the ItemsViewAdapter is contacted to create a new setAdapter for the retrieved objects. The information will then be made visible to the user. 
 
Items and Data These classes contain the setter and getter methods that are needed for us to manage our variables. This is a tactic for us to manage our data while retaining encapsulation. The getter method will check the variable and see ifs been initialised or not. The setter method will check the variables value before setting it. This is good practice to use because otherwise we can run into the problem where a method is interacting with a variable and then if the variable is not as expected the application can crash.  
 
 
 

# Chapter 6. Testing and Evaluation 
 
Introduction Being such a unique project I didn’t have any large bodies of work to refer to which paved a way for me to move forward with architectural and logical designs. While developing this application I was required to carry out extensive testing and research to constantly make sure that my design choices were correct and capable of allowing me move forward to the next step in my project.  
 
Managing User Input Data In the early stages of this project I was unsure of what would be the best way to store information in the firebase so that it could be easily managed and retrieved or how to set my RecyclerView so that it wouldn’t lose track of the information that was to be displayed. Initially I attempted to make it so that the user could enter many different social media accounts and usernames and store them all in Firebase and SQLite databases and then display to the user with the recycler view. This took me quite a long time to get just right, but the same problems with managing user input data kept arising as I continued with the project. 
 
As I made changes to the applications functionality, such as using a GPS to get the devices latitude and longitude and then store it to firebase it would always result in my application getting bug that would break the recylerview, mess with firebase or just cause the application to stop working.  
 
Eventually I came to the conclusion that I was trying to deal with something that was too sophisticated for me this early on in my project. So I decided to change my approach from trying to design perfect functionality every step of the way to building a project that works with very minimal functionality and then expanding upon it from there.  
 
This new approach helped me substantially as I wasn’t as fixated on small bugs and I was more focused on getting the project fully functional on a basic level. This also helped me to better understand all of the technology that was involved so that when it came time to begin increasing the projects functionality I was equipped with better knowledge of what needed to be implemented for me to achieve the desired goal. 
 
 
Data Transfer Function When trying to figure out how to get the android app to correctly interact with the firebase function I set up a lot of different error statuses to display what parts would execute successfully and what parts would fail.  
 
Another helpful tool for this stage was the function testing tool that Google functions provides. This would allow me to input test trigger events and then display the output beneath. This was a nice tool to work with because it let me know my function was implemented correctly before I moved onto the next stage which was sending a JSON object from android to the functions http address. 
 
When trying to figure out how to POST the JSON object from the android application to the functions http address the Google function testing tools were also beneficial to this part in the same way mentioned above and so was the onResponse() method. This method allowed me to set up tests so that I could retrieve what the POST to the function looked like and if it had been assembled correctly. If it returned saying “no device Id received” then I would know that something was wrong with the way the JSON object was being sent and when it would respond saying that it successfully received device xxxx I would then know my method was working correctly because it correctly displayed the deviceId of the device sending the JSON.  
 
Accelerometer Originally in the early stages when I had first implemented use of the devices accelerometer I put it in its own class as a new activity with a test XML that would display values such as the current acceleration on the x-axis, the max acceleration on the x-axis and the max deceleration on the x-axis. All of the changes in value on the accelerometer would then also print to the console log. 

 
Tracking all of this information helped me to understand why my first method to detect a bump wasn’t working. Initially for step 2 after acceleration had been detected I tried to detect an acceleration of 0.0 because I had thought that technically this should have executed correctly but I noticed that through all the data reading I was getting there was almost never a 0.0. Upon further research I realised that in physics the stopping of acceleration is not equal to zero but instead creates deceleration which is denoted with a minus value. This is why I couldn’t find 0.0 because if the phones not in a state of acceleration it’s in a state of deceleration and 0.0 values will only be displayed when the device is stationary for a period of time. 
 
Merging the Accelerometer into the MainActivity Later in the projects developments life cycle when the time came to start putting the pieces of the application together and removing there testing methods and xml I noticed I was having a problem with the accelerometer I had set up. My AccSensor.class was set up to be activated by the Bump button which would create and intent to swap activities to the AccSensor.class so I could safely test the accelerometer without interfering with my code in MainActivity.  
 
I made some changes and the AccSensor still wasn’t interacting with the MainActivity correctly to provide me with the functionality I needed. After changing the intent, XML and activity status of the class there was still issues. To fix these I had to instantiate a lot of the code used in in the AccSensor into the MainActivity as well. From reading about other applications that used android accelerometers online it seemed that the easiest and less messy solution was to just take the code from AccSensor and implement it into the MainActivity seeing as most of it would be required to be in there anyway.  
 
After this merge I was able to further develop upon the accelerometer as I intended without having to implement obscure work arounds or having to alter the applications functionality due to the ineffective implementation method I had used. Overall with this 

choice I definitely managed to save myself from many headaches and lots of unnecessary development time.  
 
 
# Chapter 7. Conclusions and Future Work 
 
Conclusions This project allowed me to experience what Android development is like and all of the various different technologies and methodologies that must be learnt and implemented to achieve your goal. This process proved to be much more work intensive and required me to overcome many more logical hurdles than I had anticipated.  
 
Entering this project I came in with a good understanding of Java as a programming language but very little experience in Android development. This technology turned out to be more interesting and thought provoking than expected. I was also pleasantly surprised by the powerful development tools that are available to all developers for free. From Android Studio and its amazing emulation tools to Firebase and the way they managed to make complicated and time consuming processes as easy to work with as possible.  
 
Future Work Overall I enjoyed the experience of developing an Android application for something that I was passionate about and feel could have a purpose in the real world. Just because the deadline for this project is quickly approaching doesn’t mean that I plan to stop working on it. Improvement’s I would have made if I had more time and in the coming future will be:   The ability to store more accounts and transfer multiple accounts simultaneously. Originally in the early development I had this feature but due to frequent changes mode to the structure and code this proved too complex to maintain at the time. Instead I opted to scale this back to one media account and username and then once the project was completed I could add this at the end. Unfortunately due to more prevalent problems this was push to the end of the list and didn’t manage to get resolved in time. 
 
A more accurate method to detecting Bumps When looking online for methodologies that other developers used to detect bumps between devices I was met with a shocking lack of results. No matter what I searched for 
 
I was always met with results for how to detect road bumps (which I looked at but wasn’t adequate for this task) and bump technology related to the metal industry. While presenting this project one of the professors had mentioned to me that the answer I was looking for that could track the devices bumping in 3d was with the Euclidian distance formula. Unfortunately this is something I had never come across in my research and is definitely something I plan on implementing. 
 
Improved function logic on the back end Currently the function is only suitable for a small user base. Currently the function operates under the expectation that the two devices will both contact it almost simultaneously because both devices will register the bump at the same time which means they will then post to the function simultaneously. To operate for a larger user base I will upgrade the logic in a way so that E.g. even if two groups of people bump there devices together but are 50km apart the potential error of the function accidently receiving the ID’s in a way that it will check the distance, see 50km and fail the transfer never happens. I think I could resolve this by taking the exact time the bump happens and also sending this to the server. Then this information will also be added to the receiveddevice node to help with tracking. Then even if the devices fail the distance check, instead of instantly deleting the node with the ID it can wait 1 second before deleting it so that it has time to check the other device. While a device is currently in the devicereceived node if they don’t match it can create a 2nd node for this where it will also stay alive for 1 second for deletion. This is my thinking at a high level for the steps that are required to further progress the application. 
 
 
Storing received data Currently the received data is just stored with a listed format which is adequate for a small user base but will need changes if there is to be a larger user base. Instead of displaying it in a list I think that a better design choice would be to have folders with the person’s name and then when clicked will display their account information and corresponding username. This would be a good improvement as it will increase the user’s 

organisation and allow them to view more information before they would be required to scroll.
