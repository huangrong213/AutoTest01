package com.entity;

import lombok.Data;

@Data

public class ExcelCases {

    private String caseID;//用例编号
    private String caseName;//用例名称
    private String isRun;//是否执行此条用例
    private String premise;//前置条件
    private String dependentOn;//依赖
    private String url;//接口地址
    private String method;//请求方式
    private String requestData;//请求值
    private String requestCookie;//cookie
    private String requestHeaders;//请求头
    private String expectType;//断言方式
    private String expectValue;//断言预期值
    private String result;//断言结果
    private String responseData;//响应体
    private String remark;//备注











}
