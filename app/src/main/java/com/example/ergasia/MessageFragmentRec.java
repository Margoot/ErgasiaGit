package com.example.ergasia;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MessageFragmentRec extends Fragment{

    public static MessageFragmentRec newInstance() {
            MessageFragmentRec fragment = new MessageFragmentRec();
            return fragment;
        }

    public MessageFragmentRec(){

        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_message_rec, container, false);
            return rootView;
        }

}
