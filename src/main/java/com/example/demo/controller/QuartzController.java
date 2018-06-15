package com.example.demo.controller;

import com.example.demo.configure.BaseController;
import com.example.demo.service.quartz.QuartzService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/quartz")
public class QuartzController extends BaseController {
    private Logger log = LoggerFactory.getLogger(QuartzController.class);

    @Autowired
    protected QuartzService quartzService;

    @RequestMapping("/pauseJob")
    public Object pauseJob(){
        try {
            quartzService.pauseJob();
            return success("pauseJob success");
        } catch (Exception e) {
            log.error("pauseJob Exception", e);
            return fail(e);
        }
    }

    @RequestMapping("/resumeJob")
    public Object resumeJob(){
        try {
            quartzService.resumeJob();
            return success("resumeJob success");
        } catch (Exception e) {
            log.error("pauseJob Exception", e);
            return fail(e);
        }
    }
}
