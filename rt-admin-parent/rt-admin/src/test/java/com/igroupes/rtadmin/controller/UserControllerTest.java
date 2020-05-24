package com.igroupes.rtadmin.controller;


import com.alibaba.fastjson.JSON;
import com.google.common.collect.Sets;
import com.igroupes.rtadmin.dto.request.UserLoginRequest;
import com.igroupes.rtadmin.vo.ResultVO;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


public class UserControllerTest {
    private RestTemplate restTemplate;

    @Before
    public void setRestTemplate(){
        restTemplate = new RestTemplate();
    }


    @Test
    public void login() {
        String url = "http://localhost:8081/rtapi/user/login";
        UserLoginRequest request = new UserLoginRequest();
        request.setPassword("admin12345");
        request.setUsername("admin");
        request.setCode("maPB");
        ResultVO resultVO = restTemplate.postForObject(url, request,ResultVO.class);
        System.out.println(JSON.toJSON(resultVO));
    }
    @Test
    public void verifyCode(){
        String url = "http://localhost:8081/rtapi/user/code";
        ResponseEntity<byte[]> responseEntity = restTemplate
                .exchange(url, HttpMethod.GET, null, byte[].class);
    }

    @Test
    public void info() {
        String url = "http://localhost:8081/rtapi/user/info";
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("X-TOKEN", "86417c6d0f9f4071a8142de775df1820");
        HttpEntity<MultiValueMap> requestEntity = new HttpEntity<MultiValueMap>(null, requestHeaders);
        ResponseEntity<ResultVO> response = restTemplate.exchange(url.toString(), HttpMethod.GET, requestEntity, ResultVO.class);
        System.out.println(JSON.toJSON(response.getBody()));
    }



    public static void main(String[] args) {
        Sets.SetView<Integer> difference = Sets.difference(Sets.newHashSet(1, 2, 3), Sets.newHashSet(1, 3, 4));
        System.out.println(difference);
    }
}