package net.nlacombe.vault.vaultws.service;

import net.nlacombe.vault.vaultws.api.dto.Transaction;

import java.math.BigDecimal;
import java.time.Instant;

public interface TransactionService
{
	Transaction createTransaction(int userId, Transaction transaction);

	boolean transactionExists(int userId, Instant datetime, String description, BigDecimal amount);
}
