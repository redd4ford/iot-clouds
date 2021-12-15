package com.redd4ford.steam.mapper;

import com.redd4ford.steam.domain.Game;
import com.redd4ford.steam.dto.GameDto;
import org.springframework.stereotype.Component;

@Component
public class GameMapper extends AbstractMapper<Game, GameDto> {

  @Override
  public GameDto mapObjectToDto(Game game) {
    if (game == null) {
      return null;
    }

    GameDto.GameDtoBuilder gameDto = GameDto.builder();

    gameDto.id(game.getId());
    gameDto.publisherName(game.getPublisher().getName());
    gameDto.title(game.getTitle());
    gameDto.priceInUah(game.getPriceInUah());

    return gameDto.build();
  }

}
