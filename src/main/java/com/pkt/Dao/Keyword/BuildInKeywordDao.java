package com.pkt.Dao.Keyword;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
@Mapper
public interface BuildInKeywordDao {
    /**
     * 通过bldinkeyword_name获取内部关键字信息
     * @param bldinkeyword_name
     * @return
     */
    public Map<String, Object> getBuildinKeywordByName(String bldinkeyword_name);

    /**
     * 获得分页列表
     * @param params
     * @return
     */
    public Map<String, Object> queryPageList(Map<String, Object> params);



}
