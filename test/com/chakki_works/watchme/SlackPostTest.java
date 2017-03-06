package com.chakki_works.watchme;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class SlackPostTest {

    private String token = "SLACK-API-KEY";
    private String id = "";
    private String senderName = "watchMe";
    private String senderIcon = "https://farm3.staticflickr.com/2291/33013444521_8e555dd637_o_d.png";
    private String channelName = "";

    /*
    @Test
    public void testPostMessage() {
        SlackChannel channel = new SlackChannel(this.token, this.id, this.senderName, this.senderIcon, this.channelName);
        SlackPost post = new SlackPost(channel);
        String message = "message string";
        String detail = "detail string";
        try {
            post.pushMessage(message, detail);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    @Test
    public void testPostMessageShortVer() {
        SlackChannel channel = new SlackChannel(this.token, this.id ,this.channelName);
        SlackPost post = new SlackPost(channel);
        String message = "message string";
        String detail = "detail string";
        try {
            post.pushMessage(message, detail);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}