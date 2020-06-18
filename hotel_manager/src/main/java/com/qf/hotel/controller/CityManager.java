package com.qf.hotel.controller;

import com.qf.hotel.ICityFeign;
import com.qf.hotel.pojo.City;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ChenJie
 * @date 2020-05-14 21:32:19
 * 功能说明:通过feign接口调用城市服务
 */
@Controller
@RequestMapping("/manager")
public class CityManager {
    @Resource
    private ICityFeign cityFeign;

    /**
     * 进入添加页面
     * @return
     */
    @RequestMapping("/toaddcity")
    public String toAddCity(){
        return "addcity";
    }

    /**
     * 添加城市
     * @param city
     * @return
     */
    @RequestMapping("/addcity")
    public String addCity(City city){
        // 通过feign接口调用城市服务
        cityFeign.insert(city);
        return "redirect:/manager/citylist";
    }

    /**
     * 展示城市列表
     * @param model
     * @return
     */
    @RequestMapping("/citylist")
    public String getCityList(Model model){
        List<City> cities = cityFeign.getCityList();
        model.addAttribute("cities",cities);
        return "listcity";
    }

    /**
     * 进入修改页面
     * @return
     */
    @RequestMapping("/toupdate")
    public String toUpdate(Integer cid,Model model){
        System.out.println(cid);
        model.addAttribute("cid",cid);
        return "updatecity";
    }

    /**
     * 进行修改城市的酒店数量
     * @param cid
     * @param number
     * @return
     */
    @RequestMapping("update")
    public String updateHotelNumber(Integer cid,Integer number){
        System.out.println(cid);
        System.out.println(number);
        cityFeign.updateHotelNumber(cid, number);
        return "redirect:/manager/citylist";
    }

    /**
     * 删除城市
     *
     * @param cid 要删除的城市id
     * @return
     */
    @RequestMapping("/delete")
    public String deleteCity(@RequestParam Integer cid) {
        System.out.println(cid);
        cityFeign.deleteCity(cid);
        return "redirect:/manager/citylist";
    }
}
