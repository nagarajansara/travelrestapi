package travelrestapi.com.model;

import lombok.Getter;
import lombok.Setter;

public class Trip
{
	public String getImagename()
	{
		return imagename;
	}

	public void setImagename(String imagename)
	{
		this.imagename = imagename;
	}

	@Getter
	@Setter
	private int id;

	@Getter
	@Setter
	private int userid;

	public String getDaywisedescription()
	{
		return daywisedescription;
	}

	public void setDaywisedescription(String daywisedescription)
	{
		this.daywisedescription = daywisedescription;
	}

	@Getter
	@Setter
	private String title;

	@Getter
	@Setter
	private String tripimages;

	@Getter
	@Setter
	private String daywisedescription;

	public String getTripimages()
	{
		return tripimages;
	}

	public void setTripimages(String tripimages)
	{
		this.tripimages = tripimages;
	}

	@Getter
	@Setter
	private String imagename;

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

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public int getLocationid()
	{
		return locationid;
	}

	public void setLocationid(int locationid)
	{
		this.locationid = locationid;
	}

	public int getActivityid()
	{
		return activityid;
	}

	public void setActivityid(int activityid)
	{
		this.activityid = activityid;
	}

	public int getDuration()
	{
		return duration;
	}

	public void setDuration(int duration)
	{
		this.duration = duration;
	}

	public String getFromdate()
	{
		return fromdate;
	}

	public void setFromdate(String fromdate)
	{
		this.fromdate = fromdate;
	}

	public String getTodate()
	{
		return todate;
	}

	public void setTodate(String todate)
	{
		this.todate = todate;
	}

	public int getStartpoint()
	{
		return startpoint;
	}

	public void setStartpoint(int startpoint)
	{
		this.startpoint = startpoint;
	}

	public String getRoute()
	{
		return route;
	}

	public void setRoute(String route)
	{
		this.route = route;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public int getPrice()
	{
		return price;
	}

	public void setPrice(int price)
	{
		this.price = price;
	}

	public String getGuidelines()
	{
		return guidelines;
	}

	public void setGuidelines(String guidelines)
	{
		this.guidelines = guidelines;
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

	public int getStartIndx()
	{
		return startIndx;
	}

	public void setStartIndx(int startIndx)
	{
		this.startIndx = startIndx;
	}

	public int getEndIndx()
	{
		return endIndx;
	}

	public void setEndIndx(int endIndx)
	{
		this.endIndx = endIndx;
	}

	@Getter
	@Setter
	private int locationid;

	@Getter
	@Setter
	private int activityid;

	@Getter
	@Setter
	private int duration;

	public String getTotaltrips()
	{
		return totaltrips;
	}

	public void setTotaltrips(String totaltrips)
	{
		this.totaltrips = totaltrips;
	}

	@Getter
	@Setter
	private String fromdate;

	@Getter
	@Setter
	private String totaltrips;

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	@Getter
	@Setter
	private String email;

	@Getter
	@Setter
	private String todate;

	@Getter
	@Setter
	private int startpoint;

	@Getter
	@Setter
	private String route;

	@Getter
	@Setter
	private String description;

	@Getter
	@Setter
	private int price;

	@Getter
	@Setter
	private String guidelines;

	@Getter
	@Setter
	private String status;

	@Getter
	@Setter
	private String createdat;

	@Getter
	@Setter
	private int startIndx;

	@Getter
	@Setter
	private int endIndx;

	public static final String STATUS_TYPE_ALL = "all";
	public static final String STATUS_TYPE_DEACTIVE = "deactive";
	public static final String STATUS_TYPE_EXPIRY = "expiry";
	public static final String STATUS_TYPE_ACTIVE = "active";

	public Trip()
	{

	}

	public Trip(String status, int startIndx, int endIndx)
	{
		this.status = status;
		this.startIndx = startIndx;
		this.endIndx = endIndx;
	}

}
