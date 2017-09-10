package net.nlacombe.vault.vaultws.api.dto;

public class SearchTransactionsRequest
{
	private PaginationRequest paginationRequest;

	public PaginationRequest getPaginationRequest()
	{
		return paginationRequest;
	}

	public void setPaginationRequest(PaginationRequest paginationRequest)
	{
		this.paginationRequest = paginationRequest;
	}
}
