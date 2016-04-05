package com.example.ergasia;

/**
 * Created by simonthome on 02/04/16.
 */
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MessageFragmentPost extends Fragment {

    public static MessageFragmentPost newInstance() {
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