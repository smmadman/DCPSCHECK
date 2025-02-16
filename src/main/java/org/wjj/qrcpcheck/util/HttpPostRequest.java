package org.wjj.qrcpcheck.util;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

//@Async
@Component
public class HttpPostRequest {

//    public static void main(String[] args) {
//        String url = "https://api.example.com/post";
//        String requestBody = "{\"key1\":\"value1\",\"key2\":\"value2\"}";
//
//        try {
//            String response = executePostRequest(url, requestBody);
//            System.out.println(response);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public String executePostRequest(String url, Map<String, Object> headerMap, String requestBody) throws IOException {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(url);

        // 设置请求体
        StringEntity params = new StringEntity(requestBody);
        if(headerMap != null){
            headerMap.forEach((key, value)-> {
               request.setHeader(key, value.toString());
            }); //
        }
        request.setEntity(params);

        // 发送请求，并获取响应
        HttpResponse response = httpClient.execute(request);

        // 读取响应内容
        String result = EntityUtils.toString(response.getEntity());

        return result;
    }
}