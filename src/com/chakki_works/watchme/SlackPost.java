package com.chakki_works.watchme;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;


public class SlackPost {

    private SlackStorage storage = SlackStorage.getInstance();

    private Project project;
    private Editor editor;
    private String currentFileName;

    private String selectedText;

    private SlackChannel channel;

    public SlackPost(SlackChannel channel) {
        super(channel.getId());
        this.channel = channel;
    }


    private void pushMessage(String message, String details) throws IOException {
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
                Messages.showMessageDialog(project, "Message Sent.", "Information", SlackStorage.getSlackIcon());
            }
            else {
                Messages.showMessageDialog(project, "Message not sent, check your configuration.", "Error", Messages.getErrorIcon());
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
