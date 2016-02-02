package travelrestapi.com.model;

import lombok.Getter;
import lombok.Setter;

public class Activity
{
	@Getter
	@Setter
	private int id;

	@Getter
	@Setter
	private String name;

	@Getter
	@Setter
	private String status;

	@Getter
	@Setter
	private String totalactivity;

	public String getTotalactivity()
	{
		return totalactivity;
	}

	public void setTotalactivity(String totalactivity)
	{
		this.totalactivity = totalactivity;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getCreatedat()
	{
		return createdat;
	}

	public void setCreatedat(String createdat)
	{
		this.createdat = createdat;
	}

	@Getter
	@Setter
	private String createdat;

	public Activity()
	{

	}

	public Activity(String name, String status)
	{
		this.name = name;
		this.status = status;
	}

	public Activity(String name)
	{
		this.name = name;
	}

	public static final String GET_ACTIVITY_STATUS_ACTIVE = "active";
	public static final String GET_ACTIVITY_STATUS_DEACTIVE = "deactive";
	public static final String GET_ACTIVITY_STATUS_ALL = "all";

}
