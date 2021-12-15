package com.redd4ford.steam.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PublisherDto {

  private Integer id;
  private String name;
  private String countryName;
  private Integer gamesCounter;

}
