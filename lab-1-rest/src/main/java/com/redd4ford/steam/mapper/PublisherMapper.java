package com.redd4ford.steam.mapper;

import com.redd4ford.steam.domain.Publisher;
import com.redd4ford.steam.dto.PublisherDto;
import org.springframework.stereotype.Component;

@Component
public class PublisherMapper extends AbstractMapper<Publisher, PublisherDto> {

  @Override
  public PublisherDto mapObjectToDto(Publisher publisher) {
    if (publisher == null) {
      return null;
    }

    PublisherDto.PublisherDtoBuilder publisherDto = PublisherDto.builder();

    publisherDto.id(publisher.getId());
    publisherDto.countryName(publisher.getCountry().getName());
    publisherDto.name(publisher.getName());
    publisherDto.gamesCounter(publisher.getGames().size());

    return publisherDto.build();
  }

}
