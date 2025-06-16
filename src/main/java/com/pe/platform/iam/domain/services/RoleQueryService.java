package com.pe.platform.iam.domain.services;

import com.pe.platform.iam.domain.model.entities.Role;
import com.pe.platform.iam.domain.model.queries.GetAllRolesQuery;
import com.pe.platform.iam.domain.model.queries.GetRoleByNameQuery;
import java.util.List;
import java.util.Optional;

/**
 * The interface Role query service.
 */
public interface RoleQueryService {
  /**
   * Handle list.
   *
   * @param query the query
   * @return the list
   */
  List<Role> handle(GetAllRolesQuery query);

  /**
   * Handle optional.
   *
   * @param query the query
   * @return the optional
   */
  Optional<Role> handle(GetRoleByNameQuery query);
}