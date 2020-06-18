package com.qf.hotel.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.awt.geom.FlatteningPathIterator;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.stream.DoubleStream;

/**
 * (Hotel)实体类
 *
 * @author ChenJie
 * @since 2020-05-14 15:57:48
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Hotel implements Serializable {

  //标记主键，并且设置主键的添加类型为自动增加
  //标记主键便于后面的主键回填
  @TableId(type = IdType.AUTO)
  private Integer id;
  private String hotelName;
  private String hotelImage;
  private Integer type;
  private String hotelInfo;
  private String keyword;

  // 不参与json转换  否则会被自动映射保存到ES
  @JSONField(serialize = false)
  private Double lon;
  @JSONField(serialize = false)
  private Double lat;

  // 保存到ES的地址   这个转成json才能被es的坐标类型识别
  // 告诉mybatis plus这个属性不是数据库字段
  @TableField(exist = false)
  private Double[] location = new Double[2];

  private Integer star;
  private String brand;
  private String address;

  @DateTimeFormat(pattern = "yyyy-MM-dd")//页面传来转化的格式
  @JSONField(format = "yyyy-MM-dd")//转json字符串的格式
  private Date openTime;

  private Integer cid;
  private String regid;
  private BigDecimal price;
  private Integer roomNumber;

  // 城市名称
  @TableField(exist = false)
  private String cityName;
  // 商店的点击率
  @TableField(exist = false)
  private Integer clickRate = 0;
  // 酒店的关注量
  @TableField(exist = false)
  private Integer attention = 0;
  // 距离
  @TableField(exist = false)
  private String distance;

  public Hotel setLat(Double lat) {
    this.lat = lat;
    this.location[1] = lat;
    return this;
  }

  public Hotel setLon(Double lon) {
    this.lon = lon;
    this.location[0] = lon;
    return this;
  }

  public void setLocation(Double[] location){
    this.location = location;
    this.lat = location[1];
    this.lon = location[0];
  }
}
