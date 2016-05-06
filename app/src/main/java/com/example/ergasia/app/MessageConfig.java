package com.example.ergasia.app;

/**
 * Created by simonthome on 05/05/16.
 */
public class MessageConfig {

    //flag to identify wether to show single line
    // or multi line text in push notification
    public static boolean appendNotificationMessages = true;

    //global topic to recieve app wide push notification
    public static final String TOPIC_GLOBAL = "global";

    //broadcast receiver intent filters
    public static final String SENT_TOKEN_TO_SERVER = "sentTokenToServer";
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String PUSH_NOTIFICATION = "pushNotification";

    // type of push messages
    public static final int PUSH_TYPE_CHATROOM = 1;
    public static final int PUSH_TYPE_USER = 2;

    // id to handle the notification in the notification try
    public static final int NOTIFICATION_ID = 100;
    public static final int NOTIFICATION_ID_BIG_IMAGE = 101;
}
