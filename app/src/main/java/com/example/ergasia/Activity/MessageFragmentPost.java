package com.example.ergasia.Activity;

/**
 * Created by simonthome on 02/04/16.
 */

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
        setHasOptionsMenu(true);



        return rootView;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_modify).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }







}