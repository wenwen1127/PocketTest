package com.pkt.Dao.TestProject;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Mapper
@Repository
public interface TestModuleDao {
    /**
     * 通过module_id获取module信息
     * @param testmodule_id
     * @return
     */
    public Map<String,Object> getByTestModuleId(long testmodule_id);

    /**
     * 增加Module信息
     * @param params
     * @return
     */
    public int addTestModule(Map<String, Object> params);

    /**
     * 通过module_id删除module信息
     * @param testmodule_id
     * @return
     */
    public int deleteByTestModuleId(long testmodule_id);

    /**
     * 修改module信息
     * @param params
     * @return
     */
    public int editTestModule(Map<String, Object> params);

    /**
     * 获得分页列表
     * @param params
     * @return
     */
    public Map<String, Object> queryPageList(Map<String, Object> params);

    /**
     * 批量删除
     * @param testmodule_ids
     * @return
     */
    public int batchDeleteTestModule(long[] testmodule_ids);

}
