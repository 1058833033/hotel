package com.qf.hotel;

import com.qf.hotel.pojo.City;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author ChenJie
 * @date 2020-05-15 12:39:31
 * 功能说明:其他服务调用城市服务的接口
 */
@FeignClient(value = "CITY-SERVER")
public interface ICityFeign {
    /**
     * 查询城市列表
     * @return
     */
    @RequestMapping("/city/list")
    List<City> getCityList();

    /**
     * 添加城市
     * @return
     */
    @RequestMapping("/city/insert")
    int insert(@RequestBody City city);

    /**
     * 修改城市的酒店数量
     * @return
     */
    @RequestMapping("/city/updateHotelNumber")
    int updateHotelNumber(@RequestParam Integer cid, @RequestParam Integer number);

    /**
     * 删除城市
     * @param cid 要删除的城市id
     * @return 影响行数
     */
    @RequestMapping("/city/delete")
    int deleteCity(@RequestParam Integer cid);
}
