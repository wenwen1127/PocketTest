package com.pkt.Dao.TestProject;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Mapper
@Repository
public interface TagDao {
    /**
     * 通过tag_id获取标签信息
     * @param tag_id
     * @return
     */
    public Map<String, Object> getByTagId(long tag_id);

    /**
     * 增加一个标签信息
     * @param params
     * @return
     */
    public int addTag(Map<String,Object> params);

    /**
     * 通过tag_id删除一个标签信息
     * @param tag_id
     * @return
     */
    public int deleteByTagId(long tag_id);

    /**
     * 更新标签信息
     * @param params
     * @return
     */
    public int editTag(Map<String, Object> params);
}
