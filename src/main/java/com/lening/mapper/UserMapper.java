package com.lening.mapper;

import java.util.List;

import com.lening.pojo.User;
import com.lening.vo.UserVo;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

	List<User> findAll();

	List<UserVo> findVo();

	User findByUsername(String username);
}