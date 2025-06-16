package com.pe.platform.vehicle.interfaces.rest.dto;

/**
 * The type Vehicle summary dto.
 */
public class VehicleSummaryDTO {
  private final int id;
  private final String name;
  private final String brand;
  private final String model;
  private final String year;
  private final double price;
  private final String transmission;
  private final String engine;
  private final String status;

  /**
   * Instantiates a new Vehicle summary dto.
   *
   * @param id           the id
   * @param name         the name
   * @param brand        the brand
   * @param model        the model
   * @param year         the year
   * @param price        the price
   * @param transmission the transmission
   * @param engine       the engine
   * @param status       the status
   */
  public VehicleSummaryDTO(int id, String name, String brand, String model, String year,
                           double price,
                           String transmission, String engine, String status) {
    this.id = id;
    this.name = name;
    this.brand = brand;
    this.model = model;
    this.year = year;
    this.price = price;
    this.transmission = transmission;
    this.engine = engine;
    this.status = status;
  }

  /**
   * Gets id.
   *
   * @return the id
   */
  public int getId() {
    return id;
  }

  /**
   * Gets name.
   *
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Gets brand.
   *
   * @return the brand
   */
  public String getBrand() {
    return brand;
  }

  /**
   * Gets model.
   *
   * @return the model
   */
  public String getModel() {
    return model;
  }

  /**
   * Gets year.
   *
   * @return the year
   */
  public String getYear() {
    return year;
  }

  /**
   * Gets price.
   *
   * @return the price
   */
  public double getPrice() {
    return price;
  }

  /**
   * Gets transmission.
   *
   * @return the transmission
   */
  public String getTransmission() {
    return transmission;
  }

  /**
   * Gets engine.
   *
   * @return the engine
   */
  public String getEngine() {
    return engine;
  }

  /**
   * Gets status.
   *
   * @return the status
   */
  public String getStatus() {
    return status;
  }
}
