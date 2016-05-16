package com.example.ergasia.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.ergasia.Helper.SQLiteHandler;
import com.example.ergasia.Helper.SessionManager;
import com.example.ergasia.Helper.SimpleDividerItemDecoration;
import com.example.ergasia.R;
import com.example.ergasia.adapter.ChatRoomsAdapter;
import com.example.ergasia.app.AppController;
import com.example.ergasia.app.EndPoints;
import com.example.ergasia.app.MessageConfig;
import com.example.ergasia.gcm.GcmIntentService;
import com.example.ergasia.gcm.NotificationUtils;
import com.example.ergasia.model.ChatRoom;
import com.example.ergasia.model.Message;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MessageFragmentRec extends Fragment{

    private SessionManager session;
    private SQLiteHandler db;
    private String recruiterCompany;

    private String TAG = MessageFragmentRec.class.getSimpleName();
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    private ArrayList<ChatRoom> chatRoomArrayList;
    private ChatRoomsAdapter mAdapter;
    private RecyclerView recyclerView;
    private static String job;

    public static MessageFragmentRec newInstance() {
            MessageFragmentRec fragment = new MessageFragmentRec();
            return fragment;
        }

    public MessageFragmentRec(){

        }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getActivity().setContentView(R.layout.fragment_message_post);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_message_rec, container, false);

        setHasOptionsMenu(true);

        /**
         * Check if user is logged in
         */
       /* if (AppController.getInstance().getPrefManager().getUser() == null) {
            launchLoginActivity();
        }*/
        session = new SessionManager(getActivity().getApplicationContext());
        db = new SQLiteHandler(getActivity().getApplicationContext());

        Cursor cursor = db.getOfferDetails();
        cursor.moveToFirst();
        recruiterCompany = cursor.getString(cursor.getColumnIndex("company"));
        cursor.close();


        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        /**
         * Broadcast receiver calls in two scenarios
         * 1. gcm registration is completed
         * 2. when new push notification is received
         */
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //checking for type intent filter
                System.out.println("on reveice: MessageFragmentPost");
                if (intent.getAction().equals(MessageConfig.REGISTRATION_COMPLETE)) {
                    //gcm successfully registered
                    //now subscribe to `global`topic to receive app wide notifications

                    subscribeToGlobalTopic();
                    System.out.println("on receive: subscribe to global topic");



                } else if (intent.getAction().equals(MessageConfig.SENT_TOKEN_TO_SERVER)) {
                    //gcm registration id is stored in our server's MySQL
                    Log.e(TAG, "GCM registration id is sent to our server");


                } else if (intent.getAction().equals(MessageConfig.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    handlePushNotification(intent);
                    Toast.makeText(getActivity().getApplicationContext(), "Push notification is received!", Toast.LENGTH_LONG).show();

                }
            }
        };

        chatRoomArrayList = new ArrayList<>();
        mAdapter = new ChatRoomsAdapter(getActivity().getApplication(), chatRoomArrayList);
        System.out.println("constructor : " + chatRoomArrayList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(
                getActivity().getApplicationContext()
        ));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new ChatRoomsAdapter.RecyclerTouchListener(getActivity().getApplicationContext(), recyclerView, new ChatRoomsAdapter.ClickListener(){
            @Override
            public void onClick(View view, int position) {
                // when chat is clicked, launch full chat thread activity

                ChatRoom chatRoom = chatRoomArrayList.get(position);
                chatRoom.setUnreadCount(0);
                Intent intent = new Intent(getActivity()
                        , ChatRoomActivity.class);
                intent.putExtra("chat_rooms_id", chatRoom.getId());
                intent.putExtra("name", chatRoom.getCandidateName());
                mAdapter.notifyDataSetChanged();

                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        /**
         * Always check for google play services availability before
         * proceeding further with GCM
         */
        if (checkPlayServices()) {
            Log.e(TAG, "inside if");
            registerGCM();
            fetchChatRoom();
        }
            return rootView;

        }

    /**
     * Handles new push notification
     */
    private void handlePushNotification(Intent intent) {
        int type = intent.getIntExtra("type", -1);

        //if the push is of chat room message
        //simply update the UI unread messages count
        if (type == MessageConfig.PUSH_TYPE_CHATROOM) {
            Message message = (Message) intent.getSerializableExtra("message");
            String chatRoomId = intent.getStringExtra("chat_rooms_id");
            Log.e(TAG,"Updating message count UI");

            if (message != null && chatRoomId != null) {
                updateRow(chatRoomId, message);
                Log.e(TAG, "updating row");
            }
        } else if (type == MessageConfig.PUSH_TYPE_USER) {
            //push belongs to user alone
            // just showing the message in a toast
            Message message = (Message) intent.getSerializableExtra("message");
            Toast.makeText(getActivity().getApplicationContext(), "New push: " + message.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Updates the chat list unread count and the last message
     */
    private void updateRow(String chatRoomId, Message message) {
        for (ChatRoom cr : chatRoomArrayList) {
            if (cr.getId().equals(chatRoomId)) {
                int index = chatRoomArrayList.indexOf(cr);
                cr.setLastMessage(message.getMessage());
                cr.setUnreadCount(cr.getUnreadCount() + 1);
                chatRoomArrayList.remove(index);
                chatRoomArrayList.add(index, cr);
                Log.e(TAG, "index : " + index);

                break;

            }
        }
        mAdapter.notifyDataSetChanged();
    }

    /**
     *fetching the chat rooms by making http call
     */
    private void fetchChatRooms() {
        StringRequest strReq = new StringRequest(Request.Method.GET, EndPoints.CHAT_ROOMS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "response: " + response);

                try {
                    JSONObject obj = new JSONObject(response);

                    //check for error flag
                    if (obj.getBoolean("error") == false) {
                        JSONArray chatRoomsArray = obj.getJSONArray("chat_rooms");
                        for (int i = 0; i< chatRoomsArray.length(); i++) {
                            JSONObject chatRoomsObj = (JSONObject) chatRoomsArray.get(i);
                            ChatRoom cr = new ChatRoom();
                            cr.setId(chatRoomsObj.getString("chat_rooms_id"));
                            cr.setCandidateName(chatRoomsObj.getString("candidate_firstname"));
                            cr.setLastMessage("");
                            cr.setUnreadCount(0);
                            cr.setTimestamp(chatRoomsObj.getString("created_at"));
                            Log.e(TAG, "chatrooms : " + cr);

                            chatRoomArrayList.add(cr);

                        }
                        System.out.println("fetching chat room : " + chatRoomArrayList);
                    } else {
                        //error in fetching chat rooms
                        Toast.makeText(getActivity().getApplicationContext(), "" + obj.getJSONObject("error").getString("message"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "json parsing error: " + e.getMessage());
                    Toast.makeText(getActivity().getApplicationContext(), "Json parse error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

                mAdapter.notifyDataSetChanged();
                System.out.println("data change :" + chatRoomArrayList + " : " + chatRoomArrayList.size());
                //mAdapter.notifyItemInserted(chatRoomArrayList.size());

                //subscribing to all chat room topics
                subscribeToAllTopics();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                Log.e(TAG, "Volley error : " + error.getMessage() + ", code: " + networkResponse);
                Toast.makeText(getActivity().getApplicationContext(), "Volley error: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        //Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq);
    }

    private void fetchChatRoom () {
        StringRequest strReq = new StringRequest(Request.Method.POST, EndPoints.CHAT_ROOM, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "response: " + response);

                try {
                    JSONObject obj = new JSONObject(response);
                    boolean error = obj.getBoolean("error");

                    //check for error flag
                    if (!error) {
                        JSONObject chatRoomObj = obj.getJSONObject("chat_rooms");
                        ChatRoom cr = new ChatRoom();
                        cr.setId(chatRoomObj.getString("chat_rooms_id"));
                        cr.setCandidateName(chatRoomObj.getString("candidate_firstname"));
                        cr.setLastMessage("");
                        cr.setUnreadCount(0);
                        cr.setTimestamp(chatRoomObj.getString("chat_rooms_created_at"));
                        Log.e(TAG, "chatrooms : " + cr);

                        chatRoomArrayList.add(cr);
                        System.out.println("fetching chat room : " + chatRoomArrayList);
                    } else {
                        //error in fetching chat rooms
                        Toast.makeText(getActivity().getApplicationContext(), "" + obj.getJSONObject("error").getString("message"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "json parsing error: " + e.getMessage());
                    Toast.makeText(getActivity().getApplicationContext(), "Json parse error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

                mAdapter.notifyDataSetChanged();
                System.out.println("data change :" + chatRoomArrayList + " : " + chatRoomArrayList.size());
                //mAdapter.notifyItemInserted(chatRoomArrayList.size());

                //subscribing to all chat room topics
                subscribeToAllTopics();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                Log.e(TAG, "Volley error : " + error.getMessage() + ", code: " + networkResponse);
                Toast.makeText(getActivity().getApplicationContext(), "Volley error: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override

            protected Map<String, String> getParams() {
                //Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("post_param", recruiterCompany);
                System.out.println("post : " + recruiterCompany);


                return params;
            }
        };

        //Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq);
    }

    // subscribing to global topic
    private void subscribeToGlobalTopic() {
        Intent intent = new Intent(getActivity(), GcmIntentService.class);
        intent.putExtra(GcmIntentService.KEY, GcmIntentService.SUBSCRIBE);
        intent.putExtra(GcmIntentService.TOPIC, MessageConfig.TOPIC_GLOBAL);
        getActivity().startService(intent);
        Log.e(TAG, "SUBSCRIBING to global!");
    }

    //Subscribing to all chat room topics
    // each topic name starts with `topic_` followed by the ID of the chat room
    //Ex: topic_1, topic_2
    private void subscribeToAllTopics() {
        for (ChatRoom cr : chatRoomArrayList) {

            Intent intent = new Intent(getActivity(), GcmIntentService.class);
            intent.putExtra(GcmIntentService.KEY, GcmIntentService.SUBSCRIBE);
            intent.putExtra(GcmIntentService.TOPIC, "topic_" + cr.getId());
            getActivity().startService(intent);
            Log.e(TAG, "Subscribing to all");
        }
    }

    /*private void launchLoginActivity() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        getActivity().finish();
    }*/

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(getActivity());
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(getActivity(), resultCode, PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(TAG, "This device is not supported. Google Play Services not intalled!");
                Toast.makeText(getActivity().getApplicationContext(), "This device is not supported. Google Play Services not installed!", Toast.LENGTH_LONG).show();
                getActivity().finish();
            }
            return false;
        }
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();

        //register GCM registration complete receiver
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(MessageConfig.REGISTRATION_COMPLETE));
        Log.e(TAG, "gcm registering complete receiver");
        //register new push message receiver
        //by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(MessageConfig.PUSH_NOTIFICATION));
        Log.e(TAG, "gcm registering complete push receiver");

        //clearing notification tray
        NotificationUtils.clearNotifications();
    }

    @Override
    public void onPause() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    //starting the service to register with GCM
    private void registerGCM() {
        Intent intent = new Intent(getActivity(), GcmIntentService.class);
        intent.putExtra("key", "register");
        getActivity().startService(intent);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_modify).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }

}
