package com.heheda.test.domain;

import com.heheda.test.anno.IsNeeded;
import lombok.Data;

/**
 * @program: test
 * @description: 请求实体类
 * @author: clx
 * @create: 2019-08-10 14:55
 */
@Data
public class RequestDomain {

    private int No;

    @IsNeeded
    private String api_name;

    @IsNeeded
    private String host;

    @IsNeeded
    private String request_url;

    @IsNeeded
    private String method;

    @IsNeeded
    private String action;

    @IsNeeded
    private String data_type;

    @IsNeeded
    private String request_data;


}
