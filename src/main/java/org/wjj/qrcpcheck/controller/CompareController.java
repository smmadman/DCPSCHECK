package org.wjj.qrcpcheck.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;
import org.wjj.qrcpcheck.service.CompareService;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class CompareController {
    
    @Autowired
    private CompareService compareService;

    @PostMapping("/send-request")
    public Map<String, Object> sendRequest(@RequestBody Map<String, Object> request) {
        String urlA = (String) request.get("urlA");
        String urlB = (String) request.get("urlB");
        Map<String, Object> headerA = (Map<String, Object>) request.get("headerA");
        Map<String, Object> headerB = (Map<String, Object>) request.get("headerA");
        Map<String, Object> payloadA = (Map<String, Object>) request.get("payloadA");
        Map<String, Object> payloadB = (Map<String, Object>) request.get("payloadB");
        
        return compareService.sendToBothClusters(urlA, urlB, headerA, headerB, payloadA, payloadB);
    }

    @PostMapping("/request-debug")
    public void showReuqest(@RequestBody HttpRequest request){
        System.out.println("request-debug: " + request.toString());
    }

    @PostMapping("/request-debug2")
    public void showReuqest2(@RequestBody HttpRequest request){
        System.out.println("request-debug2: " + request.toString());
    }

//    @GetMapping("/url-history")
//    public java.util.List<String> getUrlHistory() {
//        return compareService.getUrlHistory();
//    }
//
//    @PostMapping("/connect-db")
//    public Map<String, Object> connectDatabase(@RequestBody Map<String, String> config) {
//        return compareService.connectDatabase(
//            config.get("env"),
//            config.get("url"),
//            config.get("username"),
//            config.get("password")
//        );
//    }
}