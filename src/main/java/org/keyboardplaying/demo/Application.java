package org.keyboardplaying.demo;

import org.keyboardplaying.demo.monitor.Monitor;
import org.keyboardplaying.demo.runtime.OsInformation;
import org.keyboardplaying.demo.runtime.RuntimeInformation;
import org.powerapi.reporter.ConsoleDisplay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.UUID;

@SpringBootApplication
public class Application {

    @Autowired
    private RuntimeInformation runtime;
    @Autowired
    private OsInformation os;

    private Monitor monitor;

    @PostConstruct
    public void initMonitoring() {
        System.out.println(">>>> Start monitoring");

        monitor = new Monitor(runtime.getPid(), os, new ConsoleDisplay());

        monitor.startMonitoring();
        while (true) {
            UUID.randomUUID();
        }
    }

    @PreDestroy
    public void stopMonitoring() {
        System.out.println(">>>> Destroy monitoring");
        monitor.stopMonitoring();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}