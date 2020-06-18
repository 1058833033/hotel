package com.qf.hotel.controller;

import com.qf.hotel.aop.IsLogin;
import com.qf.hotel.aop.LoginParams;
import com.qf.hotel.pojo.Hotel;
import com.qf.hotel.pojo.HotelEvent;
import com.qf.hotel.pojo.ResultData;
import com.qf.hotel.service.IHotelService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author ChenJie
 * @date 2020-05-28 12:37:04
 * 功能说明
 */
@RestController
@RequestMapping("/webhotel")
public class WebHotelController {
    @Resource
    private IHotelService hotelService;

    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * 用户点击酒店详情   点击量+1
     * @param hid
     * @return
     */
    @RequestMapping("/info")
    public ResultData<Hotel> getHotelInfo(@RequestParam Integer hid){
        // 除了查询酒店信息之外，还需要修改点击量，相当于进行了一次点击
        HotelEvent hotelEvent = new HotelEvent()
                .setHid(hid)
                .setEvent(1);

        // 发送消息给Search服务
        rabbitTemplate.convertAndSend("hotel_exchange","update",hotelEvent);

        Hotel hotel = hotelService.getById(hid);
        return new ResultData<Hotel>().setCode(ResultData.Code.CODE_SUCCESS).setData(hotel);
    }

    /**
     * 用户关注收藏了某个酒店
     * 首先利用登录的自定义注解，判断用户是否登录
     * @param hid
     * @return
     */
    // 一般业务与登陆有三种场景
    // 1.这个业务不需要用户登录（直接不标记IsLogin自定义注解）
    // 2.这个业务根据用户是否登录做出不同动作，例如登录后可以免广告
    // 3.使用这个服务必须强制登录
    @RequestMapping("/attention")
    @IsLogin(mustLogin = true)
    public ResultData<Hotel> attentionHotel(@RequestParam Integer hid){
        hotelService.attentionHotel(hid);
        System.out.println(LoginParams.getUser());
        return new ResultData<Hotel>().setCode(ResultData.Code.CODE_SUCCESS);
    }
}
