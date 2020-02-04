package com.pkt.TestExecution;

import com.pkt.Common.Exception.TypeConvertException;
import com.pkt.Common.constant.SuiteConstant;
import com.pkt.Common.convert.Format;
import com.pkt.Common.convert.SuiteConvert;
import com.pkt.Handler.FileHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SetEnvironment {
    public static Map<Integer, String> runSuiteEnviroment(String suiteContent){
        Map<Integer, String> suiteEnvMap = new HashMap<Integer, String>();
        if(SuiteConvert.separateSuite(suiteContent).containsKey(SuiteConstant.SETTING_TITLE)){
            Map<String, String> settingMap = SuiteConvert.separateSetting(suiteContent);
            if(settingMap.containsKey(SuiteConstant.Suite_Setup)){
                Integer level = 1;
                String suiteSetupContent = getSettingKeywordContent(settingMap.get(SuiteConstant.Suite_Setup), suiteContent);
                suiteEnvMap.put(level, suiteSetupContent);
            }
            if(settingMap.containsKey(SuiteConstant.Suite_Teardown)){
                Integer level = -1;
                String suiteTeardownContent = getSettingKeywordContent(settingMap.get(SuiteConstant.Suite_Teardown), suiteContent);
                suiteEnvMap.put(level, suiteTeardownContent);
            }
            if(settingMap.containsKey(SuiteConstant.Test_Setup)){
                Integer level = 2;
                String TestSetupContent = getSettingKeywordContent(settingMap.get(SuiteConstant.Test_Setup), suiteContent);
                suiteEnvMap.put(level, TestSetupContent);
            }
            if(settingMap.containsKey(SuiteConstant.Test_Teardown)){
                Integer level = -2;
                String TestTeardownContent = getSettingKeywordContent(settingMap.get(SuiteConstant.Test_Teardown), suiteContent);
                suiteEnvMap.put(level, TestTeardownContent);
            }
        }
        return suiteEnvMap;
    }


    private static String getSettingKeywordContent(String settingKeywordpName,String suiteContent){
        Map<String, String> keywordMap = SuiteConvert.separateKeyword(suiteContent);
//        System.out.println(keywordMap);
        if(keywordMap.containsKey(settingKeywordpName)){
            return keywordMap.get(settingKeywordpName);
        }else {
            return null;
        }
    }

    public static Map<String, Object> getGlobalVariable(String suiteContent) throws TypeConvertException{
        Map<String, Object> globalVarMap = new HashMap<String, Object>();
        Map<String, String> variableMap = SuiteConvert.separateVariable(suiteContent);
        Iterator<Map.Entry<String, String>> entries = variableMap.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, String> entry = entries.next();
            String name = entry.getKey().substring(entry.getKey().indexOf("{")+1, entry.getKey().indexOf("}"));
            String value_type = Format.getTypeName(entry.getValue());
            try {
                if(value_type.equals("int")){
                    int value = Format.format_int(entry.getValue());
                    globalVarMap.put(name, value);
                }else if(value_type.equals("boolean")){
                    String value = Format.format_boolean(entry.getValue());
                    globalVarMap.put(name, value);
                }else if(value_type.equals("String")){
                    String value = Format.format_String(entry.getValue());
                    globalVarMap.put(name, value);
                }else {
                     throw new TypeConvertException("类型传入错误!!");
                }
            }catch (TypeConvertException e){
                e.printStackTrace();
            }
        }
        return globalVarMap;
    }

    public static void main(String[] args) {
        try {
            String suiteContent = FileHandler.readFile("/Users/wuwenwen/Documents/Homework/GraduationDesign/MaterialLibrary/BasicFile.txt");
//            System.out.println(runSuiteEnviroment(suiteContent));
            System.out.println(getGlobalVariable(suiteContent));

        }catch (IOException e1){
            e1.printStackTrace();
        }catch (TypeConvertException e2){
            e2.printStackTrace();
        }
    }


}
