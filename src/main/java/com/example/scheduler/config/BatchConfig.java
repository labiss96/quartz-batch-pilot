package com.example.scheduler.config;

import com.example.scheduler.tasks.MyTaskOne;
import com.example.scheduler.tasks.MyTaskTwo;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;


    @Bean
    public Step stepOne() {
        return stepBuilderFactory.get("stepOne")
                .tasklet(new MyTaskOne())
                .build();
    }

    @Bean
    public Step stepTwo() {
        return stepBuilderFactory.get("stepTwo")
                .tasklet(new MyTaskTwo())
                .build();
    }

    @Bean(name = "demoJobOne")
    public Job demoJobOne() {
        return jobBuilderFactory.get("demoJobOne")
                .start(stepOne())
                .next(stepTwo())
                .build();
    }

    @Bean(name = "demoJobTwo")
    public Job demoJobTwo() {
        return jobBuilderFactory.get("demoJobTwo")
                .flow(stepOne())
                .build()
                .build();
    }

}
