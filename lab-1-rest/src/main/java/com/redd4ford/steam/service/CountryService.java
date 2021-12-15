package com.redd4ford.steam.service;

import com.redd4ford.steam.domain.Country;
import com.redd4ford.steam.repository.CountryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class CountryService extends AbstractService<Country, Integer> {

  private final CountryRepository countryRepository;

  public CountryService(CountryRepository countryRepository) {
    this.countryRepository = countryRepository;
  }

  @Override
  protected JpaRepository<Country, Integer> getRepository() {
    return countryRepository;
  }

}
