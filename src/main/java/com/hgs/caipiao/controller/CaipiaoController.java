package com.hgs.caipiao.controller;

import com.hgs.caipiao.entity.CaipiaoNumberEntity;
import com.hgs.caipiao.entity.CaipiaoUserEntity;
import com.hgs.caipiao.entity.CaipiaoWinSituationEntity;
import com.hgs.caipiao.entity.HttpResponse;
import com.hgs.caipiao.service.CaipiaoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ：206612
 * @date ：Created in 2021/9/29 15:00
 * @description：TestController
 */
@RestController
@RequestMapping(value = "caipiao")
public class CaipiaoController extends BaseController {
    @Autowired
    private CaipiaoServiceImpl service;

    @GetMapping("/refreshhistory")
    public HttpResponse<String> refreshHistory()
    {
        return checkResult(service.refreshHistory());
    }


    @PostMapping("/addnumber")
    public HttpResponse<String> addNumber(@RequestBody CaipiaoNumberEntity entity)
    {
        return checkResult(service.addNumber(entity));
    }

    @GetMapping("/getnumbersbyuserid/{userId}")
    public HttpResponse<List<CaipiaoNumberEntity>> getNumbersByUserId(@PathVariable("userId") String userId)
    {
        return checkResult(service.getNumbersByUserId(userId));
    }

    @PostMapping("/adduser")
    public HttpResponse<String> addUser(@RequestBody CaipiaoUserEntity entity)
    {
        return checkResult(service.addUser(entity));
    }

    @GetMapping("/getuser")
    public HttpResponse<List<CaipiaoUserEntity>> getUser()
    {
        return checkResult(service.getUser());
    }

    @DeleteMapping("/deleteuserbyuserid/{userId}")
    public HttpResponse<String> deleteUserByUserId(@PathVariable("userId") String userId)
    {
        return checkResult(service.deleteUserByUserId(userId));
    }

    @PutMapping("/updateuser")
    public HttpResponse<String> updateUser(@RequestBody CaipiaoUserEntity entity)
    {
        return checkResult(service.updateUser(entity));
    }


    @GetMapping("/checkwinsituation/{userId}")
    public HttpResponse<CaipiaoWinSituationEntity> checkWinSituation(@PathVariable("userId") String userId)
    {
        return checkResult(service.checkWinSituation(userId));
    }

}
