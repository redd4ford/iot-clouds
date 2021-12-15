package com.redd4ford.steam.mapper;

public abstract class AbstractMapper<E, T> {

  public abstract T mapObjectToDto(E object);

}