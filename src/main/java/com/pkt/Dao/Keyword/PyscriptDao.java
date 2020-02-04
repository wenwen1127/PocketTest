package com.pkt.Dao.Keyword;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface PyscriptDao {
    /**
     * 通过pyscript_id获取脚本信息
     * @param pyscript_id
     * @return
     */
    public Map<String, Object> getByPyscriptId(long pyscript_id);

    /**
     * 增加一个脚本信息
     * @param params
     * @return
     */
    public int addPyscript(Map<String,Object> params);

    /**
     * 通过tag_id删除一个脚本信息
     * @param pyscript_id
     * @return
     */
    public int deleteByPyscriptId(long pyscript_id);

    /**
     * 更新脚本信息
     * @param params
     * @return
     */
    public int editPyscript(Map<String, Object> params);

    /**
     * 获得分页列表
     * @param params
     * @return
     */
    public Map<String, Object> queryPageList(Map<String, Object> params);

    /**
     * 批量删除
     * @param pyscript_ids
     * @return
     */
    public int batchDeletePyscript(long[] pyscript_ids);

    /**
     * 通过params获取pyscript信息
     * @param params
     * @return
     */
    public List<Map<String, Object>> getPyscriptByParams(Map<String, Object> params);

}
