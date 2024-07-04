package com.GOTEL.SchoolCar.Controller;

import com.GOTEL.SchoolCar.Entity.*;
import com.GOTEL.SchoolCar.Entity.Structure.PieDataItem;
import com.GOTEL.SchoolCar.Result.CommonResult;
import com.GOTEL.SchoolCar.Service.IMPL.ExcelServiceIMPL;
import com.GOTEL.SchoolCar.Service.IMPL.NotifyServiceIMPL;
import com.GOTEL.SchoolCar.Service.IMPL.WorkerServiceIMPL;
import com.GOTEL.SchoolCar.Utils.IDGenerator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;

@RequestMapping("worker")
@RestController
public class WorkerController {
    @Autowired
    WorkerServiceIMPL workerServiceIMPL;
    @Autowired
    ExcelServiceIMPL excelServiceIMPL;
    @Autowired
    private NotifyServiceIMPL notifyServiceIMPL;

    IDGenerator idGenerator = new IDGenerator();

    @GetMapping("all")
    public CommonResult<?> getAll(HttpServletRequest request) {
        Map<String, String> pickMap = getPickMap(request);
        List<Worker> workers = workerServiceIMPL.getAllWorker(pickMap);
        if (!workers.isEmpty()) {
            return CommonResult.success(workers);
        } else {
            return CommonResult.error("没有符合该条件的工作人员");
        }
    }

    private Map<String, String> getPickMap(HttpServletRequest request) {
        String realName = request.getParameter("realNamePick");
        String department = request.getParameter("departmentPick");
        String branch = request.getParameter("branchPick");
        String jobNumber = request.getParameter("jobNumberPick");

        return new HashMap<>() {{
            put("realName", realName);
            put("department", department);
            put("branch", branch);
            put("jobNumber", jobNumber);
        }};
    }

    @GetMapping("getInformation/{wid}")
    public CommonResult<?> getInformation(@PathVariable("wid") String wid) {
        Worker wInfo = workerServiceIMPL.getInfByWID(wid);
        if (wInfo != null) {
            return CommonResult.success(wInfo);
        } else {
            return CommonResult.error();
        }
    }

    @PutMapping("update/{wid}")
    public CommonResult<?> update(@RequestBody Worker worker, @PathVariable("wid") String wid) {
        if (wid.equals(worker.getWID())) {
            boolean b = workerServiceIMPL.updateInf(worker);
            if (b) {
                return CommonResult.success(worker);
            } else {
                return CommonResult.error("修改失败");
            }
        } else {
            return CommonResult.error("用户ID不一致");
        }
    }

    @PutMapping("updatePassword/{wid}")
    public CommonResult<?> updatePassword(@RequestBody UpdatePassword updatePassword, @PathVariable("wid") String wid) {
        String password = workerServiceIMPL.getWPasswordByWID(wid);
        if (password.equals(updatePassword.getOldPassword())) {
            boolean b = workerServiceIMPL.updatePassword(wid, updatePassword.getNewPassword());
            if (b) {
                return CommonResult.success("修改成功");
            } else {
                return CommonResult.error("修改失败");
            }
        } else {
            return CommonResult.error("原密码错误");
        }
    }

    @GetMapping("worker-number")
    public CommonResult<?> getWorkerNumber() {
        int workerNumber = workerServiceIMPL.getWorkerNumber();
        String title = "工作人员";

        return CommonResult.success(new PieDataItem(title, workerNumber));
    }

    @PostMapping("insert-worker")
    public CommonResult<?> insertWorker(@RequestBody Worker worker) {
        Worker byWID = workerServiceIMPL.getByJobNumber(worker.getW_JobNumber());
        if (byWID == null) {
            worker.setWID(idGenerator.generateID(workerServiceIMPL.getAllWid(), "W"));
            worker.setW_Name("swust" + worker.getW_JobNumber().substring(worker.getW_JobNumber().length() - 4));
            worker.setW_Password("swust" + worker.getW_JobNumber().substring(worker.getW_JobNumber().length() - 4));
            boolean b = workerServiceIMPL.insertWorker(worker);
            if (b) {
                return CommonResult.success();
            } else {
                return CommonResult.success("创建失败");
            }
        } else {
            return CommonResult.error("已存在");
        }
    }

    @PostMapping("import")
    public CommonResult<?> importWorker(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws Exception {
        String aid = request.getHeader("id");
        List<List<String>> workersLists = excelServiceIMPL.parseExcelFromMultiFile(file);
        new Thread(new importThread1(workersLists, aid, workerServiceIMPL, notifyServiceIMPL)).start();
        if (!workersLists.isEmpty()) {
            return CommonResult.success();
        } else {
            return CommonResult.error();
        }
    }

    @PostMapping("export")
    public void exportWorker(@RequestBody List<Worker> workers, HttpServletResponse response) throws IOException {
        List<List<String>> data = new ArrayList<>();
        List<String> firstRow = new ArrayList<>() {{
            add("姓名");
            add("用户名");
            add("工号");
            add("密码");
            add("部门");
            add("科室");
            add("电话");
            add("邮箱");
            add("身份证");
        }};
        data.add(firstRow);
        for (Worker worker : workers) {
            List<String> row = new ArrayList<>();
            row.add(worker.getW_RealName());
            row.add(worker.getW_Name());
            row.add(worker.getW_JobNumber());
            row.add(worker.getW_Password());
            row.add(worker.getW_Department());
            row.add(worker.getW_Branch());
            row.add(worker.getW_Tel());
            row.add(worker.getW_Email());
            row.add(worker.getW_ID());
            data.add(row);
        }
        XSSFWorkbook excel = excelServiceIMPL.createExcel(data);
        String fileName = URLEncoder.encode("工作人员信息", StandardCharsets.UTF_8) + ".xlsx";
        response.setContentType("application/binary;charset=ISO8859-1");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        response.setCharacterEncoding("GB2312");
        ServletOutputStream outputStream = response.getOutputStream();
        excel.write(outputStream);
        excel.close();
        outputStream.flush();
        outputStream.close();
    }

    @GetMapping("import-model")
    public void importModel(HttpServletResponse response) throws IOException {
        List<List<String>> data = new ArrayList<>();
        List<String> firstRow = new ArrayList<>() {{
            add("姓名");
            add("工号");
            add("部门");
            add("科室");
            add("电话");
            add("邮箱");
            add("身份证");
        }};
        data.add(firstRow);
        XSSFWorkbook excel = excelServiceIMPL.createExcel(data);
        String fileName = URLEncoder.encode("批量注册工作人员模板", StandardCharsets.UTF_8) + ".xlsx";
        response.setContentType("application/binary;charset=ISO8859-1");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        response.setCharacterEncoding("GB2312");
        ServletOutputStream outputStream = response.getOutputStream();
        excel.write(outputStream);
        excel.close();
        outputStream.flush();
        outputStream.close();
    }
}

class importThread1 implements Runnable {
    private final WorkerServiceIMPL workerServiceIMPL;
    private final NotifyServiceIMPL notifyServiceIMPL;
    private final ExcelServiceIMPL excelServiceIMPL = new ExcelServiceIMPL();
    private final List<Map<String, String>> data = new ArrayList<>();
    private final List<Map<String, String>> result = new ArrayList<>();
    private final IDGenerator idGenerator = new IDGenerator();
    private final String aid;

    public importThread1(List<List<String>> data, String aid, WorkerServiceIMPL workerServiceIMPL, NotifyServiceIMPL notifyServiceIMPL) {
        Map<String, String> workerTableMap = new Worker().getWorkerTableMap();
        List<String> workerTableList = data.get(0);
        data.remove(0);
        data.forEach(list -> {
            Map<String, String> workerMap = new HashMap<>();
            for (int i = 0; i < list.size(); i++) {
                workerMap.put(workerTableMap.get(workerTableList.get(i)), list.get(i));
            }
            this.data.add(workerMap);
        });
        this.aid = aid;
        this.workerServiceIMPL = workerServiceIMPL;
        this.notifyServiceIMPL = notifyServiceIMPL;
    }

    @Override
    public void run() {
        List<List<Map<String, String>>> theData = this.splitData();
        ExecutorService service = Executors.newFixedThreadPool(theData.size());
        List<Future<List<Map<String, String>>>> futures = new ArrayList<>();
        theData.forEach(list -> {
            importCallable1 callable = new importCallable1(list, workerServiceIMPL);
            Future<List<Map<String, String>>> future = service.submit(callable);
            futures.add(future);
        });
        futures.forEach(future -> {
            try {
                result.addAll(future.get());
            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        service.shutdown();
        List<WorkerFalse> theResult = new ArrayList<>();
        result.forEach(workerMap -> {
            theResult.add(new WorkerFalse(workerMap));
        });
        XSSFWorkbook excel = this.getXssfWorkbook(theResult);
        String filePath;
        try {
            filePath = this.createExcelFile(excel);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.createNotify(filePath);
    }

    private void createNotify(String filePath) {
        Notify notify = new Notify();
        notify.setId(idGenerator.generateID(notifyServiceIMPL.getAllId(), "NT"));
        notify.setFid("0");
        notify.setTid(aid);
        notify.setTitle("工作人员注册失败名单");
        notify.setContent(filePath);
        notify.setTime(Timestamp.valueOf(LocalDateTime.now()));
        notify.setContent_type("link");
        notifyServiceIMPL.insert(notify);
    }

    private String createExcelFile(XSSFWorkbook excel) throws IOException {
        String fileName = "导入失败工作人员名单_" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + ".xlsx";
        fileName = fileName.replace(":", "-");
        String folderPath = System.getProperty("user.dir") + "/static/file/import_worker_false";
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String filePath = folderPath + "/" + fileName;
        File file = new File(filePath);
        file.createNewFile();
        try {
            excel.write(new FileOutputStream(file));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return filePath.replace(System.getProperty("user.dir"), "");
    }

    private XSSFWorkbook getXssfWorkbook(List<WorkerFalse> theResult) {
        List<List<String>> excelData = new ArrayList<>();
        List<String> firstRow = new ArrayList<>() {{
            add("姓名");
            add("工号");
            add("部门");
            add("科室");
            add("电话");
            add("邮箱");
            add("身份证");
            add("原因");
        }};
        excelData.add(firstRow);
        for (WorkerFalse item : theResult) {
            List<String> row = new ArrayList<>();
            row.add(item.getRealName());
            row.add(item.getJobNumber());
            row.add(item.getDepartment());
            row.add(item.getBranch());
            row.add(item.getTel());
            row.add(item.getEmail());
            row.add(item.getId());
            row.add(item.getReason());
            excelData.add(row);
        }
        return excelServiceIMPL.createExcel(excelData);
    }

    private List<List<Map<String, String>>> splitData() {
        List<List<Map<String, String>>> theData = new ArrayList<>();
        int startIndex = 0;
        int endIndex = 1000;
        while (true) {
            if (endIndex > data.size()) {
                endIndex = data.size();
            }
            theData.add(data.subList(startIndex, endIndex));
            if (endIndex == data.size()) {
                break;
            }
            startIndex = endIndex;
            endIndex += 1000;
        }
        return theData;
    }
}

class importCallable1 implements Callable<List<Map<String, String>>> {
    private final IDGenerator idGenerator = new IDGenerator();
    private final WorkerServiceIMPL workerServiceIMPL;
    private final List<Map<String, String>> data;

    public importCallable1(List<Map<String, String>> data, WorkerServiceIMPL workerServiceIMPL) {
        this.workerServiceIMPL = workerServiceIMPL;
        this.data = data;
    }

    @Override
    public List<Map<String, String>> call() {
        List<Map<String, String>> result = new ArrayList<>();
        data.forEach(map -> {
            Worker worker = workerServiceIMPL.getByJobNumber(map.get("jobNumber"));
            if (worker == null) {
                map.put("wid", idGenerator.generateID(workerServiceIMPL.getAllWid(), "W"));
                map.put("name",
                        "swust" + map.get("jobNumber").substring(map.get("jobNumber").length() - 4));
                map.put("password",
                        "swust" + map.get("jobNumber").substring(map.get("jobNumber").length() - 4));
                try {
                    workerServiceIMPL.insertByMap(map);
                } catch (Exception e) {
                    map.put("reason", "系统错误，注册失败");
                    result.add(map);
                    throw new RuntimeException(e);
                }
            } else {
                map.put("reason", "此工号用户已存在");
                result.add(map);
            }
        });
        return result;
    }
}
