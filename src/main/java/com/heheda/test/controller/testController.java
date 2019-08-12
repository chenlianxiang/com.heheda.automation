package com.heheda.test.controller;

import com.alibaba.fastjson.JSON;
import com.heheda.test.domain.RequestBean;
import com.heheda.test.domain.RequestDomain;
import com.heheda.test.excel.ExcelUtil;
import com.heheda.test.excel.ImportExeclUtil;
import com.heheda.test.http.HttpClientUtil;
import org.apache.http.client.utils.URIBuilder;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: test
 * @description: 测试控制器
 * @author: clx
 * @create: 2019-08-10 00:00
 */

@RestController
public class testController {

    @Autowired
    ExcelUtil excelUtil;

    @RequestMapping("hello")
    public String hello(){
        String response="{\"rtn\":\"ok\",\"msg\":\"测试成功了\"}";
        return response;
    }

    @RequestMapping("api/test")
    public String test1(@RequestParam String request_data){

        System.out.println(request_data);
        String response="{\"rtn\":\"ok\",\"msg\":\""+request_data+"\"}";
        return response;
    }

    @RequestMapping("/api/test1")
    public String test2(@RequestParam String request_data){

        System.out.println(request_data);
        String response="{\"rtn\":\"ok\",\"msg\":\""+request_data+"\"}";
        return response;
    }



    @RequestMapping("test")
    public void test() throws Exception {
//        String path = ClassUtils.getDefaultClassLoader().getResource("static").getPath()+"/ceshi.xlsx";
        String path= ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "static/ceshi.xlsx").getPath();
        String p="C:\\Users\\thinkpad\\Desktop\\ceshi.xlsx";

        List<RequestBean> requestBeans = excelUtil.readData(path, 0);
        for (RequestBean bean:requestBeans){

           String url=bean.getHost()+":"+bean.getPort()+"/"+bean.getRequest_url();
            Map<String,String> map=new HashMap<>();
            map.put("request_data",bean.getRequest_data());
            System.out.println(HttpClientUtil.getInstance().sendHttpPost(url,map));
        }
//        System.out.println(JSON.toJSONString(requestBeans));
    }
}
