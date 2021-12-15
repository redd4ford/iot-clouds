package com.redd4ford.steam.controller;

import com.redd4ford.steam.domain.Genre;
import com.redd4ford.steam.dto.GenreDto;
import com.redd4ford.steam.mapper.AbstractMapper;
import com.redd4ford.steam.mapper.GenreMapper;
import com.redd4ford.steam.service.AbstractService;
import com.redd4ford.steam.service.GenreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping(value = "/genres")
@RestController
public class GenreController extends AbstractController<Genre, GenreDto, Integer> {

  private final GenreService genreService;
  private final GenreMapper genreMapper;

  public GenreController(GenreService genreService, GenreMapper genreMapper) {
    this.genreService = genreService;
    this.genreMapper = genreMapper;
  }

  @Override
  protected AbstractService<Genre, Integer> getService() {
    return genreService;
  }

  @Override
  protected AbstractMapper<Genre, GenreDto> getMapper() {
    return genreMapper;
  }

  @RequestMapping(method = RequestMethod.GET,
      value = "/game/{id}")
  public @ResponseBody
  ResponseEntity<List<GenreDto>> getAllByGameId(@PathVariable Integer id) {
    List<Genre> genres = genreService.getAllByGameId(id);
    if (genres != null) {
      List<GenreDto> genreDtos = new ArrayList<>();
      for (Genre genre : genres) {
        genreDtos.add(genreMapper.mapObjectToDto(genre));
      }
      return new ResponseEntity<>(genreDtos, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

}
