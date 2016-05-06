package com.example.ergasia.Activity;

/**
 * Created by simonthome on 02/04/16.
 */

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ergasia.R;

public class MessageFragmentPost extends Fragment {


     static MessageFragmentPost newInstance() {
        MessageFragmentPost fragment = new MessageFragmentPost();
        return fragment;

    }

    public MessageFragmentPost(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_message_post, container, false);



        return rootView;
    }
}