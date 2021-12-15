package com.redd4ford.steam.controller;

import com.redd4ford.steam.domain.Publisher;
import com.redd4ford.steam.dto.PublisherDto;
import com.redd4ford.steam.mapper.AbstractMapper;
import com.redd4ford.steam.mapper.PublisherMapper;
import com.redd4ford.steam.service.AbstractService;
import com.redd4ford.steam.service.PublisherService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/publishers")
@RestController
public class PublisherController extends AbstractController<Publisher, PublisherDto, Integer> {

  private final PublisherService publisherService;
  private final PublisherMapper publisherMapper;

  public PublisherController(PublisherService publisherService, PublisherMapper publisherMapper) {
    this.publisherService = publisherService;
    this.publisherMapper = publisherMapper;
  }

  @Override
  protected AbstractService<Publisher, Integer> getService() {
    return publisherService;
  }

  @Override
  protected AbstractMapper<Publisher, PublisherDto> getMapper() {
    return publisherMapper;
  }

}
