package com.pe.platform.profiles.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

/**
 * The type Person name.
 */
@Embeddable
public record PersonName(String firstName, String lastName) {

  /**
   * Instantiates a new Person name.
   */
  public PersonName() {
    this(null, null);
  }

  /**
   * Instantiates a new Person name.
   *
   * @param firstName the first name
   * @param lastName  the last name
   */
  public PersonName {
    if (firstName == null || firstName.isBlank()) {
      throw new IllegalArgumentException("First name cannot be null or blank");
    }
    if (lastName == null || lastName.isBlank()) {
      throw new IllegalArgumentException("Last name cannot be null or blank");
    }
  }

  /**
   * Gets first name.
   *
   * @return the first name
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * Gets last name.
   *
   * @return the last name
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * Gets full name.
   *
   * @return the full name
   */
  public String getFullName() {
    return String.format("%s %s", firstName, lastName);
  }
}
