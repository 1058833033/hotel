package com.qf.hotel.controller;

import com.qf.hotel.service.ICityService;
import com.qf.hotel.pojo.City;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ChenJie
 * @date 2020-05-14 17:29:05
 * 功能说明:城市服务的对外接口
 */
@RestController
@RequestMapping("/city")
public class CityController {
    @Resource
    private ICityService cityService;
    /**
     * 查询城市列表
     * @return 影响行数
     */
    @RequestMapping("/list")
    public List<City> getCityList(){
        System.out.println("getCityList被调用了");
        return cityService.getCityList();
    }

    /**
     * 添加城市
     * @return 影响行数
     */
    @RequestMapping("/insert")
    public int insert(@RequestBody City city){
        System.out.println("insert被调用了");
        return cityService.insert(city);
    }

    /**
     * 修改城市的酒店数量
     * @return 影响行数
     */
    @RequestMapping("/updateHotelNumber")
    public int updateHotelNumber(@RequestParam Integer cid, @RequestParam Integer number){
        System.out.println("updateHotelNumber被调用了");
        return cityService.updateHotelNumber(cid, number);
    }

    /**
     * 删除城市
     * @param cid 要删除的城市id
     * @return 影响行数
     */
    @RequestMapping("/delete")
    public int deleteCity(@RequestParam Integer cid){
        System.out.println("deleteCity被调用了");
        return cityService.deleteCity(cid);
    };

}
