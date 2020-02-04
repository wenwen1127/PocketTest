package com.pkt.Common.utils;

import java.io.*;

public class RunCmdUtils {
    public synchronized static String run(String[] cmd,String directory) throws IOException {
        String line=null;
        String result="";
        try {
            ProcessBuilder builder = new ProcessBuilder(cmd);
            //set working directory
            if (directory!=null) {
                builder.directory(new File(directory));
                builder.redirectErrorStream(true);
                Process process = builder.start();
                InputStream in = process.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String temp = null;
                while ((temp = br.readLine()) != null){
                    result= result+new String(temp.getBytes("UTF-8"),"UTF-8");
//                    System.out.println(new String(temp.getBytes("UTF-8"),"UTF-8"));
                }
                in.close();
                br.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
        String result=null;
        try {
            String[] arg = {"python", "-c" ,"import Test1;Test1.swap(5,3)"};
            result= run(arg,"/Users/wuwenwen/PycharmProjects/Homework3/");
            System.out.println(result);
        }catch ( IOException ex ){
            ex.printStackTrace();
        }
    }

}
