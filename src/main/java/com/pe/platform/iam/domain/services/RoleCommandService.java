package com.pe.platform.iam.domain.services;

import com.pe.platform.iam.domain.model.commands.SeedRolesCommand;

/**
 * The interface Role command service.
 */
public interface RoleCommandService {
  /**
   * Handle.
   *
   * @param command the command
   */
  void handle(SeedRolesCommand command);
}