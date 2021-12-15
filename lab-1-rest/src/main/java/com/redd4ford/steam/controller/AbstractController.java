package com.redd4ford.steam.controller;

import com.redd4ford.steam.mapper.AbstractMapper;
import com.redd4ford.steam.service.AbstractService;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public abstract class AbstractController<E, DTO, ID> {

  protected abstract AbstractService<E, ID> getService();

  protected abstract AbstractMapper<E, DTO> getMapper();

  @RequestMapping(method = RequestMethod.GET)
  public @ResponseBody ResponseEntity<List<DTO>> getAll() {
    List<E> objects = getService().getAll();
    List<DTO> objectsDto = new ArrayList<>();
    for (E object : objects) {
      objectsDto.add(getMapper().mapObjectToDto(object));
    }
    return new ResponseEntity<>(objectsDto, HttpStatus.OK);
  }

  @RequestMapping(method = RequestMethod.GET,
      value = "/{id}")
  public @ResponseBody ResponseEntity<DTO> getById(@PathVariable ID id) {
    E object = getService().getById(id);
    if (object != null) {
      return new ResponseEntity<>(getMapper().mapObjectToDto(object), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @RequestMapping(method = RequestMethod.POST,
      consumes = {MediaType.APPLICATION_JSON_VALUE})
  public @ResponseBody ResponseEntity<Void> create(@RequestBody E newObject) {
    getService().create(newObject);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @RequestMapping(method = RequestMethod.PUT,
      value = "/{id}",
      consumes = {MediaType.APPLICATION_JSON_VALUE})
  public @ResponseBody ResponseEntity<E> update(@PathVariable ID id, @RequestBody E object) {
    if (getService().getById(id) != null) {
      getService().update(id, object);
      return new ResponseEntity<>(getService().update(id, object), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  @RequestMapping(method = RequestMethod.DELETE,
      value = "/{id}")
  public ResponseEntity<Void> deleteById(@PathVariable ID id) {
    if (getService().getById(id) != null) {
      getService().deleteById(id);
      return new ResponseEntity<>(HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

}
