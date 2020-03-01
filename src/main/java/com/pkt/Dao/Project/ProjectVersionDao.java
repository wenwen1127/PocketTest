package com.pkt.Dao.Project;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface ProjectVersionDao {

    /**
     * 通过version_id获取version的所有信息
     * @param version_id
     * @return
     */
    public Map<String, Object> getByVersionId(int version_id);

    /**
     * 增加一个version信息
     * @param params
     * @return
     */
    public int addVersion(Map<String, Object> params);

    /**
     * 通过version_id删除version信息
     * @param version_id
     * @return
     */
    public int deleteByVersionId(int version_id);

    /**
     * 修改version信息
     * @param params
     * @return
     */
    public int editVersion(Map<String, Object> params);
    /**
     * 分页获取版本信息
     * @param params
     * @return
     */
    public List<Map<String, Object>> queryPageList(Map<String,Object> params);

    /**
     * 获取版本信息
     * @param params
     * @return
     */
    public List<Map<String, Object>> queryList(Map<String,Object> params);
}
