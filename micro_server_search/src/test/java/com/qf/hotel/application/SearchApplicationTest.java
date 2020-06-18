package com.qf.hotel.application;

import com.qf.hotel.pojo.Hotel;
import com.qf.hotel.service.ISearchService;
import org.elasticsearch.index.query.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SearchApplication.class)
public class SearchApplicationTest {
    @Resource
    private ISearchService searchService;

    @Test
    public void test() throws IOException {
        // 判断索引是否存在
        if (!searchService.isIndex()) {
            // 不存在就创建
            System.out.println("创建索引！");
            searchService.createIndex();
            // 创建映射关系
            searchService.addMappings();
            System.out.println("映射关系创建成功!");
        }else {
            System.out.println("索引库已经存在！");
        }
    }

    @Test
    public void addDoc() throws IOException {
        Hotel hotel1 = new Hotel()
                .setId(1)
                .setHotelName("豪华大酒店")
                .setLat(29.55643)
                .setLon(106.553263)
                .setHotelInfo("很贵的酒店")
                .setPrice(BigDecimal.valueOf(1789.99))
                .setBrand("希尔顿");

        Hotel hotel2 = new Hotel()
                .setId(2)
                .setHotelName("星级无敌酒店")
                .setLat(29.51243)
                .setLon(106.233263)
                .setHotelInfo("性价比很高的大酒店")
                .setPrice(BigDecimal.valueOf(1993.36))
                .setBrand("速8");

        Hotel hotel3 = new Hotel()
                .setId(3)
                .setHotelName("无涯酒店")
                .setLat(29.66643)
                .setLon(106.783263)
                .setHotelInfo("超级贵的酒店")
                .setPrice(BigDecimal.valueOf(99.99))
                .setBrand("香格里拉");

        Hotel hotel4 = new Hotel()
                .setId(4)
                .setHotelName("香格里拉")
                .setLat(29.00643)
                .setLon(106.883263)
                .setHotelInfo("上等酒店")
                .setPrice(BigDecimal.valueOf(2999.99))
                .setBrand("7天");

        Hotel hotel5 = new Hotel()
                .setId(5)
                .setHotelName("等你来客栈")
                .setLat(29.89433)
                .setLon(106.500063)
                .setHotelInfo("一般民宿")
                .setPrice(BigDecimal.valueOf(69.99))
                .setBrand("速8");
        searchService.addDocument(hotel1);
        searchService.addDocument(hotel2);
        searchService.addDocument(hotel3);
        searchService.addDocument(hotel4);
        searchService.addDocument(hotel5);
        System.out.println("文档添加成功！");
    }

    @Test
    public void search() throws IOException {
        // term查询
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("brand", "7天");

        // match查询
        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery("大")
                .field("hotelName")
                .field("hotelInfo");
        MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("hotelName","酒店");


        // bool查询
        /*QueryBuilders.boolQuery()
                .must(termQueryBuilder)// 必须满足的条件
                .must(multiMatchQueryBuilder)// 必须满足的条件
                .should(null)// 没有must必须满足一个should，有must可以不满足
                .should(null)// 没有must必须满足一个should，有must可以不满足
                .mustNot(null)// 满足这个条件的结果排除
                .minimumShouldMatch(1); // should至少有n个满足*/

        //searchService.query(matchQueryBuilder);
    }
}