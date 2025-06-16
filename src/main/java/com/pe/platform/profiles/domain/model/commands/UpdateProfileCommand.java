package com.pe.platform.profiles.domain.model.commands;

/**
 * The type Update profile command.
 */
public record UpdateProfileCommand(String firstName,
                                   String lastName,
                                   String email,
                                   String image,
                                   String dni,
                                   String address,
                                   String phone) {

  /**
   * Instantiates a new Update profile command.
   *
   * @param firstName the first name
   * @param lastName  the last name
   * @param email     the email
   * @param image     the image
   * @param dni       the dni
   * @param address   the address
   * @param phone     the phone
   */
  public UpdateProfileCommand {
    validateNonEmpty(firstName, "First name");
    validateNonEmpty(lastName, "Last name");
    validateNonEmpty(email, "Email");
    validateNonEmpty(dni, "DNI");
    validateNonEmpty(address, "Address");
    validateNonEmpty(image, "Image");
    validateNonEmpty(phone, "Phone number");
  }

  private static void validateNonEmpty(String value, String fieldName) {
    if (value == null || value.isBlank()) {
      throw new IllegalArgumentException(fieldName + " cannot be null or blank");
    }
  }
}
