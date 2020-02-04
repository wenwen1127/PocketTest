package com.pkt.Service.Keyword;

import com.pkt.Dao.Keyword.BuildInKeywordDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class BuildInKeywordService {
    @Autowired
    private BuildInKeywordDao buildInKeywordDao;

    public Map<String, Object> getBuildinKeywordByName(String bldinkeyword_name){
        return buildInKeywordDao.getBuildinKeywordByName(bldinkeyword_name);
    }

    public Map<String, Object> queryPageList(Map<String, Object> params){
        return buildInKeywordDao.queryPageList(params);
    }

}
