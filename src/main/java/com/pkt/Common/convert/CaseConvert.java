package com.pkt.Common.convert;

import com.pkt.Handler.FileHandler;
import java.io.IOException;
import java.util.*;

public class CaseConvert {
    /**
     * 将case中的语句分离出 keyword returnList inputList
     * @param caseContent
     * @return
     */
    public static List<Map<String, Object>> separeteCaseSentence(String caseContent){

        List<Map<String, Object>> sentenceList = new ArrayList<Map<String, Object>>();

        List<String> lst = Arrays.asList(caseContent.split("\n"));
        for(String sentline : lst){
            Map<String, Object> sentenceMap = new HashMap<String, Object>();
            if(sentline.contains("[Tags]")){
                continue;
            }
            List<String> sentlinePart = Arrays.asList(sentline.trim().split("\\s{2,}"));
            int keywordindex = -1;
            for(String sentPart : sentlinePart){
                if(!(sentPart.contains("$") || sentPart.contains("&") || sentPart.contains("@") && sentPart.contains("{") && sentPart.contains("}"))){
                    keywordindex = sentlinePart.indexOf(sentPart);
                }
            }
            String keyword = sentlinePart.get(keywordindex);
            List<String> returnList = new ArrayList<String>();
            List<String> inputList = new ArrayList<String>();
            if(keywordindex == 0){
                inputList = sentlinePart.subList(keywordindex+1, sentlinePart.size());
            }else if (keywordindex == sentlinePart.size()-1){
                returnList = sentlinePart.subList(0,keywordindex);
            }else if(keywordindex > 0 && keywordindex < sentlinePart.size()-1){
                returnList = sentlinePart.subList(0,keywordindex);
                inputList = sentlinePart.subList(keywordindex+1, sentlinePart.size());
            }
            sentenceMap.put("keyword", keyword);
            sentenceMap.put("returnList", returnList);
            sentenceMap.put("inputList", inputList);
            sentenceList.add(sentenceMap);
        }
        return sentenceList;
    }

    public static void main(String[] args) {
        try {
            String suiteContent = FileHandler.readFile("/Users/wuwenwen/Documents/Homework/GraduationDesign/MaterialLibrary/BasicFile.txt");
            String caseContent = SuiteConvert.separateCase(suiteContent).get(0).get(1);
            System.out.println(separeteCaseSentence(caseContent));
        }catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
