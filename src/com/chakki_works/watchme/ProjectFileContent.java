package com.chakki_works.watchme;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProjectFileContent {
    private final Map<String, String> fileContent;
    private List<String> roots = new ArrayList<String>();
    private boolean isPython = false;

    public ProjectFileContent(List<String> roots, boolean isPython){
        this.roots = roots;
        this.isPython = isPython;
        this.fileContent = new HashMap<String, String>();
    }

    private ProjectFileContent(List<String> roots, boolean isPython, Map<String, String> fileContent){
        this.roots = roots;
        this.isPython = isPython;
        this.fileContent = fileContent;
    }

    public static ProjectFileContent createFromContent(Map<String, String> fileContent, boolean isPython){
        return new ProjectFileContent(new ArrayList<String>(), isPython, fileContent);
    }

    public Map<String, String> get(){
        return fileContent;
    }

    public String get(String fileName){
        return fileContent.get(fileName);
    }

    public boolean isPython(){
        return this.isPython;
    }

    public boolean hasFile(String fileName){
        return this.fileContent.containsKey(fileName);
    }

    public boolean contains(String path){
        boolean match = false;
        for(String r : this.roots){
            if(path.startsWith(r)){
                match = true;
                break;
            }
        }
        return match;
    }

}
