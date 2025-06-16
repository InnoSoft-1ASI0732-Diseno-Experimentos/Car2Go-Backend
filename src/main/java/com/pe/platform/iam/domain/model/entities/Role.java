package com.pe.platform.iam.domain.model.entities;

import com.pe.platform.iam.domain.model.valueobjects.Roles;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import java.util.List;
import lombok.Getter;
import org.springframework.data.annotation.Id;

/**
 * Role entity
 * <p>
 * This entity represents the role of a user in the system.
 * It is used to define the permissions of a user.
 * </p>
 */
@Entity
public class Role {
  @jakarta.persistence.Id
  @Getter
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Getter
  @Enumerated(EnumType.STRING)
  @Column(length = 20, unique = true, nullable = false)
  private Roles name;

  /**
   * Instantiates a new Role.
   */
  public Role() {
  }

  /**
   * Instantiates a new Role.
   *
   * @param name the name
   */
  public Role(Roles name) {
    this.name = name;
  }

  /**
   * Get the default role
   *
   * @return the default role
   */
  public static Role getDefaultRole() {
    return new Role(Roles.ROLE_SELLER);
  }

  /**
   * Get the role from its name
   *
   * @param name the name of the role
   * @return the role
   */
  public static Role toRoleFromName(String name) {
    return new Role(Roles.valueOf(name));
  }

  /**
   * Validate the role set
   * <p>
   * This method validates the role set and returns the default role if the set is empty.
   * </p>
   *
   * @param roles the role set
   * @return the role set
   */
  public static List<Role> validateRoleSet(List<Role> roles) {
    if (roles == null || roles.isEmpty()) {
      return List.of(getDefaultRole());
    }
    return roles;
  }

  /**
   * Get the name of the role as a string
   *
   * @return the name of the role as a string
   */
  public String getStringName() {
    return name.name();
  }

  /**
   * Gets id.
   *
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * Sets id.
   *
   * @param id the id
   */
  public void setId(Long id) {
    this.id = id;
  }
}