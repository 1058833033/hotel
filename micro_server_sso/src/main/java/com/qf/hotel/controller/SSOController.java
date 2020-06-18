package com.qf.hotel.controller;

import com.qf.hotel.pojo.ResultData;
import com.qf.hotel.pojo.User;
import com.qf.hotel.service.IUserService;
import com.qf.hotel.utils.JwtUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ChenJie
 * @date 2020-05-28 21:24:27
 * 功能说明
 */
@RestController
@RequestMapping("/sso")
public class SSOController {

    @Resource
    private IUserService userService;

    /**
     * 注册
     * @param user
     * @return
     */
    @RequestMapping("/register")
    public ResultData register(User user){
        int result = userService.insert(user);
        if (result > 0){
            return new ResultData().setCode(ResultData.Code.CODE_SUCCESS).setData(true);
        }
        return new ResultData().setCode(ResultData.Code.CODE_ERROR).setMsg("账号已存在，注册失败！");
    }

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    @RequestMapping("/login")
    public ResultData login(@RequestParam String username, @RequestParam String password){
        // 进行查询
        User user = userService.queryByUserName(username);
        System.out.println(user);
        if (user != null && user.getPassword().equals(password)) {
            // 登录成功
            String jwtToken = JwtUtil.createJwtToken(user);
            Map<String,String> map = new HashMap<>();
            map.put("nickname",user.getNickname());
            map.put("jwtToken",jwtToken);
            return new ResultData().setCode(ResultData.Code.CODE_SUCCESS).setData(map);
        }
        return new ResultData().setCode(ResultData.Code.CODE_ERROR).setMsg("密码错误！");
    }
}
