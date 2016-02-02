package travelrestapi.com.model;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("unused")
public class AdminAuth
{
	@Getter
	@Setter
	private int id;

	@Getter
	@Setter
	private int userid;

	@Getter
	@Setter
	private String token;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getUserid()
	{
		return userid;
	}

	public void setUserid(int userid)
	{
		this.userid = userid;
	}

	public String getToken()
	{
		return token;
	}

	public void setToken(String token)
	{
		this.token = token;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getCreated_at()
	{
		return created_at;
	}

	public void setCreated_at(String created_at)
	{
		this.created_at = created_at;
	}

	@Getter
	@Setter
	private String status;

	@Getter
	@Setter
	private String created_at;

	public AdminAuth()
	{

	}

	public AdminAuth(String token, int userId, String status)
	{
		this.token = token;
		this.userid = userId;
		this.status = status;
	}

}
