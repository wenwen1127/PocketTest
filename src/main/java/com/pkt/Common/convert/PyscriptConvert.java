package com.pkt.Common.convert;

import com.pkt.Handler.FileHandler;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PyscriptConvert {

    public static List<String> separatePyscript(String pyscriptContent) {
        List<String> functionList = new ArrayList<String>();
        String pattern ="def\\s+\\S+\\((.*)\\)\\s*:";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(pyscriptContent);
        int count = 0;
        while (m.find()) {
            String function_name = m.group().split("def")[1].split("\\(")[0].trim();
            functionList.add(function_name);
        }
        return functionList;
    }

    public static void main(String[] args) {
        try {
            String pyscriptContent = FileHandler.readFile("/Users/wuwenwen/Documents/TrendMicro/TestiCore/StartTest.py");
            separatePyscript(pyscriptContent);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
