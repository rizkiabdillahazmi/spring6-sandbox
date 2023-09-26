package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Rizki Abdillah Azmi on 23-Sep-23
 */

@RestController
public class BaseController {
    @GetMapping
    public ResponseEntity<String> get() throws InterruptedException {
        Thread.sleep(2000);
        return ResponseEntity.ok(Thread.currentThread().toString());
    }
}
