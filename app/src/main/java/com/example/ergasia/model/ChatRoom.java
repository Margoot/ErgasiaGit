package com.example.ergasia.model;

import java.io.Serializable;

/**
 * Created by simonthome on 07/05/16.
 */
public class ChatRoom implements Serializable {

    String id, candidateName, recruiterCompany, lastMessage, timestamp;
    int unreadCount;

    public ChatRoom () {

    }

    public ChatRoom(String id, String candidateName, String recruiterCompany, String lastMessage, String timestamp, int unreadCount) {
        this.id = id;
        this.candidateName = candidateName;
        this.recruiterCompany = recruiterCompany;
        this.lastMessage = lastMessage;
        this.timestamp = timestamp;
        this.unreadCount = unreadCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCandidateName() {
        return candidateName;
    }

    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }

    public String getRecruiterCompany() {return recruiterCompany;}

    public void setRecruiterCompany (String recruiterCompany) {this.recruiterCompany = recruiterCompany; }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public int getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(int unreadCount) {
        this.unreadCount = unreadCount;
        System.out.println("chaatRoom setUnread count : " + this.unreadCount);
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

}
