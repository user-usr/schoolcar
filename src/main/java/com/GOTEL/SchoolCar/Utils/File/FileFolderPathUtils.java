package com.GOTEL.SchoolCar.Utils.File;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@ToString
public class FileFolderPathUtils {
    private String basicPath;
    private Map<String, String> folderPath = new HashMap<>();

    public FileFolderPathUtils() {
        this.basicPath = System.getProperty("user.dir") + "/static/file/";
        this.folderPath.put("电动车信息更新", "carInformationChange/");
        this.folderPath.put("电动车信息入库", "carInformationCreate/");
        this.folderPath.put("电动车违规信息上传", "carViolationInsert/");
    }

    public String getFilePath(String fileUsage, String id) {
        return this.getBasicPath() + this.folderPath.get(fileUsage) + id;
    }
}
