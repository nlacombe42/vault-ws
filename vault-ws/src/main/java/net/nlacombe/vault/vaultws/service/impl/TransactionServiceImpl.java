package net.nlacombe.vault.vaultws.service.impl;

import net.nlacombe.vault.vaultws.api.dto.PaginationRequest;
import net.nlacombe.vault.vaultws.api.dto.PaginationResponse;
import net.nlacombe.vault.vaultws.api.dto.SearchTransactionsRequest;
import net.nlacombe.vault.vaultws.api.dto.SplitChildTransactionRequest;
import net.nlacombe.vault.vaultws.api.dto.SplitTransactionRequest;
import net.nlacombe.vault.vaultws.api.dto.Transaction;
import net.nlacombe.vault.vaultws.entity.AccountEntity;
import net.nlacombe.vault.vaultws.entity.CategoryEntity;
import net.nlacombe.vault.vaultws.entity.ParentTransactionEntity;
import net.nlacombe.vault.vaultws.entity.TransactionEntity;
import net.nlacombe.vault.vaultws.mapper.TransactionMapper;
import net.nlacombe.vault.vaultws.repositorty.AccountRepository;
import net.nlacombe.vault.vaultws.repositorty.ParentTransactionRepository;
import net.nlacombe.vault.vaultws.repositorty.TransactionRepository;
import net.nlacombe.vault.vaultws.service.CategoryAccessService;
import net.nlacombe.vault.vaultws.service.TransactionService;
import net.nlacombe.vault.vaultws.util.PaginationUtils;
import net.nlacombe.wsutils.restexception.exception.InvalidInputRestException;
import net.nlacombe.wsutils.restexception.exception.NotFoundRestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TransactionServiceImpl implements TransactionService
{
	private final AccountRepository accountRepository;
	private final CategoryAccessService categoryAccessService;
	private final TransactionRepository transactionRepository;
	private final TransactionMapper transactionMapper;
	private final ParentTransactionRepository parentTransactionRepository;

	public TransactionServiceImpl(AccountRepository accountRepository, CategoryAccessService categoryAccessService, TransactionRepository transactionRepository,
								  TransactionMapper transactionMapper, ParentTransactionRepository parentTransactionRepository)
	{
		this.accountRepository = accountRepository;
		this.categoryAccessService = categoryAccessService;
		this.transactionRepository = transactionRepository;
		this.transactionMapper = transactionMapper;
		this.parentTransactionRepository = parentTransactionRepository;
	}

	@Override
	public Transaction createTransaction(int userId, Transaction transaction)
	{
		transaction.setTransactionId(0);

		if (transaction.getTemporary() == null) {
			transaction.setTemporary(false);
		}

		var accountEntity = getAccountEntity(userId, transaction.getAccountId());
		var categoryentity = categoryAccessService.getCategoryEntity(userId, transaction.getCategoryId());

		var transactionEntity = transactionMapper.mapToDomainObject(transaction);
		transactionEntity.setAccount(accountEntity);
		transactionEntity.setCategory(categoryentity);

		transactionEntity = transactionRepository.save(transactionEntity);

		return transactionMapper.mapToDto(transactionEntity);
	}

	@Override
	public int countTransactions(int accountId, Instant datetime, String description, BigDecimal amount)
	{
		return transactionRepository.countByAccountAccountIdAndDatetimeAndDescriptionAndAmount(accountId, datetime, description, amount);
	}

	@Override
	public List<Transaction> getUncategorizedTransactions(int userId)
	{
		return transactionRepository.findByCategory(null)
				.map(transactionMapper::mapToDto)
				.collect(Collectors.toList());
	}

	@Override
	public void categorizeTransaction(int userId, int transactionId, Integer categoryId)
	{
		TransactionEntity transactionEntity = getTransactionEntity(userId, transactionId);
		transactionEntity.setCategory(categoryAccessService.getCategoryEntity(userId, categoryId));
		transactionRepository.save(transactionEntity);
	}

	@Override
	public PaginationResponse<Transaction> searchTransactions(int userId, SearchTransactionsRequest searchTransactionsRequest)
	{
		PaginationRequest paginationRequest = searchTransactionsRequest.getPaginationRequest();
		Pageable pageRequest = PaginationUtils.toPageRequest(paginationRequest);

		Page<TransactionEntity> transactionsPage = getAllOrOnlyCategorizedTransactions(userId, pageRequest, searchTransactionsRequest.isCategorizedOnly());
		List<Transaction> transactions = transactionsPage.getContent()
				.stream()
				.map(transactionMapper::mapToDto)
				.collect(Collectors.toList());

		return new PaginationResponse<>(paginationRequest, transactionsPage.getTotalElements(), transactions);
	}

	@Override
	public BigDecimal getCategoriesTotal(int userId, Collection<Integer> categoryIds, Instant startDate, Instant endDate)
	{
		if (categoryIds.isEmpty())
			return BigDecimal.ZERO;

		BigDecimal categoryTotal = transactionRepository.getCategoriesTotal(userId, categoryIds, startDate, endDate);

		return categoryTotal == null ? BigDecimal.ZERO : categoryTotal;
	}

	@Override
	public BigDecimal getCategoryTotal(int userId, int categoryId, Instant startDate, Instant endDate)
	{
		return getCategoriesTotal(userId, Collections.singleton(categoryId), startDate, endDate);
	}

	@Override
	public BigDecimal getTotalAmount(int userId, Instant startDate, Instant endDate)
	{
		BigDecimal totalAmount = transactionRepository.getTotalAmount(userId, startDate, endDate);

		return totalAmount == null ? BigDecimal.ZERO : totalAmount;
	}

	@Override
	public Stream<Transaction> getTransactions(int userId, Integer categoryId, Instant startDate, Instant endDate)
	{
		return getTransactions(userId, Collections.singletonList(categoryId), startDate, endDate);
	}

	@Override
	public Stream<Transaction> getTransactions(int userId, Collection<Integer> categoryIds, Instant startDate, Instant endDate)
	{
		return transactionRepository.getTransactions(userId, categoryIds, startDate, endDate)
				.map(transactionMapper::mapToDto);
	}

	@Override
	public Transaction getTransaction(int userId, int transactionId)
	{
		TransactionEntity transactionEntity = getTransactionEntity(userId, transactionId);

		return transactionMapper.mapToDto(transactionEntity);
	}

	@Override
	public void deleteTransaction(int userId, int transactionId)
	{
		TransactionEntity transactionEntity = getTransactionEntity(userId, transactionId);

		transactionRepository.delete(transactionEntity);
	}

	@Override
	public void deleteTemporaryTransactions(int userId) {
		transactionRepository.deleteByAccount_UserIdAndTemporary(userId, true);
	}

	@Override
	public void splitTransaction(int userId, SplitTransactionRequest splitTransactionRequest) {
		var parentTransaction = getTransactionEntity(userId, splitTransactionRequest.getTransactionId());

		validateNotSplittingAChildTransaction(parentTransaction);
		validateNotSplittingTemporaryTransaction(parentTransaction);
		validateChildTransactionInfoProvided(splitTransactionRequest);
		validateTotalChildAmountMatchesParentAmount(parentTransaction, splitTransactionRequest);

		var parentTransactionEntity = moveTransactionToParentTransaction(parentTransaction);

		splitTransactionRequest.getChildTransactions().forEach(childTransaction ->
				createChildTransaction(parentTransactionEntity, childTransaction.getDescription(), childTransaction.getAmount())
		);
	}

	private void createChildTransaction(ParentTransactionEntity parentTransactionEntity, String description, BigDecimal amount) {
		var transactionEntity = new TransactionEntity();
		transactionEntity.setTransactionId(0);
		transactionEntity.setParentTransaction(parentTransactionEntity);
		transactionEntity.setAccount(parentTransactionEntity.getAccount());
		transactionEntity.setCategory(null);
		transactionEntity.setDatetime(parentTransactionEntity.getDatetime());
		transactionEntity.setTemporary(false);
		transactionEntity.setDescription(description);
		transactionEntity.setAmount(amount);

		transactionRepository.save(transactionEntity);
	}

	private ParentTransactionEntity moveTransactionToParentTransaction(TransactionEntity parentTransaction) {
		var parentTransactionEntity = new ParentTransactionEntity();
		parentTransactionEntity.setParentTransactionId(parentTransaction.getTransactionId());
		parentTransactionEntity.setAccount(parentTransaction.getAccount());
		parentTransactionEntity.setAmount(parentTransaction.getAmount());
		parentTransactionEntity.setDatetime(parentTransaction.getDatetime());
		parentTransactionEntity.setDescription(parentTransaction.getDescription());

		parentTransactionEntity = parentTransactionRepository.save(parentTransactionEntity);
		transactionRepository.delete(parentTransaction);

		return parentTransactionEntity;
	}

	private void validateTotalChildAmountMatchesParentAmount(TransactionEntity parentTransaction, SplitTransactionRequest splitTransactionRequest) {
		var childTotalAmount = splitTransactionRequest.getChildTransactions().stream()
				.map(SplitChildTransactionRequest::getAmount)
				.reduce(BigDecimal::add)
				.orElseThrow();
		var parentTransactionAmount = parentTransaction.getAmount();

		if (!parentTransactionAmount.equals(childTotalAmount))
			throw new InvalidInputRestException("total amount of child transactions not equal to parent transaction amount of " + parentTransactionAmount);
	}

	private void validateChildTransactionInfoProvided(SplitTransactionRequest splitTransactionRequest) {
		if (splitTransactionRequest.getChildTransactions().isEmpty())
			throw new InvalidInputRestException("you must supply child transactions to split a transaction");
	}

	private void validateNotSplittingTemporaryTransaction(TransactionEntity parentTransaction) {
		if (parentTransaction.isTemporary())
			throw new InvalidInputRestException("cannot split a transaction that is temporary");
	}

	private void validateNotSplittingAChildTransaction(TransactionEntity parentTransaction) {
		if (parentTransaction.getParentTransaction() != null)
			throw new InvalidInputRestException("cannot split a transaction that was created by a transaction split");
	}

	private Page<TransactionEntity> getAllOrOnlyCategorizedTransactions(int userId, Pageable pageRequest, boolean categorizedOnly)
	{
		Page<TransactionEntity> transactionsPage;

		if (categorizedOnly)
			transactionsPage = transactionRepository.findByAccountUserIdAndCategoryNotNullOrderByDatetimeDesc(userId, pageRequest);
		else
			transactionsPage = transactionRepository.findByAccountUserIdOrderByDatetimeDesc(userId, pageRequest);

		return transactionsPage;
	}

	private TransactionEntity getTransactionEntity(int userId, int transactionId)
	{
		NotFoundRestException transactionNotFound = new NotFoundRestException("Transaction with ID " + transactionId + " not found for user ID " + userId);

		TransactionEntity transactionEntity = transactionRepository.getOne(transactionId).orElseThrow(() -> transactionNotFound);

		if (transactionEntity.getAccount().getUserId() != userId)
			throw transactionNotFound;

		return transactionEntity;
	}

	private AccountEntity getAccountEntity(int userId, int accountId)
	{
		AccountEntity accountEntity = accountRepository.getOne(accountId);

		if (accountEntity == null || accountEntity.getUserId() != userId)
			throw new NotFoundRestException("Account ID " + accountId + " not found for user ID " + userId);

		return accountEntity;
	}
}
