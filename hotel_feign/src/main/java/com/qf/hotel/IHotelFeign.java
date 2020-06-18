package com.qf.hotel;

import com.qf.hotel.pojo.Hotel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author ChenJie
 * @date 2020-05-17 16:51:17
 * 功能说明:其他服务调用酒店服务的接口
 */
@FeignClient(value = "HOTEL-SERVER")
public interface IHotelFeign {
    /**
     * 获取酒店列表
     * @return
     */
    @RequestMapping("/hotel/list")
    List<Hotel> getHotelList();

    /**
     * 保存酒店
     * @param hotel
     * @return
     */
    @RequestMapping("/hotel/saveHotel")
    int saveHotel(@RequestBody Hotel hotel);

    /**
     * 根据id查询酒店
     * @param id
     * @return
     */
    @RequestMapping("/hotel/getById")
    Hotel getById(Integer id);
}
