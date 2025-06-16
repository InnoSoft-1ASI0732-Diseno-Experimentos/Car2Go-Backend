package com.pe.platform.vehicle.application.internal.services.queryservices;

import com.pe.platform.vehicle.domain.model.aggregates.Vehicle;
import com.pe.platform.vehicle.domain.model.queries.GetAllVehicleByLocationQuery;
import com.pe.platform.vehicle.domain.model.queries.GetAllVehicleByProfileId;
import com.pe.platform.vehicle.domain.model.queries.GetAllVehicleQuery;
import com.pe.platform.vehicle.domain.model.queries.GetVehicleByIdQuery;
import com.pe.platform.vehicle.domain.model.queries.GetVehicleIdByProfileId;
import com.pe.platform.vehicle.domain.model.queries.PutVehicleById;
import com.pe.platform.vehicle.domain.services.VehicleQueryService;
import com.pe.platform.vehicle.infrastructure.persistence.jpa.VehicleRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * The type Vehicle query service.
 */
@Service
public class VehicleQueryServiceImpl implements VehicleQueryService {

  private final VehicleRepository vehicleRepository;

  /**
   * Instantiates a new Vehicle query service.
   *
   * @param vehicleRepository the vehicle repository
   */
  public VehicleQueryServiceImpl(VehicleRepository vehicleRepository) {
    this.vehicleRepository = vehicleRepository;
  }

  @Override
  public Optional<Vehicle> handle(GetVehicleByIdQuery query) {
    return vehicleRepository.findById(query.id());
  }

  @Override
  public List<Vehicle> handle(GetAllVehicleByLocationQuery query) {
    return vehicleRepository.findAllByLocation(query.location());
  }

  @Override
  public List<Vehicle> handle(GetAllVehicleQuery query) {
    return vehicleRepository.findAll();
  }

  @Override
  public Optional<Vehicle> handle(PutVehicleById query) {
    return vehicleRepository.updateById(query.id());
  }

  @Override
  public List<Vehicle> handle(GetAllVehicleByProfileId query) {
    return vehicleRepository.findAllVehiclesByProfileId(query.profileId());
  }

  @Override
  public List<Vehicle> handle(GetVehicleIdByProfileId query) {
    return vehicleRepository.findByProfileId(query.profileId());
  }
}
