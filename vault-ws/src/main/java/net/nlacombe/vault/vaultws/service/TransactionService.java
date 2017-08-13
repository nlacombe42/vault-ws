package net.nlacombe.vault.vaultws.service;

import net.nlacombe.vault.vaultws.api.dto.Transaction;

import java.math.BigDecimal;
import java.time.Instant;

public interface TransactionService
{
	Transaction createTransaction(int userId, Transaction transaction);

	int countTransactions(int accountId, Instant datetime, String description, BigDecimal amount);
}
