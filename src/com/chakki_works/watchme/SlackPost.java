package com.chakki_works.watchme;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;


public class SlackPost {

    private SlackChannel channel;

    public SlackPost(SlackChannel channel) {
        this.channel = channel;
    }

    public void pushMessage(String message, String details) throws IOException {
        String input = "payload=" + URLEncoder.encode(channel.getPayloadMessage(details, message), "UTF-8");

        try {
            URL url = new URL(channel.getUrl());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream (conn.getOutputStream ());
            wr.writeBytes (input);
            wr.flush ();
            wr.close ();

            if (conn.getResponseCode() == 200 && readInputStreamToString(conn).equals("ok")) {
                //Messages.showMessageDialog(project, "Message Sent.", "Information", SlackStorage.getSlackIcon());
                Notifications.Bus.notify(
                        new Notification("not error", "Message sent!", "Error message was sent!", NotificationType.INFORMATION)
                );
            }
            else {
                int status_code = conn.getResponseCode();
                String title = "";
                String content = "";
                if (status_code == 400) {
                    title = "invalid_payload";
                    content = "the data sent in your request cannot be understood as presented; verify your content body matches your content type and is structurally valid.";
                } else if (status_code == 403) {
                    title = "action_prohibited";
                    content = "the team associated with your request has some kind of restriction on the webhook posting in this context.";
                } else if (status_code == 404) {
                    title = "channel_not_found";
                    content = "the channel associated with your request does not exist.";
                } else if (status_code == 410) {
                    title = "channel_is_archived";
                    content = "the channel has been archived and doesn't accept further messages, even from your incoming webhook.";
                } else if (status_code == 500) {
                    title = "rollup_error";
                    content = "something strange and unusual happened that was likely not your fault at all.";
                }

                Notifications.Bus.notify(
                        new Notification("error", title, content, NotificationType.ERROR)
                );
                //Messages.showMessageDialog(project, "Message not sent, check your configuration.", "Error", Messages.getErrorIcon());
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param connection object; note: before calling this function,
     *   ensure that the connection is already be open, and any writes to
     *   the connection's output stream should have already been completed.
     * @return String containing the body of the connection response or
     *   null if the input stream could not be read correctly
     */
    private String readInputStreamToString(HttpURLConnection connection) {
        String result = null;
        StringBuffer sb = new StringBuffer();
        InputStream is = null;

        try {
            is = new BufferedInputStream(connection.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String inputLine = "";
            while ((inputLine = br.readLine()) != null) {
                sb.append(inputLine);
            }
            result = sb.toString();
        }
        catch (Exception e) {
            result = "";
        }

        return result;
    }
}
