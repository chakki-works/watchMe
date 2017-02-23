import junit.framework.Assert;
import junit.framework.AssertionFailedError;
import junit.framework.TestCase;
import com.chakki_works.watchme.HandledMessage;


public class HandledMessageTest extends TestCase{

    public void testMessageParse(){
        String sample = "";
        sample += "Exception in thread \"main\" java.lang.Exception: Test Exception\n" +
                "\tat com.company.Main.main(Main.java:8)\n" +
                "\tat sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n" +
                "\tat sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n" +
                "\tat sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n" +
                "\tat java.lang.reflect.Method.invoke(Method.java:497)\n" +
                "\tat com.intellij.rt.execution.application.AppMain.main(AppMain.java:147)";

        HandledMessage hm = HandledMessage.create(sample);
        System.out.println(hm);

    }

}
