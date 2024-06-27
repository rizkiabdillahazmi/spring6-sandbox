package com.example.demo_multiple_datasource.repository;

import com.example.demo_multiple_datasource.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountJpaRepository extends JpaRepository<Account, Long> {
}
