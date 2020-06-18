package com.qf.hotel.service.impl;

import com.alibaba.fastjson.JSON;
import com.qf.hotel.pojo.Hotel;
import com.qf.hotel.pojo.HotelEvent;
import com.qf.hotel.pojo.SearchParams;
import com.qf.hotel.service.ISearchService;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.PutMappingRequest;
import org.elasticsearch.common.lucene.search.function.CombineFunction;
import org.elasticsearch.common.lucene.search.function.FunctionScoreQuery;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.common.xcontent.json.JsonXContent;
import org.elasticsearch.index.query.*;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @author ChenJie
 * @date 2020-05-24 22:14:55
 * 功能说明
 */
@Service
public class SearchServiceImpl implements ISearchService {
    @Resource
    private RestHighLevelClient client;

    private static final String INDEX_NAME = "hotel_index";

    /**
     * 创建索引
     *
     * @return
     * @throws IOException
     */
    @Override
    public boolean createIndex() throws IOException {
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(INDEX_NAME);
        Map<String, Object> map = new HashMap<>();
        // 分片数量
        map.put("number_of_shards", 1);
        // 副本数量
        map.put("number_of_replicas", 0);
        Settings settings = createIndexRequest.settings();
        CreateIndexResponse response = client.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        return response.isAcknowledged();
    }

    /**
     * 判断索引是否存在
     *
     * @return
     */
    @Override
    public boolean isIndex() throws IOException {
        GetIndexRequest getIndexRequest = new GetIndexRequest(INDEX_NAME);
        boolean exists = client.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
        return exists;
    }

    /**
     * 删除索引
     *
     * @return
     */
    @Override
    public boolean deleteIndex() throws IOException {
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(INDEX_NAME);
        AcknowledgedResponse response = client.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
        return response.isAcknowledged();
    }


    /*
    * {
          "properties": {
            "hotelName":{
              "type": "text",
              "analyzer": "ik_max_word"
            },
            "hotelInfo":{
              "type": "text",
              "analyzer": "ik_max_word"
            },
            "hotelImage":{
              "type": "keyword",
              "index": false
            },
            "price":{
              "type": "scaled_float",
              "scaling_factor": 100
            },
            "openTime":{
              "type": "date",
              "format": "yyyy-MM-dd"
            },
            "location":{
              "type": "geo_point"
            }
          }
        }
    */

    /**
     * 添加映射关系  创建表结构
     *
     * @return
     */
    @Override
    public boolean addMappings() throws IOException {
        PutMappingRequest putMappingRequest = new PutMappingRequest(INDEX_NAME);

        XContentBuilder builder = JsonXContent.contentBuilder();
        builder
                .startObject()
                .startObject("properties")

                .startObject("hotelName")
                .field("type", "text")
                .field("analyzer", "ik_max_word")
                .endObject()

                .startObject("hotelImage")
                .field("type", "keyword")
                .field("index", "false")
                .endObject()

                .startObject("type")
                .field("type", "integer")
                .endObject()

                .startObject("hotelInfo")
                .field("type", "text")
                .field("analyzer", "ik_max_word")
                .endObject()

                .startObject("keyword")
                .field("type", "text")
                .field("analyzer", "ik_max_word")
                .endObject()

                .startObject("location")
                .field("type", "geo_point")
                .endObject()

                .startObject("star")
                .field("type", "integer")
                .endObject()

                .startObject("brand")
                .field("type", "text")
                .field("analyzer", "ik_max_word")
                .endObject()

                .startObject("address")
                .field("type", "text")
                .field("analyzer", "ik_max_word")
                .endObject()

                .startObject("openTime")
                .field("type", "date")
                .field("format", "yyyy-MM-dd")
                .endObject()

                .startObject("cityName")
                .field("type", "keyword")
                .endObject()

                .startObject("regid")
                .field("type", "text")
                .field("analyzer", "ik_max_word")
                .endObject()

                .startObject("clickRate")
                .field("type", "integer")
                .endObject()

                .startObject("attention")
                .field("type", "integer")
                .endObject()

                .endObject()
                .endObject();

        putMappingRequest.source(builder);
        AcknowledgedResponse response = client.indices().putMapping(putMappingRequest, RequestOptions.DEFAULT);
        return response.isAcknowledged();
    }

    /**
     * 添加文档
     *
     * @param hotel
     * @return
     */
    @Override
    public boolean addDocument(Hotel hotel) throws IOException {
        IndexRequest indexRequest = new IndexRequest(INDEX_NAME, "_doc", hotel.getId() + "");

        // 复杂的json需要自己手动拼写
        // json = {"name":"陈杰","sex":"男","age":18}
        String json = JSON.toJSONString(hotel);
        indexRequest.source(json, XContentType.JSON);
        System.out.println(json);
        IndexResponse response = client.index(indexRequest, RequestOptions.DEFAULT);
        return response.getShardInfo().getSuccessful() == 1;
    }

    /**
     * 根据查询条件执行相应的查询
     * @param searchParams
     * @return
     * @throws IOException
     */
    @Override
    public List<Hotel> query(SearchParams searchParams) throws IOException {
        // 构建查询构建器

        // 按照城市查询
        TermQueryBuilder cityQuery = QueryBuilders.termQuery("cityName", searchParams.getCityName());
        System.out.println(searchParams.getCityName());

        // 通过关键词匹配多个字段
        QueryBuilder keywordQuery = null;
        if (StringUtils.isNotEmpty(searchParams.getKeyword())) {
            // 用户输入了关键字
            keywordQuery = QueryBuilders
                    .multiMatchQuery(searchParams.getKeyword())
                    .field("hotelName").boost(4)
                    .field("brand").boost(2)
                    .field("regid")
                    .field("keyword")
                    .field("hotelInfo");
        }else {
            // 关键字没有就查询本城市所有酒店
            keywordQuery = cityQuery;
        }
        // 通过价格限制条件查询
        RangeQueryBuilder priceQuery = QueryBuilders.rangeQuery("price")
                .gte(searchParams.getMinPrice() == null ? 0 : searchParams.getMinPrice().doubleValue())
                .lte(searchParams.getMaxPrice() == null ? 10000 : searchParams.getMaxPrice().doubleValue());

        // bool查询拼接两个必须满足的条件查询
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery()
                .must(keywordQuery)
                .must(priceQuery);

        // 降级查询 对不是本城市的酒店进行评分降级
        // bool对城市查询取非
        BoolQueryBuilder boolQuery2 = QueryBuilders.boolQuery().mustNot(cityQuery);

        // 使用boosting查询将两个bool查询组合起来
        BoostingQueryBuilder execQuery = QueryBuilders.boostingQuery(
                boolQuery,
                boolQuery2
                ).negativeBoost(0.2f);

        // 构建评分查询器
        List<FunctionScoreQueryBuilder.FilterFunctionBuilder> list = new ArrayList<>();
        list.add(new FunctionScoreQueryBuilder
                .FilterFunctionBuilder(ScoreFunctionBuilders
                .fieldValueFactorFunction("clickRate")
                .setWeight(0.1f)));
        list.add(new FunctionScoreQueryBuilder
                .FilterFunctionBuilder(ScoreFunctionBuilders
                .fieldValueFactorFunction("attention")
                .setWeight(0.5f)));

        FunctionScoreQueryBuilder scoreQuery =
                QueryBuilders.functionScoreQuery(execQuery,
                        list.toArray(new FunctionScoreQueryBuilder.FilterFunctionBuilder[0]))
                .scoreMode(FunctionScoreQuery.ScoreMode.SUM)
                .boostMode(CombineFunction.MULTIPLY);

        // 创建排序器 1-智能排序 2-价格排序 3-距离排序
        SortBuilder sortBuilder = null;
        switch (searchParams.getSortType()){
            case 1:
                break;
            case 2:
                // 价格排序，默认升序
                sortBuilder = SortBuilders.fieldSort("price").order(SortOrder.ASC);
                break;
            case 3:
                // 按照距离排序  单位设置为km   升序排列
                sortBuilder = SortBuilders
                        .geoDistanceSort("location",searchParams.getLat(),searchParams.getLon())
                        .unit(DistanceUnit.KILOMETERS)
                        .order(SortOrder.ASC);
                break;
        }

        // 执行查询
        SearchResponse response = query(scoreQuery, sortBuilder);

        // 封装查询结果

        long totalHits = response.getHits().getTotalHits();
        System.out.println("查询的总条数：" + totalHits);
        if (totalHits==0) {
            // 没查找到就进行模糊查询
            //QueryBuilders.fuzzyQuery();
        }

        // 获得查询结果集
        SearchHit[] hits = response.getHits().getHits();
        // 流式编程   一个hit就是一个文档，也就是一个酒店

        List<Hotel> hotels = new ArrayList<>();

        Arrays.stream(hits).forEach(hit -> {
            //System.out.println("酒店信息：" + hit.getSourceAsString());
            //hit.getSourceAsMap();
            String hotelJson = hit.getSourceAsString();
            Hotel hotel = JSON.parseObject(hotelJson, Hotel.class);
            if(searchParams.getSortType()==3){
                // 按照距离排序
                // 获取距离信息

                System.out.println(hit.getSortValues()[0]);
                Double sortValue = (Double) hit.getSortValues()[0];
                String distance = new DecimalFormat("0.00").format(sortValue) + "km";
                //System.out.println("距离："+sortValue);
                // 截取为两位小数的字符串
                //DecimalFormat decimalFormat = new DecimalFormat("#.00");
                //hotel.setDistance(decimalFormat.format(sortValue)+"km");
                hotel.setDistance(distance);
            }
            System.out.println(hotel);
            hotels.add(hotel);

        });
        return hotels;
    }

    /**
     * 修改酒店的文档信息
     * @param hotelEvent
     * @return
     */
    @Override
    public boolean updateDocument(HotelEvent hotelEvent) throws IOException {

        UpdateByQueryRequest updateByQueryRequest = new UpdateByQueryRequest(INDEX_NAME);

        // 根据酒店id查询文档，后面的脚本就会修改查询出来的文档
        updateByQueryRequest.setQuery(QueryBuilders.idsQuery().addIds(hotelEvent.getHid()+""));

        // 设置修改的脚本
        String script = null;
        if (hotelEvent.getEvent()==1) {
            // 点击酒店的事件
            script = "ctx._source.clickRate=ctx._source.clickRate+1";
        }else if (hotelEvent.getEvent()==2){
            // 收藏酒店的事件
            script = "ctx._source.attention=ctx._source.attention+1";
        }
        updateByQueryRequest.setScript(new Script(script));
        BulkByScrollResponse response = client.updateByQuery(updateByQueryRequest, RequestOptions.DEFAULT);
        //client.update();

        return response.getUpdated() > 0;
    }

    /**
     * 查询基本业务
     * @param queryBuilder
     * @param sortBuilder
     * @return
     * @throws IOException
     */
    private SearchResponse query(QueryBuilder queryBuilder, SortBuilder sortBuilder) throws IOException {
        SearchRequest searchRequest = new SearchRequest(INDEX_NAME);

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder()
                .query(queryBuilder);
        // 如果排序构建器不等于null则设置进去
        if (sortBuilder != null) {
            searchSourceBuilder.sort(sortBuilder);
        }
        //设置查询构建器
        searchRequest.source(searchSourceBuilder);

        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        return response;
    };




















    /**
     * 查询文档
     *
     * @param
     * @return
     */
   /* public List<Hotel> query(QueryBuilder queryBuilder) throws IOException {
        SearchRequest searchRequest = new SearchRequest(INDEX_NAME);

        // 设置高亮信息
        HighlightBuilder highlightBuilder = new HighlightBuilder()
                .field("hotelName")
                .preTags("<font color='red'>")
                .postTags("</font>")
                .numOfFragments(5) // 五个短句  类似百度每条记录下面的小介绍
                .fragmentSize(100); // 每句长度为100

        // 设置查询构建器
        searchRequest.source(new SearchSourceBuilder()
                .query(queryBuilder)
                .highlighter(highlightBuilder));
        //.sort("price", SortOrder.ASC)); // 手动设置排序方式

        // 执行查询
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);

        // 通过响应获得结果
        SearchHit[] hits = response.getHits().getHits();
        for (SearchHit hit : hits) {
            // 获取文档信息
            String id = hit.getId();
            float score = hit.getScore();
            System.out.println("文档id:" + id);
            System.out.println("文档评分:" + score);
            System.out.println("文档内容:");

            // 得到保存的对象信息
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            for (Map.Entry<String, Object> entry : sourceAsMap.entrySet()) {
                System.out.println(entry.getKey() + ":" + entry.getValue());
            }

            // 得到高亮信息
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            for (Map.Entry<String, HighlightField> entry : highlightFields.entrySet()) {
                System.out.println("高亮内容：" + entry.getKey() + "--------" + entry.getValue().getFragments()[0]);
            }
        }
        return null;
    }*/


}
