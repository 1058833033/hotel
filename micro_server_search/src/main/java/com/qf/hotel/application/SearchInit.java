package com.qf.hotel.application;

import com.qf.hotel.service.ISearchService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author ChenJie
 * @date 2020-05-26 15:58:59
 * 功能说明
 */
// 启动springboot环境之后触发
@Component
public class SearchInit implements CommandLineRunner {
    @Resource
    private ISearchService searchService;
    @Override
    public void run(String... args) throws Exception {
        if (!searchService.isIndex()) {
            searchService.createIndex();
            searchService.addMappings();
            System.out.println("成功创建索引并且构建映射类型");
        }
    }
}
