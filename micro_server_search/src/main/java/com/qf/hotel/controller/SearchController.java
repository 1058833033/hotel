package com.qf.hotel.controller;

import com.qf.hotel.pojo.Hotel;
import com.qf.hotel.pojo.ResultData;
import com.qf.hotel.pojo.SearchParams;
import com.qf.hotel.service.ISearchService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @author ChenJie
 * @date 2020-05-27 18:41:02
 * 功能说明
 */
@RestController
@RequestMapping("/search")
public class SearchController {
    @Resource
    private ISearchService searchService;

    @RequestMapping("/searchlist")
    public ResultData<List<Hotel>> searchList(SearchParams searchParams) throws IOException {
        List<Hotel> hotels = searchService.query(searchParams);
        System.out.println(searchParams);
        return new ResultData<List<Hotel>>().setCode(ResultData.Code.CODE_SUCCESS).setData(hotels);
    }

}
