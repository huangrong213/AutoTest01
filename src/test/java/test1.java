import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.httprequest.HttpRequests;
import com.jayway.jsonpath.JsonPath;
import com.util.PropertiesHandle;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 这是我需要测试代码的类
 */
public class test1 {


    @DataProvider(name = "testdata")
    public Object[][] testDataFeed() {
//        HashMap<String, String> map = new HashMap<>();
//        return new Object[][] {
//                { "Cedric", new Integer(36) },
//                { "Anne", new Integer(37)},
//        };

        return new Object[][]{
                {"name1", "张三"},
                {"name2", "李四"},
        };


    }


    @Test(dataProvider = "testdata")
    public void test6(String name1, String name2) {
        System.out.println(name1);
        if (name1.equals("name1")) {
            Assert.assertTrue(true);
        } else {
            Assert.assertTrue(false);

        }

    }


    @Test
    public void test5() {
//        bundle.keySet();

        String str = PropertiesHandle.headerProperties().getProperty("content-Type");
        System.out.println(str);
        //定义application的配置文件,取值方式为：bundle.getString("key")
        ResourceBundle bundle = ResourceBundle.getBundle("application", Locale.CHINA);

    }

//    //从配置文件中获取域名
//    public static String getHost() {
//        String host = bundle.getString("host");
//        bundle.keySet();
//
//        return host;
//    }


    @Test
    public void test04() {

        //获取当前项目路径
        String path = System.getProperty("user.dir");
        //设置当前时间
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMddhhmm");
        String time = dateFormat.format(new Date().getTime());
        String exportFilePath = path + "\\TestOutput\\Case\\result" + time + ".xlsx";
        System.out.println(exportFilePath);
    }


    @Test
    public void test3() {

//        String token = "7b753957-f2fe-45b1-9c37-680c9bf31d05";
        String token = "111";
        String host = "https://taoweb.distrii.com";
//        String url = "/api/ao/ob-property/list";
//        String body = "{\"pageSize\":10, \"page\":1}";

        String url = "/api/middle/generalUser/userLogin";
        String body = "{\"mobile\":\"+86 18930539672\",\"password\":\"539672\"}";

        String path = host + url;
        System.out.println(path);
        System.out.println(body);
        Response response = HttpRequests.postTest(token, path, body);
        String token1 = response.jsonPath().get("data.token");
        System.out.println(token1);


//        JSONObject jsonObject = JSONObject.parseObject(body);

//        Response response = HttpRequests.postTest(token, path, body);
//        int code = response.getStatusCode();
//        System.out.println(code);
//        ResponseBody s = response.getBody();
//        String ss = response.getBody().asString();
//
//        System.out.println("响应体是111" + ss);


    }


    @Test
    public void test2() {

        String result = "未断言";
        if (1 == 1) {
            result = "成功";
        }
        System.out.println(result);
    }

    @Test
    public void yamlTest() throws IOException {

        String path = System.getProperty("user.dir");
        String headerPath = path + "\\config\\header.properties";
//        File file = new File(path + "\\config\\header.properties");

        Properties properties = new Properties();
        System.out.println(headerPath);
//        InputStream inputStream = new FileInputStream(headerPath);

        properties.load(new InputStreamReader(new FileInputStream(headerPath), "GBK"));
        String token = properties.getProperty("token");
        System.out.println("首次取的token：" + token);

        properties.setProperty("token", "abcdefg");
        String token2 = properties.getProperty("token");
        System.out.println("更新后的的token：" + token2);

    }


    @Test
    public void test1() {

        String ss = "responseData,pro_007>data.list[1].propertyName;rodomInt,11;rodomString,5";
        String s2 = "searchStr;mobile;name";
        String[] str = ss.split(";");
        //替换当前接口的值
        String[] strKey = s2.split(";");
        for (int i = 0; i < str.length; i++) {
            String[] str2 = str[i].split(",");

            //如果前置条件是响应数据
            if (str2[0].equals("responseData")) {
                String[] str3 = str2[1].split(">");

                String ylcaseid = str3[0];
                String jsonpath = str3[1];
                System.out.println("依赖caseid：" + ylcaseid);
                System.out.println("json路径：" + jsonpath);

                String key = strKey[i];
                System.out.println("替换的路径是:" + key);

            } else if (str2[0].equals("rodomInt")) {
                //如果前置条件是随机整数
                String rodomInt = str2[1];
                System.out.println("随机整数是：" + rodomInt);


                String key = strKey[i];
                System.out.println("替换的路径是:" + key);

            } else if (str2[0].equals("rodomString")) {
                //如果前置条件是随机字符串
                String rodomString = str2[1];
                System.out.println("随机字符串是：" + rodomString);

                String key = strKey[i];
                System.out.println("替换的路径是:" + key);


            } else {
                System.out.println("不支持(" + str2[0] + ")的前置条件类型，请修改");
            }


        }


    }


    /**
     * 测试根据json参数路径获取值、替换值
     */
    @Test
    public void jsonPathTest() {

        String data = "{\"key1\":\"value1\",\"data\":[{\"name\":\"翠花\",\"age\":18}]}";
        String data1 = "{\"key1\":\"value1\",\"data\":[{\"name\":\"狗子\",\"age\":18}]}";
        String value = "data[0].name";
//
////        //根据value(地址)从data里获取对应的值
//        String obj = JSONPath.read(data, value).toString();
        String obj = JsonPath.read(data, value).toString();
        System.out.println(obj);

//        //将字符串转json
        JSONObject jsonObject = JSONObject.parseObject(data1);
        //替换json值
        JsonPath.parse(jsonObject).set("data[0].name", "毕翠花");
//        jsonObject.replace("name", "三狗子");

        System.out.println(jsonObject.toString());


    }


}
