package com.pe.platform.profiles.domain.model.aggregates;

import com.pe.platform.profiles.domain.model.commands.CreateProfileCommand;
import com.pe.platform.profiles.domain.model.commands.UpdateProfileCommand;
import com.pe.platform.profiles.domain.model.valueobjects.PaymentMethod;
import com.pe.platform.profiles.domain.model.valueobjects.PersonName;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

/**
 * The type Profile.
 */
@Getter
@Entity
public class Profile {

  private static final int MAX_PAYMENT_METHODS = 3;
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "profile_id")
  private final List<PaymentMethod> paymentMethods = new ArrayList<>();
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Embedded
  private PersonName name;
  @Column(nullable = false)
  private String email;
  @Column(nullable = false, columnDefinition = "LONGTEXT")
  private String image;
  @Column(nullable = false)
  private String dni;
  @Column(nullable = false)
  private String address;
  @Column(nullable = false)
  private String phone;
  @Column(nullable = false)
  private Long profileId;

  /**
   * Instantiates a new Profile.
   */
  protected Profile() {
  }

  /**
   * Instantiates a new Profile.
   *
   * @param command   the command
   * @param profileId the profile id
   */
  public Profile(CreateProfileCommand command, Long profileId) {
    this.name = new PersonName(command.firstName(), command.lastName());
    this.email = command.email();
    this.image = command.image();
    this.dni = command.dni();
    this.address = command.address();
    this.phone = command.phone();
    this.profileId = profileId;
  }

  /**
   * Instantiates a new Profile.
   *
   * @param command the command
   */
  public Profile(UpdateProfileCommand command) {
    this.name = new PersonName(command.firstName(), command.lastName());
    this.email = command.email();
    this.image = command.image();
    this.dni = command.dni();
    this.address = command.address();
    this.phone = command.phone();
  }

  /**
   * Gets profile id.
   *
   * @return the profile id
   */
  public long getProfileId() {
    return profileId;
  }

  /**
   * Sets profile id.
   *
   * @param profileId the profile id
   */
  public void setProfileId(long profileId) {
    this.profileId = profileId;
  }

  /**
   * Gets first name.
   *
   * @return the first name
   */
  public String getFirstName() {
    return name.getFirstName();
  }

  /**
   * Gets last name.
   *
   * @return the last name
   */
  public String getLastName() {
    return name.getLastName();
  }

  /**
   * Update name.
   *
   * @param firstName the first name
   * @param lastName  the last name
   */
  public void updateName(String firstName, String lastName) {
    this.name = new PersonName(firstName, lastName);
  }


  /**
   * Add payment method.
   *
   * @param paymentMethod the payment method
   */
  public void addPaymentMethod(PaymentMethod paymentMethod) {
    if (paymentMethods.size() >= MAX_PAYMENT_METHODS) {
      throw new IllegalArgumentException(
          "Cannot add more than " + MAX_PAYMENT_METHODS + " payment methods");
    }
    paymentMethods.add(paymentMethod);
  }

  /**
   * Remove payment method by id boolean.
   *
   * @param paymentMethodId the payment method id
   * @return the boolean
   */
  public boolean removePaymentMethodById(Long paymentMethodId) {
    return paymentMethods.removeIf(paymentMethod -> paymentMethod.getId().equals(paymentMethodId));
  }

  /**
   * Update payment method boolean.
   *
   * @param paymentMethodId      the payment method id
   * @param updatedPaymentMethod the updated payment method
   * @return the boolean
   */
  public boolean updatePaymentMethod(Long paymentMethodId, PaymentMethod updatedPaymentMethod) {
    for (PaymentMethod paymentMethod : paymentMethods) {
      if (paymentMethod.getId().equals(paymentMethodId)) {
        paymentMethod.setType(updatedPaymentMethod.getType());
        paymentMethod.setDetails(updatedPaymentMethod.getDetails());
        return true;
      }
    }
    return false;
  }

  /**
   * Gets id.
   *
   * @return the id
   */
  public int getId() {
    return id;
  }
}
