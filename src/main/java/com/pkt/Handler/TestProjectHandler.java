package com.pkt.Handler;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TestProjectHandler {
    public Map<String, Object> handlerTestProjectList(List<Map<String, Object>> listInfo, String type){
        Map<String, Object> resMap = new HashMap<String, Object>();
        resMap.put("dirname",type);
        resMap.put("subList",listInfo);
        return resMap;
    }

    public List<Map<String, Object>> handlerTestProjectObject(List<Map<String, Object>> listInfo, String nametype){
        for (Map<String, Object> info : listInfo) {
            info.put("dirname", info.get(nametype));
        }
        return listInfo;
    }
}
