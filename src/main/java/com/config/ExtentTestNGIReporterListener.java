package com.config;


import com.aventstack.extentreports.*;
import com.aventstack.extentreports.model.TestAttribute;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.*;
import org.testng.xml.XmlSuite;

import java.io.File;
import java.util.*;

public class ExtentTestNGIReporterListener implements IReporter {
    //    private static final String OUTPUT_FOLDER = "test-output/";
    private static final String OUTPUT_FOLDER = "test-output/";//指定报告存放的位置
    private static final String FILE_NAME = "mytest.html";//测试报告的名称
    private ExtentReports extent;

    public ExtentTestNGIReporterListener() {

    }

    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        this.init();
        boolean createSuiteNode = false;
        if (suites.size() > 1) {
            createSuiteNode = true;
        }

        Iterator var5 = suites.iterator();

        while (true) {
            ISuite suite;
            Map result;
            do {
                if (!var5.hasNext()) {
                    this.extent.flush();
                    return;
                }

                suite = (ISuite) var5.next();
                result = suite.getResults();
            } while (result.size() == 0);

            //统计suite下的成功、失败、跳过的总用例数
            int suiteFailSize = 0;
            int suitePassSize = 0;
            int suiteSkipSize = 0;
            ExtentTest suiteTest = null;

            //存在多个suite的情况下，在报告中将同一个一个suite的测试结果归为一类，创建一级节点。
            if (createSuiteNode) {
                suiteTest = this.extent.createTest(suite.getName()).assignCategory(new String[]{suite.getName()});
            }

            boolean createSuiteResultNode = false;
            if (result.size() > 1) {
                createSuiteResultNode = true;
            }

            Iterator var13 = result.values().iterator();

            while (var13.hasNext()) {
                ISuiteResult r = (ISuiteResult) var13.next();
                ITestContext context = r.getTestContext();
                ExtentTest resultNode;
                if (createSuiteResultNode) {

                    //没有创建suite的情况下，将在SuiteResult的创建为一级节点，否则创建为suite的一个子节点。
                    if (null == suiteTest) {
                        resultNode = this.extent.createTest(r.getTestContext().getName());
                    } else {
                        resultNode = suiteTest.createNode(r.getTestContext().getName());
                    }
                } else {
                    resultNode = suiteTest;
                }

                if (resultNode != null) {
                    resultNode.getModel().setName(suite.getName() + " : " + r.getTestContext().getName());
                    if (resultNode.getModel().hasCategory()) {
                        resultNode.assignCategory(new String[]{r.getTestContext().getName()});
                    } else {
                        resultNode.assignCategory(new String[]{suite.getName(), r.getTestContext().getName()});
                    }

                    resultNode.getModel().setStartTime(r.getTestContext().getStartDate());
                    resultNode.getModel().setEndTime(r.getTestContext().getEndDate());
                    int passSize = r.getTestContext().getPassedTests().size();
                    int failSize = r.getTestContext().getFailedTests().size();
                    int skipSize = r.getTestContext().getSkippedTests().size();
                    suitePassSize += passSize;
                    suiteFailSize += failSize;
                    suiteSkipSize += skipSize;
                    if (failSize > 0) {
                        resultNode.getModel().setStatus(Status.FAIL);
                    }

                    resultNode.getModel().setDescription(String.format("Pass: %s ; Fail: %s ; Skip: %s ;", passSize, failSize, skipSize));
                }

                this.buildTestNodes(resultNode, context.getFailedTests(), Status.FAIL);
                this.buildTestNodes(resultNode, context.getSkippedTests(), Status.SKIP);
                this.buildTestNodes(resultNode, context.getPassedTests(), Status.PASS);
            }

            if (suiteTest != null) {
                suiteTest.getModel().setDescription(String.format("Pass: %s ; Fail: %s ; Skip: %s ;", suitePassSize, suiteFailSize, suiteSkipSize));
                if (suiteFailSize > 0) {
                    suiteTest.getModel().setStatus(Status.FAIL);
                }
            }
        }
    }

    private void init() {
//        File reportDir = new File("test-output/");//创建测试报告的文件夹
        //文件夹不存在的话进行创建
        File reportDir = new File(OUTPUT_FOLDER);
        if (!reportDir.exists() && !reportDir.isDirectory()) {
            reportDir.mkdir();
        }

//        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("D:/test-output/index.html");//创建测试报告
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(OUTPUT_FOLDER + FILE_NAME);
        //设置CDN // 设置静态文件的DNS
        htmlReporter.config().setResourceCDN(ResourceCDN.EXTENTREPORTS);
        htmlReporter.config().setDocumentTitle("api自动化测试报告");
        htmlReporter.config().setReportName("api自动化测试报告");
        htmlReporter.config().setChartVisibilityOnOpen(true);
        htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setCSS(".node.level-1  ul{ display:none;} .node.level-1.active ul{display:block;}");
        this.extent = new ExtentReports();
        this.extent.attachReporter(new ExtentReporter[]{htmlReporter});
        this.extent.setReportUsesManualConfiguration(true);
    }

    private void buildTestNodes(ExtentTest extenttest, IResultMap tests, Status status) {

        //存在父节点时，获取父节点的标签
        String[] categories = new String[0];
        if (extenttest != null) {
            List<TestAttribute> categoryList = extenttest.getModel().getCategoryContext().getAll();
            categories = new String[categoryList.size()];

            for (int index = 0; index < categoryList.size(); ++index) {
                categories[index] = ((TestAttribute) categoryList.get(index)).getName();
            }
        }

        if (tests.size() > 0) {

            //调整用例排序，按时间排序
            Set<ITestResult> treeSet = new TreeSet(new Comparator<ITestResult>() {
                public int compare(ITestResult o1, ITestResult o2) {
                    return o1.getStartMillis() < o2.getStartMillis() ? -1 : 1;
                }
            });
            treeSet.addAll(tests.getAllResults());
            Iterator var7 = treeSet.iterator();

            while (var7.hasNext()) {
                ITestResult result = (ITestResult) var7.next();
                Object[] parameters = result.getParameters();
                String name = "";
                Object[] var11 = parameters;
                int var12 = parameters.length;

                int var13;

                //如果有参数，则使用参数的toString组合代替报告中的name
                for (var13 = 0; var13 < var12; ++var13) {
                    Object param = var11[var13];
                    name = name + param.toString();
                }

                if (name.length() > 0) {
                    if (name.length() > 50) {
                        name = name.substring(0, 49) + "...";
                    }
                } else {
                    name = result.getMethod().getMethodName();
                }

                ExtentTest test;
                if (extenttest == null) {
                    test = this.extent.createTest(name);
                } else {

                    //作为子节点进行创建时，设置同父节点的标签一致，便于报告检索。
                    test = extenttest.createNode(name).assignCategory(categories);
                }

                String[] var17 = result.getMethod().getGroups();
                var12 = var17.length;

                for (var13 = 0; var13 < var12; ++var13) {
                    String group = var17[var13];
                    test.assignCategory(new String[]{group});
                }

                List<String> outputList = Reporter.getOutput(result);
                Iterator var19 = outputList.iterator();

                while (var19.hasNext()) {
                    String output = (String) var19.next();

                    //将用例的log输出报告中
                    test.debug(output);
                }

                if (result.getThrowable() != null) {
                    test.log(status, result.getThrowable());
                } else {
                    test.log(status, "Test " + status.toString().toLowerCase() + "ed");
                }

                test.getModel().setStartTime(this.getTime(result.getStartMillis()));
                test.getModel().setEndTime(this.getTime(result.getEndMillis()));
            }
        }

    }

    private Date getTime(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }
}
