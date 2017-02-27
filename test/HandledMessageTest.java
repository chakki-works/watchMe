import junit.framework.TestCase;
import com.chakki_works.watchme.HandledMessage;
import com.chakki_works.watchme.ProjectFileContent;
import com.chakki_works.watchme.SlackPost;
import com.chakki_works.watchme.SlackChannel;

import java.io.IOException;
import java.util.HashMap;


public class HandledMessageTest extends TestCase{

    public void testMessageParsePython(){
        String sample = "";
        sample += "Traceback (most recent call last):\n" +
                "  File \"/Users/smap6/IdeaProjects/watchMePythonTestProject/main.py\", line 12, in <module>\n" +
                "    main()\n" +
                "  File \"/Users/smap6/IdeaProjects/watchMePythonTestProject/main.py\", line 7, in main\n" +
                "    with open(path, encoding=\"utf-8\") as f:\n" +
                "TypeError: 'encoding' is an invalid keyword argument for this function";

        HashMap<String, String> fileCache = new HashMap<String, String>();
        fileCache.put("main.py", System.getProperty("user.dir") + "/README.md");
        HandledMessage hm = HandledMessage.create(sample, ProjectFileContent.createFromContent(fileCache, true));
        System.out.println(hm);

    }

    public void testMessageParseJava(){
        String sample = "";
        sample += "Exception in thread \"main\" java.lang.Exception: Test Exception\n" +
                "\tat com.company.Main.main(Main.java:8)\n" +
                "\tat sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n" +
                "\tat sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n" +
                "\tat sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n" +
                "\tat java.lang.reflect.Method.invoke(Method.java:497)\n" +
                "\tat com.intellij.rt.execution.application.AppMain.main(AppMain.java:147)";

        HashMap<String, String> fileCache = new HashMap<String, String>();
        fileCache.put("Main.java", System.getProperty("user.dir") + "/README.md");
        HandledMessage hm = HandledMessage.create(sample, ProjectFileContent.createFromContent(fileCache, false));
        System.out.println(hm);

    }

    public void testPostMessage() {
        String sample = "";
        sample += "Exception in thread \"main\" java.lang.Exception: Test Exception\n" +
                "\tat com.company.Main.main(Main.java:8)\n" +
                "\tat sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n" +
                "\tat sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n" +
                "\tat sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n" +
                "\tat java.lang.reflect.Method.invoke(Method.java:497)\n" +
                "\tat com.intellij.rt.execution.application.AppMain.main(AppMain.java:147)";

        HashMap<String, String> fileCache = new HashMap<String, String>();
        fileCache.put("Main.java", System.getProperty("user.dir") + "/README.md");
        HandledMessage hm = HandledMessage.create(sample, ProjectFileContent.createFromContent(fileCache, false));
        System.out.println(hm);

        String token = "SLACK-API-TOKEN";
        String id = "";
        String senderName = "watchMe";
        String senderIcon = ":thunder_cloud_and_rain:";
        String channelName = "";

        SlackChannel channel = new SlackChannel(token, id, senderName, senderIcon, channelName);

        SlackPost post = new SlackPost(channel);
        String message = hm.toString();
        String detail = "Hironsan is in trouble with the following error.";
        try {
            post.pushMessage(message, detail);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
