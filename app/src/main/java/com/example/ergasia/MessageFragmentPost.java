package com.example.ergasia;

/**
 * Created by simonthome on 02/04/16.
 */

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

public class MessageFragmentPost extends Fragment implements AdapterView.OnItemClickListener {

    private ListView listview;
    String[] container = new String[]{
            "salut", "aurevoir"
    };

     static MessageFragmentPost newInstance() {
        MessageFragmentPost fragment = new MessageFragmentPost();
        return fragment;

        //ListView.setOnItemClickListener(new ListClickHandler());
    }

    public MessageFragmentPost(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_message_post, container, false);




        //Edit

        listview = (ListView) rootView.findViewById(R.id.messageListViewPost);
        listview.setOnItemClickListener(this);
        return rootView;
    }




    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // Starting of a new activity by clicking on listview part
        Intent intent = new Intent();
        intent.setClass(getActivity(), Message_post_activity.class);
        intent.putExtra("position", position);
        intent.putExtra("id", id);
        startActivity(intent);
    }

}
