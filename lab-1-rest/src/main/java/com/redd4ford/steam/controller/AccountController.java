package com.redd4ford.steam.controller;

import com.redd4ford.steam.domain.Account;
import com.redd4ford.steam.dto.AccountDto;
import com.redd4ford.steam.mapper.AbstractMapper;
import com.redd4ford.steam.mapper.AccountMapper;
import com.redd4ford.steam.service.AbstractService;
import com.redd4ford.steam.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping(value = "/accounts")
@RestController
public class AccountController extends AbstractController<Account, AccountDto, Integer> {

  private final AccountService accountService;
  private final AccountMapper accountMapper;

  public AccountController(AccountService accountService, AccountMapper accountMapper) {
    this.accountService = accountService;
    this.accountMapper = accountMapper;
  }

  @Override
  protected AbstractService<Account, Integer> getService() {
    return accountService;
  }

  @Override
  protected AbstractMapper<Account, AccountDto> getMapper() {
    return accountMapper;
  }

  @RequestMapping(method = RequestMethod.GET,
      value = "/country/{id}")
  public @ResponseBody
  ResponseEntity<List<AccountDto>> getAllByCountry(@PathVariable Integer id) {
    List<Account> accounts = accountService.getAllByCountryId(id);
    if (accounts != null) {
      List<AccountDto> accountDtos = new ArrayList<>();
      for (Account account : accounts) {
        accountDtos.add(accountMapper.mapObjectToDto(account));
      }
      return new ResponseEntity<>(accountDtos, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

}
