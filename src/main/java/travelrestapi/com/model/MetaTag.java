package travelrestapi.com.model;

import com.itextpdf.text.Meta;

import lombok.Getter;
import lombok.Setter;

public class MetaTag
{
	@Getter
	@Setter
	private int id;

	@Getter
	@Setter
	private String keywords;

	@Getter
	@Setter
	private int tripid;

	public String getKeywords()
	{
		return keywords;
	}

	public void setKeywords(String keywords)
	{
		this.keywords = keywords;
	}

	@Getter
	@Setter
	private String created_at;

	public MetaTag(String keywords, int tripid)
	{
		this.keywords = keywords;
		this.tripid = tripid;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getTripid()
	{
		return tripid;
	}

	public void setTripid(int tripid)
	{
		this.tripid = tripid;
	}

	public String getCreated_at()
	{
		return created_at;
	}

	public void setCreated_at(String created_at)
	{
		this.created_at = created_at;
	}

}
