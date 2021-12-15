package com.redd4ford.steam.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GameDto {

  private Integer id;
  private String publisherName;
  private String title;
  private Integer priceInUah;

}
