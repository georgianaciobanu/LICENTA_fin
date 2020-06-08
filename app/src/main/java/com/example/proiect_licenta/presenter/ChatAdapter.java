package com.example.proiect_licenta.presenter;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.proiect_licenta.R;
import com.example.proiect_licenta.model.ChatMessage;
import com.example.proiect_licenta.model.ProductsItem;

import java.util.ArrayList;

    public class ChatAdapter extends ArrayAdapter<ChatMessage> {

        public ChatAdapter(Context context, ArrayList<ChatMessage> countryList) {
            super(context, 0, countryList);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            return initView(position, convertView, parent);
        }

        @Override
        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            return initView(position, convertView, parent);
        }

        private View initView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(
                        R.layout.message, parent, false
                );
            }

            TextView messageText = (TextView)convertView.findViewById(R.id.message_text);
            TextView messageUser = (TextView)convertView.findViewById(R.id.message_user);
            TextView messageTime = (TextView)convertView.findViewById(R.id.message_time);


            ChatMessage model = getItem(position);

            if (model != null) {
                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());

                // Format the date before showing it
                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                        model.getMessageTime()));

            }
            return convertView;
        }
    }