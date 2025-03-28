package com.cryptory.be.global.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Component
public class FileUtils {

    // window, mac 바탕화면 경로 지정
    // S3 등 외부 저장소 사용 시 yml에 경로 설정
//     public static String getBaseDir() {
//        String userHome = System.getProperty("user.home");
//        String os = System.getProperty("os.name").toLowerCase();
//
//        if (os.contains("win")) {
//            return Paths.get(userHome, "Desktop", "cryptory_files").toString();
//        } else if (os.contains("mac")) {
//            return Paths.get(userHome, "Desktop", "cryptory_files").toString();
//        } else {
//            throw new UnsupportedOperationException("지원되지 않는 운영 체제입니다.");
//        }
//    }

    // 환경 변수 사용
//    @Value("${BASE_UPLOAD_DIR}")
//    private String BASE_UPLOAD_DIR;

    public static String getBaseDir() {
//        String baseDir = System.getenv(BASE_UP); // 환경 변수에서 가져오기
//        if (baseDir == null || baseDir.isEmpty()) {
//            baseDir = "/app/uploads"; // 기본값 설정
//        }
//        return baseDir;
        return "/app/uploads";
    }

    public String saveFile(MultipartFile file) {
        if (file.isEmpty()) {
            return null;
        }

        log.info("baseDir: {}", getBaseDir());

        try {
            String dateFolder = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            String uploadDir = Paths.get(getBaseDir(), dateFolder).toString();

            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String originalFilename = file.getOriginalFilename();
            String extension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf(".")); // 확장자 포함 (jpg, png 등)
            }

            log.info("extension: {}", extension);

            String storedFilename = UUID.randomUUID() + extension;
            log.info("storedFilename: {}", storedFilename);

            String filePath = Paths.get(uploadDir, storedFilename).toString();
            log.info("filePath: {}", filePath);

            File dest = new File(filePath);
            file.transferTo(dest);

            return "/" + dateFolder + "/" + storedFilename;
        } catch (IOException e) {
            log.error("파일 저장 실패", e);
            throw new RuntimeException("파일 저장 중 오류 발생");
        }
    }

    // 파일 삭제
    public boolean deleteFile(String storedFilename) {
        File file = new File(Paths.get(getBaseDir(), storedFilename).toString());
        return file.exists() && file.delete();
    }

    // 파일명 반환
    public String getStoredFileName(String storedDir) {
        return storedDir.substring(storedDir.lastIndexOf("/") + 1);
    }

}
