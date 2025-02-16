package org.wjj.qrcpcheck.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.wjj.qrcpcheck.util.HttpPostRequest;

import java.util.*;
import java.util.concurrent.*;

@Service
public class CompareService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ExecutorService executor = Executors.newFixedThreadPool(2);
    @Autowired
    HttpPostRequest httpPostRequest;
    
//    @Autowired
//    private JdbcTemplate jdbcTemplate;

    public Map<String, Object> sendToBothClusters(String urlA, String urlB,
                                                  Map<String,Object> headerA, Map<String,Object> headerB,
                                                  String payloadA,String payloadB) {
        try {
            // 保存URL历史
//            saveUrlHistory(urlA);
//            saveUrlHistory(urlB);

//            String resultA = httpPostRequest.executePostRequest(urlA, headerA, payloadA);
//            String resultB = httpPostRequest.executePostRequest(urlB, headerB, payloadB);


//            // 并行发送请求
            Future<String> futureA = executor.submit(() ->
                    httpPostRequest.executePostRequest(urlA, headerA, payloadA));
            Future<String> futureB = executor.submit(() ->
                    httpPostRequest.executePostRequest(urlB, headerB, payloadB));
//
            String resultA = futureA.get(5, TimeUnit.SECONDS);
            String resultB = futureB.get(5, TimeUnit.SECONDS);

            System.out.println("resultA: " + resultA);
            System.out.println("resultB: " + resultB);
//
//            // 结果对比
//            Map<String, Object> diff = compareResults(resultA, resultB);
            Map<String, Object> myMap = new HashMap<>();
            myMap.put("status", "success");
            myMap.put("resultA", resultA);
            myMap.put("resultB", resultB);
//            myMap.put("differences", "diff");

            return  myMap;
        } catch (Exception e) {
            Map<String, Object> myMap = new HashMap<>();
            myMap.put("error", e);
            return myMap;
        }
    }

    private Map<String, Object> sendRequest(String url, Map<String, Object> payload) {
        try {
            return restTemplate.postForObject(url, payload, Map.class);
        } catch (Exception e) {
            Map<String, Object> myMap = new HashMap<>();
            myMap.put("error", e);
            return myMap;
        }
    }

    private Map<String, Object> compareResults(Map<?, ?> a, Map<?, ?> b) {
        Map<String, Object> diff = new LinkedHashMap<>();
        compareMaps("", a, b, diff);
        return diff;
    }

    private void compareMaps(String path, Map<?, ?> a, Map<?, ?> b, Map<String, Object> diff) {
//        Set<Object> allKeys = new HashSet<>();
//        allKeys.addAll(a.keySet());
//        allKeys.addAll(b.keySet());
//
//        for (Object key : allKeys) {
//            String currentPath = path.isEmpty() ? key.toString() : path + "." + key;
//            Object valA = a.get(key);
//            Object valB = b.get(key);
//
//            if (valA instanceof Map && valB instanceof Map) {
//                compareMaps(currentPath, (Map<?, ?>) valA, (Map<?, ?>) valB, diff);
//            } else if (!Objects.equals(valA, valB)) {
//                diff.put(currentPath, Map.of(
//                    "A", valA,
//                    "B", valB
//                ));
//            }
//        }
    }

//    private void saveUrlHistory(String url) {
//        jdbcTemplate.update("INSERT INTO url_history (url) VALUES (?) ON DUPLICATE KEY UPDATE last_used=NOW()", url);
//    }
//
//    public List<String> getUrlHistory() {
//        return jdbcTemplate.queryForList("SELECT url FROM url_history ORDER BY last_used DESC LIMIT 10", String.class);
//    }
//
//    public Map<String, Object> connectDatabase(String env, String url, String username, String password) {
//        try {
//            // 保存数据源配置
//            jdbcTemplate.update("INSERT INTO datasource_config (env, jdbc_url, username, password) VALUES (?, ?, ?, ?)",
//                    env, url, username, password);
//
//            return Map.of("status", "success", "message", "数据库连接配置已保存");
//        } catch (Exception e) {
//            return Map.of("status", "error", "message", e.getMessage());
//        }
//    }
}