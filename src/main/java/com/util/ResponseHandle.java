package com.util;

import com.entity.ExcelCases;
import io.restassured.response.Response;
import org.testng.Assert;

/**
 * 接口响应数据的处理
 */
public class ResponseHandle {


    public static void getResponse(ExcelCases excelCases, Response response) {

        //这个code码是说明接口执行成功
        if (response.getStatusCode() == 200) {

            String caseName = excelCases.getCaseName();//用例名称
            //获取是登录接口，则获取token保存
            if (caseName.contains("登录成功")) {

                String token = response.jsonPath().get("data.token");
                PropertiesHandle.setToken(token);
//                PropertiesHandle.headerProperties().setProperty("token", token);
            }

            //获取响应体
            String responseData = response.getBody().asString();
            //更新用例实体中的响应体
            excelCases.setResponseData(responseData);

            String result = "未断言";

            //获取断言方式
            String expectType = excelCases.getExpectType();
            String[] str = expectType.split(";");
            for (int i = 0; i < str.length; i++) {
                if (str[i].equals("msg")) {
                    //如果是根据msg断言
                    String resMsg = response.jsonPath().get("msg").toString();
                    String assMsg = excelCases.getExpectValue();
                    if (assMsg.equals(resMsg)) {
                        result = "成功";
                        Assert.assertTrue(true);
                    } else {
                        result = "失败";
                        Assert.assertTrue(false);
                    }


                } else if (str[i].equals("code")) {
                    //如果是根据code码断言

                } else if (str[i].equals("json")) {
                    //如果是根据json断言
                    result = "我是json断言";

                } else if (str[i].equals("sql")) {
                    //如果是根据sql断言
                    result = "我是sql断言";

                } else {
                    System.out.println("暂不支持(" + str[i] + ")的断言方式");
                }


            }
//        更新此实体用例的断言结果
            excelCases.setResult(result);


//        String expectValue = excelCases.getExpectValue();//预期结果
//        String result = excelCases.getResult();//断言后的实际结果，成功/失败
//
//        String responseData = excelCases.getResponseData();//接口返回的data
//
        } else {
            System.out.println("接口执行的状态码是:" + response.getStatusCode());
            System.out.println("接口执行的响应体是:" + response.getBody().asString());
            System.out.println("用例（" + excelCases.getCaseName() + "）执行失败，请检查此条用例的路径或参数");
        }

    }

    public static void writeResponse() {

    }


}
