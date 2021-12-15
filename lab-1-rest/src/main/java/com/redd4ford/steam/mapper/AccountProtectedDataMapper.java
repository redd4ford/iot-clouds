package com.redd4ford.steam.mapper;

import com.redd4ford.steam.domain.AccountProtectedData;
import com.redd4ford.steam.dto.AccountProtectedDataDto;
import org.springframework.stereotype.Component;

@Component
public class AccountProtectedDataMapper
    extends AbstractMapper<AccountProtectedData, AccountProtectedDataDto> {

  @Override
  public AccountProtectedDataDto mapObjectToDto(AccountProtectedData accountProtectedData) {
    if (accountProtectedData == null) {
      return null;
    }

    AccountProtectedDataDto.AccountProtectedDataDtoBuilder accountProtectedDataDto =
        AccountProtectedDataDto.builder();

    accountProtectedDataDto.id(accountProtectedData.getAccountId());
    accountProtectedDataDto.email(accountProtectedData.getEmail());

    return accountProtectedDataDto.build();
  }

}
