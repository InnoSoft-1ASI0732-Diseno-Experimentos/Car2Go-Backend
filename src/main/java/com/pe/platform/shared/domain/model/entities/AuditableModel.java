package com.pe.platform.shared.domain.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import java.util.Date;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * The type Auditable model.
 */
/*
 * The type Auditable model.
 */
@Getter
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public class AuditableModel {
  // marca farm
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  // marca farm

  @CreatedDate
  @Column(nullable = false, updatable = false)
  private Date createdAt;

  @LastModifiedDate
  @Column(nullable = false)
  private Date updatedAt;
}