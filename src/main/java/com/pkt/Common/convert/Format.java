package com.pkt.Common.convert;

import com.pkt.Common.Exception.TypeConvertException;

import javax.swing.text.html.parser.Entity;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Format {
    /**
     * 获取suite对应的格式
     * 例如  ${1} -> int
     *      ${a} -> variable
     *      @{b} -> list
     *      &{c} -> dict
     * @param input
     * @return
     */

    public static String getTypeName(String input){
        if(input.contains("@") || input.contains("$") || input.contains("&") && input.contains("{") && input.contains("}")){
            String value_str = input.substring(input.indexOf("{")+1, input.indexOf("}")).trim();
            if(input.contains("$") && input.contains("{") && input.contains("}")){
                if(isInteger(value_str)){
                    return "int";
                }else if(value_str.toLowerCase().equals("false") || value_str.toLowerCase().equals("true")){
                    return "boolean";
                }else {
                    return "var";
                }
            }
            else if(input.contains("@") && input.contains("{") && input.contains("}")){
                return "list";
            }
            else{
                return "dict";
            }
        }

        else {
            return "String";
        }
    }
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    /**
     * 将 var/list/dict这三种变量形式的值提取出对应的变量名
     * 例如：${a}  ->  {"var", "a"}
     *      @{b}  ->  {"list", "b"}
     *      &{c}  ->  {"dict", "c"}
     * @param input
     * @return
     * @throws TypeConvertException
     */
    public static Map<String, String> convert_value_name(String input) throws TypeConvertException {

        Map<String, String> outputMap = new HashMap<String, String>();
        String output = input.substring(input.indexOf("{")+1, input.indexOf("}")).trim();

        if(getTypeName(input).equals("var")) {
            outputMap.put("var", output);
            return outputMap;
        }
        if(getTypeName(input).equals("list")){
            outputMap.put("list", output);
            return outputMap;
        }
        if(getTypeName(input).equals("dict")){
            outputMap.put("dict", output);
            return outputMap;
        }
        else {
            throw new TypeConvertException("类型转换异常：当前类型不是变量");
        }
    }

    /**
     * 将所有类型变量及常量都对应成具体的值
     * @param input
     * @return
     * @throws TypeConvertException
     */
    public static Object getValue(String input, Map<String, Object> valueCacheMap) throws TypeConvertException{
        String type = getTypeName(input);
        if(type.equals("int")){
            return format_int(input);
        }
        else if(type.equals("String")){
            return format_String(input);
        }
        else if(type.equals("var")){
            return getValueByName(convert_value_name(input).get("var"), valueCacheMap);
        }
        else if(type.equals("list")){
            return getValueByName(convert_value_name(input).get("list"),valueCacheMap);
        }
        else if(type.equals("dict")){
            return getValueByName(convert_value_name(input).get("dict"),valueCacheMap);
        }
        else if(type.equals("boolean")){
            return format_boolean(input);
        }
        else {
            throw new TypeConvertException("类型异常：当前类型无法识别");
        }
    }

    /**
     * 从case/suite缓存值列表中查找key为name的value
     * 拿到变量类型对应的值（var/list/dict）
     * @param name
     * @param valueCacheMap
     * @return
     */
    // 将一个case的值都存到list<Map>当中，Map对应一个个valuename,valuecontent.
    public static Object getValueByName(String name, Map<String, Object> valueCacheMap){
        Iterator<Map.Entry<String, Object>> entries = valueCacheMap.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, Object> entry = entries.next();
            if(entry.getKey().equals(name)){
                return entry.getValue();
            }
        }
        return null;
    }

    public static int format_int(String input)throws TypeConvertException {

        if(getTypeName(input).equals("int")){
            try {
                int output = Integer.valueOf(input.substring(input.indexOf("{")+1, input.indexOf("}")).trim());
                return output;
            }catch (NumberFormatException e2){
                e2.printStackTrace();
            }
        }else {
            throw new TypeConvertException("类型转换异常：当前类型不是int");
        }
        return -1;
    }

    public static String format_boolean(String input)throws TypeConvertException{
        if (getTypeName(input).equals("boolean")){
            input = input.substring(input.indexOf("{")+1, input.indexOf("}")).trim();
            if(input.toLowerCase().equals("false")){
                return "False";
            }else{
                return "True";
            }
        }else {
            throw new TypeConvertException("类型转换异常：当前类型不是boolean");
        }
    }

    public static String format_String(String input)throws TypeConvertException{
        if (getTypeName(input).equals("String")){
            input = '"' + input + '"';
            return input;
        }else {
            throw new TypeConvertException("类型转换异常：当前类型不是boolean");
        }
    }


}
