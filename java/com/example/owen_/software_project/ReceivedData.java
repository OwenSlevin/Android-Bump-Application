package com.example.owen_.software_project;

import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReceivedData extends AppCompatActivity {

    private RecyclerView rvItems;
    private ItemsViewAdapter itemsViewAdapter;
    private DatabaseReference mDatabasereference;
    private DatabaseReference mRef;
    List<Items> dataArrayList = new ArrayList<>();
    Button edit;

    TextView noRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_received_data);

        //To display on top of screen
        getSupportActionBar().setTitle("All Data");

        //Whats used to get androids unique device ID
        String android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        //reference to firebase database
        mDatabasereference = FirebaseDatabase.getInstance().getReference();

        //reference to firebase database that directs us to the node from where we will retrieve data
        mRef = mDatabasereference.child(android_id).child("data-received");

        //to be displayed if no items are available
        noRecord = (TextView) findViewById(R.id.noRecord);

        edit = (Button) findViewById(R.id.rvEdit);

        //Using recycler view to display items onto screen
        rvItems = (RecyclerView) findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvItems.setLayoutManager(layoutManager);
        loadTransferData(android_id);
    }

    //Method that displays transfered data by taking a data snapshot of database reference mRef
    //and then looping through data only retrieving items we are interested in as stated by the Items.class
    public void loadTransferData (String android_id) {
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Items items = child.getValue(Items.class);
                    dataArrayList.add(items);
                }

                //If more than 0 items in arrayList display onto screen
                if (dataArrayList.size()>0) {

                    Collections.reverse(dataArrayList);
                    itemsViewAdapter = new ItemsViewAdapter(ReceivedData.this,dataArrayList);
                    rvItems.setAdapter(itemsViewAdapter);
                    rvItems.setVisibility(View.VISIBLE);
                    noRecord.setVisibility(View.GONE);
                }
                //else display nothing
                else {
                    rvItems.setVisibility(View.GONE);
                    noRecord.setVisibility(View.VISIBLE);
                }
            }

            //Incase of error during data retrieval have toast pop up to notify user
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ReceivedData.this, "Network error", Toast.LENGTH_LONG).show();
                rvItems.setVisibility(View.GONE);
                noRecord.setVisibility(View.VISIBLE);
            }
        });
    }
}

