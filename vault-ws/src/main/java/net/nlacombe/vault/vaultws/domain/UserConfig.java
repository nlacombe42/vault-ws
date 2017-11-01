package net.nlacombe.vault.vaultws.domain;

import java.time.ZoneId;

public class UserConfig
{
	private int userConfigId;
	private int userId;
	private ZoneId timezone;

	public int getUserConfigId()
	{
		return userConfigId;
	}

	public void setUserConfigId(int userConfigId)
	{
		this.userConfigId = userConfigId;
	}

	public int getUserId()
	{
		return userId;
	}

	public void setUserId(int userId)
	{
		this.userId = userId;
	}

	public ZoneId getTimezone()
	{
		return timezone;
	}

	public void setTimezone(ZoneId timezone)
	{
		this.timezone = timezone;
	}
}
