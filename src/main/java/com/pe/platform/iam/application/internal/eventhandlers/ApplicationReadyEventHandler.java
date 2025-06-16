package com.pe.platform.iam.application.internal.eventhandlers;

import com.pe.platform.iam.domain.model.commands.SeedRolesCommand;
import com.pe.platform.iam.domain.services.RoleCommandService;
import java.sql.Timestamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * The type Application ready event handler.
 */
/*
 * ApplicationReadyEventHandler class
 * This class is used to handle the ApplicationReadyEvent
 */
@Service
public class ApplicationReadyEventHandler {
  private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationReadyEventHandler.class);
  private final RoleCommandService roleCommandService;


  /**
   * Instantiates a new Application ready event handler.
   *
   * @param roleCommandService the role command service
   */
  public ApplicationReadyEventHandler(RoleCommandService roleCommandService) {
    this.roleCommandService = roleCommandService;
  }

  /**
   * On.
   *
   * @param event the event
   */
  /*
   * Handle the ApplicationReadyEvent
   * This method is used to seed the roles
   *
   * @param event the ApplicationReadyEvent the event to handle
   */
  @EventListener
  public void on(ApplicationReadyEvent event) {
    var applicationName = event.getApplicationContext().getId();
    LOGGER.info("Starting to verify if roles seeding is needed for {} at {}", applicationName,
        currentTimestamp());
    var seedRolesCommand = new SeedRolesCommand();
    roleCommandService.handle(seedRolesCommand);
    LOGGER.info("Roles seeding verification finished for {} at {}", applicationName,
        currentTimestamp());
  }

  private Timestamp currentTimestamp() {
    return new Timestamp(System.currentTimeMillis());
  }
}