package com.qf.hotel.listener;

import com.qf.hotel.pojo.Hotel;
import com.qf.hotel.service.ICityService;
import com.qf.hotel.service.impl.CityServiceImpl;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author ChenJie
 * @date 2020-05-23 18:00:58
 * 功能说明
 */
@Component
public class RabbitMsgListener {
    @Resource
    private ICityService cityService;

    // 使用注解进行交换机与队列的绑定
    @RabbitListener(bindings = {
            @QueueBinding(
                    exchange = @Exchange(name = "hotel_exchange",type = "direct"),
                    value = @Queue(name = "city_queue",durable = "true"),
                    key = {"add"}
            )

    })
    public void msgHandler(Hotel hotel, Channel channel, Message message){
        System.out.println("城市服务接收到消息：" + hotel);
        // 修改对应的城市酒店数量
        cityService.updateHotelNumber(hotel.getCid(),1);

        // 手动确认
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
