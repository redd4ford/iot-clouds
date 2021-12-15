package com.redd4ford.steam.service;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class AbstractService<T, ID> {
  protected abstract JpaRepository<T, ID> getRepository();

  public List<T> getAll() {
    return getRepository().findAll();
  }

  public T getById(ID id) {
    return getRepository().getOne(id);
  }

  public T create(T newObject) {
    return getRepository().save(newObject);
  }

  public T update(ID id, T object) {
    if (getRepository().findById(id).isPresent()) {
      return getRepository().save(object);
    } else {
      return null;
    }
  }

  public void deleteById(ID id) {
    if (getRepository().findById(id).isPresent()) {
      getRepository().deleteById(id);
    }
  }

}