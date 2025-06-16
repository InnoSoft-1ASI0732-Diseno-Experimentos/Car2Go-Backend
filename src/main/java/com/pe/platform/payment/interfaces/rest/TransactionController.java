package com.pe.platform.payment.interfaces.rest;

import static org.springframework.http.HttpStatus.CREATED;

import com.pe.platform.iam.infrastructure.authorization.sfs.model.UserDetailsImpl;
import com.pe.platform.payment.domain.model.aggregates.Transaction;
import com.pe.platform.payment.domain.model.queries.GetAllTransactionsQuery;
import com.pe.platform.payment.domain.model.queries.GetTransactionByIdQuery;
import com.pe.platform.payment.domain.services.TransactionCommandService;
import com.pe.platform.payment.domain.services.TransactionQueryService;
import com.pe.platform.payment.interfaces.rest.resources.CreateTransactionResource;
import com.pe.platform.payment.interfaces.rest.resources.TransactionResource;
import com.pe.platform.payment.interfaces.rest.resources.UpdateTransactionResource;
import com.pe.platform.payment.interfaces.rest.transform.CreateTransactionCommandFromResourceAssembler;
import com.pe.platform.payment.interfaces.rest.transform.TransactionResourceFromEntityAssembler;
import com.pe.platform.payment.interfaces.rest.transform.UpdateTransactionCommandFromResourceAssembler;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Transaction controller.
 */
@RestController
@RequestMapping(value = "/api/v1/transaction", produces = MediaType.APPLICATION_JSON_VALUE)
public class TransactionController {
  private final TransactionQueryService transactionQueryService;
  private final TransactionCommandService transactionCommandService;

  /**
   * Instantiates a new Transaction controller.
   *
   * @param transactionQueryService   the transaction query service
   * @param transactionCommandService the transaction command service
   */
  public TransactionController(TransactionQueryService transactionQueryService,
                               TransactionCommandService transactionCommandService) {
    this.transactionQueryService = transactionQueryService;
    this.transactionCommandService = transactionCommandService;
  }

  /**
   * Create transaction response entity.
   *
   * @param resource the resource
   * @return the response entity
   */
  @PostMapping
  public ResponseEntity<TransactionResource> createTransaction(
      @RequestBody CreateTransactionResource resource) {
    var command = CreateTransactionCommandFromResourceAssembler.toCommandFromResource(resource);

    Optional<Transaction> transaction = transactionCommandService.handle(command);
    return transaction.map(resp ->
            new ResponseEntity<>(TransactionResourceFromEntityAssembler.toResourceFromEntity(resp),
                CREATED))
        .orElseGet(() -> ResponseEntity.badRequest().build());
  }

  /**
   * Gets transaction.
   *
   * @param transactionId the transaction id
   * @return the transaction
   */
  @GetMapping("/{transactionId}")
  public ResponseEntity<TransactionResource> getTransaction(@PathVariable Long transactionId) {
    var getTransactionByIdQuery = new GetTransactionByIdQuery(transactionId);
    var transaction = transactionQueryService.handle(getTransactionByIdQuery);
    if (transaction.isEmpty()) {
      return ResponseEntity.badRequest().build();
    }
    var transactionResource =
        TransactionResourceFromEntityAssembler.toResourceFromEntity(transaction.get());
    return ResponseEntity.ok(transactionResource);
  }

  /**
   * Gets my transactions.
   *
   * @return the my transactions
   */
  @GetMapping("/me")
  public ResponseEntity<List<TransactionResource>> getMyTransactions() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

    var getAllTransactionsQuery = new GetAllTransactionsQuery();

    var transactions = transactionQueryService.handle(getAllTransactionsQuery);

    var transactionResources = transactions.stream()
        .map(TransactionResourceFromEntityAssembler::toResourceFromEntity)
        .collect(Collectors.toList());

    return ResponseEntity.ok(transactionResources);
  }


  /**
   * Update transaction response entity.
   *
   * @param transactionId the transaction id
   * @param resource      the resource
   * @return the response entity
   */
  @PutMapping("/{transactionId}")
  public ResponseEntity<TransactionResource> updateTransaction(@PathVariable Long transactionId,
                                                               @RequestBody
                                                               UpdateTransactionResource resource) {
    var command = UpdateTransactionCommandFromResourceAssembler.toCommandFromResource(resource);

    Optional<Transaction> transaction = transactionCommandService.handle(command);
    return transaction.map(resp ->
            new ResponseEntity<>(TransactionResourceFromEntityAssembler.toResourceFromEntity(resp),
                CREATED))
        .orElseGet(() -> ResponseEntity.badRequest().build());
  }
}
