
import com.entity.ExcelCases;
import com.jayway.jsonpath.JsonPath;
import com.util.ExcelHandle;
import com.util.ExcelReader;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


import java.util.Arrays;
import java.util.List;


public class ExcelReadTest {


    public static List<ExcelCases> caseList() {
        //获取当前项目路径
        String path = System.getProperty("user.dir");
        String fullPath = path + "\\case\\case01.xlsx";//读取的excel文件

        //要执行的sheet名称
//        String sheetName = "";
        List<ExcelCases> list = ExcelReader.readExcel(fullPath);
        return list;
    }

    @DataProvider
    public Object[] excelData() {

        //获取读出来的excel数据
        List<ExcelCases> list = caseList();
        //将list转成object[]
        Object[] array = list.toArray(new ExcelCases[list.size()]);
        return array;

    }


    @Test(dataProvider = "excelData")
    public void ziyunTest(Object[] array) {
        ExcelCases cases = (ExcelCases) array[0];
        Reporter.log("我是log1:" + cases.getCaseName());
        Reporter.log("我是log2:" + cases.getUrl());
        Reporter.log("我是log3:" + cases.getRequestData());
        Assert.assertTrue(true);

        for (int i = 0; i < caseList().size(); i++) {
            if (cases.getCaseID().equals(caseList().get(i).getCaseID())) {
                caseList().get(i).setResponseData("");
                break;
            }
        }
    }


}



