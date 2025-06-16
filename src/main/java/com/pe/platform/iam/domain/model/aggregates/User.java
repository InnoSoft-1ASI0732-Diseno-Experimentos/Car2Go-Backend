package com.pe.platform.iam.domain.model.aggregates;

import com.pe.platform.iam.domain.model.entities.Role;
import com.pe.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Getter;

/**
 * User aggregate root
 * This class represents the aggregate root for the User entity.
 *
 * @see AuditableAbstractAggregateRoot
 */
@Getter
@Entity
public class User extends AuditableAbstractAggregateRoot<User> {
  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id"))
  private final Set<Role> roles;
  @NotBlank
  @Size(max = 50)
  @Column(unique = true)
  private String username;
  @NotBlank
  @Size(max = 120)
  private String password;

  /**
   * Instantiates a new User.
   */
  public User() {
    this.roles = new HashSet<>();
  }

  /**
   * Instantiates a new User.
   *
   * @param username the username
   * @param password the password
   */
  public User(String username, String password) {
    this.username = username;
    this.password = password;
    this.roles = new HashSet<>();
  }

  /**
   * Instantiates a new User.
   *
   * @param username the username
   * @param password the password
   * @param roles    the roles
   */
  public User(String username, String password, List<Role> roles) {
    this(username, password);
    addRoles(roles);
  }

  /**
   * Add a role to the user
   *
   * @param role the role to add
   * @return the user with the added role
   */
  public User addRole(Role role) {
    this.roles.add(role);
    return this;
  }

  /**
   * Add a list of roles to the user
   *
   * @param roles the list of roles to add
   * @return the user with the added roles
   */
  public User addRoles(List<Role> roles) {
    var validatedRoles = Role.validateRoleSet(roles);
    this.roles.addAll(validatedRoles);
    return this;
  }

  /**
   * Gets username.
   *
   * @return the username
   */
  public @NotBlank @Size(max = 50) String getUsername() {
    return username;
  }

  /**
   * Gets password.
   *
   * @return the password
   */
  public @NotBlank @Size(max = 120) String getPassword() {
    return password;
  }

  /**
   * Gets roles.
   *
   * @return the roles
   */
  public Set<Role> getRoles() {
    return roles;
  }
}
