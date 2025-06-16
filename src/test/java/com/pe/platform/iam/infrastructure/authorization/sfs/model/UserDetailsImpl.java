package com.pe.platform.iam.infrastructure.authorization.sfs.model;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * The type User details.
 */
public class UserDetailsImpl implements UserDetails {
  private final Long id;
  private final String username;
  private final String email;
  private final String password;
  private final Collection<? extends GrantedAuthority> authorities;

  /**
   * Instantiates a new User details.
   *
   * @param id          the id
   * @param username    the username
   * @param email       the email
   * @param password    the password
   * @param authorities the authorities
   */
  public UserDetailsImpl(Long id, String username, String email, String password,
                         Collection<? extends GrantedAuthority> authorities) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.password = password;
    this.authorities = authorities;
  }

  /**
   * Instantiates a new User details.
   *
   * @param id          the id
   * @param username    the username
   * @param email       the email
   * @param authorities the authorities
   */
  public UserDetailsImpl(Long id, String username, String email,
                         Collection<? extends GrantedAuthority> authorities) {
    this(id, username, email, "", authorities);
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
   * Gets email.
   *
   * @return the email
   */
  public String getEmail() {
    return email;
  }

  /**
   * Is seller boolean.
   *
   * @return the boolean
   */
  public boolean isSeller() {
    return authorities.stream()
        .anyMatch(auth -> auth.getAuthority().equals("ROLE_SELLER"));
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
} 