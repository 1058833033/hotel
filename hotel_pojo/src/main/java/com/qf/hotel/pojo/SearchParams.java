package com.qf.hotel.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author ChenJie
 * @date 2020-05-27 15:49:38
 * 功能说明
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class SearchParams implements Serializable {
    // 关键字
    private String  keyword;
    // 最低价
    private BigDecimal  minPrice;
    // 最高价
    private BigDecimal  maxPrice;
    // 城市名称
    private String  cityName;
    // 位置信息
    private Double  lat;
    private Double  lon;
    // 排序类型
    private Integer sortType = 1;//1 - 智能排序 2 - 价格排序 3 - 距离排序
}
