package com.pe.platform.iam.interfaces.rest.resources;

import java.util.List;

/**
 * The type Sign up resource.
 */
public record SignUpResource(String username, String password, List<String> roles) {
}