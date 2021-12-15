package com.redd4ford.steam.repository;

import com.redd4ford.steam.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

  List<Account> getAccountsByCountryId(Integer countryId);

}
