package com.qf.hotel.service.impl;

import com.qf.hotel.mapper.CityMapper;
import com.qf.hotel.pojo.City;
import com.qf.hotel.service.ICityService;
import com.qf.hotel.utils.PinyinUtile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ChenJie
 * @date 2020-05-14 17:46:34
 * 功能说明:城市服务业务层的实现类
 */
@Service
public class CityServiceImpl implements ICityService {

    @Resource
    private CityMapper cityDao;

    /**
     * 获取城市列表
     * @return 影响行数
     */
    @Override
    @Transactional(readOnly = true)
    public List<City> getCityList() {
        return cityDao.selectList(null);
    }

    /**
     * 添加城市
     * @param city 城市实体对象
     * @return 影响行数
     */
    @Override
    @Transactional
    public int insert(City city) {
        // 生成城市的拼音
        city.setCityPinyin(PinyinUtile.str2Pinyin(city.getCityName()));

        return cityDao.insert(city);
    }

    /**
     * 修改城市的酒店数量
     * @param cid 城市的id
     * @param number 酒店的数量
     * @return 影响的行数
     */
    @Override
    @Transactional
    public int updateHotelNumber(Integer cid, Integer number) {
        return cityDao.updateHotelNumber(cid, number);
    }

    /**
     * 删除当前id的城市
     * @param cid 要删除城市的id
     * @return 影响行数
     */
    @Override
    public int deleteCity(Integer cid) {
        return cityDao.deleteById(cid);
    }
}
