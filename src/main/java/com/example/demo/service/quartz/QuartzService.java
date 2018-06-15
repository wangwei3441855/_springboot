package com.example.demo.service.quartz;

import com.example.demo.domain.QuartzJob;

public interface QuartzService {
    void addQuartzJob(QuartzJob job);
    void startQuartzJob();
    void pauseJob();
    void resumeJob();
}
