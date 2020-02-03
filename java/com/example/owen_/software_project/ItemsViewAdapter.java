package com.example.owen_.software_project;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;


public class ItemsViewAdapter extends RecyclerView.Adapter<ItemsViewAdapter.MyViewHolder> {

    private List<Items> myData;
    private Activity mContext;

    AlertDialog editDialogMedia;
    AlertDialog editDialogUsername;

    DataBaseHelper dataBaseHelper;
    private DatabaseReference mDatabasereference;
    boolean facebook = false;
    boolean whatsapp = false;
    boolean snapchat = false;

    public ItemsViewAdapter(Activity context, List<Items> mData) {
        this.myData = mData;
        this.mContext = context;
        mDatabasereference = FirebaseDatabase.getInstance().getReference("");
        dataBaseHelper = new DataBaseHelper(context); //create item DB
    }

    //Viewholder to keep items in place
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_items, parent, false);
        return new MyViewHolder(itemView);
    }

    //To hold mediaType and Username position
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.mediaType.setText(myData.get(position).getMediaType());
        holder.userName.setText(myData.get(position).getUserName());
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mediaType, userName;
        Button edit, delete;

        MyViewHolder(View view) {
            super(view);
            mediaType = (TextView) view.findViewById(R.id.rvTask);
            userName = (TextView) view.findViewById(R.id.rvUsers);
            edit = (Button) view.findViewById(R.id.rvEdit);
            delete = (Button) view.findViewById(R.id.rvDelete);

            //If clicked will get items adapter positions and open alertDialogBox
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialogBox(myData.get(getAdapterPosition()).getUserName(), myData.get(getAdapterPosition()));
                }
            });

            //if clicked will delete item from SQLite database and then update list
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteData(myData.get(getAdapterPosition()).getUserName());
                    dataBaseHelper.deleteItem(myData.get(getAdapterPosition()));
                    myData.remove(myData.get(getAdapterPosition()));
                    notifyDataSetChanged();
                }
            });
        }
    }

    //Get count of items
    @Override
    public int getItemCount() {
        return myData.size();
    }


    //Box that pops up when a user selects edit on an item.
    //This is the same box as whats presented when they enter a new item
    public void alertDialogBox(final String currentName, final Items items) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
        final LayoutInflater inflater = mContext.getLayoutInflater();
        View view = inflater.inflate(R.layout.select_account_type, null);  //change to select_account_type
        alertDialog.setView(view);

        editDialogMedia = alertDialog.create();

        //Instanciating XML layout
        Button btn_Cancel = ((Button) view.findViewById(R.id.btnCancel));
        final Button btn_Apply = ((Button) view.findViewById(R.id.btnSave));
        final Button btn_facebook = ((Button) view.findViewById(R.id.btn_fb));
        final Button btn_whatsapp = ((Button) view.findViewById(R.id.btn_whatsapp));
        final Button btn_snapchat = ((Button) view.findViewById(R.id.btn_sc));

        editDialogMedia.show();

        //If cancels selected close the edit item dialog box
        btn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editDialogMedia.cancel();
            }
        });

        //If Facebooks selected change colour and update boolean value to true
        btn_facebook.setOnClickListener(new View.OnClickListener() {
            @Override //force user to enter username first to resolve
            public void onClick(View view) {

                btn_facebook.setBackgroundColor(mContext.getColor(R.color.orangeTetradic));
                btn_whatsapp.setBackgroundColor(mContext.getColor(R.color.lightGrey));
                btn_snapchat.setBackgroundColor(mContext.getColor(R.color.lightGrey));

                facebook = true;
                whatsapp = false;
                snapchat = false;
            }
        });

        //If Whatsapps selected change colour and update boolean value to true
        btn_whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_facebook.setBackgroundColor(mContext.getColor(R.color.lightGrey));
                btn_whatsapp.setBackgroundColor(mContext.getColor(R.color.orangeTetradic));
                btn_snapchat.setBackgroundColor(mContext.getColor(R.color.lightGrey));

                facebook = false;
                whatsapp = true;
                snapchat = false;
            }
        });

        //If Snapchats selected change colour and update boolean value to true
        btn_snapchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_facebook.setBackgroundColor(mContext.getColor(R.color.lightGrey));
                btn_whatsapp.setBackgroundColor(mContext.getColor(R.color.lightGrey));
                btn_snapchat.setBackgroundColor(mContext.getColor(R.color.orangeTetradic));

                facebook = false;
                whatsapp = false;
                snapchat = true;
            }
        });

        //After clicking next check for the selected button and the previous mediaType
        btn_Apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (facebook)
                    alertDialogBoxFB("Facebook", currentName, items);
                else if (whatsapp)
                    alertDialogBoxFB("Whatsapp", currentName, items);
                else if (snapchat)
                    alertDialogBoxFB("Snapchat", currentName, items);
                else
                    Toast.makeText(mContext, "Please select media type", Toast.LENGTH_SHORT).show();
                editDialogMedia.cancel();
            }
        });
    }


    //Dialog box that opens and presents user with interface to edit there username
    public void alertDialogBoxFB(final String mediatype, final String currentName, final Items items) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
        final LayoutInflater inflater = mContext.getLayoutInflater();
        View view = inflater.inflate(R.layout.enter_username_box, null);
        alertDialog.setView(view);

        editDialogUsername = alertDialog.create();

        final EditText txt_item = ((EditText) view.findViewById(R.id.txt_item));
        Button btn_dialogCancel = ((Button) view.findViewById(R.id.btnCancel));
        final Button btn_dialogApply = ((Button) view.findViewById(R.id.btnSave));

        editDialogUsername.show();

        //If cancel selected close dialog box and no changes are saved
        btn_dialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editDialogUsername.cancel();
            }
        });

        //If input is saved the new username and mediaType will overwrite the previous ones
        //Call to firebase will be made to overwrite old data
        btn_dialogApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!txt_item.getText().toString().isEmpty()) {
                    mDatabasereference.orderByChild("userName").equalTo(currentName)
                            .addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot child: dataSnapshot.getChildren()){
                                        child.getRef().child("userName").setValue(txt_item.getText().toString());
                                        child.getRef().child("mediaType").setValue(mediatype);
                                        Toast.makeText(mContext, "Changes saved", Toast.LENGTH_SHORT).show();
                                        items.setUserName(txt_item.getText().toString());
                                        items.setMediaType(mediatype);
                                        notifyDataSetChanged();
                                    }
                                }

                                //Notify user of error is there a database connection error
                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    Toast.makeText(mContext, "Network error", Toast.LENGTH_SHORT).show();
                                }
                            });
                    editDialogUsername.cancel();

                    //display pop up if user tries to proceed without entering there username
                } else
                    Toast.makeText(mContext,"Please enter username",Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Method to delete item from SQLite and Firebase database
    public void deleteData(String currentName) {
        mDatabasereference.orderByChild("username").equalTo(currentName)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot child: dataSnapshot.getChildren()){
                            Toast.makeText(mContext, "Deleted", Toast.LENGTH_SHORT).show();
                        }
                    }

                    //notify user if network error occurs
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(mContext, "Network error", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
