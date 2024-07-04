package com.GOTEL.SchoolCar.Controller;

import com.GOTEL.SchoolCar.Result.CommonResult;
import com.GOTEL.SchoolCar.Utils.File.IFile;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@RestController
@RequestMapping("file")
public class FileController {
    private final IFile iFile = new IFile();
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @PostMapping("upload")
    public CommonResult<?> uploadFile(HttpServletRequest request, @RequestParam("file") MultipartFile file) throws IOException {
        String fileUsage = URLDecoder.decode(request.getHeader("fileUsage"), StandardCharsets.UTF_8);
        String id = request.getHeader("id");
        String filename = sdf.format(new Date()).replace(":", "-")
                + Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf("."));

        String fileFolderPath = iFile.CreateFileFolder(fileUsage, id);
        if (!"文件夹创建失败".equals(fileFolderPath)) {
            File localFile = iFile.CreateFile(fileFolderPath, filename);
            if (localFile != null) {
                boolean transfer = iFile.TransferToFile(file, localFile);
                if (transfer) {
                    String fileRelativePath = iFile.getFileRelativePath(localFile.getPath());
                    return CommonResult.success(fileRelativePath);
                }
            }
        }
        return CommonResult.error("文件上传失败");
    }

    @GetMapping("download")
    public void downloadFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = request.getParameter("path");
        String filePath = System.getProperty("user.dir") + path;
        File file = new File(filePath);
        FileInputStream inputStream = new FileInputStream(file);
        XSSFWorkbook excel = new XSSFWorkbook(inputStream);
        String[] split = path.split("/");
        String name = split[split.length - 1].split("\\.")[0];
        String fileName = URLEncoder.encode(name, StandardCharsets.UTF_8) + ".xlsx";
        response.setContentType("application/binary;charset=ISO8859_1");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        response.setCharacterEncoding("GB2312");
        excel.write(response.getOutputStream());
        excel.close();
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }
}
