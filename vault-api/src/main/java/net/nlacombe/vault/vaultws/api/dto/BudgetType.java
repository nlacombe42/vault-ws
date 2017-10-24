package net.nlacombe.vault.vaultws.api.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum BudgetType
{
	MONTH("month"),
	YEAR("year"),
	RANGE("range")
	;

	private String code;

	BudgetType(String code)
	{
		this.code = code;
	}

	@JsonCreator
	public static BudgetType fromCode(String code)
	{
		return Arrays.stream(values())
				.filter(budgetType -> budgetType.getCode().equals(code))
				.findFirst().orElseThrow(() -> new IllegalArgumentException("No budget type found for code: " + code));
	}

	@JsonValue
	public String getCode()
	{
		return code;
	}
}
