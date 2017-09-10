package net.nlacombe.vault.vaultws.api.dto;

public class PaginationRequest
{
	private int pageNumber;
	private int size;

	public PaginationRequest()
	{
	}

	public PaginationRequest(int pageNumber, int size)
	{
		this.pageNumber = pageNumber;
		this.size = size;
	}

	public int getPageNumber()
	{
		return pageNumber;
	}

	public void setPageNumber(int pageNumber)
	{
		this.pageNumber = pageNumber;
	}

	public int getSize()
	{
		return size;
	}

	public void setSize(int size)
	{
		this.size = size;
	}
}
