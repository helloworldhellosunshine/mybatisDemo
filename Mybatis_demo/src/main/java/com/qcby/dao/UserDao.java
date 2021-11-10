package com.qcby.dao;

import com.qcby.entity.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserDao {

    public List<User> findAll();

}
