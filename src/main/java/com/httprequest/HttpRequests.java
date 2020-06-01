package com.httprequest;

import io.restassured.response.Response;

import java.util.HashMap;

import static io.restassured.RestAssured.given;


public class HttpRequests {

    /**
     * Get请求
     *
     * @param url     接口地址
     * @param headers 请求头
     * @param params  请求参数
     * @return 响应体
     */
    public static Response getHttp(String url, String headers, String params) {
        Response response = given()
                .contentType("application/json;charset=UTF-8")
                .headers("isadmin", "false", "token", headers)
//                .param("", params)
                .get(url);
        return response;
    }

    /**
     * Post请求
     *
     * @param header 请求头参数
     * @param url    接口地址
     * @param body   请求参数
     * @return 响应体
     */
    public static Response postHttp(HashMap header, String url, Object body) {

        Response response = given()
                .headers(header)
//                .contentType("application/json;charset=UTF-8")
                .body(body)
                .post(url);
        return response;
    }


    public static Response postTest(String header, String url, String body) {
        Response response = given()
                .header("token", header)
                .contentType("application/json;charset=UTF-8")
                .body(body)
                .post(url);
        return response;
    }


}
