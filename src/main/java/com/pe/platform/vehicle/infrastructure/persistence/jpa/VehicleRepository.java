package com.pe.platform.vehicle.infrastructure.persistence.jpa;

import com.pe.platform.vehicle.domain.model.aggregates.Vehicle;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * The interface Vehicle repository.
 */
@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

  /**
   * Find all by location list.
   *
   * @param location the location
   * @return the list
   */
  List<Vehicle> findAllByLocation(String location);

  Optional<Vehicle> findById(@NotNull Integer id);

  /**
   * Find by profile id list.
   *
   * @param profileId the profile id
   * @return the list
   */
  List<Vehicle> findByProfileId(Long profileId);

  /**
   * Find by name list.
   *
   * @param name the name
   * @return the list
   */
  List<Vehicle> findByName(String name);

  /**
   * Find all vehicles by profile id list.
   *
   * @param profileId the profile id
   * @return the list
   */
  List<Vehicle> findAllVehiclesByProfileId(Long profileId);

  /**
   * Update by id optional.
   *
   * @param id the id
   * @return the optional
   */
  @Modifying
  @Transactional
  @Query("UPDATE Vehicle v SET v.name = :name, v.location = :location, v.brand = :brand, v.model = :model, v.color = :color, v.year = :year, v.transmission = :transmission, v.engine = :engine, v.mileage = :mileage, v.doors = :doors, v.plate = :plate, v.description = :description, v.price = :price, v.fuel = :fuel, v.speed = :speed WHERE v.id = :id")
  Optional<Vehicle> updateById(Integer id);
}
