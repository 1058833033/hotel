package com.qf.hotel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qf.hotel.pojo.City;
import org.apache.ibatis.annotations.Param;

/**
 * @author ChenJie
 * @date 2020-05-14 18:12:09
 * 功能说明:城市服务dao的接口
 */
public interface CityMapper extends BaseMapper<City> {
    /**
     * 修改城市的酒店数量
     * @param cid
     * @param number
     * @return
     */
    int updateHotelNumber(@Param("cid") Integer cid,@Param("number") Integer number);
}
