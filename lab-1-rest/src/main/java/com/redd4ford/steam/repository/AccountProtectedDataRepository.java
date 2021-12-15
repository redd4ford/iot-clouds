package com.redd4ford.steam.repository;

import com.redd4ford.steam.domain.AccountProtectedData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountProtectedDataRepository extends JpaRepository<AccountProtectedData,
    Integer> {
}
