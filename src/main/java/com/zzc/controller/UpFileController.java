package com.zzc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @Author:zzc
 * @Date:2021/1/9 19:15
 * @Version:原创
 * @Title:
 */
@Controller
public class UpFileController {

    //访问 localhost 自动跳转到上传页面
    @GetMapping("/")
    public String index() {
        return "FileUp";
    }

    //上传业务处理
    //MultipartFile是Spring上传文件的封装类，包含了文件的二进制流和文件属性等信息，在配置文件中也可对相关属性进行配置
    @PostMapping("/upload")
    public String singleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes, HttpServletRequest request) {
        //判断是否获取前台文件路径
        if (file.isEmpty()) {
            request.setAttribute("message", "Please select a file to upload");
            return "ResultOf";
        }
        try {
            byte[] bytes = file.getBytes();
            //封装路径
            Path dir = Paths.get("E:\\upFile");
            //判断路径是否存在
            if (!Files.exists(dir)){
                //不存在创建
                Files.createDirectories(dir);
            }
            Path path = Paths.get("E:\\upFile" , file.getOriginalFilename());
            Files.write(path, bytes);
            request.setAttribute("message",
                    "You successfully uploaded '" + file.getOriginalFilename() + "'");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "ResultOf";
    }

}
