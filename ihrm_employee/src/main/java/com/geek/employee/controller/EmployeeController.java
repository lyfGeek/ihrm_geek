package com.geek.employee.controller;

import com.geek.common.controller.BaseController;
import com.geek.common.entity.PageResult;
import com.geek.common.entity.Result;
import com.geek.common.entity.ResultCode;
import com.geek.common.poi.ExcelExportUtil;
import com.geek.common.utils.BeanMapUtils;
import com.geek.domain.employee.*;
import com.geek.domain.employee.response.EmployeeReportResult;
import com.geek.employee.service.*;
import net.sf.jasperreports.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/employees")
public class EmployeeController extends BaseController {

    @Autowired
    private IUserCompanyPersonalService userCompanyPersonalService;

    @Autowired
    private IUserCompanyJobsService userCompanyJobsService;

    @Autowired
    private IResignationService resignationService;

    @Autowired
    private ITransferPositionService transferPositionService;

    @Autowired
    private IPositiveService positiveService;

    @Autowired
    private IArchiveService archiveService;

    /**
     * 打印员工 pdf 报表。
     *
     * @param id
     * @throws IOException
     */
    @RequestMapping(value = "/{id}/pdf", method = RequestMethod.GET)
    public void pdf(@PathVariable String id) throws IOException {
        // 引入 jasper 文件。
        Resource resource = new ClassPathResource("templates/profile.jasper");
        FileInputStream fis = new FileInputStream(resource.getFile());

        // 构造数据。
        // 用户详情数据。
        UserCompanyPersonal personal = userCompanyPersonalService.findById(id);
        // 用户岗位信息数据。
        UserCompanyJobs jobs = userCompanyJobsService.findById(id);

        // 用户头像。
        String staffPhoto = "http://qav8b72pi.bkt.clouddn.com/" + id + "?t=100";

        // 填充 pdf 模版数据，并输出 pdf。
        Map params = new HashMap();

        Map<String, Object> personalMap = BeanMapUtils.beanToMap(personal);
        Map<String, Object> jobsMap = BeanMapUtils.beanToMap(jobs);

        params.putAll(personalMap);
        params.putAll(jobsMap);
        params.put("staffPhoto", staffPhoto);

        ServletOutputStream sos = response.getOutputStream();
        try {
            JasperPrint print = JasperFillManager.fillReport(fis, params, new JREmptyDataSource());
            JasperExportManager.exportReportToPdfStream(print, sos);
        } catch (JRException e) {
            e.printStackTrace();
        } finally {
            sos.flush();
        }
    }

    /**
     * 员工个人信息保存。
     *
     * @param uid
     * @param map
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/{id}/personalInfo", method = RequestMethod.PUT)
    public Result savePersonalInfo(@PathVariable(name = "id") String uid, @RequestBody Map map) throws Exception {
        UserCompanyPersonal sourceInfo = BeanMapUtils.mapToBean(map, UserCompanyPersonal.class);
        if (sourceInfo == null) {
            sourceInfo = new UserCompanyPersonal();
        }
        sourceInfo.setUserId(uid);
        sourceInfo.setCompanyId(super.companyId);
        userCompanyPersonalService.save(sourceInfo);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 员工个人信息读取。
     *
     * @param uid
     * @return
     */
    @RequestMapping(value = "/{id}/personalInfo", method = RequestMethod.GET)
    public Result findPersonalInfo(@PathVariable(name = "id") String uid) {
        UserCompanyPersonal info = userCompanyPersonalService.findById(uid);
        if (info == null) {
            info = new UserCompanyPersonal();
            info.setUserId(uid);
        }
        return new Result(ResultCode.SUCCESS, info);
    }

    /**
     * 员工岗位信息保存。
     *
     * @param uid
     * @param sourceInfo
     * @return
     */
    @RequestMapping(value = "/{id}/jobs", method = RequestMethod.PUT)
    public Result saveJobsInfo(@PathVariable(name = "id") String uid, @RequestBody UserCompanyJobs sourceInfo) {
        // 更新员工岗位信息。
        if (sourceInfo == null) {
            sourceInfo = new UserCompanyJobs();
            sourceInfo.setUserId(uid);
            sourceInfo.setCompanyId(super.companyId);
        }
        userCompanyJobsService.save(sourceInfo);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 员工岗位信息读取。
     *
     * @param uid
     * @return
     */
    @RequestMapping(value = "/{id}/jobs", method = RequestMethod.GET)
    public Result findJobsInfo(@PathVariable(name = "id") String uid) {
        UserCompanyJobs info = userCompanyJobsService.findById(uid);
        if (info == null) {
            info = new UserCompanyJobs();
            info.setUserId(uid);
            info.setCompanyId(companyId);
        }
        return new Result(ResultCode.SUCCESS, info);
    }

    /**
     * 离职表单保存。
     *
     * @param uid
     * @param resignation
     * @return
     */
    @RequestMapping(value = "/{id}/leave", method = RequestMethod.PUT)
    public Result saveLeave(@PathVariable(name = "id") String uid, @RequestBody EmployeeResignation resignation) {
        resignation.setUserId(uid);
        resignationService.save(resignation);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 离职表单读取。
     *
     * @param uid
     * @return
     */
    @RequestMapping(value = "/{id}/leave", method = RequestMethod.GET)
    public Result findLeave(@PathVariable(name = "id") String uid) {
        EmployeeResignation resignation = resignationService.findById(uid);
        if (resignation == null) {
            resignation = new EmployeeResignation();
            resignation.setUserId(uid);
        }
        return new Result(ResultCode.SUCCESS, resignation);
    }

    /**
     * 导入员工。
     *
     * @param attachment
     * @return
     */
    @RequestMapping(value = "/import", method = RequestMethod.POST)
    public Result importData(@RequestParam(name = "file") MultipartFile attachment) {
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 调岗表单保存。
     *
     * @param uid
     * @param transferPosition
     * @return
     */
    @RequestMapping(value = "/{id}/transferPosition", method = RequestMethod.PUT)
    public Result saveTransferPosition(@PathVariable(name = "id") String uid, @RequestBody EmployeeTransferPosition transferPosition) {
        transferPosition.setUserId(uid);
        transferPositionService.save(transferPosition);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 调岗表单读取。
     *
     * @param uid
     * @return
     */
    @RequestMapping(value = "/{id}/transferPosition", method = RequestMethod.GET)
    public Result findTransferPosition(@PathVariable(name = "id") String uid) {
        UserCompanyJobs jobsInfo = userCompanyJobsService.findById(uid);
        if (jobsInfo == null) {
            jobsInfo = new UserCompanyJobs();
            jobsInfo.setUserId(uid);
        }
        return new Result(ResultCode.SUCCESS, jobsInfo);
    }

    /**
     * 转正表单保存。
     *
     * @param uid
     * @param positive
     * @return
     */
    @RequestMapping(value = "/{id}/positive", method = RequestMethod.PUT)
    public Result savePositive(@PathVariable(name = "id") String uid, @RequestBody EmployeePositive positive) {
        positiveService.save(positive);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 转正表单读取。
     *
     * @param uid
     * @return
     */
    @RequestMapping(value = "/{id}/positive", method = RequestMethod.GET)
    public Result findPositive(@PathVariable(name = "id") String uid) {
        EmployeePositive positive = positiveService.findById(uid);
        if (positive == null) {
            positive = new EmployeePositive();
            positive.setUserId(uid);
        }
        return new Result(ResultCode.SUCCESS, positive);
    }

    /**
     * 历史归档详情列表。
     *
     * @param month
     * @param type
     * @return
     */
    @RequestMapping(value = "/archives/{month}", method = RequestMethod.GET)
    public Result archives(@PathVariable(name = "month") String month, @RequestParam(name = "type") Integer type) {
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 归档更新。
     *
     * @param month
     * @return
     */
    @RequestMapping(value = "/archives/{month}", method = RequestMethod.PUT)
    public Result saveArchives(@PathVariable(name = "month") String month) {
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 历史归档列表。
     *
     * @param pagesize
     * @param page
     * @param year
     * @return
     */
    @RequestMapping(value = "/archives", method = RequestMethod.GET)
    public Result findArchives(@RequestParam(name = "pagesize") Integer pagesize, @RequestParam(name = "page") Integer page, @RequestParam(name = "year") String year) {
        Map map = new HashMap();
        map.put("year", year);
        map.put("companyId", companyId);
        Page<EmployeeArchive> searchPage = archiveService.findSearch(map, page, pagesize);
        PageResult<EmployeeArchive> pr = new PageResult(searchPage.getTotalElements(), searchPage.getContent());
        return new Result(ResultCode.SUCCESS, pr);
    }

    /**
     * 采用模版打印的形式完成报表导出。百万数据。SXSSFWorkbook()。
     *
     * SXSSFWorkbook() ~ 通过 xml 临时文件形式存储临时的内存对象。
     *
     * @param month 年月-月（2018-02%）。
     * @throws Exception
     */
//    @RequestMapping(value = "/export/{month}", method = RequestMethod.GET)
//    public void exportPlain(@PathVariable String month) throws Exception {
////        // 获取报表数据。
//        List<EmployeeReportResult> list = userCompanyPersonalService.findByReport(companyId, month);
//////        List<EmployeeReportResult> list = userCompanyPersonalService.findByReport("1", month);
//
//        // - 构造 Excel。
//        // 创建工作薄。
////        Workbook workbook = new XSSFWorkbook();
//        // 处理百万数据报表。
//        Workbook workbook = new SXSSFWorkbook(100);// 当内存中的对象数量达到此值，便写入文件。
//
//        // 构造 Sheet。
//        Sheet sheet = workbook.createSheet();
//        // 创建行。
//        // 标题。
//        String[] titles = "编号,姓名,手机,最高学历,国家地区,护照号,籍贯,生日,属相,入职时间,离职类型,离职原因,离职时间".split(",");
//
//        // 处理标题。
//        Row row = sheet.createRow(0);
//
//        int titleIndex = 0;
//
//        for (String title : titles) {
//            Cell cell = row.createCell(titleIndex++);
//            cell.setCellValue(title);
//        }
//
//        // 构造单元格。
//        int rowIndex = 1;
//        Cell cell = null;
//
//        for (int i = 0; i < 1000000; i++) {
//
//            for (EmployeeReportResult employeeReportResult : list) {
//                row = sheet.createRow(rowIndex++);
//                // 编号。
//                cell = row.createCell(0);
//                cell.setCellValue(employeeReportResult.getUserId());
////            cell.setCellStyle(styles[0]);
//                // 姓名。
//                cell = row.createCell(1);
//                cell.setCellValue(employeeReportResult.getUsername());
////            cell.setCellStyle(styles[1]);
//                // 手机。
//                cell = row.createCell(2);
//                cell.setCellValue(employeeReportResult.getMobile());
////            cell.setCellStyle(styles[2]);
//                // 最高学历。
//                cell = row.createCell(3);
//                cell.setCellValue(employeeReportResult.getTheHighestDegreeOfEducation());
////            cell.setCellStyle(styles[3]);
//                // 国家地区。
//                cell = row.createCell(4);
//                cell.setCellValue(employeeReportResult.getNationalArea());
////            cell.setCellStyle(styles[4]);
//                // 护照号。
//                cell = row.createCell(5);
//                cell.setCellValue(employeeReportResult.getPassportNo());
////            cell.setCellStyle(styles[5]);
//                // 籍贯。
//                cell = row.createCell(6);
//                cell.setCellValue(employeeReportResult.getNativePlace());
////            cell.setCellStyle(styles[6]);
//                // 生日。
//                cell = row.createCell(7);
//                cell.setCellValue(employeeReportResult.getBirthday());
////            cell.setCellStyle(styles[7]);
//                // 属相。
//                cell = row.createCell(8);
//                cell.setCellValue(employeeReportResult.getZodiac());
////            cell.setCellStyle(styles[8]);
//                // 入职时间。
//                cell = row.createCell(9);
//                cell.setCellValue(employeeReportResult.getTimeOfEntry());
////            cell.setCellStyle(styles[9]);
//                // 离职类型。
//                cell = row.createCell(10);
//                cell.setCellValue(employeeReportResult.getTypeOfTurnover());
////            cell.setCellStyle(styles[10]);
//                // 离职原因。
//                cell = row.createCell(11);
//                cell.setCellValue(employeeReportResult.getReasonsForLeaving());
////            cell.setCellStyle(styles[11]);
//                // 离职时间。
//                cell = row.createCell(12);
//                cell.setCellValue(employeeReportResult.getResignationTime());
////            cell.setCellStyle(styles[12]);
//            }
//        }
//
//        // 下载。
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        workbook.write(byteArrayOutputStream);
//        new DownloadUtils().download(byteArrayOutputStream, response, month + "~人事报表.xlsx");
//    }

    /**
     * http://localhost:9013/employees/export/02
     * <p>
     * 采用模版打印的形式完成报表导出。
     * 使用注解 + 工具类。
     *
     * @param month 年-月（2018-02%）。
     * @throws Exception
     */
    @RequestMapping(value = "/export/{month}", method = RequestMethod.GET)
    public void export(@PathVariable String month) throws Exception {
        // 获取报表数据。
        List<EmployeeReportResult> list = userCompanyPersonalService.findByReport(companyId, month);

        // 加载模版。
        Resource resource = new ClassPathResource("excel-template/hr-demo.xlsx");
        FileInputStream fileInputStream = new FileInputStream(resource.getFile());

        // 通过工具类下载文件。
        new ExcelExportUtil(EmployeeReportResult.class, 2, 2)
                .export(response, fileInputStream, list, month + " ~ 人事报表。.xlsx");
    }
//
//    /**
//     * 采用模版打印的形式完成报表导出。原版。
//     *
//     * @param month 年月-月（2018-02%）。
//     * @throws Exception
//     */
//    @RequestMapping(value = "/export/{month}", method = RequestMethod.GET)
//    public void export2(@PathVariable String month) throws Exception {
//        // 获取报表数据。
//        List<EmployeeReportResult> list = userCompanyPersonalService.findByReport(companyId, month);
//
//        // 加载模版。
//        Resource resource = new ClassPathResource("excel-templates/hr-demo.xlsx");
//        FileInputStream fileInputStream = new FileInputStream(resource.getFile());
//
//        // ~ ~ ~
//        // 通过工具类下载文件。
////        new ExcelExportUtil(EmployeeReportResult.class, 2, 2)
////                .export(response, fileInputStream, list, month + "人事报表.xlsx");
//        // ~ ~ `
//
//        // 根据模板创建工作簿。
//        Workbook wb = new XSSFWorkbook(fileInputStream);
//        // 读取工作表。
//        Sheet sheet = wb.getSheetAt(0);
//        // 抽取公共样式。
//        Row row = sheet.getRow(2);
//        CellStyle styles[] = new CellStyle[row.getLastCellNum()];
//        for (int i = 0; i < row.getLastCellNum(); i++) {
//            Cell cell = row.getCell(i);
//            styles[i] = cell.getCellStyle();
//        }
//
//        // ~ ~ ~
//
//        // 构造单元格。
//        int rowIndex = 2;
//        Cell cell = null;
//
//        for (EmployeeReportResult employeeReportResult : list) {
//            row = sheet.createRow(rowIndex++);
//            // 编号。
//            cell = row.createCell(0);
//            cell.setCellValue(employeeReportResult.getUserId());
//            cell.setCellStyle(styles[0]);
//            // 姓名。
//            cell = row.createCell(1);
//            cell.setCellValue(employeeReportResult.getUsername());
//            cell.setCellStyle(styles[1]);
//            // 手机。
//            cell = row.createCell(2);
//            cell.setCellValue(employeeReportResult.getMobile());
//            cell.setCellStyle(styles[2]);
//            // 最高学历。
//            cell = row.createCell(3);
//            cell.setCellValue(employeeReportResult.getTheHighestDegreeOfEducation());
//            cell.setCellStyle(styles[3]);
//            // 国家地区。
//            cell = row.createCell(4);
//            cell.setCellValue(employeeReportResult.getNationalArea());
//            cell.setCellStyle(styles[4]);
//            // 护照号。
//            cell = row.createCell(5);
//            cell.setCellValue(employeeReportResult.getPassportNo());
//            cell.setCellStyle(styles[5]);
//            // 籍贯。
//            cell = row.createCell(6);
//            cell.setCellValue(employeeReportResult.getNativePlace());
//            cell.setCellStyle(styles[6]);
//            // 生日。
//            cell = row.createCell(7);
//            cell.setCellValue(employeeReportResult.getBirthday());
//            cell.setCellStyle(styles[7]);
//            // 属相。
//            cell = row.createCell(8);
//            cell.setCellValue(employeeReportResult.getZodiac());
//            cell.setCellStyle(styles[8]);
//            // 入职时间。
//            cell = row.createCell(9);
//            cell.setCellValue(employeeReportResult.getTimeOfEntry());
//            cell.setCellStyle(styles[9]);
//            // 离职类型。
//            cell = row.createCell(10);
//            cell.setCellValue(employeeReportResult.getTypeOfTurnover());
//            cell.setCellStyle(styles[10]);
//            // 离职原因。
//            cell = row.createCell(11);
//            cell.setCellValue(employeeReportResult.getReasonsForLeaving());
//            cell.setCellStyle(styles[11]);
//            // 离职时间。
//            cell = row.createCell(12);
//            cell.setCellValue(employeeReportResult.getResignationTime());
//            cell.setCellStyle(styles[12]);
//        }
//
//        // 下载。
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        wb.write(byteArrayOutputStream);
//        new DownloadUtils().download(byteArrayOutputStream, response, month + "~人事报表.xlsx");
//    }

    /**
     * 采用模版打印的形式完成报表导出。jvm 内存占用太大。
     *
     * @param month 年月-月（2018-02%）。
     * @throws Exception
     */
//    @RequestMapping(value = "/export/{month}", method = RequestMethod.GET)
//    public void exportPlain(@PathVariable String month) throws Exception {
////        // 获取报表数据。
//        List<EmployeeReportResult> list = userCompanyPersonalService.findByReport(companyId, month);
//////        List<EmployeeReportResult> list = userCompanyPersonalService.findByReport("1", month);
//
//        // - 构造 Excel。
//        // 创建工作薄。
//        Workbook workbook = new XSSFWorkbook();
//        // 构造 Sheet。
//        Sheet sheet = workbook.createSheet();
//        // 创建行。
//        // 标题。
//        String[] titles = "编号,姓名,手机,最高学历,国家地区,护照号,籍贯,生日,属相,入职时间,离职类型,离职原因,离职时间".split(",");
//
//        // 处理标题。
//        Row row = sheet.createRow(0);
//
//        int titleIndex = 0;
//
//        for (String title : titles) {
//            Cell cell = row.createCell(titleIndex++);
//            cell.setCellValue(title);
//        }
//
//        // 构造单元格。
//        int rowIndex = 1;
//        Cell cell = null;
//        for (EmployeeReportResult employeeReportResult : list) {
//            row = sheet.createRow(rowIndex++);
//            // 编号。
//            cell = row.createCell(0);
//            cell.setCellValue(employeeReportResult.getUserId());
////            cell.setCellStyle(styles[0]);
//            // 姓名。
//            cell = row.createCell(1);
//            cell.setCellValue(employeeReportResult.getUsername());
////            cell.setCellStyle(styles[1]);
//            // 手机。
//            cell = row.createCell(2);
//            cell.setCellValue(employeeReportResult.getMobile());
////            cell.setCellStyle(styles[2]);
//            // 最高学历。
//            cell = row.createCell(3);
//            cell.setCellValue(employeeReportResult.getTheHighestDegreeOfEducation());
////            cell.setCellStyle(styles[3]);
//            // 国家地区。
//            cell = row.createCell(4);
//            cell.setCellValue(employeeReportResult.getNationalArea());
////            cell.setCellStyle(styles[4]);
//            // 护照号。
//            cell = row.createCell(5);
//            cell.setCellValue(employeeReportResult.getPassportNo());
////            cell.setCellStyle(styles[5]);
//            // 籍贯。
//            cell = row.createCell(6);
//            cell.setCellValue(employeeReportResult.getNativePlace());
////            cell.setCellStyle(styles[6]);
//            // 生日。
//            cell = row.createCell(7);
//            cell.setCellValue(employeeReportResult.getBirthday());
////            cell.setCellStyle(styles[7]);
//            // 属相。
//            cell = row.createCell(8);
//            cell.setCellValue(employeeReportResult.getZodiac());
////            cell.setCellStyle(styles[8]);
//            // 入职时间。
//            cell = row.createCell(9);
//            cell.setCellValue(employeeReportResult.getTimeOfEntry());
////            cell.setCellStyle(styles[9]);
//            // 离职类型。
//            cell = row.createCell(10);
//            cell.setCellValue(employeeReportResult.getTypeOfTurnover());
////            cell.setCellStyle(styles[10]);
//            // 离职原因。
//            cell = row.createCell(11);
//            cell.setCellValue(employeeReportResult.getReasonsForLeaving());
////            cell.setCellStyle(styles[11]);
//            // 离职时间。
//            cell = row.createCell(12);
//            cell.setCellValue(employeeReportResult.getResignationTime());
////            cell.setCellStyle(styles[12]);
//        }
//
//        // 下载。
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        workbook.write(byteArrayOutputStream);
//        new DownloadUtils().download(byteArrayOutputStream, response, month + "~人事报表.xlsx");
//    }

    // Excel 读取。
    // 一次性读取所有 Excel 存入内存中。占用内存。
    // ↓ ↓ ↓
    // 逐行读取并使用。

    // Excel 读取的两种模式。
    // - 用户模式：使用系列封装好的 API 操作 Excel。
    //      操作简单，占用内存。
    // - 事件驱动。
    //      基于 sax 的读取方式。
    //      读取 Excel 的 xml 文件


    // 事件驱动。
    //      主线程（事件监听）。
    //      注册事件处理器。
    // 发生某件事件时，委托给事件处理器进行处理。

    // sax
    // SAX（simple API for XML）是一种 XML 解析的替代方法。相比于 DOM，SAX 是一种速度更快，更有效的方法。它逐行扫描文档，一边扫描一边解析。而且相比于 DOM，SAX 可以在解析文档的任意时刻停止解析，但任何事物都有其相反的一面，对于 SAX 来说就是操作复杂。
    // JAVA 解析 XML 通常有两种方式:DOM 和SAX。DOM（文档对象模型）是 W3C 标准，提供了标准的解析方式，但其解析效率一直不尽如人意，这是因为 DOM 解析 XML 文档时，把所有内容一次性的装载入内存，并构建一个驻留在内存中的树状结构（节点树）。如果需要解析的 XML 文档过大，或者我们只对该文档中的一部分感兴趣，这样就会引起性能问题。

    // SAX，它既是一个接口，也是一个软件包。但作为接口，SAX 是事件驱动型 XML 解析的一个标准接口，不会改变  SAX 的工作原理。简单地说就是对文档进行顺序扫描，当扫描到文档（document）开始与结束、元素（element）开始与结束、文档（document）结束等地方时通知事件处理函数，由事件处理函数做相应动作，然后继续同样的扫描，直至文档结束。
    //大多数 SAX 都会产生以下类型的事件。
    // - 在文档的开始和结束时触发文档处理事件。
    // - 在文档内每一 XML 元素接受解析的前后触发元素事件。
    // - 任何元数据通常由单独的事件处理
    // - 在处理文档的 DTD 或 Schema 时产生 DTD 或 Schema 事件。
    // - 产生错误事件用来通知主机应用程序解析错误。

    /*
    <xml>
        <a></a> ~~~ 处理器 A ~ 触发 A 处理器处理事件。↓ ↓ ↓。（以节点为单位触发处理器处理事件）。
        <b></b> ~~~ 处理器 B ~ 触发 B 处理器处理事件。
    </xml>
     */


    // 2007 版本 Excel 本质 ~ OOXML（XML）。
    // 将一行数据指定为一个处理事件。
}
