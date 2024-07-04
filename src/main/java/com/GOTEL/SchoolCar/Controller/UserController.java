package com.GOTEL.SchoolCar.Controller;

import com.GOTEL.SchoolCar.Entity.Notify;
import com.GOTEL.SchoolCar.Entity.Structure.PieDataItem;
import com.GOTEL.SchoolCar.Entity.UpdatePassword;
import com.GOTEL.SchoolCar.Entity.User;
import com.GOTEL.SchoolCar.Entity.UserFalse;
import com.GOTEL.SchoolCar.Result.CommonResult;
import com.GOTEL.SchoolCar.Service.IMPL.ExcelServiceIMPL;
import com.GOTEL.SchoolCar.Service.IMPL.NotifyServiceIMPL;
import com.GOTEL.SchoolCar.Service.IMPL.UserServiceIMPL;
import com.GOTEL.SchoolCar.Utils.IDGenerator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserServiceIMPL userServiceIMPL;
    @Autowired
    private ExcelServiceIMPL excelServiceIMPL;
    @Autowired
    private NotifyServiceIMPL notifyServiceIMPL;

    @GetMapping("all")
    public CommonResult<?> getAll(HttpServletRequest request) {
        Map<String, String> pickMap = getPickMap(request);
        List<User> users = userServiceIMPL.getAllUser(pickMap);

        if (!users.isEmpty()) {
            return CommonResult.success(users);
        } else {
            return CommonResult.error("没有符合条件的用户");
        }
    }

    private Map<String, String> getPickMap(HttpServletRequest request) {
        String realName = request.getParameter("realNamePick");
        String studentId = request.getParameter("studentIdPick");
        String college = request.getParameter("collegePick");
        String major = request.getParameter("majorPick");
        String grade = request.getParameter("gradePick");
        String classNumber = request.getParameter("classPick");

        return new HashMap<>() {{
            put("realName", realName);
            put("studentId", studentId);
            put("college", college);
            put("major", major);
            put("grade", grade);
            put("classNumber", classNumber);
        }};
    }

    @GetMapping("student-id-all")
    public CommonResult<?> getAllStudentId() {
        return CommonResult.success(userServiceIMPL.getAllStudentId());
    }

    @PutMapping("update/{uid}")
    public CommonResult<?> update(@RequestBody User user, @PathVariable("uid") String uid) {
        if (uid.equals(user.getUID())) {
            Boolean b = userServiceIMPL.updateUser(user);
            if (b) {
                return CommonResult.success(user);
            } else {
                return CommonResult.error("修改失败");
            }
        } else {
            return CommonResult.error("用户ID不一致");
        }
    }

    @GetMapping("user-number")
    public CommonResult<?> getUserNumber() {
        int userNumber = userServiceIMPL.getUserNumber();
        String title = "普通用户";

        PieDataItem pieDataItem = new PieDataItem(title, userNumber);

        return CommonResult.success(pieDataItem);
    }

    @PutMapping("updatePassword/{uid}")
    public CommonResult<?> updatePassword(@RequestBody UpdatePassword updatePassword, @PathVariable("uid") String uid) {
        String password = userServiceIMPL.getUPasswordByUID(uid);
        if (password.equals(updatePassword.getOldPassword())) {
            Boolean b = userServiceIMPL.updatePassword(uid, updatePassword.getNewPassword());
            if (b) {
                return CommonResult.success("修改成功");
            } else {
                return CommonResult.error("修改失败");
            }
        } else {
            return CommonResult.error("原密码错误");
        }
    }

    @GetMapping("getInformation/{uid}")
    public CommonResult<?> getInformation(@PathVariable("uid") String uid) {
        User uInfo = userServiceIMPL.getUInformationByUID(uid);
        if (uInfo != null) {
            return CommonResult.success(uInfo);
        } else {
            return CommonResult.error();
        }
    }

    @PostMapping("import")
    public CommonResult<?> importUser(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws Exception {
        String aid = request.getHeader("id");
        List<List<String>> usersLists = excelServiceIMPL.parseExcelFromMultiFile(file);
        new Thread(new importThread(usersLists, aid, userServiceIMPL, notifyServiceIMPL)).start();
        if (!usersLists.isEmpty()) {
            return CommonResult.success();
        } else {
            return CommonResult.error();
        }
    }

    @PostMapping("export")
    public void exportUser(@RequestBody List<User> users, HttpServletResponse response) throws IOException {
        List<List<String>> data = new ArrayList<>();
        List<String> firstRow = new ArrayList<>() {{
            add("姓名");
            add("用户名");
            add("学号");
            add("密码");
            add("学院");
            add("专业");
            add("年级");
            add("班级");
            add("电话");
            add("邮箱");
            add("身份证");
        }};
        data.add(firstRow);
        for (User user : users) {
            List<String> row = new ArrayList<>();
            row.add(user.getU_RealName());
            row.add(user.getU_Name());
            row.add(user.getU_StudentID());
            row.add(user.getU_Password());
            row.add(user.getU_College());
            row.add(user.getU_Major());
            row.add(user.getU_Grade());
            row.add(user.getU_Class());
            row.add(user.getU_Tel());
            row.add(user.getU_Email());
            row.add(user.getU_ID());
            data.add(row);
        }
        XSSFWorkbook excel = excelServiceIMPL.createExcel(data);
        String fileName = URLEncoder.encode("用户信息", StandardCharsets.UTF_8) + ".xlsx";
        response.setContentType("application/binary;charset=ISO8859_1");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        response.setCharacterEncoding("GB2312");
        excel.write(response.getOutputStream());
        excel.close();
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }

    @GetMapping("import-model")
    public void importModel(HttpServletResponse response) throws IOException {
        List<List<String>> data = new ArrayList<>();
        List<String> firstRow = new ArrayList<>() {{
            add("姓名");
            add("学号");
            add("学院");
            add("专业");
            add("年级");
            add("班级");
            add("电话");
            add("邮箱");
            add("身份证");
        }};
        data.add(firstRow);
        XSSFWorkbook excel = excelServiceIMPL.createExcel(data);
        String fileName = URLEncoder.encode("用户批量注册模板", StandardCharsets.UTF_8) + ".xlsx";
        response.setContentType("application/binary;charset=ISO8859_1");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        response.setCharacterEncoding("GB2312");
        excel.write(response.getOutputStream());
        excel.close();
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }
}

class importThread implements Runnable {
    private final UserServiceIMPL userServiceIMPL;
    private final NotifyServiceIMPL notifyServiceIMPL;
    private final ExcelServiceIMPL excelServiceIMPL = new ExcelServiceIMPL();
    private final List<Map<String, String>> data = new ArrayList<>();
    private final List<Map<String, String>> result = new ArrayList<>();
    private final IDGenerator idGenerator = new IDGenerator();
    private final String aid;

    public importThread(List<List<String>> data, String aid, UserServiceIMPL userServiceIMPL, NotifyServiceIMPL notifyServiceIMPL) {
        Map<String, String> userTableMap = new User().getUserTableMap();
        List<String> userTableList = data.get(0);
        data.remove(0);
        data.forEach(list -> {
            Map<String, String> userMap = new HashMap<>();
            for (int i = 0; i < list.size(); i++) {
                userMap.put(userTableMap.get(userTableList.get(i)), list.get(i));
            }
            this.data.add(userMap);
        });
        this.aid = aid;
        this.userServiceIMPL = userServiceIMPL;
        this.notifyServiceIMPL = notifyServiceIMPL;
    }

    @Override
    public void run() {
        List<List<Map<String, String>>> theData = this.splitData();
        ExecutorService service = Executors.newFixedThreadPool(theData.size());
        List<Future<List<Map<String, String>>>> futures = new ArrayList<>();
        theData.forEach(list -> {
            importCallable callable = new importCallable(userServiceIMPL, list);
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
        List<UserFalse> theResult = new ArrayList<>();
        result.forEach(userMap -> {
            theResult.add(new UserFalse(userMap));
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
        notify.setTitle("用户注册失败名单");
        notify.setContent(filePath);
        notify.setTime(Timestamp.valueOf(LocalDateTime.now()));
        notify.setContent_type("link");
        notifyServiceIMPL.insert(notify);
    }

    private String createExcelFile(XSSFWorkbook excel) throws IOException {
        String fileName = "导入失败用户名单_" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + ".xlsx";
        fileName = fileName.replace(":", "-");
        String folderPath = System.getProperty("user.dir") + "/static/file/import_user_false";
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

    private XSSFWorkbook getXssfWorkbook(List<UserFalse> theResult) {
        List<List<String>> excelData = new ArrayList<>();
        List<String> firstRow = new ArrayList<>() {{
            add("姓名");
            add("学号");
            add("学院");
            add("专业");
            add("年级");
            add("班级");
            add("电话");
            add("邮箱");
            add("身份证");
            add("原因");
        }};
        excelData.add(firstRow);
        for (UserFalse userFalse : theResult) {
            List<String> row = new ArrayList<>();
            row.add(userFalse.getRealName());
            row.add(userFalse.getStudentID());
            row.add(userFalse.getCollege());
            row.add(userFalse.getMajor());
            row.add(userFalse.getGrade());
            row.add(userFalse.getClassNumber());
            row.add(userFalse.getTel());
            row.add(userFalse.getEmail());
            row.add(userFalse.getId());
            row.add(userFalse.getReason());
            excelData.add(row);
        }
        XSSFWorkbook excel = excelServiceIMPL.createExcel(excelData);
        return excel;
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

class importCallable implements Callable<List<Map<String, String>>> {
    private final IDGenerator idGenerator = new IDGenerator();
    private final UserServiceIMPL userServiceIMPL;
    private final List<Map<String, String>> data;

    public importCallable(UserServiceIMPL userServiceIMPL, List<Map<String, String>> data) {
        this.userServiceIMPL = userServiceIMPL;
        this.data = data;
    }

    @Override
    public List<Map<String, String>> call() {
        List<Map<String, String>> result = new ArrayList<>();
        data.forEach(map -> {
            User user = userServiceIMPL.getUserByUserStudentID(map.get("studentID"));
            if (user == null) {
                map.put("uid", idGenerator.generateID(userServiceIMPL.getAllUID(), "U"));
                try {
                    map.put("name",
                            "swust" + map.get("studentID").substring(map.get("studentID").length() - 4));
                    map.put("password",
                            "swust" + map.get("studentID").substring(map.get("studentID").length() - 4));
                } catch (Exception e){
                    map.put("reason", "学号格式错误");
                    result.add(map);
                    throw new RuntimeException(e);
                }
                try {
                    userServiceIMPL.insertByMap(map);
                } catch (Exception e) {
                    map.put("reason", "系统错误，注册失败");
                    result.add(map);
                    throw new RuntimeException(e);
                }
            } else {
                map.put("reason", "此学号用户已存在");
                result.add(map);
            }
        });
        return result;
    }
}
