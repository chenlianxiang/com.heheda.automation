package com.heheda.test;


import com.heheda.test.http.HttpClientUtil;
import org.junit.Test;

/**
 * @program: test
 * @description: 测试类
 * @author: clx
 * @create: 2019-08-09 23:56
 */
public class HttpClientUtilTest {


    @Test
    public void testSendPost(){
        String responseContent=HttpClientUtil.getInstance()
                .sendHttpGet("http://127.0.0.1:8080/hello");
        System.out.println(responseContent);
    }
}
