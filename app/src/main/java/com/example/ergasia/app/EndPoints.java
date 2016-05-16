package com.example.ergasia.app;

/**
 * Created by simonthome on 07/05/16.
 */
public class EndPoints {

    //localhost url
    public static final String BASE_URL = "http://192.168.0.13:8888/task_manager/v1";
    public static final String USER = BASE_URL + "/user/_ID_";
    public static final String CHAT_ROOMS = BASE_URL + "/chat_rooms";
    public static final String CHAT_THREAD = BASE_URL + "/chat_rooms/_ID_";
    public static final String CHAT_ROOM_MESSAGE = BASE_URL + "/chat_rooms/_ID_/message";
}
