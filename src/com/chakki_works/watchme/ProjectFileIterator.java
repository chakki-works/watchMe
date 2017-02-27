package com.chakki_works.watchme;

import com.intellij.openapi.roots.ContentIterator;
import com.intellij.openapi.vfs.VirtualFile;
import com.chakki_works.watchme.ProjectFileContent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.chakki_works.watchme.ProjectFileContent;


public class ProjectFileIterator implements ContentIterator {

    private final ProjectFileContent fileContent;

    public ProjectFileIterator(ProjectFileContent content){
        this.fileContent = content;
    }

    public ProjectFileContent getContent(){
        return this.fileContent;
    }

    @Override
    public boolean processFile(final VirtualFile file) {
        final String fileName = file.getName();
        final String filePath = file.getPath();
        boolean isTarget = false;
        if(fileContent.isPython()){
            isTarget = !file.isDirectory() && !fileContent.contains(filePath);
        }else{
            isTarget = !file.isDirectory() && fileContent.contains(filePath);
        }

        if (isTarget && !this.fileContent.hasFile(fileName)) {
            fileContent.get().put(fileName, filePath);
            //System.out.println("include: " + fileName);
        }

        return true;
    }
}
