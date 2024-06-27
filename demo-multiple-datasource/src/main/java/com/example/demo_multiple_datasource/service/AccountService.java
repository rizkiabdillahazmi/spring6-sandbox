package com.example.demo_multiple_datasource.service;

import com.example.demo_multiple_datasource.entity.Account;
import com.example.demo_multiple_datasource.repository.AccountJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AccountService {
    private final AccountJpaRepository accountJpaRepository;
    private final DataSource lazyDataSource;

    public Account get(Long id) {
        try (Connection connection = lazyDataSource.getConnection() ) {
            log.info("read url : {}", connection.getMetaData().getURL());
            return accountJpaRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Found Account"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public Long create(String name) {
        try (Connection connection = lazyDataSource.getConnection() ) {
            log.info("write url : {}", connection.getMetaData().getURL());
            return accountJpaRepository.save(Account.builder().name(name).build()).getId();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
