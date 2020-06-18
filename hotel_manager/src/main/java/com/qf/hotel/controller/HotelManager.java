package com.qf.hotel.controller;

import com.qf.hotel.ICityFeign;
import com.qf.hotel.IHotelFeign;
import com.qf.hotel.pojo.City;
import com.qf.hotel.pojo.Hotel;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import sun.nio.ch.IOUtil;

import javax.annotation.Resource;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

/**
 * @author ChenJie
 * @date 2020-05-17 16:58:11
 * 功能说明
 */
@Controller
@RequestMapping("/manager")
public class HotelManager{

    @Resource
    private ICityFeign cityFeign;
    @Resource
    private IHotelFeign hotelFeign ;

    @RequestMapping("/hotellist")
    public String hotelList(Model model){
        List<Hotel> hotels = hotelFeign.getHotelList();
        model.addAttribute("hotels",hotels);
        return "listhotel";
    }

    /**
     * 前往酒店展示页面
     * @return
     */
    @RequestMapping("/toaddthotel")
    public String toAddHotel(Model model){
        List<City> cities = cityFeign.getCityList();
        model.addAttribute("cities",cities);
        return "addhotel";
    }

    @RequestMapping("/addhotel")
    public String addHotel(MultipartFile file, Hotel hotel) throws IOException {

        // 生成一个文件名
        String name = UUID.randomUUID().toString();
        // 获得当前服务器的classpath路径   此方式打成jar包就不可用了
        String upLoadPath = HotelManager.class.getResource("/").getPath() + "static/image/" + name;
        System.out.println(upLoadPath);
        try (
                InputStream in = file.getInputStream();
                OutputStream out = new FileOutputStream(upLoadPath);
        ){
            IOUtils.copy(in, out);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
        hotel.setHotelImage("http://localhost:9090/image/" +name);
        System.out.println(hotel);
        hotelFeign.saveHotel(hotel);
        return "succ";
    }

}
