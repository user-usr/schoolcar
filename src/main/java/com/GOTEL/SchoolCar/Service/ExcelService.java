package com.GOTEL.SchoolCar.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ExcelService {
    List<List<String>> parseExcelFromMultiFile(MultipartFile file) throws Exception;
}
