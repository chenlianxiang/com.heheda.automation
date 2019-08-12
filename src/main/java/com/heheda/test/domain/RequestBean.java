package com.heheda.test.domain;

import com.heheda.test.anno.DescAnno;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

/**
 * @program: test
 * @description:
 * @author: clx
 * @create: 2019-08-10 23:43
 */

@Data
public class RequestBean {

//    @DescAnno(desc = "编号")
//    private int id;

    @DescAnno(desc = "API名称")
    private String api_name;

    @DescAnno(desc = "服务器地址")
    private String host;

    @DescAnno(desc = "请求端口")
    private String port;

    @DescAnno(desc = "请求地址")
    private String request_url;

    @DescAnno(desc = "请求类型")
    private String method;

    @DescAnno(desc = "动作")
    private String action;

    @DescAnno(desc = "数据类型")
    private String data_type;

    @DescAnno(desc = "请求参数")
    private String request_data;

}
