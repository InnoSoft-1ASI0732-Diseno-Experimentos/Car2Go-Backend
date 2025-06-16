package com.pe.platform.vehicle.interfaces.rest;

import static org.springframework.http.HttpStatus.CREATED;

import com.pe.platform.iam.infrastructure.authorization.sfs.model.UserDetailsImpl;
import com.pe.platform.vehicle.domain.model.aggregates.Vehicle;
import com.pe.platform.vehicle.domain.model.commands.UpdateVehicleCommand;
import com.pe.platform.vehicle.domain.model.queries.GetAllVehicleByLocationQuery;
import com.pe.platform.vehicle.domain.model.queries.GetAllVehicleByProfileId;
import com.pe.platform.vehicle.domain.model.queries.GetAllVehicleQuery;
import com.pe.platform.vehicle.domain.model.queries.GetVehicleByIdQuery;
import com.pe.platform.vehicle.domain.services.VehicleCommandService;
import com.pe.platform.vehicle.domain.services.VehicleQueryService;
import com.pe.platform.vehicle.interfaces.rest.resources.CreateVehicleResource;
import com.pe.platform.vehicle.interfaces.rest.resources.UpdateVehicleResource;
import com.pe.platform.vehicle.interfaces.rest.resources.VehicleResource;
import com.pe.platform.vehicle.interfaces.rest.transform.CreateVehicleCommandFromResourceAssembler;
import com.pe.platform.vehicle.interfaces.rest.transform.UpdateVehicleCommandFromResourceAssembler;
import com.pe.platform.vehicle.interfaces.rest.transform.VehicleResourceFromEntityAssembler;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Vehicle controller.
 */
@RestController
@RequestMapping("/api/v1/vehicle")
public class VehicleController {

  private final VehicleCommandService vehicleCommandService;
  private final VehicleQueryService vehicleQueryService;

  /**
   * Instantiates a new Vehicle controller.
   *
   * @param vehicleCommandService the vehicle command service
   * @param vehicleQueryService   the vehicle query service
   */
  public VehicleController(VehicleCommandService vehicleCommandService,
                           VehicleQueryService vehicleQueryService) {
    this.vehicleCommandService = vehicleCommandService;
    this.vehicleQueryService = vehicleQueryService;
  }

  /**
   * Create vehicle response entity.
   *
   * @param resource the resource
   * @return the response entity
   */
  @PreAuthorize("hasAuthority('ROLE_SELLER')")
  @PostMapping
  public ResponseEntity<VehicleResource> createVehicle(
      @RequestBody CreateVehicleResource resource) {
    Optional<Vehicle> vehicle = vehicleCommandService.handle(
        CreateVehicleCommandFromResourceAssembler.toCommandFromResource(resource));
    return vehicle.map(
            resp -> new ResponseEntity<>(VehicleResourceFromEntityAssembler.toResourceFromEntity(resp),
                CREATED))
        .orElseGet(() -> ResponseEntity.badRequest().build());
  }

  /**
   * Gets vehicle by id.
   *
   * @param id the id
   * @return the vehicle by id
   */
  @GetMapping("/{id}")
  public ResponseEntity<VehicleResource> getVehicleById(@PathVariable int id) {
    Optional<Vehicle> vehicle = vehicleQueryService.handle(new GetVehicleByIdQuery(id));
    return vehicle.map(
            resp -> ResponseEntity.ok(VehicleResourceFromEntityAssembler.toResourceFromEntity(resp)))
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  /**
   * Gets all by location.
   *
   * @param location the location
   * @return the all by location
   */
  @GetMapping("/location/{location}")
  public ResponseEntity<List<VehicleResource>> getAllByLocation(@PathVariable String location) {
    var getAllVehicleByLocation = new GetAllVehicleByLocationQuery(location);
    var vehicles = vehicleQueryService.handle(getAllVehicleByLocation);
    if (vehicles.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    var vehicleResources = vehicles.stream()
        .map(VehicleResourceFromEntityAssembler::toResourceFromEntity)
        .toList();
    return ResponseEntity.ok(vehicleResources);
  }

  /**
   * Gets all vehicles.
   *
   * @return the all vehicles
   */
  @GetMapping("/all")
  public ResponseEntity<List<VehicleResource>> getAllVehicles() {
    var vehicles = vehicleQueryService.handle(new GetAllVehicleQuery());
    if (vehicles.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    var vehicleResources =
        vehicles.stream().map(VehicleResourceFromEntityAssembler::toResourceFromEntity).toList();
    return ResponseEntity.ok(vehicleResources);
  }

  /**
   * Update vehicle response entity.
   *
   * @param vehicleId the vehicle id
   * @param resource  the resource
   * @return the response entity
   */
  @PreAuthorize("hasAuthority('ROLE_SELLER')")
  @PutMapping("/{vehicleId}")
  public ResponseEntity<VehicleResource> updateVehicle(@PathVariable int vehicleId, @RequestBody
  UpdateVehicleResource resource) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    long userId = userDetails.getId();

    UpdateVehicleCommand command =
        UpdateVehicleCommandFromResourceAssembler.toCommandFromResource(resource);
    Optional<Vehicle> updatedVehicleOptional = vehicleCommandService.handle(command, vehicleId);

    return updatedVehicleOptional
        .filter(updatedVehicle -> updatedVehicle.getProfileId() == userId)
        .map(updatedVehicle -> ResponseEntity.ok(
            VehicleResourceFromEntityAssembler.toResourceFromEntity(updatedVehicle)))
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  /**
   * Gets all vehicles by profile id.
   *
   * @param profileId the profile id
   * @return the all vehicles by profile id
   */
  @GetMapping("/all/vehicles/profile/{profileId}")
  public ResponseEntity<List<VehicleResource>> getAllVehiclesByProfileId(
      @PathVariable long profileId) {
    var vehicles = vehicleQueryService.handle(new GetAllVehicleByProfileId(profileId));
    if (vehicles.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    var vehicleResources =
        vehicles.stream().map(VehicleResourceFromEntityAssembler::toResourceFromEntity).toList();
    return ResponseEntity.ok(vehicleResources);
  }

  /**
   * Delete vehicle response entity.
   *
   * @param vehicleId the vehicle id
   * @return the response entity
   */
  @PreAuthorize("hasAuthority('ROLE_SELLER')")
  @DeleteMapping("/{vehicleId}")
  public ResponseEntity<Void> deleteVehicle(@PathVariable int vehicleId) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    long userId = userDetails.getId();

    try {
      vehicleCommandService.deleteVehicle(vehicleId, userId);
      return ResponseEntity.noContent().build();
    } catch (IllegalStateException e) {
      return ResponseEntity.status(403).build();
    }
  }


}
