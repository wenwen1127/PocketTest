package com.pkt.TestExecution;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pkt.Common.utils.RunCmdUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetSentenceReturn {

    /**
     * 写一个承接return的python脚本
     *
     *
     */

    public static List<String> getSentReturn(String scriptname, String script_dir, String keyword, String params){
        List<String> cmd_list = new ArrayList<String>();
        cmd_list.add("python");
        cmd_list.add("-c");
        StringBuilder sb = new StringBuilder("import " + scriptname + ";import "+ "getReturn;"+"getReturn._return("+scriptname+"."+keyword+"("+ params + "))");
        cmd_list.add(sb.toString());
        String[] cmd_args = new String[cmd_list.size()];
        cmd_list.toArray(cmd_args);
        List<String> res_list = null;
        try {
            String resprint = RunCmdUtils.run(cmd_args, script_dir);

            String res_str = resprint.split("%")[resprint.split("%").length-1];
            res_list = Arrays.asList(res_str.split("-"));
            System.out.println(res_list);
        }catch (IOException e2){
            e2.printStackTrace();
        }
        return res_list;
    }
}
