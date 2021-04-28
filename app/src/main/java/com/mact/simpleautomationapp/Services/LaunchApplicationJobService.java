package com.mact.simpleautomationapp.Services;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobService;

public class LaunchApplicationJobService extends JobService {

    private boolean isJobCancelled = false;

    @Override
    public boolean onStartJob(JobParameters params) {
        launchApp(params);
        return true;
    }

    private void launchApp(JobParameters params){
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(isJobCancelled){
                    jobFinished(params, false);
                }

            }
        }).start();
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        isJobCancelled = true;
        return false;
    }



}
