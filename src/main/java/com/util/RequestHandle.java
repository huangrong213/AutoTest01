package com.util;

import com.alibaba.fastjson.JSONObject;
import com.entity.ExcelCases;
import com.httprequest.HttpRequests;
import io.restassured.response.Response;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Properties;

public class RequestHandle {

    /**
     * 发送请求
     *
     * @param excelCases 用例实体
     * @return 响应体
     */
    public static Response sendResponse(ExcelCases excelCases) {
        String caseID = excelCases.getCaseID();//用例编号

        String url = excelCases.getUrl();//接口地址
        String method = excelCases.getMethod();//请求方式
        String requestData = excelCases.getRequestData();//请求值
        String cookie = excelCases.getRequestCookie();//cookie
        String header = excelCases.getRequestHeaders();//请求头
        HashMap<String, String> map = getHeader(header);

        String host = "https://taoweb.distrii.com";
        String path = host + url;

        Response response = null;
        if (method.equals("post")) {
            //post 请求
            response = HttpRequests.postHttp(map, path, requestData);

        } else if (method.equals("get")) {
            //get 请求

        }


        return response;
    }

    //获取配置文件中的header
    public static HashMap getHeader(String headers) {

        HashMap<String, String> headerMap = new HashMap<>();
        //分割header
        String[] str = headers.split(";");
        for (int i = 0; i < str.length; i++) {
            if (str[i].equals("token")) {
                String token = PropertiesHandle.headerProperties().getProperty("token");
                headerMap.put("token", token);
            } else if (str[i].equals("contentType")) {
                String contentType = PropertiesHandle.headerProperties().getProperty("content-Type");
                headerMap.put("content-Type", contentType);
            }
        }

        return headerMap;
    }


}
