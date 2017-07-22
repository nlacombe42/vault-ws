package net.nlacombe.vault.vaultws.mapper;

import net.nlacombe.vault.vaultws.api.dto.Transaction;
import net.nlacombe.vault.vaultws.entity.CategoryEntity;
import net.nlacombe.vault.vaultws.entity.TransactionEntity;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper extends BeanMapper<Transaction, TransactionEntity>
{
	public TransactionMapper()
	{
		super(Transaction.class, TransactionEntity.class);
	}

	@Override
	public Transaction mapToDto(TransactionEntity transactionEntity)
	{
		Transaction transactionDto = super.mapToDto(transactionEntity);
		transactionDto.setAccountId(transactionEntity.getAccount().getAccountId());

		CategoryEntity category = transactionEntity.getCategory();

		if (category != null)
			transactionDto.setCategoryId(category.getCategoryId());

		return transactionDto;
	}
}
