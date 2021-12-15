package com.redd4ford.steam.controller;

import com.redd4ford.steam.domain.AccountProtectedData;
import com.redd4ford.steam.dto.AccountProtectedDataDto;
import com.redd4ford.steam.mapper.AbstractMapper;
import com.redd4ford.steam.mapper.AccountProtectedDataMapper;
import com.redd4ford.steam.service.AbstractService;
import com.redd4ford.steam.service.AccountProtectedDataService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/protected")
@RestController
public class AccountProtectedDataController
    extends AbstractController<AccountProtectedData, AccountProtectedDataDto, Integer> {

  private final AccountProtectedDataService accountProtectedDataService;
  private final AccountProtectedDataMapper accountProtectedDataMapper;

  public AccountProtectedDataController(AccountProtectedDataService accountProtectedDataService,
                                        AccountProtectedDataMapper accountProtectedDataMapper) {
    this.accountProtectedDataService = accountProtectedDataService;
    this.accountProtectedDataMapper = accountProtectedDataMapper;
  }

  @Override
  protected AbstractService<AccountProtectedData, Integer> getService() {
    return accountProtectedDataService;
  }

  @Override
  protected AbstractMapper<AccountProtectedData, AccountProtectedDataDto> getMapper() {
    return accountProtectedDataMapper;
  }

}
