package com.qf.hotel.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * (City)实体类
 *
 * @author ChenJie
 * @since 2020-05-14 15:57:48
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)//开启链式编程  new City.set().set().set()
public class City implements Serializable {
    private static final long serialVersionUID = -70064069501419002L;

    //标记主键，并且设置主键的添加类型为自动增加
    //标记主键便于后面的主键回填
    @TableId(type= IdType.AUTO)
    private Integer id;
    private String cityName;
    private String cityPinyin;
    private Integer hotelNumber;
    private Date createTime;
    private Integer status;

}