package net.nlacombe.vault.vaultws.service;

import net.nlacombe.vault.vaultws.api.dto.Transaction;

public interface TransactionService
{
	Transaction createTransaction(int userId, Transaction transaction);
}
