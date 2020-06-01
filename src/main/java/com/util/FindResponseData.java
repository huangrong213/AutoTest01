package com.util;

import com.entity.ExcelCases;
import com.jayway.jsonpath.JsonPath;

import java.util.List;

public class FindResponseData {


    /**
     * 根据前置条件的caseID,查询出响应数据中要获取的值
     * 根据caseID返回此用例的响应体
     *
     * @param list
     * @param caseID
     * @param findKey
     * @return
     */
    public static String getResponseValue(List<ExcelCases> list, String caseID, String findKey) {

        String values = "";
        for (ExcelCases cases : list) {
            if (cases.getCaseID().equals(caseID)) {
                String data = cases.getResponseData();
                values = JsonPath.read(data, findKey).toString();
                break;
            }

        }

        return values;

    }


}
