package com.pkt.Dao.TestProject;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface TestCaseDao {
    /**
     * 通过case_id获取测试用例信息
     * @param case_id
     * @return
     */
    public Map<String, Object> getByCaseId(long case_id);

    /**
     * 增加一个测试用例信息
     * @param params
     * @return
     */
    public int addTestCase(Map<String,Object> params);

    /**
     * 通过suite_id删除一个测试用例信息
     * @param case_id
     * @return
     */
    public int deleteByCaseId(long case_id);

    /**
     * 更新测试用例信息
     * @param params
     * @return
     */
    public int editTestCase(Map<String, Object> params);

    /**
     * 获得分页列表
     * @param params
     * @return
     */
    public List<Map<String, Object>> queryPageList(Map<String, Object> params);

    /**
     * 批量删除
     * @param case_ids
     * @return
     */
    public int batchDeleteTestCase(long[] case_ids);

    /**
     * 通过给定参数删除
     * @param params
     * @return
     */
    public int deleteByParams(Map<String, Object> params);

    /**
     * 通过case_id 获取所属的suite的所有信息
     * @param case_id
     * @return
     */
    public Map<String, Object> getSuiteInfoByCaseId(long case_id);

    /**
     * 更新case的结果
     * @param params
     * @return
     */
    public int updateCaseResult(Map<String,Object> params);

    /**
     * 通过其他信息获取
     * @param
     * @return
     */
    public long getCaseIdByParams(String case_name, long suite_id);

}
