package com.example.demo.configure.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SparkConfig {

    private String sparkHome = ".";
    private String appName = "sparkTest";
    private String master = "local";

    @Bean
    public SparkConf sparkConf() {
        SparkConf conf = new SparkConf()
                .setSparkHome(sparkHome)
                .setAppName(appName)
                .setMaster(master);
        return conf;
    }

    @Bean
    public JavaSparkContext javaSparkContext() {
        return new JavaSparkContext(sparkConf());
    }

    @Bean
    public SparkSession sparkSession() {
        SparkSession sparkSession = SparkSession.builder().config(sparkConf()).getOrCreate();
        return sparkSession;
    }

}
