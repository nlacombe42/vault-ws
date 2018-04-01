package net.nlacombe.vault.vaultws.api.dto;

public class Category
{
	private int categoryId;

	private int userId;

	private String name;

	private int numberOfUses;

	public int getCategoryId()
	{
		return categoryId;
	}

	public void setCategoryId(int categoryId)
	{
		this.categoryId = categoryId;
	}

	public int getUserId()
	{
		return userId;
	}

	public void setUserId(int userId)
	{
		this.userId = userId;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getNumberOfUses()
	{
		return numberOfUses;
	}

	public void setNumberOfUses(int numberOfUses)
	{
		this.numberOfUses = numberOfUses;
	}
}
