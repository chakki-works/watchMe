package com.chakki_works.watchme;

import java.util.HashMap;
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

    private static Map<String, Integer> JavaParser(String message){
        Pattern fileAndLinePattern = Pattern.compile("\\([A-Z][A-Za-z]+\\.[a-z]+:\\d+\\)");
        Matcher m = fileAndLinePattern.matcher(message);
        HashMap<String, Integer> fileLine = new HashMap<String, Integer>();
        while(m.find()) {
            String[] fileAndLine = m.group().replace("(", "").replace(")", "").split(":");
            if (fileAndLine.length == 2) {
                String file = fileAndLine[0];
                int line = Integer.parseInt(fileAndLine[1]);
                fileLine.put(file, line);
            }
        }
        return fileLine;
    }

    private static Map<String, Integer> PythonParser(String message){
        //"/Users/smap6/IdeaProjects/watchMePythonTestProject/main.py", line 12, in <module>
        Pattern fileAndLinePattern = Pattern.compile("/[A-Za-z]+\\.py\", line \\d+");
        Matcher m = fileAndLinePattern.matcher(message);
        HashMap<String, Integer> fileLine = new HashMap<String, Integer>();
        while(m.find()) {
            String[] fileAndLine = m.group().replace("/", "").replace("\", line ", ",").split(",");
            if (fileAndLine.length == 2) {
                String file = fileAndLine[0];
                int line = Integer.parseInt(fileAndLine[1]);
                fileLine.put(file, line);
            }
        }
        return fileLine;
    }

    public static HandledMessage create(String rawMessage, ProjectFileContent content){
        HandledMessage hm = null;
        String spaceIndented = rawMessage.replace("\t", "  ");
        String file = "";
        int line = -1;
        String[] codes = new String[0];
        Map<String, Integer> fileLine = null;

        if(content.isPython()){
            fileLine = PythonParser(spaceIndented);
        }else{
            fileLine = JavaParser(spaceIndented);
        }

        for(Map.Entry<String, Integer> e: fileLine.entrySet()){
            if(content.hasFile(e.getKey())){
                file = e.getKey();
                line = e.getValue();
                break;
            }
        }

        if(line > -1){
            String path = content.get(file);
            try (Stream<String> stream = Files.lines(Paths.get(path))) {
                int skips = line > 3 ? line - 3 : 0; // take before 2 and after 2 = total 5 lines of code.
                codes = stream.skip(skips).limit(5).toArray(size -> new String[size]);
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
        msg += String.format("> ...\n");
        for(int i = 0; i < this.sourceCodes.length; i++){
            if(i == this.sourceCodes.length / 2){
                msg += String.format("> `%s`\n", this.sourceCodes[i]); // center code
            }else{
                msg += String.format("> %s\n", this.sourceCodes[i]);
            }
        }
        msg += String.format("> ...\n");
        msg += String.format("Error \n ```%s```", this.error);

        return msg;
    }
}
