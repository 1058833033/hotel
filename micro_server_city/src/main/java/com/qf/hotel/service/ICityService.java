package com.qf.hotel.service;

import com.qf.hotel.pojo.City;
import org.omg.CORBA.INTERNAL;

import java.util.List;

/**
 * @author ChenJie
 * @date 2020-05-14 17:36:53
 * 功能说明:城市服务业务层接口
 */
public interface ICityService {
    /**
     * 获取城市列表接口
     * @return 影响行数
     */
    List<City> getCityList();

    /**
     * 添加城市接口
     * @param city
     * @return 影响行数
     */
    int insert(City city);

    /**
     * 修改城市的酒店数量接口
     * @param cid 更新的城市id
     * @param number 当前城市的酒店数量
     * @return 影响行数
     */
    int updateHotelNumber(Integer cid,Integer number);

    /**
     * 删除城市接口
     * @param cid 要删除城市的id
     * @return 影响行数
     */
    int deleteCity(Integer cid);
}
