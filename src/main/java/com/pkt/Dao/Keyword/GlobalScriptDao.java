package com.pkt.Dao.Keyword;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface GlobalScriptDao {

    /**
     * 通过globalscript_id获取全局脚本信息
     * @param globalscript_id
     * @return
     */
    public Map<String, Object> getByGlobalScriptId(long globalscript_id);

    /**
     * 通过globalscript_name获取全局脚本信息
     * @param globalscriopt_name
     * @return
     */
    public Map<String, Object> getByGlobalScriptName(String globalscriopt_name);

    /**
     * 增加一个全局脚本信息
     * @param params
     * @return
     */
    public int addGlobalScript(Map<String,Object> params);

    /**
     * 通过tag_id删除一个全局脚本信息
     * @param globalscript_id
     * @return
     */
    public int deleteByGlobalScriptId(long globalscript_id);

    /**
     * 更新全局脚本信息
     * @param params
     * @return
     */
    public int editGlobalScript(Map<String, Object> params);

    /**
     * 获得分页列表
     * @param params
     * @return
     */
    public List<Map<String, Object>> queryPageList(Map<String, Object> params);

}
