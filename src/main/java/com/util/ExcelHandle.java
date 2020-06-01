package com.util;

import com.alibaba.fastjson.JSONObject;
import com.entity.ExcelCases;
import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ExcelHandle {

    /**
     * 读取完excel后的工作
     *
     * @param filePath
     * @return
     */
    public static List<ExcelCases> excelRead_Handle(String filePath) {

        //读取excel
        List<ExcelCases> list = ExcelReader.readExcel(filePath);
        for (ExcelCases cases : list) {

            //如果是否执行等于Y
            if (cases.getIsRun().equals("Y")) {

                String premise = cases.getPremise();
                //如果前置条件有值
                if (premise != null && !"".equals(premise)) {
                    //分割前置条件
                    String[] str = premise.split(";");
                    //分割依赖key
                    String dependentOn = cases.getDependentOn();
                    String[] strKey = dependentOn.split(";");
                    for (int i = 0; i < str.length; i++) {

                        //分割出用例ID和对应地址值
                        String[] str2 = str[i].split(",");

                        //如果前置条件是响应数据
                        if (str2[0].equals("responseData")) {
                            String[] str3 = str2[1].split(">");

                            //以下是获取前置条件中的用例ID和对应值地址
                            String ylcaseid = str3[0];//获取依赖的caseid(pro_002)
                            String jsonpath = str3[1];//获取依赖caseid对应值的路径(data.list[0].propertyName)

                            //根据依赖路径返回对应的value值("data.list[0].propertyName":"value")
                            String ylValue = FindResponseData.getResponseValue(list, ylcaseid, jsonpath);

                            //以下是获取依赖key中的值
                            String key = strKey[i];//获取依赖key

                            //以下是替换本条请求中的对应值
                            String requestData = cases.getRequestData();
                            JSONObject jsonObject = JSONObject.parseObject(requestData);
                            //替换json值
                            JsonPath.parse(jsonObject).set(key, ylValue);
                            cases.setRequestData(jsonObject.toJSONString());

                        } else if (str2[0].equals("rodomInt")) {
                            //如果前置条件是随机整数
                            String rodomInt = str2[1];

//                        //以下是获取依赖key中的值
//                        String key = strKey[i];//获取依赖key
//
//                        //以下是替换本条请求中的对应值
//                        String requestData = cases.getRequestData();
//                        JSONObject jsonObject = JSONObject.parseObject(requestData);
//                        //替换json值
//                        JsonPath.parse(jsonObject).set(key, ylValue);
//                        cases.setRequestData(jsonObject.toJSONString());


                        } else if (str2[0].equals("rodomString")) {
                            //如果前置条件是随机字符串
                            String rodomString = str2[1];
//                        System.out.println("随机字符串是：" + rodomString);

                            //以下是获取依赖key中的值
                            String key = strKey[i];//获取依赖key

                        } else {
                            System.out.println("不支持(" + str2[0] + ")的前置条件类型，请修改");
                        }


                    }


                }

                //执行api请求
                Response response = RequestHandle.sendResponse(cases);

                //断言
                ResponseHandle.getResponse(cases, response);

            } else {
                System.out.println("(" + cases.getCaseID() + ")已跳过执行");
                continue;
            }

        }

        return list;
    }


    /**
     * 处理写入excel
     *
     * @param list 需要写入的内容
     */
    public static void excelWrite_Handle(List<ExcelCases> list) {
        // 写入数据到工作簿对象内
        Workbook workbook = ExcelWriter.exportData(list);

        // 以文件的形式输出工作簿对象
        FileOutputStream fileOut = null;
        try {
            //获取当前项目路径
            String path = System.getProperty("user.dir");

            //设置当前时间
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMddhhmm");
            String time = dateFormat.format(new Date().getTime());

            //输出的文件所存放的地址
            String exportFilePath = path + "\\TestOutput\\Case\\result" + time + ".xlsx";
            File exportFile = new File(exportFilePath);
            if (!exportFile.exists()) {
                exportFile.createNewFile();
            }

            fileOut = new FileOutputStream(exportFilePath);
            workbook.write(fileOut);
            fileOut.flush();
        } catch (Exception e) {
            e.printStackTrace();
//            logger.warning("输出Excel时发生错误，错误原因：" + e.getMessage());
        } finally {
            try {
                if (null != fileOut) {
                    fileOut.close();
                }
                if (null != workbook) {
                    workbook.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
//                logger.warning("关闭输出流时发生错误，错误原因：" + e.getMessage());
            }
        }

    }


}
