package com.qf.hotel.controller;

import com.qf.hotel.pojo.City;
import com.qf.hotel.pojo.ResultData;
import com.qf.hotel.service.ICityService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ChenJie
 * @date 2020-05-18 12:43:19
 * 功能说明
 */
@RestController
@RequestMapping("/web")
public class WebCityController {
    @Resource
    private ICityService cityService;
    @RequestMapping("/list")
    public ResultData<List<City>> getCityList(){
        List<City> cities = cityService.getCityList();
        return new ResultData<List<City>>().setCode(ResultData.Code.CODE_SUCCESS).setData(cities);
    }
}
