package com.chakki_works.watchme;


public class SlackChannel {
    protected String id;
    protected String token;
    protected String senderName = "watchMe";
    protected String senderIcon = "https://farm3.staticflickr.com/2291/33013444521_8e555dd637_o_d.png";
    protected String channelName = "";

    public SlackChannel(String token, String id, String channelName) {
        this.token = token;
        this.id = id;
        this.channelName = channelName;
    }

    public String getPayloadMessage(String title, String message) {

        message = message.replace("\\", "\\\\").replace("\"", "\\\"");


        String payload = "{" +
                "\"text\" : \"*" + title + "*\n" + message + "\"," +
                //"\"attachments\" : [{" +
                //"\"title\" : \"" + title + "\"," +
                //"\"text\" : \"" + message + "\"," +
                //"\"mrkdwn_in\" : [\"title\", \"text\"]" +
                //"}]," +
                "\"username\" : \"" + this.getSenderName() + "\"," +
                "\"icon_url\" : \"" + this.getSenderIcon() + "\"";
        String channel = this.getChannelName();
        if (channel != null && !channel.isEmpty()) {
            payload += ",\"channel\" : \"" + channel + "\"";
        }
        payload += "}";

        return payload;
    }

    public String getId() {
        return id;
    }

    public static String getIdDescription() {
        return "Enter Your Name";
    }

    public String getToken() {
        return token;
    }

    public static String getTokenDescription() {
        return "Enter your slack webhook integration URL (https://hooks.slack.com/services/xxx/yyy/zzz)";
    }

    public String getSenderName() {
        return senderName == null ? getSenderNameDefaultValue() : senderName;
    }

    public static String getSenderNameDescription() {
        return "Username to post as:";
    }

    public static String getSenderNameDefaultValue() {
        return "watchMe";
    }

    public String getSenderIcon() {
        return senderIcon == null ? getDefaultSenderIcon() : senderIcon ;
    }

    public static String getSenderIconDescription() {
        return "Icon used to post:";
    }

    public static String getDefaultSenderIcon() {
        return "https://farm3.staticflickr.com/2291/33013444521_8e555dd637_o_d.png";
    }

    public static String getSettingsDescription() {
        return "Slack Channel Settings";
    }

    public String getUrl() {
        if (this.token.contains("https://hooks.slack.com/services")) {
            return this.token;
        }
        return "https://hooks.slack.com/services/" + this.token;
    }

    public static String getChanneNameDescription() {
        return "Channel Override: (leave blank for default channel)";
    }

    public String getChannelName()  {
        return channelName == null ? "" : channelName;
    }
}