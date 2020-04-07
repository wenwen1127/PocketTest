package com.pkt.Dao.User;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface UserInfoDao {
    /**
     * 通过主键获取map对象
     * @param user_id
     * @return
     */
    public Map<String,Object> getUserInfoById(long user_id);

    /**
     * 添加用户
     * @param params
     * @return
     */
    public int addUser(Map<String, Object> params);

    /**
     * 统计
     * @param params
     * @return
     */
    public int count(Map<String, Object> params);

    /**
     * 返回一个user的所有info
     * @param params
     * @return
     */
    public Map<String, Object> queryList(Map<String, Object> params);

    /**
     * 修改用户信息
     * @param params
     * @return
     */
    public int edit(Map<String, Object> params);

    /**
     * 分页获取用户信息
     * @param params
     * @return
     */
    public List<Map<String, Object>> queryPageList(Map<String, Object> params);

}
