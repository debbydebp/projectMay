package com.fileRename;

import java.io.File;
import java.lang.String;


public class FileRenameMain{
  public static void main(String[] args){
    String path="D:/aa";
    File dirFile=new File(path);
    File []fileList=dirFile.listFiles();
    for(File tempFile : fileList) {
      if(tempFile.isFile()) {
        String tempPath=tempFile.getParent();
        String tempFileName=tempFile.getName();
        String tempRename=null;
        
        System.out.println("Path="+tempPath);
        System.out.println("FileName="+tempFileName);
        

        StringBuffer a= new StringBuffer(tempPath);
        a.append("\\");
        a.append(tempFileName);
        System.out.println(a.toString());
        
        File file1 = new File(a.toString());
        tempRename=tempFileName.replaceFirst("Interchange4thEd_ClassAudio1_Unit","");
        StringBuffer b= new StringBuffer(tempPath);
        b.append("\\");
        b.append(tempRename);
        System.out.println("will be rename to "+b.toString());
        
        file1.renameTo(new File(b.toString()));
        System.out.println("Finally Rename to >> \""+file1.getPath()+"/"+file1.getName()+"\"");
        System.out.println("===========");
      }
    }
  }
}
