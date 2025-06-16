package com.pe.platform.profiles.domain.model.commands;

/**
 * The type Create mechanic command.
 */
public record CreateMechanicCommand(String firstName,
                                    String lastName,
                                    String phoneNumber,
                                    String street,
                                    String address,
                                    String password) {
  /**
   * Instantiates a new Create mechanic command.
   *
   * @param firstName   the first name
   * @param lastName    the last name
   * @param phoneNumber the phone number
   * @param street      the street
   * @param address     the address
   * @param password    the password
   */
  public CreateMechanicCommand {
    validateNonEmpty(firstName, "First name");
    validateNonEmpty(lastName, "Last name");
    validateNonEmpty(phoneNumber, "Phone number");
    validateNonEmpty(street, "Street");
    validateNonEmpty(address, "Address");
    validateNonEmpty(password, "Password");
  }

  private static void validateNonEmpty(String value, String fieldName) {
    if (value == null || value.isBlank()) {
      throw new IllegalArgumentException(fieldName + " cannot be null or blank");
    }
  }
}
