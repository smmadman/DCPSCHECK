package org.wjj.qrcpcheck.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.ls.LSException;
import org.wjj.qrcpcheck.common.dao.AccountLogic;
import org.wjj.qrcpcheck.service.CompareService;
import org.wjj.qrcpcheck.service.DBService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CompareController {
    
    @Autowired
    private CompareService compareService;
    @Autowired
    private DBService dbService;
    @Autowired
    private AccountLogic accountLogic;

    @PostMapping("/send-request")
    public Map<String, Object> sendRequest(@RequestBody Map<String, Object> request) {
        String urlA = (String) request.get("urlA");
        String urlB = (String) request.get("urlB");

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> myMap = new HashMap<>();
        try {
            // 将 JSON 字符串转换为 Map 对象
            Map<String, Object> headerA  = objectMapper.readValue((String) request.get("headerA"), Map.class);
            Map<String, Object> headerB  = objectMapper.readValue((String) request.get("headerB"), Map.class);
            String payloadA = (String) request.get("payloadA");
            String payloadB = (String) request.get("payloadB");

            return compareService.sendToBothClusters(urlA, urlB, headerA, headerB, payloadA, payloadB);
        } catch (IOException e) {
            e.printStackTrace();
        }
            return myMap;
    }

    @PostMapping("/requestA")
    public String showReuqest(@RequestBody String request) throws InterruptedException {
        System.out.println("request-debug: " + request.toString());
//        Thread.sleep(10000);
        return "{\"message\":\"request-debug接口成功接收POST请求!\",\"requestBody\":" + request.toString() + "}";
    }

    @PostMapping("/requestB")
    public String showReuqest2(@RequestBody String request){
        System.out.println("request-debug2: " + request.toString());
        return "{\"message\":\"request-debug2接口成功接收POST请求!\",\"requestBody\":" + request.toString() + "}";
    }

//    @GetMapping("/url-history")
//    public java.util.List<String> getUrlHistory() {
//        return compareService.getUrlHistory();
//    }
//
    @PostMapping("/connect-db")
    public Map<String, Object> connectDatabase(@RequestBody Map<String, String> config) {
        String url = config.get("url");
        String username = config.get("username");
        String password = config.get("password");
        String dbType = config.get("dbType");
        List<String> tablesName = dbService.getTableNames(url, username, password, dbType);

        Map<String, Object> result = new HashMap<>();
        result.put("status", "success");
        result.put("tablesName", tablesName);
        return result;
    }

    @PostMapping("/table-column-get")
    public Map<String, Object> tableColumnGet(@RequestBody Map<String, String> config){
        String url = config.get("url");
        String username = config.get("username");
        String password = config.get("password");
        String tableName = config.get("tableName");
        String dbType = config.get("dbType");
        List<String> tableColumns = dbService.getTableColumns(url, username, password, tableName, dbType);

        Map<String, Object> result = new HashMap<>();
        result.put("status", "success");
        result.put("tableColumns", tableColumns);
        return result;
    }

    @PostMapping("/table-data-json-get")
        public Map<String, Object> tableDataJsonGet(@RequestBody Map<String, String> config){
        String url = config.get("url");
        String username = config.get("username");
        String password = config.get("password");
        String tableName = config.get("tableName");
        String columnKey = config.get("columnKey");
        String columnValue = config.get("columnValue");
        String dbType = config.get("dbType");

        String tableDataJson = dbService.getTableDataJSon(url, username, password, tableName, columnKey, columnValue);
        Map<String, Object> result = new HashMap<>();
        result.put("status", "success");
        result.put("tableDataJson", tableDataJson);
        return result;
    }

    @GetMapping("/test")
        public void test(){
        System.out.println(accountLogic.getById("1"));
    }
}