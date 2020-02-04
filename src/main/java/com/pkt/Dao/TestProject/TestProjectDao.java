package com.pkt.Dao.TestProject;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Mapper
@Repository
public interface TestProjectDao {
    /**
     * 通过testproject_id获取测试项目信息
     * @param testproject_id
     * @return
     */
    public Map<String, Object> getByTestProjectId(long testproject_id);

    /**
     * 增加一个测试项目信息
     * @param params
     * @return
     */
    public int addTestProject(Map<String,Object> params);

    /**
     * 通过testproject_id删除一个测试项目信息
     * @param testproject_id
     * @return
     */
    public int deleteByTestProjectId(long testproject_id);

    /**
     * 更新测试项目信息
     * @param params
     * @return
     */
    public int editTestProject(Map<String, Object> params);

    /**
     * 获得分页列表
     * @param params
     * @return
     */
    public Map<String, Object> queryPageList(Map<String, Object> params);

    /**
     * 批量删除
     * @param testproject_ids
     * @return
     */
    public int batchDeleteTestProject(long[] testproject_ids);
}
