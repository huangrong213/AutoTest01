
import com.entity.ExcelCases;
import com.util.ExcelHandle;
import com.util.ExcelReader;
import org.testng.annotations.Test;


import java.util.List;


public class ExcelReadTest {


    @Test
    public void case01() throws Exception {

        //获取当前项目路径
        String path = System.getProperty("user.dir");
        String fullPath = path + "\\case\\case01.xlsx";

        //要执行的sheet名称
        String sheetName = "";

        //获取读出来的excel数据
        List<ExcelCases> list = ExcelHandle.excelRead_Handle(fullPath);
//        List<ExcelCases> list = ExcelReader.readExcel(fullPath);
//        System.out.println(list.get(0));


        //写入数据
        ExcelHandle.excelWrite_Handle(list);


    }








}



