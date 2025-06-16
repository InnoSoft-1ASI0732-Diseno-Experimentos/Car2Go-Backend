package com.pe.platform.interaction.domain.services;

import com.pe.platform.interaction.domain.model.aggregates.Review;
import java.util.List;
import java.util.Optional;

/**
 * The interface Review command service.
 */
public interface ReviewCommandService {

  /**
   * Gets all reviews.
   *
   * @return the all reviews
   */
  List<Review> getAllReviews();

  /**
   * Gets review by vehicle id.
   *
   * @param vehicleId the vehicle id
   * @return the review by vehicle id
   */
  Optional<Review> getReviewByVehicleId(int vehicleId);

  /**
   * Create review review.
   *
   * @param vehicleId  the vehicle id
   * @param reviewedBy the reviewed by
   * @param notes      the notes
   * @param isApproved the is approved
   * @return the review
   */
  Review createReview(int vehicleId, String reviewedBy, String notes, boolean isApproved);

}
