package com.example.demo;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class MyBatisXmlWatcher {

    @PostConstruct
    public void start() {

        Thread thread = new Thread(() -> {

            System.out.println("=== XML Watcher 시작 ===");

            while (!Thread.currentThread().isInterrupted()) {

                try {
                    // 파일 감시
                    // System.out.println("Watcher 실행 중");

                    Thread.sleep(1000);

                } catch (InterruptedException e) {

                    Thread.currentThread().interrupt();
                    break;
                }
            }

        }, "mybatis-xml-watcher");

        thread.start();
    }
}