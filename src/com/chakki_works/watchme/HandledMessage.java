package com.chakki_works.watchme;

import java.util.regex.Pattern;
import java.util.regex.Matcher;


public class HandledMessage {

    private String sourceFile = "";
    private int sourceLine = 0;
    private String sourceCode = "";
    private String error = "";

    public HandledMessage(String sourceFile, int sourceLine, String sourceCode, String error){
        this.sourceFile = sourceFile;
        this.sourceLine = sourceLine;
        this.sourceCode = sourceCode;
        this.error = error;
    }

    public static HandledMessage create(String rawMessage){
        Pattern fileAndLinePattern = Pattern.compile("\\([A-Z][a-z]+\\.[a-z]+:\\d+\\)");
        Matcher m = fileAndLinePattern.matcher(rawMessage);
        String[] fileAndLine = new String[0];

        while(m.find()) {
            System.out.println(m.group());
            fileAndLine = m.group().replace("(", "").replace(")", "").split(":");
            break; // use first file name
        }

        HandledMessage hm = null;
        if(fileAndLine.length == 2){
            String file = fileAndLine[0];
            int line = Integer.parseInt(fileAndLine[1]);
            hm = new HandledMessage(file, line, "hogehoge", rawMessage);
        }

        return hm;
    }

    public String toString(){
        String s = String.format(
                "At %s(%d) = %s. \n %s",
                this.sourceFile, this.sourceLine, this.sourceCode, this.error
        );
        return s;
    }
}
