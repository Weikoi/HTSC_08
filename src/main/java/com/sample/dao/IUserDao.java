package com.sample.dao;
import com.sample.domain.QueryVo;
import com.sample.domain.User;

import java.util.List;

public interface IUserDao {
    List<User> findAll();
    User findById(Integer uid);
    int saveUser(User user);
    int updateUser(User user);
    int deleteUser(Integer uid);
    List<User> findByName(String username);
    int count();
    User findByVo(String username, String address);
    List<User> findByVo(QueryVo vo);
}
