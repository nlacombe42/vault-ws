package net.nlacombe.vault.vaultws.service.impl;

import net.nlacombe.vault.vaultws.api.dto.PaginationRequest;
import net.nlacombe.vault.vaultws.api.dto.PaginationResponse;
import net.nlacombe.vault.vaultws.api.dto.SearchTransactionsRequest;
import net.nlacombe.vault.vaultws.api.dto.Transaction;
import net.nlacombe.vault.vaultws.entity.AccountEntity;
import net.nlacombe.vault.vaultws.entity.CategoryEntity;
import net.nlacombe.vault.vaultws.entity.TransactionEntity;
import net.nlacombe.vault.vaultws.mapper.TransactionMapper;
import net.nlacombe.vault.vaultws.repositorty.AccountRepository;
import net.nlacombe.vault.vaultws.repositorty.CategoryRepository;
import net.nlacombe.vault.vaultws.repositorty.TransactionRepository;
import net.nlacombe.vault.vaultws.service.TransactionService;
import net.nlacombe.vault.vaultws.util.PaginationUtils;
import net.nlacombe.wsutils.restexception.exception.NotFoundRestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService
{
	@Inject
	private AccountRepository accountRepository;

	@Inject
	private CategoryRepository categoryRepository;

	@Inject
	private TransactionRepository transactionRepository;

	@Inject
	private TransactionMapper transactionMapper;

	@Override
	public Transaction createTransaction(int userId, Transaction transaction)
	{
		transaction.setTransactionId(0);

		AccountEntity accountEntity = getAccountEntity(userId, transaction.getAccountId());
		CategoryEntity categoryentity = getCategoryEntity(userId, transaction.getCategoryId());

		TransactionEntity transactionEntity = transactionMapper.mapToEntity(transaction);
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
		transactionEntity.setCategory(getCategoryEntity(userId, categoryId));
		transactionRepository.save(transactionEntity);
	}

	@Override
	public PaginationResponse<Transaction> searchTransactions(int userId, SearchTransactionsRequest searchTransactionsRequest)
	{
		PaginationRequest paginationRequest = searchTransactionsRequest.getPaginationRequest();
		Pageable pageRequest = PaginationUtils.toPageRequest(paginationRequest);

		Page<TransactionEntity> transactionsPage = transactionRepository.findByAccountUserIdOrderByDatetimeDesc(userId, pageRequest);
		List<Transaction> transactions = transactionsPage.getContent()
				.stream()
				.map(transactionMapper::mapToDto)
				.collect(Collectors.toList());

		return new PaginationResponse<>(paginationRequest, transactionsPage.getTotalElements(), transactions);
	}

	private TransactionEntity getTransactionEntity(int userId, int transactionId)
	{
		NotFoundRestException transcationNotFound = new NotFoundRestException("Transaction with ID " + transactionId + " not found for user ID " + userId);

		TransactionEntity transactionEntity = transactionRepository.findOne(transactionId).orElseThrow(() -> transcationNotFound);

		if (transactionEntity.getAccount().getUserId() != userId)
			throw transcationNotFound;

		return transactionEntity;
	}

	private CategoryEntity getCategoryEntity(int userId, Integer categoryId)
	{
		if (categoryId == null)
			return null;

		CategoryEntity categoryEntity = categoryRepository.findOne(categoryId);

		if (categoryEntity == null || categoryEntity.getUserId() != userId)
			throw new NotFoundRestException("Category ID " + categoryId + " not found for user ID " + userId);

		return categoryEntity;
	}

	private AccountEntity getAccountEntity(int userId, int accountId)
	{
		AccountEntity accountEntity = accountRepository.findOne(accountId);

		if (accountEntity == null || accountEntity.getUserId() != userId)
			throw new NotFoundRestException("Account ID " + accountId + " not found for user ID " + userId);

		return accountEntity;
	}
}
