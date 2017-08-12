package net.nlacombe.vault.vaultws.service.impl;

import net.nlacombe.vault.vaultws.api.dto.Transaction;
import net.nlacombe.vault.vaultws.entity.AccountEntity;
import net.nlacombe.vault.vaultws.entity.CategoryEntity;
import net.nlacombe.vault.vaultws.entity.TransactionEntity;
import net.nlacombe.vault.vaultws.mapper.TransactionMapper;
import net.nlacombe.vault.vaultws.repositorty.AccountRepository;
import net.nlacombe.vault.vaultws.repositorty.CategoryRepository;
import net.nlacombe.vault.vaultws.repositorty.TransactionRepository;
import net.nlacombe.vault.vaultws.service.TransactionService;
import net.nlacombe.wsutils.restexception.exception.NotFoundRestException;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.Instant;

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
	public boolean transactionExists(int userId, Instant datetime, String description, BigDecimal amount)
	{
		return transactionRepository.existsByAccountUserIdAndDatetimeAndDescriptionAndAmount(userId, datetime, description, amount);
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
