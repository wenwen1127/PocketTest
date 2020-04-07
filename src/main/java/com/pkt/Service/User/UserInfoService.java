package com.pkt.Service.User;

import com.pkt.Dao.User.UserInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserInfoService {
    @Autowired
    private UserInfoDao userInfoDao;

    public Map<String, Object> getUserInfoById(long user_id) {
        return userInfoDao.getUserInfoById(user_id);
    }

    public int addUser(Map<String,Object> params){
        return userInfoDao.addUser(params);
    }

    public int count(Map<String, Object> params){
        return userInfoDao.count(params);
    }

    public Map<String, Object> queryList(Map<String, Object> params){
        return userInfoDao.queryList(params);
    }

    public int edit(Map<String, Object> params){
        return userInfoDao.edit(params);
    }

    public List<Map<String, Object>> queryPageList(Map<String,Object> params){
        return userInfoDao.queryPageList(params);
    }
}
