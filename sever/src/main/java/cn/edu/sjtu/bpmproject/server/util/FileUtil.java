package cn.edu.sjtu.bpmproject.server.util;

import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileUtil {

    public static File transferFile(MultipartFile multipartFile) throws IOException {
        File path = null;
        try {
            path = new File(ResourceUtils.getURL("classpath:").getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (!path.exists()) path = new File("");
        File upload = new File(path.getAbsolutePath(), "static/file/upload/");
        if (!upload.exists()) upload.mkdirs();
        File tempFile = new File(upload + "/" + multipartFile.getOriginalFilename());
        if (!tempFile.getParentFile().exists()) {
            tempFile.getParentFile().mkdirs();//创建父级文件路径
            try {
                tempFile.createNewFile();//创建文件
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            multipartFile.transferTo(tempFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tempFile;
    }
}
