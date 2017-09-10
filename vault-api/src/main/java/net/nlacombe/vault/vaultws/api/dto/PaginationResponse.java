package net.nlacombe.vault.vaultws.api.dto;

import java.util.List;

public class PaginationResponse<Element>
{
	private PaginationRequest paginationRequest;
	private long total;
	private List<Element> elements;

	public PaginationResponse(PaginationRequest paginationRequest, long total, List<Element> elements)
	{
		this.paginationRequest = paginationRequest;
		this.total = total;
		this.elements = elements;
	}

	public PaginationRequest getPaginationRequest()
	{
		return paginationRequest;
	}

	public void setPaginationRequest(PaginationRequest paginationRequest)
	{
		this.paginationRequest = paginationRequest;
	}

	public long getTotal()
	{
		return total;
	}

	public void setTotal(long total)
	{
		this.total = total;
	}

	public List<Element> getElements()
	{
		return elements;
	}

	public void setElements(List<Element> elements)
	{
		this.elements = elements;
	}
}
