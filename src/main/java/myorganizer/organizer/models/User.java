package myorganizer.organizer.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  private String fullName;

  private String email;

  private String password;

  private boolean terms;

  private boolean isAdmin;

  @Override
  public String toString() {
    return "User{" +
      "id=" + id +
      ", fullName='" + fullName + '\'' +
      ", email='" + email + '\'' +
      ", password='" + password + '\'' +
      ", terms=" + terms +
      ", isAdmin=" + isAdmin +
      '}';
  }

  public boolean isAdmin() {
    return isAdmin;
  }

  public void setAdmin(boolean admin) {
    isAdmin = admin;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public void setName(String name) {
    this.fullName = name;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setTerms(boolean terms) {
    this.terms = terms;
  }

  public Integer getId() {
    return id;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }

  public boolean isTerms() {
    return terms;
  }
}
