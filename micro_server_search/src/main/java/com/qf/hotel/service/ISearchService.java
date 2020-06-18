package com.qf.hotel.service;

import com.qf.hotel.pojo.Hotel;
import com.qf.hotel.pojo.HotelEvent;
import com.qf.hotel.pojo.SearchParams;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import java.io.IOException;
import java.util.List;

/**
 * @author ChenJie
 * @date 2020-05-24 22:14:24
 * 功能说明
 */
public interface ISearchService {
    /**
     * 创建索引库
     * @return
     */
    boolean createIndex() throws IOException;

    /**
     * 判断索引库是否存在
     * @return
     */
    boolean isIndex() throws IOException;

    /**
     * 删除索引库
     * @return
     */
    boolean deleteIndex() throws IOException;

    /**
     * 添加索引映射
     * @return
     */
    boolean addMappings() throws IOException;

    /**
     * 添加文档
     * @param hotel
     * @return
     */
    boolean addDocument(Hotel hotel) throws IOException;

    /**
     * 查询文档
     * @param
     * @return
     */
    List<Hotel> query(SearchParams searchParams) throws IOException;

    /**
     * 修改酒店的文档信息
     * @param hotelEvent
     * @return
     */
    boolean updateDocument(HotelEvent hotelEvent) throws IOException;


}
