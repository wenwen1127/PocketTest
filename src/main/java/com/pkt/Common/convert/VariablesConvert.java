package com.pkt.Common.convert;

import com.pkt.Common.Exception.TypeConvertException;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

public class VariablesConvert {

//    public static String convert_input_to_string()

    public static Map<String, Object> convert_to_var(String varName, Object input){
        Map<String, Object> varMap = new HashMap<String, Object>();
        varMap.put(varName, input);
        return varMap;
    }






//    public static Map<String, List<Object>> convert_String_to_list(String listName, String input){
//        Map<String, List<Object>> listMap = new HashMap<String, List<Object>>();
//        List<String> inputlst = Arrays.asList(input.split("\\s{2,}"));
//        List<Object> outputlst = new ArrayList<Object>();
//        for(String item: inputlst){
//            try {
//                if (getTypeName(item).equals("int")){
//                    outputlst.add(convert_string_to_int(item));
//                }else if(getTypeName(item).equals("String")){
//                    outputlst.add(item);
//                }
//                else{
//                    Map<String,String> valuemap = convert_value_name(item);
//                    if(valuemap.containsKey("var")){
//                        String varName = valuemap.get("var");
//                        outputlst.add(getValueByName(varName));
//                    }
//                    else if(valuemap.containsKey("list")){
//                        String lstName = valuemap.get("list");
//                        outputlst.add(getValueByName(lstName));
//                    }
//                    else if(valuemap.containsKey("dict")){
//                        String dictName = valuemap.get("dict");
//                        outputlst.add(getValueByName(dictName));
//                    }
//                }
//            }catch (TypeConvertException e){
//                e.printStackTrace();
////            }
//        }
//        listMap.put(listName, outputlst);
//        return listMap;
//    }

//    public static Map<String, Map<Object, Object>> convert_String_to_dict(String map, String input){
//        Map<String, Map<Object, Object>> dictMap = new HashMap<String, Map<Object, Object>>();
//        List<String> inputlst = Arrays.asList(input.split("\\s{2,}"));
//        for(String item : inputlst){
//            String key = item.split(":")[0];
//            String value = item.split(":")[1];
//            try {
//                if (getTypeName(item).equals("int")){
//                    outputlst.add(convert_string_to_int(item));
//                }else if(getTypeName(item).equals("String")){
//                    outputlst.add(item);
//                }
//                else{
//                    Map<String,String> valuemap = convert_value_name(item);
//                    if(valuemap.containsKey("var")){
//                        String varName = valuemap.get("var");
//                        outputlst.add(getValueByName(varName));
//                    }
//                    else if(valuemap.containsKey("list")){
//                        String lstName = valuemap.get("list");
//                        outputlst.add(getValueByName(lstName));
//                    }
//                    else if(valuemap.containsKey("dict")){
//                        String dictName = valuemap.get("dict");
//                        outputlst.add(getValueByName(dictName));
//                    }
//                }
//            }catch (TypeConvertException e){
//                e.printStackTrace();
//            }
//
//
//
//        }
//
//
//
//    }









    public static Boolean convert_int_to_boolean(int input){
        if(input == 0){
            return false;
        }else {
            return true;
        }
    }

    public static Boolean convert_list_to_boolean(List<Object> input){
        if(input.size() == 0){
            return false;
        }else {
            return true;
        }
    }

    public static Boolean convert_map_to_boolean(Map<Object,Object> input){
        if(input.size() == 0){
            return false;
        }else {
            return true;
        }
    }



    public static void main(String[] args) {
//        try {
//            System.out.println(Integer.parseInt("1", 10));
//        } catch (NumberFormatException e2){
//            e2.printStackTrace();
//        }
//        try {
////            System.out.println(VariablesConvert.convert_string_to_list("@{list}"));
//        }catch (TypeConvertException e){
//            e.printStackTrace();
//        }
//

    }

}
