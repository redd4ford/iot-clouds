package com.redd4ford.steam.mapper;

import com.redd4ford.steam.domain.Genre;
import com.redd4ford.steam.dto.GenreDto;
import org.springframework.stereotype.Component;

@Component
public class GenreMapper extends AbstractMapper<Genre, GenreDto> {

  @Override
  public GenreDto mapObjectToDto(Genre genre) {
    if (genre == null) {
      return null;
    }

    GenreDto.GenreDtoBuilder genreDto = GenreDto.builder();

    genreDto.id(genre.getId());
    genreDto.name(genre.getName());

    return genreDto.build();
  }

}
