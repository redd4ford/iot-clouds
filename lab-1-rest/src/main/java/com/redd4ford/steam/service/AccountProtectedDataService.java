package com.redd4ford.steam.service;

import com.redd4ford.steam.domain.AccountProtectedData;
import com.redd4ford.steam.repository.AccountProtectedDataRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountProtectedDataService extends AbstractService<AccountProtectedData, Integer> {

  private final AccountProtectedDataRepository accountProtectedDataRepository;

  public AccountProtectedDataService(AccountProtectedDataRepository
                                         accountProtectedDataRepository) {
    this.accountProtectedDataRepository = accountProtectedDataRepository;
  }

  @Override
  protected JpaRepository<AccountProtectedData, Integer> getRepository() {
    return accountProtectedDataRepository;
  }

}
