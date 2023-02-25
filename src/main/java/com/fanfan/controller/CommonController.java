package com.fanfan.controller;

import com.fanfan.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/common")
public class CommonController {

    @Value("${reggie.path}")
    private String imgPath;

    @PostMapping("/upload")
    public R<String> upload(MultipartFile file) {
        log.info(file.toString());
        //获取原始文件名
        String filename = file.getOriginalFilename();
        //断言文件名不为null
        assert filename != null;
        //用.分割文件名的后缀
        String s = filename.substring(filename.lastIndexOf("."));
        String fileName = UUID.randomUUID() + s;
        //创建要保存的目录
        File dir = new File(imgPath);
        //判断目录存在
        if (!dir.exists()){
            //如果img目录不存在就创建目录
            dir.mkdir();
        }

        try {
            file.transferTo(new File(imgPath+fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return R.success(fileName);


    }

    /**
     * 下载
     * @param name
     * @param response
     */
    @GetMapping("/download")
    public void download(String name , HttpServletResponse response){
        try {
            //输入流，读取文件
            FileInputStream fileInputStream = new FileInputStream(imgPath + name);
            //输出流。上传文件给浏览器
            ServletOutputStream outputStream = response.getOutputStream();
            response.setContentType("image/jpeg");

            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) != -1){
                outputStream.write(bytes,0,len);
                outputStream.flush();
            }

            //关闭资源
            outputStream.close();
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
