package com.redd4ford.steam.mapper;

import com.redd4ford.steam.domain.Country;
import com.redd4ford.steam.dto.CountryDto;
import org.springframework.stereotype.Component;

@Component
public class CountryMapper extends AbstractMapper<Country, CountryDto> {

  @Override
  public CountryDto mapObjectToDto(Country country) {
    if (country == null) {
      return null;
    }

    CountryDto.CountryDtoBuilder countryDto = CountryDto.builder();

    countryDto.id(country.getId());
    countryDto.name(country.getName());
    countryDto.accountsCounter(country.getAccounts().size());

    return countryDto.build();
  }

}
