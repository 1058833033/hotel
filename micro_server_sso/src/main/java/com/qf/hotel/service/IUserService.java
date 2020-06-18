package com.qf.hotel.service;

import com.qf.hotel.pojo.User;

/**
 * @author ChenJie
 * @date 2020-05-28 21:30:11
 * 功能说明
 */
public interface IUserService {

    int insert(User user);

    User queryByUserName(String username);

}
