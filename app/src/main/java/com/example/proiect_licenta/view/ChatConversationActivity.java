package com.example.proiect_licenta.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.proiect_licenta.R;
import com.example.proiect_licenta.model.ChatMessage;
import com.example.proiect_licenta.model.OnGetDataListener;
import com.example.proiect_licenta.model.Review;
import com.example.proiect_licenta.presenter.FirebaseFunctions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

public class ChatConversationActivity extends AppCompatActivity {
    OnGetDataListener listenerMessages;
    FirebaseUser firebaseUser;
    ChatMessage chatMessage;
    ArrayList<String> chatConversation= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_conversation);
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();



        listenerMessages=new OnGetDataListener() {
            @Override
            public void onStartFirebaseRequest() {


            }

            @Override
            public void onSuccess(DataSnapshot data) {

                for(DataSnapshot singleSnapshot : data.getChildren()) {
                    chatMessage = singleSnapshot.getValue(ChatMessage.class);
                    if(chatMessage.getMessageFor().equals(firebaseUser.getEmail()) || chatMessage.getMessageUser().equals(firebaseUser.getEmail())) {
                        if(!chatConversation.contains(chatMessage.getMessageFor()) && (!chatMessage.getMessageFor().equals(firebaseUser.getEmail()))) {

                            chatConversation.add(chatMessage.getMessageFor());
                        }
                        else if(!chatConversation.contains(chatMessage.getMessageUser()) && (!chatMessage.getMessageUser().equals(firebaseUser.getEmail()))){
                            chatConversation.add(chatMessage.getMessageUser());
                        }
                    }

                }
                ListView chatsList = (ListView)findViewById(R.id.chatsListview);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, chatConversation);
                chatsList.setAdapter(adapter);
                chatsList.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.teacherActivGradientStart));


                chatsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        goToChat(chatConversation.get(i));
                    }
                });




            }

            @Override
            public void onFailed(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),databaseError.toString(),Toast.LENGTH_LONG).show();
            }
        };
        FirebaseFunctions.getMessagesFirebase(listenerMessages);



    }

    public void goToChat(String messageFor){
        Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
        intent.putExtra("messageFor",messageFor);
        startActivity(intent);
    }
}
