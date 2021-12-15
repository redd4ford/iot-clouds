package com.redd4ford.steam.service;

import com.redd4ford.steam.domain.Game;
import com.redd4ford.steam.domain.Genre;
import com.redd4ford.steam.repository.GameRepository;
import com.redd4ford.steam.repository.GenreRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService extends AbstractService<Game, Integer> {

  private final GameRepository gameRepository;
  private final GenreRepository genreRepository;

  public GameService(GameRepository gameRepository, GenreRepository genreRepository) {
    this.gameRepository = gameRepository;
    this.genreRepository = genreRepository;
  }

  @Override
  protected JpaRepository<Game, Integer> getRepository() {
    return gameRepository;
  }

  public List<Game> getAllByGenreId(Integer id) {
    Genre genre = genreRepository.getOne(id);
    return genre.getGames();
  }

}
