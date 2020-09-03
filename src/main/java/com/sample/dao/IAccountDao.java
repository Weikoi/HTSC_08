package com.sample.dao;

import com.sample.domain.User;

import java.util.List;

public interface IAccountDao {
    List<User> findAll();
}
