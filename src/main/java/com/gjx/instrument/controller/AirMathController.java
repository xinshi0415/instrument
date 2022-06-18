package com.gjx.instrument.controller;

import com.gjx.instrument.service.AirMathService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
@RequestMapping("/airMathController")
public class AirMathController {
    @Autowired
    private AirMathService airMathService;

    /**
     * 处理汇总空气质量excel并生成本地文件
     * @return
     */
    @RequestMapping("/getAirExcel")
    @ResponseBody
    public String getAirExcel(String inputUrl, String outputUrl) {
        try {
            airMathService.collectAirExcel(inputUrl, outputUrl);
        } catch (Exception e) {
            log.error("后台处理数据失败，失败msg={}", e.getMessage());
            //返回1 证明失败
            return "1";
        }
        return "0";
    }
}
