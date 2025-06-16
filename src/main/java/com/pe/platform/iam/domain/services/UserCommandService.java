package com.pe.platform.iam.domain.services;

import com.pe.platform.iam.domain.model.aggregates.User;
import com.pe.platform.iam.domain.model.commands.SignInCommand;
import com.pe.platform.iam.domain.model.commands.SignUpCommand;
import java.util.Optional;
import org.apache.commons.lang3.tuple.ImmutablePair;

/**
 * The interface User command service.
 */
public interface UserCommandService {
  /**
   * Handle optional.
   *
   * @param command the command
   * @return the optional
   */
  Optional<User> handle(SignUpCommand command);

  /**
   * Handle optional.
   *
   * @param command the command
   * @return the optional
   */
  Optional<ImmutablePair<User, String>> handle(SignInCommand command);
}