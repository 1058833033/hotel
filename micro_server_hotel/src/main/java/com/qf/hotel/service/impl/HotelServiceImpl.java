package com.qf.hotel.service.impl;

import com.qf.hotel.ICityFeign;
import com.qf.hotel.mapper.HotelMapper;
import com.qf.hotel.pojo.Hotel;
import com.qf.hotel.pojo.HotelEvent;
import com.qf.hotel.service.IHotelService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ChenJie
 * @date 2020-05-17 15:31:52
 * 功能说明
 */
@Service
public class HotelServiceImpl implements IHotelService {
    @Resource
    private HotelMapper hotelMapper;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private ICityFeign cityFeign;
    /**
     * 获取酒店列表
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<Hotel> getHotelList() {
        return hotelMapper.selectList(null);
    }

    /**
     * 保存酒店
     * @param hotel
     * @return
     */
    @Override
    @Transactional
    public int saveHotel(Hotel hotel) {
        // 同步修改城市的酒店数量
//        cityFeign.updateHotelNumber(hotel.getCid(),1);

        // 自己的业务走完了再发消息
        int flag = hotelMapper.insert(hotel);
        // 发布rabbitmq
        rabbitTemplate.convertAndSend("hotel_exchange","add",hotel);
        return flag;
    }

    /**
     * 根据id查询酒店
     * @param id
     * @return
     */
    @Override
    @Transactional
    public Hotel getById(Integer id) {
        return hotelMapper.selectById(id);
    }

    /**
     * 用户点击收藏
     * @param hid
     */
    @Override
    public void attentionHotel(Integer hid) {
        HotelEvent hotelEvent = new HotelEvent().setHid(hid).setEvent(2);
        rabbitTemplate.convertAndSend("hotel_exchange","update",hotelEvent);
    }
}
