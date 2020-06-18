package com.qf.hotel.listener;

import com.qf.hotel.pojo.Hotel;
import com.qf.hotel.pojo.HotelEvent;
import com.qf.hotel.service.ISearchService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author ChenJie
 * @date 2020-05-23 18:00:58
 * 功能说明
 */
@Component
// 使用注解进行交换机与队列的绑定
@RabbitListener(bindings = {
        @QueueBinding(
                exchange = @Exchange(name = "hotel_exchange",type = "direct"),
                value = @Queue(name = "search_queue",durable = "true"),
                key = {"add","update"}
        )

})
public class RabbitMsgListener {
    @Resource
    private ISearchService searchService;

    // 根据接收的参数类型自动调用方法

    /**
     * 对添加的酒店保存到ES服务器中
     * @param hotel
     * @param channel
     * @param message
     */
    @RabbitHandler
    public void msgHandler(Hotel hotel, Channel channel, Message message){
        System.out.println("城市服务接收到消息：" + hotel);
        try {
            // 保存到ES
            searchService.addDocument(hotel);
            // 手动确认
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理酒店的各种事件
     * @param hotelEvent
     * @param channel
     * @param message
     */
    @RabbitHandler
    public void msgHandler(HotelEvent hotelEvent, Channel channel, Message message){
        try {
            System.out.println("当前接收到的事件：" + hotelEvent);
            searchService.updateDocument(hotelEvent);
            // 手动确认
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
