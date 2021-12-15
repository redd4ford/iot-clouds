package com.redd4ford.steam.domain;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "account_protected_data")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AccountProtectedData implements Serializable {

  @Id
  @Column(name = "account_id")
  private Integer accountId;

  @Column(name = "password")
  private String password;

  @Column(name = "email")
  private String email;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AccountProtectedData that = (AccountProtectedData) o;
    return Objects.equals(accountId, that.accountId)
        && Objects.equals(password, that.password)
        && Objects.equals(email, that.email);
  }

  @Override
  public int hashCode() {
    return Objects.hash(accountId, password, email);
  }

  @Override
  public String toString() {
    return "AccountProtectedData{"
        + "account_id=" + accountId + ", "
        + "password='" + password + ", "
        + "email='" + email + "'"
        + "};";
  }

}
