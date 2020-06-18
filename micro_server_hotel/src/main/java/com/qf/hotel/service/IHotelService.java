package com.qf.hotel.service;

import com.qf.hotel.pojo.Hotel;
import com.qf.hotel.pojo.ResultData;

import java.util.List;

/**
 * @author ChenJie
 * @date 2020-05-17 15:31:24
 * 功能说明:酒店业务层接口
 */
public interface IHotelService {
    /**
     * 获取酒店列表
     * @return
     */
    List<Hotel> getHotelList();

    /**
     * 保存酒店
     * @param hotel
     * @return
     */
    int saveHotel(Hotel hotel);

    /**
     * 根据id查询酒店
     * @param id
     * @return
     */
    Hotel getById(Integer id);

    /**
     * 用户点击收藏
     * @param hid
     */
    void attentionHotel(Integer hid);
}
