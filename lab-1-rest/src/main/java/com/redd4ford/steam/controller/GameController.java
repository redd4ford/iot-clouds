package com.redd4ford.steam.controller;

import com.redd4ford.steam.domain.Game;
import com.redd4ford.steam.domain.Genre;
import com.redd4ford.steam.dto.GameDto;
import com.redd4ford.steam.dto.GenreDto;
import com.redd4ford.steam.mapper.AbstractMapper;
import com.redd4ford.steam.mapper.GameMapper;
import com.redd4ford.steam.service.AbstractService;
import com.redd4ford.steam.service.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping(value = "/games")
@RestController
public class GameController extends AbstractController<Game, GameDto, Integer> {

  private final GameService gameService;
  private final GameMapper gameMapper;

  public GameController(GameService gameService, GameMapper gameMapper) {
    this.gameService = gameService;
    this.gameMapper = gameMapper;
  }

  @Override
  protected AbstractService<Game, Integer> getService() {
    return gameService;
  }

  @Override
  protected AbstractMapper<Game, GameDto> getMapper() {
    return gameMapper;
  }

  @RequestMapping(method = RequestMethod.GET,
      value = "/genre/{id}")
  public @ResponseBody
  ResponseEntity<List<GameDto>> getAllByGenreId(@PathVariable Integer id) {
    List<Game> games = gameService.getAllByGenreId(id);
    if (games != null) {
      List<GameDto> gameDtos = new ArrayList<>();
      for (Game game : games) {
        gameDtos.add(gameMapper.mapObjectToDto(game));
      }
      return new ResponseEntity<>(gameDtos, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

}
