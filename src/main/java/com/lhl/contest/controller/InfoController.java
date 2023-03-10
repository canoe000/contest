package com.lhl.contest.controller;

import com.lhl.contest.entity.Info;
import com.lhl.contest.entity.InfoPara;
import com.lhl.contest.entity.Type;
import com.lhl.contest.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class InfoController {
    @Autowired
    private InfoService infoService;

    //获取所有信息
    @GetMapping("/listAllInfo")
    public List<Info> listAllInfo() throws IOException {
        return infoService.listInfo(null);
    }

    //获取关键字进行查询
    @GetMapping("/searchInfo")
    public List search(@RequestParam("keyword") String keyword) throws IOException {
        return infoService.listInfo(keyword);
    }

    //通过Id获取信息内容
    @GetMapping("/getInfoById")
    public Info getInfoById(@RequestParam("id") int id) throws IOException {
        return infoService.getInfoById(id);
    }
    //通过类型获取信息
    @GetMapping("/getInfoByType")
    public List getInfoByType(@RequestParam("type") int type) throws IOException {
        switch (type){
            case 1:
                return infoService.getInfoByType("经济");
            case 2:
                return infoService.getInfoByType("文化");
            default:
                return infoService.getInfoByType("生活");
        }
    }



    //测试！！！上传信息
    @PostMapping("uploadInfo")
    public String uploadInfo(@RequestParam("infoTitle") String infoTitle,
                             @RequestParam("infoContent") String[] infoParas,
                             @RequestParam("infoType") String infoType,
                             @RequestParam("imgs") MultipartFile[] imgs
    ) throws IOException {
        Info info = new Info();
        info.setInfoTitle(infoTitle);
        //未处理的信息段落（数组转化为列表）
        info.setInfoParaList(new ArrayList<String>(Arrays.asList(infoParas)));
        info.setInfoType(infoType);
        //未处理的图片（数组转化为列表）
        info.setImgList(new ArrayList<MultipartFile>(Arrays.asList(imgs)));
        if (infoService.saveInfo(info)) {
            return "上传成功！";
        } else {
            return "上传失败！";
        }
    }

    //测试：获取数据库所有图片
    @GetMapping("/img")
    public List listAllImg() {
        return infoService.listAllImg();
    }

}
