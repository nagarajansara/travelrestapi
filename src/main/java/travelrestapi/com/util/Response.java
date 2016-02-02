package travelrestapi.com.util;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

public class Response
{
	int responseStatus;
    String responseMsg;
    Object responseData;

    public int getResponseStatus() {
        return responseStatus;
    }

    @XmlElement
    public void setResponseStatus(int responseStatus) 
    {
        this.responseStatus = responseStatus;
    }

    public String getResponseMsg() 
	{
        return responseMsg;
    }

    @XmlElement
    public void setResponseMsg(String responseMsg) 
    {
        this.responseMsg = responseMsg;
    }

    public Object getResponseData() 
	{
        return responseData;
    }

    @XmlElement
    public void setResponseData(Object responseData) 
    {
        this.responseData = responseData;
    }

    public Response(int responseStatus, String responseMsg) 
	{
        this.responseStatus = responseStatus;
        this.responseMsg = responseMsg;
    }

    public Response()
	{
        responseStatus = -1;
        responseMsg = "Unknown";
    }
}

