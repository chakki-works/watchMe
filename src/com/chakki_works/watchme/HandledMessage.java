package com.chakki_works.watchme;

import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;


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

    public static HandledMessage create(String rawMessage, Map<String, String> fileCache){
        HandledMessage hm = null;
        Pattern fileAndLinePattern = Pattern.compile("\\([A-Z][a-z]+\\.[a-z]+:\\d+\\)");
        Matcher m = fileAndLinePattern.matcher(rawMessage);
        String file = "";
        int line = -1;
        String code = "";

        while(m.find()) {
            //System.out.println(m.group());
            String[] fileAndLine = m.group().replace("(", "").replace(")", "").split(":");
            if (fileAndLine.length == 2 && fileCache.containsKey(fileAndLine[0])) {
                file = fileAndLine[0];
                line = Integer.parseInt(fileAndLine[1]);
                break;
            }
        }

        if(line > -1){
            String path = fileCache.get(file);
            try (Stream<String> stream = Files.lines(Paths.get(path))) {
                code = stream.skip(line -1).findFirst().get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            hm = new HandledMessage(file, line, code, rawMessage);
        }

        return hm;
    }

    public String toString(){
        String s = String.format(
                "At %s(%d). \n code: \t%s \n error: \t %s",
                this.sourceFile, this.sourceLine, this.sourceCode, this.error
        );
        return s;
    }
}
