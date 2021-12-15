package com.redd4ford.steam.service;

import com.redd4ford.steam.domain.Game;
import com.redd4ford.steam.domain.Genre;
import com.redd4ford.steam.repository.GameRepository;
import com.redd4ford.steam.repository.GenreRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService extends AbstractService<Genre, Integer> {

  private final GenreRepository genreRepository;
  private final GameRepository gameRepository;

  public GenreService(GenreRepository genreRepository, GameRepository gameRepository) {
    this.genreRepository = genreRepository;
    this.gameRepository = gameRepository;
  }

  @Override
  protected JpaRepository<Genre, Integer> getRepository() {
    return genreRepository;
  }

  public List<Genre> getAllByGameId(Integer id) {
    Game game = gameRepository.getOne(id);
    return game.getGenres();
  }

}
