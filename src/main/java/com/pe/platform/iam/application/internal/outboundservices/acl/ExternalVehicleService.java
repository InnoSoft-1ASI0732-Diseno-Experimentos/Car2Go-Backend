package com.pe.platform.iam.application.internal.outboundservices.acl;

import com.pe.platform.vehicle.interfaces.acl.VehicleContextFacade;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * The type External vehicle service.
 */
@Service
public class ExternalVehicleService {
  private final VehicleContextFacade vehicleContextFacade;

  /**
   * Instantiates a new External vehicle service.
   *
   * @param vehicleContextFacade the vehicle context facade
   */
  public ExternalVehicleService(VehicleContextFacade vehicleContextFacade) {
    this.vehicleContextFacade = vehicleContextFacade;
  }

  /**
   * Fetch vehicle id by user id list.
   *
   * @param userId the user id
   * @return the list
   */
  public List<Long> fetchVehicleIdByUserId(long userId) {
    return vehicleContextFacade.findByProfileId(userId);
  }
}
