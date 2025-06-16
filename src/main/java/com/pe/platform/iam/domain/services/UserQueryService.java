package com.pe.platform.iam.domain.services;

import com.pe.platform.iam.domain.model.aggregates.User;
import com.pe.platform.iam.domain.model.queries.GetAllUsersQuery;
import com.pe.platform.iam.domain.model.queries.GetUserByIdQuery;
import com.pe.platform.iam.domain.model.queries.GetUserByUsernameQuery;
import java.util.List;
import java.util.Optional;

/**
 * The interface User query service.
 */
public interface UserQueryService {
  /**
   * Handle list.
   *
   * @param query the query
   * @return the list
   */
  List<User> handle(GetAllUsersQuery query);

  /**
   * Handle optional.
   *
   * @param query the query
   * @return the optional
   */
  Optional<User> handle(GetUserByIdQuery query);

  /**
   * Handle optional.
   *
   * @param query the query
   * @return the optional
   */
  Optional<User> handle(GetUserByUsernameQuery query);
}