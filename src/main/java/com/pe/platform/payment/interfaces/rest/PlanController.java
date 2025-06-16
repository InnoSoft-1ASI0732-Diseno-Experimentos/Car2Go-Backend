package com.pe.platform.payment.interfaces.rest;

import com.pe.platform.payment.domain.model.queries.GetAllPlanQuery;
import com.pe.platform.payment.domain.model.queries.GetPlanByIdQuery;
import com.pe.platform.payment.domain.services.PlanCommandService;
import com.pe.platform.payment.domain.services.PlanQueryService;
import com.pe.platform.payment.interfaces.rest.resources.CreatePlanResource;
import com.pe.platform.payment.interfaces.rest.resources.PlanResource;
import com.pe.platform.payment.interfaces.rest.transform.CreatePlanCommandFromResourceAssembler;
import com.pe.platform.payment.interfaces.rest.transform.PlanResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Plan controller.
 */
@RestController
@RequestMapping(value = "/api/v1/plans", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Plans", description = "Plans Management Endpoints")
public class PlanController {

  private final PlanQueryService planQueryService;
  private final PlanCommandService planCommandService;

  /**
   * Instantiates a new Plan controller.
   *
   * @param planQueryService   the plan query service
   * @param planCommandService the plan command service
   */
  public PlanController(PlanQueryService planQueryService, PlanCommandService planCommandService) {
    this.planQueryService = planQueryService;
    this.planCommandService = planCommandService;
  }

  /**
   * Create plan response entity.
   *
   * @param resource the resource
   * @return the response entity
   */
  @PostMapping
  public ResponseEntity<PlanResource> createPlan(@RequestBody CreatePlanResource resource) {
    var createPlanCommand = CreatePlanCommandFromResourceAssembler.toCommandFromResource(resource);
    var plan = planCommandService.handle(createPlanCommand);
    if (plan.isEmpty()) {
      return ResponseEntity.badRequest().build();
    }
    var planResource = PlanResourceFromEntityAssembler.toResourceFromEntity(plan.get());
    return new ResponseEntity<>(planResource, HttpStatus.CREATED);
  }

  /**
   * Gets plan by id.
   *
   * @param planId the plan id
   * @return the plan by id
   */
  @GetMapping("/{planId}")
  public ResponseEntity<PlanResource> getPlanById(@PathVariable Long planId) {
    var getPlanByIdQuery = new GetPlanByIdQuery(planId);
    var plan = planQueryService.handle(getPlanByIdQuery);
    if (plan.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    var planResource = PlanResourceFromEntityAssembler.toResourceFromEntity(plan.get());
    return ResponseEntity.ok(planResource);
  }

  /**
   * Gets all plans.
   *
   * @return the all plans
   */
  @GetMapping("/all")
  public ResponseEntity<List<PlanResource>> getAllPlans() {
    var plans = planQueryService.handle(new GetAllPlanQuery());
    if (plans.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    var planResources =
        plans.stream().map(PlanResourceFromEntityAssembler::toResourceFromEntity).toList();
    return ResponseEntity.ok(planResources);
  }

}
