package com.qf.hotel.controller;

import com.qf.hotel.pojo.Hotel;
import com.qf.hotel.service.IHotelService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ChenJie
 * @date 2020-05-17 15:31:02
 * 功能说明
 */
@RestController
@RequestMapping("/hotel")
public class HotelController {
    @Resource
    private IHotelService hotelService;

    /**
     * 获取酒店列表
     * @return
     */
    @RequestMapping("/list")
    public List<Hotel> getHotelList(){
        return hotelService.getHotelList();
    }

    /**
     * 保存酒店
     * @param hotel
     * @return
     */
    @RequestMapping("/saveHotel")
    public int saveHotel(@RequestBody Hotel hotel){
        return hotelService.saveHotel(hotel);
    }

    /**
     * 根据id查询酒店
     * @param id
     * @return
     */
    @RequestMapping("/getById")
    public Hotel getById(Integer id){
        return hotelService.getById(id);
    }

}
