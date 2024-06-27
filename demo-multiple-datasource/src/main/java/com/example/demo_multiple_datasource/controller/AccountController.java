package com.example.demo_multiple_datasource.controller;

import com.example.demo_multiple_datasource.entity.Account;
import com.example.demo_multiple_datasource.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping
    public Account getAccount(@RequestParam Long id) {
        return accountService.get(id);
    }

    @PostMapping
    public Long createAccount(@RequestBody String name) {
        return accountService.create(name);
    }
}
