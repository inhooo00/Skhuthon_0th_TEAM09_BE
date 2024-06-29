package com.skhuthon.skhuthon_0th_team9.app.service.review;

import com.skhuthon.skhuthon_0th_team9.global.util.GetS3Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface S3ImageFileService {

    GetS3Resource uploadSingleImageFile(MultipartFile imageFile, String directory) throws IOException;
    List<GetS3Resource> uploadImageFiles(List<MultipartFile> imageFiles, String directory);
    void deleteFile(String imageFileName);
}

