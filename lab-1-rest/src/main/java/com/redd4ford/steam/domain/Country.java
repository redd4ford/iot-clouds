package com.redd4ford.steam.domain;

import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "country")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Country {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private Integer id;

  @Column(name = "name")
  private String name;

  @OneToMany(mappedBy = "country", fetch = FetchType.EAGER)
  @JsonIgnore
  private Set<Account> accounts;

  @OneToMany(mappedBy = "country", fetch = FetchType.EAGER)
  @JsonIgnore
  private Set<Publisher> publishers;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Country country = (Country) o;
    return id.equals(country.id)
        && name.equals(country.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name);
  }

  @Override
  public String toString() {
    return "Country{"
        + "id=" + id + ", "
        + "name='" + name + "'"
        + "};";
  }

}
