//Owen Slevin - 2873156

package com.example.owen_.software_project;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.PaintDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.provider.Settings.Secure;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.functions.FirebaseFunctions;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static android.os.Build.ID;
import static android.support.v7.widget.AppCompatDrawableManager.get;
import static java.security.AccessController.getContext;
import static javax.xml.transform.OutputKeys.MEDIA_TYPE;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private RecyclerView rvItems;
    private ItemsViewAdapter itemsViewAdapter;
    private TextView noRecord;
    DataBaseHelper dataBaseHelper;
    AlertDialog editDialogMedia;
    AlertDialog editDialogUsername;
    private static final int MY_PER_REQ = 7000;
    private static final String TAG = "MainActivity";

    Button btnBump;
    Button rvEdit;

    private SensorManager sensorManager;
    private Sensor accelerometer;

    private ListView mListView;
    private ArrayList<String> mUsernames = new ArrayList<>();

    private DatabaseReference mDatabasereference;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    boolean facebook = false;
    boolean whatsapp = false;
    boolean snapchat = false;
    final ArrayList<Data> dataArrayList = new ArrayList<>();
    List<Items> itemsArrayList = new ArrayList<>();
    String android_id;


    private boolean bumpCounter = true;
    private boolean updateLatLong = false;

    //private Location location;
    private LocationManager locationManager;
    private LocationListener locationListener;


    private FirebaseFunctions mFunctions;

    private float deltaX = 0;
    private float lastX;


    int movementTracker = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dataBaseHelper = new DataBaseHelper(this); //create item DB
        mDatabasereference = FirebaseDatabase.getInstance().getReference("");
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        //Pulls info from the xml

        noRecord = (TextView) findViewById(R.id.noRecord);

        btnBump = (Button) findViewById(R.id.btn_bump);

        rvEdit = (Button) findViewById(R.id.rvEdit);


        mFunctions = FirebaseFunctions.getInstance();

        android_id = Secure.getString(this.getContentResolver(),Secure.ANDROID_ID) ;


        final Button btn_facebook = ((Button) findViewById(R.id.btn_fb));
        final TextView fb_text = ((TextView) findViewById(R.id.add_fb));

        //Instantiating recycler view and loading items from SQLite DB
        rvItems = (RecyclerView) findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager =new LinearLayoutManager(this);
        rvItems.setLayoutManager(layoutManager);
        loadDBData();

        //Initialising sensor manager
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        //initialising firebase DB reference
        final DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //When Bump button is pressed, change boolean value that tracks state
        //Change color and text of button to notify user
        //Initialise Accelerometer and GPS location
        //Everytime the location changes(past a certain threshold as stated in enablelocation ) update the latitude and longitude values in firebase
        btnBump.setOnClickListener(new View.OnClickListener() {
            @NonNull
            @Override
            public void onClick(View view) {
                 if (bumpCounter == true) {
                    btnBump.setText("Click again to cancel");
                    btnBump.setBackgroundColor(Color.parseColor("#ff004d"));

                    sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
                    accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                    sensorManager.registerListener(MainActivity.this, accelerometer, SensorManager.SENSOR_DELAY_FASTEST);

                    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    locationListener = new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                        mDatabase.child(android_id).child("latitude").setValue(location.getLatitude());
                        mDatabase.child(android_id).child("longitude").setValue(location.getLongitude());
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }

                    @Override
                    public void onProviderEnabled(String provider) {

                    }

                    @Override
                    public void onProviderDisabled(String provider) { //check if enabled
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);

                    }
                };
                    //Check if Accelerometer and GPS have been granted required permissions
                    if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{
                            android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION,
                            android.Manifest.permission.INTERNET

                    }, 10);
                    return;
                }
                    else {
                        //call method
                         enablelocation();
                    }
                    bumpCounter = false;
                 }

                 //If Bump button state is changed turn off accelerometer and change buttons text and colour to notify user then change boolean value of state tracker
                 else {
                     if (bumpCounter == false ) {
                        sensorManager.unregisterListener(MainActivity.this);
                        btnBump.setText("Bump");
                        btnBump.setBackgroundColor(Color.parseColor("#0095ff"));
                    }
                    bumpCounter = true;
                }
        }
        });

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    //If there is changes in the accelerometer call method and print accelerometer values to console for troubleshooting and testing
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        int movementTracker = 0;
        detectBump();

        // get the change of X from accelerometer
        deltaX = Math.abs(lastX - sensorEvent.values[0]);

        Log.d(TAG, "onSensorChanged: "+ sensorEvent.values[0]);
    }

    //Method for activity life cycle. Called when app comes back into view
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(MainActivity.this, accelerometer, SensorManager.SENSOR_DELAY_FASTEST);
    }

    //Method for activity life cycle. Called when app goes out of view
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(MainActivity.this);
    }


    //Checks for permissions then calls method to enable GPS location
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 10:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    enablelocation();
                return;
        }
    }


    //GPS location manager updates every 200ms or change in location of 50 meters
    //Warning is being displayed even though its called after user has granted permissions (Seems to be a bug from what I've researched? )
    private void enablelocation() {

        locationManager.requestLocationUpdates("gps", 200, 50, locationListener); //warning but we already granted permissions
        //changed min distance to 50m instead of 0 to prevent update spam
    }

    //Method to detect Bump. Detected by monitoring accelerations speeds on X axis. If acceleration passes specified threshold
    //It will update counter and move to next check. Once all 3 checks are completed it is registered as a successful bump and calls the method
    public int detectBump (){
        if (deltaX > 3 && movementTracker == 0) {
            movementTracker = 1;
        }

        if (deltaX < 1 && movementTracker == 1) {
            movementTracker = 2;
        }

        if (deltaX > 3 && movementTracker == 2) {
            movementTracker = 3;
            bumpDetected();
        }

        if ( deltaX == 0 ) {  //to reset counter to 0 after bump is detected
            movementTracker = 0;
        }
        return movementTracker;

    }


    //When bump has been detected accelerometer is switched off and the state of the Bump button is changed
    //Calls method to notify Firebase function that a bump was detected
    public boolean bumpDetected () {
        final DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        bumpCounter = false;
        updateLatLong = true;
        sensorManager.unregisterListener(MainActivity.this);
        btnBump.setText("Bump");
        btnBump.setBackgroundColor(Color.parseColor("#0095ff"));
        firebaseFunction();
        Toast.makeText(MainActivity .this,"Bump Detected",Toast.LENGTH_SHORT).show();
        movementTrackerFix();
        return bumpCounter;

    }

    //Method to reset movement tracker after Bump has been detected
    public int movementTrackerFix () {
        movementTracker = 0;
        return movementTracker;
    }


    //To load items in SQLite database and present to screen
    public void loadDBData() {
        try {
            itemsArrayList = dataBaseHelper.getAllItems();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (itemsArrayList.size()>0) {
            Collections.reverse(dataArrayList);
            itemsViewAdapter = new ItemsViewAdapter(MainActivity.this, itemsArrayList);
            rvItems.setAdapter(itemsViewAdapter);
            rvItems.setVisibility(View.VISIBLE);
            noRecord.setVisibility(View.GONE);
        }else {
            rvItems.setVisibility(View.GONE);
            noRecord.setVisibility(View.VISIBLE);
        }
    }

    //To inflate menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //Checks to see if any items from menu have been selected
    //If selected corresponding method will be called, else do nothing
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            alertDialogBox();
            return true;
        }

        if (id == R.id.action_view_received) {
            dataReceived();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Dialog box presented to user when they select add item from the menu
    //Inflates new layout and initialises XML
    public void alertDialogBox() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        final LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.select_account_type, null);
        alertDialog.setView(view);

        editDialogMedia = alertDialog.create();


        Button btn_Cancel = ((Button) view.findViewById(R.id.btnCancel));
        final Button btn_Apply = ((Button) view.findViewById(R.id.btnSave));
        final Button btn_facebook = ((Button) view.findViewById(R.id.btn_fb));
        final Button btn_whatsapp = ((Button) view.findViewById(R.id.btn_whatsapp));
        final Button btn_snapchat = ((Button) view.findViewById(R.id.btn_sc));

        editDialogMedia.show();

        //Cancel button, if selected Dialog box will close
        btn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editDialogMedia.cancel();
            }
        });

        //If Facebooks selected change colour and update boolean value to true and all other button values to false
        btn_facebook.setOnClickListener(new View.OnClickListener() {
            @Override //force user to enter username first to resolve
            public void onClick(View view) {
                facebook = true;
                whatsapp = false;
                snapchat = false;

                btn_facebook.setBackgroundColor(getColor(R.color.orangeTetradic));
                btn_whatsapp.setBackgroundColor(getColor(R.color.lightGrey));
                btn_snapchat.setBackgroundColor(getColor(R.color.lightGrey));
            }
        });


        //If Facebooks selected change colour and update boolean value to true and all other button values to false
        btn_whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                facebook = false;
                whatsapp = true;
                snapchat = false;

                btn_facebook.setBackgroundColor(getColor(R.color.lightGrey));
                btn_whatsapp.setBackgroundColor(getColor(R.color.orangeTetradic));
                btn_snapchat.setBackgroundColor(getColor(R.color.lightGrey));
            }
        });


        //If Facebooks selected change colour and update boolean value to true and all other button values to false
        btn_snapchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                facebook = false;
                whatsapp = false;
                snapchat = true;

                btn_facebook.setBackgroundColor(getColor(R.color.lightGrey));
                btn_whatsapp.setBackgroundColor(getColor(R.color.lightGrey));
                btn_snapchat.setBackgroundColor(getColor(R.color.orangeTetradic));
            }
        });


        //When Next button is selected, check for the button with the boolean value thats true and call the method while passing this string value
        //If no selection was made user is notified with pop up that that they cant proceed until a selections made
        btn_Apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if (facebook) //change mediatype to store multiple types?
                        alertDialogBoxFB("Facebook");
                    else if (whatsapp)
                        alertDialogBoxFB("Whatsapp");
                    else if (snapchat)
                        alertDialogBoxFB("Snapchat");
                    else
                        Toast.makeText(MainActivity.this, "Please select media type", Toast.LENGTH_SHORT).show();
                    editDialogMedia.cancel();
            }
        });
    }

    //Dialog box that opens for user to enter there corresponding username for the account they just selected
    //Inflate new layouts and initialise new XML
    public void alertDialogBoxFB(final String mediatype) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        final LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.enter_username_box, null);
        alertDialog.setView(view);

        editDialogUsername = alertDialog.create();

        final EditText txt_item = ((EditText) view.findViewById(R.id.txt_item));
        Button btn_dialogCancel = ((Button) view.findViewById(R.id.btnCancel));
        final Button btn_dialogApply = ((Button) view.findViewById(R.id.btnSave));

        editDialogUsername.show();

        //Cancel, if selected Dialog box will close and no changes are saved
        btn_dialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editDialogUsername.cancel();
            }
        });

        //saves mediaType and entered username to firebase  and SQLite DB
        //Method is called to update whats displayed to user
        btn_dialogApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!txt_item.getText().toString().isEmpty()) {

                    myRef.child(android_id).child("mediaType").setValue(mediatype);
                    myRef.child(android_id).child("userName").setValue(txt_item.getText().toString());
                    editDialogUsername.cancel();

                    Items items = new Items();
                    items.setAndroidID(android_id);
                    items.setMediaType(mediatype);
                    items.setUserName(txt_item.getText().toString());
                    items.setLatitude(0.0);
                    items.setLongitude(0.0);
                    items.setRadius(0.0);

                    dataBaseHelper.addItem(items);
                    loadDBData();

                    //If no usernames entered user cannot proceed
                } else
                    Toast.makeText(MainActivity .this,"Please enter username",Toast.LENGTH_SHORT).show();
            }
        });
    }


    //Method to send functions https address a JSON object with the devices ID with a header name its expecting
    //
    public void firebaseFunction() {

        //Create a JSON object with a header title and device ID in the body (function will extract ID after reading header title)
        JSONObject postData = new JSONObject();
        String deviceId = Secure.getString(this.getContentResolver(),Secure.ANDROID_ID);
        try {
            postData.put("deviceId", deviceId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Setting the parameters for JSON and https
        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, postData.toString());

        //sending JSON object to stated https address of function
        final Request request = new Request.Builder()
                .url("https://us-central1-softwareproject-9a18c.cloudfunctions.net/transferDatav2")
                .post(body)
                .build();

        //instantiate OkHttpClient
        Call call = new OkHttpClient().newCall(request);
        call.enqueue(new Callback() {
            //Print to console if error occurs
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "Failed to get data: " + e.getMessage());
            }

            //Tests to display to console if function responds with anything
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful())
                    Log.e(TAG, "Response: " + response.body().string());
                    ResponseBody pushData = ResponseBody.create(JSON, "/deviceId/data-transfer");
                    String pushDataString = pushData.toString();
                    Log.e(TAG, "DATA Recieved: " + " PushDataString " + pushDataString + " RegPushData " + pushData + " ID? : " + "" );
            }
        });
    }

    //To start new intent to display information that has been recieved
    public void dataReceived() {
        Intent intent = new Intent(MainActivity.this, ReceivedData.class);
        startActivity(intent);
    }


   /* public void retrieveData(DatabaseReference mDatabase) {
        mListView = (ListView) findViewById(R.id.mListView);
        final ArrayAdapter<String>arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mUsernames );
        mListView.setAdapter(arrayAdapter);

        FirebaseListAdapter<String>firebaseListAdapter = new FirebaseListAdapter<String>(
                //list adapter to display retrieved items
                this, String.class, android.R.layout.simple_list_item_1, mDatabase) {
            @Override
            protected void populateView(View v, String model, int position) {
                TextView textView = (TextView) v.findViewById(android.R.id.text1);
                textView.setText(model);

            }
        };

        mListView.setAdapter(firebaseListAdapter);
    } */


        //Method to aquire permissions from user to give functionality required
        private void requestpermissions () {
            ActivityCompat.requestPermissions(this, new String[]
                    {
                            android.Manifest.permission.ACCESS_COARSE_LOCATION,
                            android.Manifest.permission.ACCESS_FINE_LOCATION,
                            android.Manifest.permission.INTERNET,
                            android.Manifest.permission.ACCESS_NETWORK_STATE,
                            android.Manifest.permission.VIBRATE,
                            android.Manifest.permission.ACCESS_WIFI_STATE,
                            android.Manifest.permission.READ_EXTERNAL_STORAGE,
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            android.Manifest.permission.READ_PHONE_STATE,
                    }, MY_PER_REQ);
        }
}

