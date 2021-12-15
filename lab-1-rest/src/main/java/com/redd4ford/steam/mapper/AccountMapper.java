package com.redd4ford.steam.mapper;

import com.redd4ford.steam.domain.Account;
import com.redd4ford.steam.dto.AccountDto;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper extends AbstractMapper<Account, AccountDto> {

  @Override
  public AccountDto mapObjectToDto(Account account) {
    if (account == null) {
      return null;
    }

    AccountDto.AccountDtoBuilder accountDto = AccountDto.builder();

    accountDto.id(account.getId());
    accountDto.accountName(account.getAccountName());
    accountDto.countryName(account.getCountry().getName());
    accountDto.level(account.getLevel());

    return accountDto.build();
  }

}
