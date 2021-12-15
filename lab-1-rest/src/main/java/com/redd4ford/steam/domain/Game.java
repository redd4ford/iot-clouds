package com.redd4ford.steam.domain;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "game")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Game {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private Integer id;

  @ManyToOne
  @JoinColumn(name = "publisher_id", referencedColumnName = "id", nullable = false)
  private Publisher publisher;

  @Column(name = "title")
  private String title;

  @Column(name = "rating")
  private Integer rating;

  @Column(name = "release_date")
  private String releaseDate;

  @Column(name = "price_in_uah")
  private Integer priceInUah;

  @ManyToMany
  @JoinTable(name = "game_has_genre",
      joinColumns = @JoinColumn(
          name = "game_id",
          referencedColumnName = "id"
      ),
      inverseJoinColumns = @JoinColumn(
          name = "genre_id",
          referencedColumnName = "id"
      ))
  private List<Genre> genres;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Game game = (Game) o;
    return id.equals(game.id)
        && publisher.equals(game.publisher)
        && title.equals(game.title)
        && Objects.equals(rating, game.rating)
        && Objects.equals(releaseDate, game.releaseDate)
        && priceInUah.equals(game.priceInUah);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, publisher, title, rating, releaseDate, priceInUah);
  }

  @Override
  public String toString() {
    return "Game{"
        + "id=" + id + ", "
        + "publisherId=" + publisher.getId() + ", "
        + "title='" + title + "', "
        + "rating=" + rating + ", "
        + "releaseDate='" + releaseDate + "', "
        + "priceInUah=" + priceInUah
        + "};";
  }

}
