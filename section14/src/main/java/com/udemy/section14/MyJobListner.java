package com.udemy.section14;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

public class MyJobListner implements JobExecutionListener {

//    Listening before the job is done
    @Override
    public void beforeJob(JobExecution jobExecution) {
        System.out.println("Job started, before.");
    }

//    Listening after the job is done to see the changes made
    @Override
    public void afterJob(JobExecution jobExecution) {
//        Printing the Job end message with "jobexecution" status
        System.out.println("Job ended - "+jobExecution.getStatus().toString());
    }
}
