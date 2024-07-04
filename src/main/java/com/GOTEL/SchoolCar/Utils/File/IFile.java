package com.GOTEL.SchoolCar.Utils.File;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.Arrays;
import java.util.Properties;

public class IFile {
    private final FileFolderPathUtils fileFolderPathUtils = new FileFolderPathUtils();
    public String CreateFileFolder(String fileUsage, String id) {
        String fileFolderPath = fileFolderPathUtils.getFilePath(fileUsage, id);

        File fileFolder = new File(fileFolderPath);
        if (!fileFolder.exists()) {
            boolean mkdirs = fileFolder.mkdirs();
            if (!mkdirs) {
                return "文件夹创建失败";
            }
        }
        return fileFolderPath;
    }

    public File CreateFile(String fileFolderPath, String fileName) throws IOException {
        File file = new File(fileFolderPath + "/" + fileName);
        if (!file.exists()) {
            boolean newFile = file.createNewFile();
            if (!newFile) {
                return null;
            }
        }
        return file;
    }

    public boolean TransferToFile(MultipartFile multipartFile, File file) {
        try {
            multipartFile.transferTo(file);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public String getFileRelativePath(String filePath) {
        StringBuilder relativePath = new StringBuilder();
        boolean begin = false;

        Properties properties = new Properties(System.getProperties());
        String FileSeparator = properties.getProperty("file.separator");
        String fileSeparator = FileSeparator.equals("\\") ? "\\\\" : FileSeparator;
        String[] split = filePath.split(fileSeparator);

        for (String path : split) {
            if ("static".equals(path)) {
                begin = true;
            }
            if (begin) {
                relativePath.append(path)
                        .append("/");
            }
        }
        relativePath.deleteCharAt(relativePath.length() - 1);
        return relativePath.toString();
    }
}
