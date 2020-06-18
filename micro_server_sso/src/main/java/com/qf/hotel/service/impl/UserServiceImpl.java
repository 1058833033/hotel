package com.qf.hotel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qf.hotel.mapper.UserMapper;
import com.qf.hotel.pojo.User;
import com.qf.hotel.service.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author ChenJie
 * @date 2020-05-28 21:33:09
 * 功能说明
 */
@Service
public class UserServiceImpl implements IUserService {

    @Resource
    private UserMapper userMapper;

    @Override
    @Transactional
    public int insert(User user) {
        return userMapper.insert(user);
    }

    @Override
    @Transactional
    public User queryByUserName(String username) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username",username);
        return userMapper.selectOne(queryWrapper);
    }
}
