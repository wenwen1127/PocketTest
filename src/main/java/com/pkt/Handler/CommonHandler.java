package com.pkt.Handler;

import com.pkt.Entity.Section.Section;
import com.pkt.Entity.Section.SectionTree;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Repository
public class CommonHandler {
    public Map<String,Object> getParams(HttpServletRequest request){
        Map<String,Object> params = new HashMap<String,Object>();
        Set<String> key = request.getParameterMap().keySet();
        for (Iterator<String> it = key.iterator(); it.hasNext();) {
            String name = it.next();
            Object[] value = (Object[]) request.getParameterMap().get(name);
            if(value[0]!=null&&value[0].toString().length()>0)
                params.put(name, value[0]);
        }
        return params;
    }

    public Map<String, Object> getSectionTree(SectionTree sectionTree){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("section_id", sectionTree.getSectionId());
        map.put("section_name", sectionTree.getSectionName());
        if(sectionTree.getSectionTreeList().size()==0){
            map.put("sectionTreeList",null);
            return map;
        }
        List<SectionTree> sectionTreeList = sectionTree.getSectionTreeList();
        List<Map> list = new ArrayList<Map>();
        for (int i = 0; i < sectionTree.getSectionTreeList().size(); i++) {
            list.add(getSectionTree(sectionTreeList.get(i)));
        }
        map.put("sectionTreeList",list);
        return map;
    }

    public ArrayList<Long> handlerJsonArray(String ids){
        String[] idstr = ids.substring(1,ids.length()-1).split(",");
        ArrayList<Long> idlist = new ArrayList<Long>();
        for(int i=0;i<idstr.length;i++){
            idlist.add(Long.valueOf(idstr[i]));
        }
        return idlist;
    }

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
