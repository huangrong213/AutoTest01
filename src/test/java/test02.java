import com.entity.ExcelCases;
import com.util.ExcelHandle;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class test02 {


    @DataProvider
    public Object[] testdata() {

        //获取读出来的excel数据
        List<ExcelCases> list = ExcelHandle.excelRead_Handle();
        //将list转成object[]
        Object[] array = list.toArray(new ExcelCases[list.size()]);
        return array;

    }

    @Test(dataProvider = "testdata")
    public void test06(Object[] array) {
        for (int i = 0; i < array.length; i++) {
            ExcelCases cases = (ExcelCases) array[i];
            System.out.println(cases.getCaseName());
        }

    }


    @Test
    public void test5() {
        //获取当前项目路径
        String path = System.getProperty("user.dir");
        String fullPath = path + "\\case\\case01.xlsx";

        //要执行的sheet名称
        String sheetName = "";

        //获取读出来的excel数据
        List<ExcelCases> list = ExcelHandle.excelRead_Handle();
//        System.out.println(list.size());
        Object[] array = list.toArray(new ExcelCases[list.size()]);
        for (int i = 0; i < array.length; i++) {
//            System.out.println(array[i]);

            ExcelCases cases = (ExcelCases) array[i];
            System.out.println(cases.getCaseName());
        }

    }


    @Test
    public void test4() {

//        List<ExcelCases> list = new ArrayList<>();
        List a = new ArrayList();
        a.add("王利虎");
        a.add("张三");
        a.add("李四");

        Object[] array = a.toArray(new String[a.size()]);
        for (int i = 0; i < array.length; i++) {
            System.out.println(array[i]);
        }

//        Object[] array = new Object[a.size()];
//        for (int i = 0; i < a.size(); i++) {
//            array[i] = a.get(i);
//        }
//
//        for (int i = 0; i < array.length; i++) {
//            System.out.println(array[i]);
//        }


    }










}
