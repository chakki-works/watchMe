package com.chakki_works.watchme;

import com.intellij.openapi.roots.ContentIterator;
import com.intellij.openapi.vfs.VirtualFile;

import java.util.List;
import java.util.Map;

public class ProjectFileIterator implements ContentIterator {

    private final Map<String, String> fileCache;
    private final List<String> roots;

    public ProjectFileIterator(final Map<String, String> fileCache, List<String> roots){
        this.fileCache = fileCache;
        this.roots = roots;
    }

    private boolean matchRoot(String path){
        boolean match = false;
        for(String r : this.roots){
            if(path.startsWith(r)){
                match = true;
                break;
            }
        }
        return match;
    }

    @Override
    public boolean processFile(final VirtualFile file) {
        if (!file.isDirectory()) {
            final String fileName = file.getName();
            final String filePath = file.getPath();

            if(matchRoot(filePath) && !fileCache.containsKey(fileName)){
                fileCache.put(fileName, filePath);
                //System.out.println("include: " + fileName);
            }
        }

        return true;
    }
}
