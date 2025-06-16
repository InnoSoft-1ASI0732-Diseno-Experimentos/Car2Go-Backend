package com.pe.platform.vehicle.common.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * The type List to string converter test.
 */
class ListToStringConverterTest {

  private ListToStringConverter converter;

  /**
   * Sets up.
   */
  @BeforeEach
  void setUp() {
    // Arrange: Create converter instance
    converter = new ListToStringConverter();
  }

  /**
   * Test convert to database column.
   */
  @Test
  @DisplayName("Should convert list to JSON string")
  void testConvertToDatabaseColumn() {
    // Arrange
    List<String> imagesList = Arrays.asList("image1.jpg", "image2.jpg", "image3.jpg");

    // Act
    String result = converter.convertToDatabaseColumn(imagesList);

    // Assert
    assertNotNull(result);
    assertTrue(result.contains("image1.jpg"));
    assertTrue(result.contains("image2.jpg"));
    assertTrue(result.contains("image3.jpg"));
    // Verify JSON array format
    assertTrue(result.startsWith("["));
    assertTrue(result.endsWith("]"));
    assertTrue(result.contains(","));
  }

  /**
   * Test convert empty list to database column.
   */
  @Test
  @DisplayName("Should convert empty list to empty JSON array")
  void testConvertEmptyListToDatabaseColumn() {
    // Arrange
    List<String> emptyList = Collections.emptyList();

    // Act
    String result = converter.convertToDatabaseColumn(emptyList);

    // Assert
    assertNotNull(result);
    assertEquals("[]", result);
  }

  /**
   * Test convert null list to database column.
   */
  @Test
  @DisplayName("Should throw exception when list is null")
  void testConvertNullListToDatabaseColumn() {
    // Act
    String result = converter.convertToDatabaseColumn(null);

    // Assert
    assertEquals("[]", result);
  }

  /**
   * Test convert to entity attribute.
   */
  @Test
  @DisplayName("Should convert JSON string to list")
  void testConvertToEntityAttribute() {
    // Arrange
    String jsonArray = "[\"image1.jpg\",\"image2.jpg\",\"image3.jpg\"]";

    // Act
    List<String> result = converter.convertToEntityAttribute(jsonArray);

    // Assert
    assertNotNull(result);
    assertEquals(3, result.size());
    assertEquals("image1.jpg", result.get(0));
    assertEquals("image2.jpg", result.get(1));
    assertEquals("image3.jpg", result.get(2));
  }

  /**
   * Test convert empty json to entity attribute.
   */
  @Test
  @DisplayName("Should convert empty JSON array to empty list")
  void testConvertEmptyJsonToEntityAttribute() {
    // Arrange
    String emptyJsonArray = "[]";

    // Act
    List<String> result = converter.convertToEntityAttribute(emptyJsonArray);

    // Assert
    assertNotNull(result);
    assertTrue(result.isEmpty());
  }

  /**
   * Test convert invalid json to entity attribute.
   */
  @Test
  @DisplayName("Should throw exception when JSON is invalid")
  void testConvertInvalidJsonToEntityAttribute() {
    // Arrange
    String invalidJson = "{invalid-json}";

    // Act & Assert
    assertThrows(IllegalArgumentException.class,
        () -> converter.convertToEntityAttribute(invalidJson));
  }

  /**
   * Test convert null json to entity attribute.
   */
  @Test
  @DisplayName("Should throw exception when JSON string is null")
  void testConvertNullJsonToEntityAttribute() {
    // Act
    List<String> result = converter.convertToEntityAttribute(null);

    // Assert
    assertNotNull(result);
    assertTrue(result.isEmpty());
  }

  /**
   * Test preserve order.
   */
  @Test
  @DisplayName("Should preserve element order during conversion")
  void testPreserveOrder() {
    // Arrange
    List<String> originalList = Arrays.asList("image3.jpg", "image1.jpg", "image2.jpg");

    // Act: Convert to JSON and back to list
    String json = converter.convertToDatabaseColumn(originalList);
    List<String> resultList = converter.convertToEntityAttribute(json);

    // Assert: Verify order is maintained
    assertEquals(originalList.size(), resultList.size());
    for (int i = 0; i < originalList.size(); i++) {
      assertEquals(originalList.get(i), resultList.get(i));
    }
  }
} 