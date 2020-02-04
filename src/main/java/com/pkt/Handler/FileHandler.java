package com.pkt.Handler;

import com.pkt.Common.constant.CommonConstant;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import javax.xml.stream.FactoryConfigurationError;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileHandler {

    private static Date createTimeDate;
    private static Date lastmodfiyTimeDate;

    public static Map<String, Date> getTimeDate(File file){
        Map<String, Date> timeDate = new HashMap<String, Date>();
        BasicFileAttributeView basicViews = Files.getFileAttributeView(file.toPath(), BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
        BasicFileAttributes attributes;
        try {
            attributes = basicViews.readAttributes();
            createTimeDate = new Date(attributes.creationTime().toMillis());
            lastmodfiyTimeDate = new Date(attributes.lastModifiedTime().toMillis());
        } catch (Exception e) {
            e.printStackTrace();
        }
        timeDate.put("create_time", createTimeDate);
        timeDate.put("modify_time", lastmodfiyTimeDate);

        return timeDate;
    }

    public static Map<String, Object> createFile(String folderpath, String filename) throws IOException {
        Map<String, Object> createFileInfo = new HashMap<String, Object>();
        String filepath = folderpath + CommonConstant.FILE_SEPARATOR + filename;
        File file = new File(filepath);
        if (!file.exists()) {
            file.createNewFile();
        }
        createFileInfo.put("file_path", filepath);
        createFileInfo.put("create_time", getTimeDate(file).get("create_time"));
        createFileInfo.put("modify_time", getTimeDate(file).get("modify_time"));
        return createFileInfo;
    }

    public static Date modifyFile(String filepath, String content) throws IOException{
        StringBuilder output = new StringBuilder();

        File file = new File(filepath);
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write("");

        output.append(content+"\r");

        System.out.println("output:" + output.toString());
        fileWriter.write(output.toString().replaceAll("((?m)^\\s*$(\\n|\\r\\n)){2,}", ""));
        try {
            fileWriter.close();
        } catch (IOException e2) {
            e2.printStackTrace();
        }

        lastmodfiyTimeDate = getTimeDate(file).get("modify_time");

        return lastmodfiyTimeDate;
    }


    public static String readFile(String filepath)throws IOException{
        FileReader fileReader = new FileReader(filepath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        StringBuilder stringBuilder = new StringBuilder();
        String line = bufferedReader.readLine();
        while(line!=null){
            if(line.contains("\\n|\\r|\\n\\r|\\r\\n")){
                stringBuilder.append(line);
            }else {
                stringBuilder.append(line+"\n");
            }
            line = bufferedReader.readLine();
        }
        try {
            bufferedReader.close();
            fileReader.close();
        }catch (IOException e2){
            e2.printStackTrace();
        }
        String fileContent = stringBuilder.toString();

        return fileContent;
    }

    public static boolean deleteFile(String filepath)throws IOException {
        boolean result = false;
        File file = new File(filepath);
        if(file.exists()){
            result = file.delete();
        }
        return result;
    }

    public static Map<String, Object> createFolder(String parentfolder, String foldername) throws IOException {
        Map<String, Object> createFolderInfo = new HashMap<String, Object>();
        String folderpath = parentfolder + CommonConstant.FILE_SEPARATOR + foldername;
        File file = new File(folderpath);
        if (!file.exists()) {
            file.mkdir();
        }
        createFolderInfo.put("folder_path", folderpath);
        createFolderInfo.put("create_time", getTimeDate(file).get("create_time"));
        createFolderInfo.put("modify_time", getTimeDate(file).get("modify_time"));
        return createFolderInfo;
    }

    public static boolean deleteFolder(String folderpath)throws IOException {
        File file = new File(folderpath);
        if(!file.exists()){//判断是否待删除目录是否存在
            System.err.println("The folder are not exists!");
            return false;
        }

        String[] content = file.list();//取得当前目录下所有文件和文件夹
        for(String name : content){
            File temp = new File(folderpath, name);
            if(temp.isDirectory()){//判断是否是目录
                deleteFolder(temp.getAbsolutePath());//递归调用，删除目录里的内容
                temp.delete();//删除空目录
            }else{
                if(!temp.delete()){//直接删除文件
                    System.err.println("Failed to delete " + name);
                }
            }
        }
        return true;
    }
    public static Map<String, Date> CopyFile(String src_path, String dst_path)throws IOException{
        File srcFile = new File(src_path);
        File dstFile = new File(dst_path);
        if(dstFile.exists()){
            dstFile.delete();
        }
        Files.copy(srcFile.toPath(), dstFile.toPath());

        Map<String, Date> copyFileInfo = new HashMap<String, Date>();
        copyFileInfo.put("create_time", getTimeDate(dstFile).get("create_time"));
        copyFileInfo.put("modify_time", getTimeDate(dstFile).get("modify_time"));

        return copyFileInfo;
    }

    public static boolean exist_file_in_folder(String filename, String foldername){
        File file = new File(foldername);
        String[] subfilelist = file.list();
        for(String subfilename:subfilelist){
            if(subfilename.equals(filename)){
                return true;
            }
        }
        return false;
    }



    public static void main(String[] args){
//        FileHandler.Test("/Users/wuwenwen/Documents/Homework/GraduationDesign/MaterialLibrary/BasicFile.txt");

        String src_file = "/Users/wuwenwen/Documents/Homework/GraduationDesign/MaterialLibrary/test/aa";
        String dst_file = "/Users/wuwenwen/Documents/Homework/GraduationDesign/MaterialLibrary/test/cc";
        try {
            Map<String,Date> copyFileInfo =  FileHandler.CopyFile(src_file, dst_file);
            System.out.println(copyFileInfo);
        }catch (IOException e){
            e.printStackTrace();
        }

    }

}