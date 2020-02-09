package com.pkt.Dao.TestProject;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface TestSuiteDao {
    /**
     * 通过suite_id获取测试套件信息
     * @param suite_id
     * @return
     */
    public Map<String, Object> getBySuiteId(long suite_id);

    /**
     * 增加一个测试套件信息
     * @param params
     * @return
     */
    public int addTestSuite(Map<String,Object> params);

    /**
     * 通过suite_id删除一个测试套件信息
     * @param suite_id
     * @return
     */
    public int deleteBySuiteId(long suite_id);

    /**
     * 更新测试套件信息
     * @param params
     * @return
     */
    public int editTestSuite(Map<String, Object> params);

    /**
     * 获得分页列表
     * @param params
     * @return
     */
    public List<Map<String, Object>> queryPageList(Map<String, Object> params);

    /**
     * 批量删除
     * @param suite_ids
     * @return
     */
    public int batchDeleteTestSuite(long[] suite_ids);
}
