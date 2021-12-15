package com.redd4ford.steam.service;

import com.redd4ford.steam.domain.Publisher;
import com.redd4ford.steam.repository.PublisherRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class PublisherService extends AbstractService<Publisher, Integer> {

  private final PublisherRepository publisherRepository;

  public PublisherService(PublisherRepository publisherRepository) {
    this.publisherRepository = publisherRepository;
  }

  @Override
  protected JpaRepository<Publisher, Integer> getRepository() {
    return publisherRepository;
  }

}
