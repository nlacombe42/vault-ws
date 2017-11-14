package net.nlacombe.vault.vaultws.service;

import net.nlacombe.vault.vaultws.api.dto.PaginationResponse;
import net.nlacombe.vault.vaultws.api.dto.SearchTransactionsRequest;
import net.nlacombe.vault.vaultws.api.dto.Transaction;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collection;
import java.util.List;

public interface TransactionService
{
	Transaction createTransaction(int userId, Transaction transaction);

	int countTransactions(int accountId, Instant datetime, String description, BigDecimal amount);

	List<Transaction> getUncategorizedTransactions(int userId);

	void categorizeTransaction(int userId, int transactionId, Integer categoryId);

	PaginationResponse<Transaction> searchTransactions(int userId, SearchTransactionsRequest searchTransactionsRequest);

	BigDecimal getCategoryTotal(int userId, int categoryId, Instant startDate, Instant endDate);

	BigDecimal getCategoriesTotal(int userId, Collection<Integer> categoryIds, Instant startDate, Instant endDate);

	BigDecimal getTotalAmount(int userId, Instant startDate, Instant endDate);

	List<Transaction> getTransactions(int userId, Integer categoryId, Instant startDate, Instant endDate);
}
