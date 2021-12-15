package com.redd4ford.steam.controller;

import com.redd4ford.steam.domain.Country;
import com.redd4ford.steam.dto.CountryDto;
import com.redd4ford.steam.mapper.AbstractMapper;
import com.redd4ford.steam.mapper.CountryMapper;
import com.redd4ford.steam.service.AbstractService;
import com.redd4ford.steam.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/countries")
@RestController
public class CountryController extends AbstractController<Country, CountryDto, Integer> {

  private final CountryService countryService;
  private final CountryMapper countryMapper;

  public CountryController(CountryService countryService, CountryMapper countryMapper) {
    this.countryService = countryService;
    this.countryMapper = countryMapper;
  }

  @Override
  protected AbstractService<Country, Integer> getService() {
    return countryService;
  }

  @Override
  protected AbstractMapper<Country, CountryDto> getMapper() {
    return countryMapper;
  }

}
