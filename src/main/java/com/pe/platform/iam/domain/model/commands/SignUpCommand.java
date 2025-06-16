package com.pe.platform.iam.domain.model.commands;

import com.pe.platform.iam.domain.model.entities.Role;
import java.util.List;

/**
 * The type Sign up command.
 */
public record SignUpCommand(String username, String password, List<Role> roles) {
}