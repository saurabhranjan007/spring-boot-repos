package com.udemy.section14;


import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Here we're configuring our Batch job by creating all the step (ItemReader, ItemProcessor, ItemWriter) beans in a configuration file
@Configuration
public class BatchConfig {

    @Autowired
    private StepBuilderFactory sbf;

    @Autowired
    private JobBuilderFactory jbf;

//    creating the Spring Batch "Job" using the configurations of "Step" and "JobBuilderFactory"
    @Bean
    public Job job() {
        return jbf.get("job1").incrementer(new RunIdIncrementer())
                .listener(listener())
                .start(step())
                .build();
    }

    //    creating "Step" for Batching using "StepBuilderFactory"
    @Bean
    public Step step() {
        return sbf.get("step1").<String, String>chunk(1)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

//    exposing step beans as methods to be later used while configuring "Step"
    @Bean
    public Reader reader() {
        return new Reader();
    }

    @Bean
    public Processor processor() {
        return new Processor();
    }

    @Bean
    public Writer writer() {
        return new Writer();
    }

//    exposing "JobListener" as method

    @Bean
    public MyJobListner listener() {
        return new MyJobListner();
    }

//    Now using all these exposed methods we can define "Step" and "Job" for Spring Batching.
}
