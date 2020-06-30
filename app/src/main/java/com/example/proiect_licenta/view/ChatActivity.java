package com.example.proiect_licenta.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proiect_licenta.R;
import com.example.proiect_licenta.model.ChatMessage;
import com.example.proiect_licenta.model.OnGetDataListener;
import com.example.proiect_licenta.presenter.ChatAdapter;
import com.example.proiect_licenta.presenter.FirebaseFunctions;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class ChatActivity extends AppCompatActivity  {
    FloatingActionButton btnSend;
    FirebaseUser firebaseUser;
    ListView listOfMessages;
    //FirebaseListAdapter<ChatMessage> adapter;
    //FirebaseListOptions<ChatMessage> options;
    ArrayList<ChatMessage> chatList= new ArrayList<>();
    String messageFor;
    ChatAdapter adapter;
    OnGetDataListener listenerMessages;
    ChatMessage model= new ChatMessage();





    @Override
    protected void onStart() {
        super.onStart();
        chatList= new ArrayList<>();
       displayChatMessages();
    }

    @Override
    protected void onStop() {
        super.onStop();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        NotificationManager notificationmanager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        btnSend  = (FloatingActionButton)findViewById(R.id.fabChat);
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();

        Intent i=getIntent();
        messageFor=(String) i.getSerializableExtra("messageFor");


        listenerMessages=new OnGetDataListener() {
            @Override
            public void onStartFirebaseRequest() {


            }

            @Override
            public void onSuccess(DataSnapshot data) {
                chatList=new ArrayList<>();
                for(DataSnapshot singleSnapshot : data.getChildren()) {
                    model = singleSnapshot.getValue(ChatMessage.class);
                    if ((model.getMessageUser().equals(messageFor) && model.getMessageFor().equals(firebaseUser.getEmail())) || (model.getMessageUser().equals(firebaseUser.getEmail()) && model.getMessageFor().equals(messageFor))) {

                            chatList.add(model);
                        }

                    }
                 listOfMessages = (ListView)findViewById(R.id.list_of_messages);
                 adapter=new ChatAdapter(getApplicationContext(),chatList,firebaseUser.getEmail());
                 listOfMessages.setAdapter(adapter);

                }


            @Override
            public void onFailed(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),databaseError.toString(),Toast.LENGTH_LONG).show();
            }
        };



        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input = (EditText)findViewById(R.id.input);

                // Read the input field and push a new instance
                // of ChatMessage to the Firebase database


                FirebaseDatabase.getInstance()
                        .getReference("ChatMessage")
                        .push()
                        .setValue(new ChatMessage(input.getText().toString(),firebaseUser.getEmail(),messageFor)
                        );

                // Clear the input
                input.setText("");
                onStart();
            }
        });


        displayChatMessages();
    }

    public void displayChatMessages(){

        chatList=new ArrayList<>();
        FirebaseFunctions.getMessagesFirebase(listenerMessages);

//        options = new FirebaseListOptions.Builder<ChatMessage>()
//                .setQuery(FirebaseDatabase.getInstance().getReference("ChatMessage") , ChatMessage.class)
//                .setLayout(R.layout.message)
//                .build();



//        adapter = new FirebaseListAdapter<ChatMessage>(options) {
//
//
//            @Override
//            protected void populateView(@NonNull View v, @NonNull ChatMessage model, int position) {
//
//                if ((model.getMessageUser().equals(messageFor) && model.getMessageFor().equals(firebaseUser.getEmail())) || (model.getMessageFor().equals(messageFor) && model.getMessageUser().equals(firebaseUser.getEmail()))){
//                    // Get references to the views of message.xml
//                    TextView messageText = (TextView)v.findViewById(R.id.message_text);
//                    TextView messageUser = (TextView)v.findViewById(R.id.message_user);
//                    TextView messageTime = (TextView)v.findViewById(R.id.message_time);
//
//
//                    // Set their text
//
//                    messageText.setText(model.getMessageText());
//                    messageUser.setText(model.getMessageUser());
//
//                    // Format the date before showing it
//                    messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
//                            model.getMessageTime()));
//
//
//                }
//                else{
//
//                }
//
//            }
//            };






    }




}
