package com.chakki_works.watchme;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Before;
import org.junit.Test;

public class SlackChannelTest {

    protected String token = "SLACK-API-TOKEN";
    protected String id = "";
    protected String senderName = "SlackStorm";
    protected String senderIcon = ":thunder_cloud_and_rain:";
    protected String channelName = "";
    SlackChannel channel;

    @Before
    public void setUp() {
        this.channel = new SlackChannel(this.token, this.id, this.senderName, this.senderIcon, this.channelName);
    }

    @Test
    public void testAttributes() {
        assertEquals(this.token, this.channel.getToken());
        assertEquals(this.id, this.channel.getId());
        assertEquals(this.senderName, this.channel.getSenderName());
        assertEquals(this.senderIcon, this.channel.getSenderIcon());
        assertEquals(this.channelName, this.channel.getChannelName());
    }

    @Test
    public void testURL() {
        String url = "https://hooks.slack.com/services/" + token;
        assertEquals(url, this.channel.getUrl());
    }

    @Test
    public void testPayload() {
        String message = this.channel.getPayloadMessage("Test Title", "Hello Message");
        assertThat(message, is(instanceOf(String.class)));
    }
}