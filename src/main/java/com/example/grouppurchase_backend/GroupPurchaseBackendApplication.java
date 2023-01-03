package com.example.grouppurchase_backend;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class GroupPurchaseBackendApplication {

    static public StdSchedulerFactory factory = new StdSchedulerFactory();
    static public Scheduler scheduler;

    static {
        try {
            scheduler = factory.getScheduler();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public GroupPurchaseBackendApplication() {
    }

    public static void main(String[] args) {
        SpringApplication.run(GroupPurchaseBackendApplication.class, args);
    }

}
