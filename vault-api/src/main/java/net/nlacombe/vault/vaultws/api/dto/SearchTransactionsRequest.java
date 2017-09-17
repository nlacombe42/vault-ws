package net.nlacombe.vault.vaultws.api.dto;

public class SearchTransactionsRequest
{
	private PaginationRequest paginationRequest;
	private boolean categorizedOnly;

	public PaginationRequest getPaginationRequest()
	{
		return paginationRequest;
	}

	public void setPaginationRequest(PaginationRequest paginationRequest)
	{
		this.paginationRequest = paginationRequest;
	}

	public boolean isCategorizedOnly()
	{
		return categorizedOnly;
	}

	public void setCategorizedOnly(boolean categorizedOnly)
	{
		this.categorizedOnly = categorizedOnly;
	}
}
