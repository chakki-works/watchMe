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
    private String[] sourceCodes = new String[0];
    private String error = "";

    public HandledMessage(String sourceFile, int sourceLine, String[] sourceCodes, String error){
        this.sourceFile = sourceFile;
        this.sourceLine = sourceLine;
        this.sourceCodes = sourceCodes;
        this.error = error;
    }

    public static HandledMessage create(String rawMessage, Map<String, String> fileCache){
        HandledMessage hm = null;
        Pattern fileAndLinePattern = Pattern.compile("\\([A-Z][a-z]+\\.[a-z]+:\\d+\\)");
        String spaceIndented = rawMessage.replace("\t", "  ");
        Matcher m = fileAndLinePattern.matcher(spaceIndented);
        String file = "";
        int line = -1;
        String[] codes = new String[0];

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
                codes = stream.skip(line -3).limit(5).toArray(size -> new String[size]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            hm = new HandledMessage(file, line, codes, rawMessage);
        }

        return hm;
    }

    public String toString(){
        String msg = "";
        msg += String.format("%s(%d) \n", this.sourceFile, this.sourceLine);
        for(int i = 0; i < this.sourceCodes.length; i++){
            if(i == i / 2 + 1){
                msg += String.format("> `%s`\n", this.sourceCodes[i]);
            }else{
                msg += String.format("> %s\n", this.sourceCodes[i]);
            }
        }
        msg += String.format("Error \n ```%s```", this.error);

        return msg;
    }
}
