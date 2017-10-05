package com.mshoaibluqman.flashchatnewfirebase;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

/**
 * Created by muhammadirfan on 04/10/2017.
 */

public class ChatListAdapter extends BaseAdapter{

    private Activity mActivity;
    private DatabaseReference mDatabaseReference;
    private String mDisplyName;
    private ArrayList<DataSnapshot> mDataSnapshotsList;
    private ChildEventListener mEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            mDataSnapshotsList.add(dataSnapshot);
            notifyDataSetChanged();

        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };


    public ChatListAdapter(Activity activity, DatabaseReference databaseReference, String mDisplyName) {

        this.mActivity = activity;
        this.mDatabaseReference = databaseReference.child("Messages");
        mDatabaseReference.addChildEventListener(mEventListener);
        this.mDisplyName = mDisplyName;
        mDataSnapshotsList = new ArrayList<>();

    }

    static class ViewHolder{
        TextView auther;
        TextView body;
        LinearLayout.LayoutParams params;
    }

    @Override
    public int getCount() {

        return mDataSnapshotsList.size();
    }

    @Override
    public InstantMessage getItem(int position) {

        DataSnapshot snapshot = mDataSnapshotsList.get(position);

        return snapshot.getValue(InstantMessage.class);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){

            LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.chat_msg_row, parent, false);

            final ViewHolder holder = new ViewHolder();
            holder.auther = (TextView) convertView.findViewById(R.id.author);
            holder.body = (TextView) convertView.findViewById(R.id.message);
            holder.params = (LinearLayout.LayoutParams) holder.auther.getLayoutParams();
            convertView.setTag(holder);
        }

        final InstantMessage message = getItem(position);
        final  ViewHolder holder = (ViewHolder) convertView.getTag();

        //Change Appearence of  chatMessages
        boolean isMe = message.getmAuther().equals(mDisplyName);
        setChatRowAppearance(isMe, holder);

        String auther = message.getmAuther();
        holder.auther.setText(auther);

        String body = message.getmMessage();
        holder.body.setText(body);

        return convertView;
    }

    private void setChatRowAppearance(boolean isMe, ViewHolder holder){

        if (isMe){

            holder.params.gravity = Gravity.END;
            holder.auther.setTextColor(Color.GREEN);
            holder.body.setBackgroundResource(R.drawable.bubble2);

        }else {

            holder.params.gravity = Gravity.START;
            holder.auther.setTextColor(Color.BLUE);
            holder.body.setBackgroundResource(R.drawable.bubble1);
        }

        holder.auther.setLayoutParams(holder.params);
        holder.body.setLayoutParams(holder.params);
    }

    public void cleanUp(){

        mDatabaseReference.removeEventListener(mEventListener);
    }
}
