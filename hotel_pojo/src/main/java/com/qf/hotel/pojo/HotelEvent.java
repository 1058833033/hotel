package com.qf.hotel.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author ChenJie
 * @date 2020-05-28 13:17:00
 * 功能说明
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class HotelEvent implements Serializable {

    // 酒店id
    private Integer hid;

    // 1-点击量  2-收藏量
    private Integer event;
}
