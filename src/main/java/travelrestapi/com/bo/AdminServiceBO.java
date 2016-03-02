package travelrestapi.com.bo;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import travelrestapi.com.dao.ActivityDAO;
import travelrestapi.com.dao.AdminDAO;
import travelrestapi.com.dao.TripDetailsDAO;
import travelrestapi.com.model.Activity;
import travelrestapi.com.model.CsvConnection;
import travelrestapi.com.model.Login;
import travelrestapi.com.model.Admin;
import travelrestapi.com.model.AdminAuth;
import travelrestapi.com.model.Trip;
import travelrestapi.com.service.AdminService;
import travelrestapi.com.util.AppProp;

@SuppressWarnings(
{ "unused", "unchecked" })
public class AdminServiceBO implements AdminService
{

	@Autowired
	@Qualifier("adminDAO")
	AdminDAO adminDAO;

	@Autowired
	@Qualifier("tripDetailsDAO")
	TripDetailsDAO tripDetailsDAO;

	@Autowired
	@Qualifier("activityDAO")
	ActivityDAO activityDAO;

	@Autowired
	@Qualifier("appProp")
	AppProp appProp;

	@Autowired
	@Qualifier("csvConnection")
	CsvConnection csvConnection;

	public static final int ONE_MB = 1024 * 1024; // 1 MB - Do not modify this.
	public static final int MAX_BUFFER_SIZE = ONE_MB; // 1 MB
	public static final int MAX_VIDEO_SIZE = 50 * ONE_MB; // 10 MB
	public static final int MAX_IMAGE_SIZE = 2 * ONE_MB; // 10 MB

	@Override
	public List<Admin> getVendorList(int startIndx, int endIndx)
			throws Exception
	{
		return adminDAO.getVendorList(startIndx, endIndx);
	}

	@Override
	public List<Admin> getAdminLoginValidate(Admin admin) throws Exception
	{
		return adminDAO.getAdminLoginValidate(admin);
	}

	@Override
	public void setAuthToken(AdminAuth adminAuth) throws Exception
	{
		adminDAO.setAuthToken(adminAuth);
	}

	public List<AdminAuth> getUserId_AuthToken(String authToken, String status)
			throws Exception
	{
		return adminDAO.getUserId_AuthToken(authToken, status);
	}

	@Override
	public int getVendorListCount() throws Exception
	{
		return adminDAO.getVendorListCount();
	}

	@Override
	public void updateApprovedStatus(String userEmail, String approvedStatus)
			throws Exception
	{
		adminDAO.updateApprovedStatus(userEmail, approvedStatus);
	}

	@Override
	public List<Admin> getUserBasedApprovedType(String approvedStatus,
			int startIndx, int endIndx) throws Exception
	{
		return adminDAO.getUserBasedApprovedType(approvedStatus, startIndx,
				endIndx);
	}

	@Override
	public int getNumEntries_UserStatus(String approvedStatus) throws Exception
	{
		return adminDAO.getNumEntries_UserStatus(approvedStatus);
	}

	@Override
	public List<Trip> getTripDetails_ALL(int startIndx, int endInx)
			throws Exception
	{
		return tripDetailsDAO.getTripDetails_ALL(startIndx, endInx);
	}

	@Override
	public List<Trip> getTripDetails(String status, int startINdx, int endindx)
			throws Exception
	{
		return tripDetailsDAO.getTripDetails(status, startINdx, endindx);
	}

	@Override
	public int getTripDetails_ALL_numEntries() throws Exception
	{
		return tripDetailsDAO.getTripDetails_ALL_numEntries();
	}

	@Override
	public int getTripDetails_Status_numEntries(String status) throws Exception
	{
		return tripDetailsDAO.getTripDetails_Status_numEntries(status);
	}

	@Override
	public void updateTripdetails(String status, int id) throws Exception
	{
		tripDetailsDAO.updateTripdetails(status, id);
	}

	public void addActivity(String activityName) throws Exception
	{
		activityDAO.addActivity(activityName);
	}

	@Override
	public List<Activity> getActivitys_All(int startIndx, int maxIndx)
			throws Exception
	{
		return activityDAO.getActivitys_All(startIndx, maxIndx);
	}

	@Override
	public List<Activity> getActivity(String activityStatus, int startIndx,
			int endIndx) throws Exception
	{
		return activityDAO.getActivity(activityStatus, startIndx, endIndx);
	}

	@Override
	public int getActivityAll_NumEntries() throws Exception
	{
		return activityDAO.getActivityAll_NumEntries();
	}

	@Override
	public int getActivity_NumEntries(String status) throws Exception
	{
		return activityDAO.getActivity_NumEntries(status);
	}

	@Override
	public void updateActivityStatus(int activityId, String status)
			throws Exception
	{
		activityDAO.updateActivityStatus(activityId, status);
	}

	@Override
	public List<Admin> getVendorListSearch(String searchKey, String status,
			int startIndx, int maxIndx) throws Exception
	{
		return adminDAO.getVendorListSearch(searchKey, status, startIndx,
				maxIndx);
	}

	public int getVendorListSearchNumEntries(String searchKey, String status,
			int startIndx, int maxIndx) throws Exception
	{
		return adminDAO.getVendorListSearchNumEntries(searchKey, status,
				startIndx, maxIndx);
	}

	@Override
	public List<Trip> getTripSearchKey(String searchKey, String status,
			int startIndx, int maxIndx) throws Exception
	{
		return tripDetailsDAO.getTripSearchKey(searchKey, status, startIndx,
				maxIndx);
	}

	@Override
	public int getTripSearchKey_NumEntries(String searchKey, String status)
			throws Exception
	{
		return tripDetailsDAO.getTripSearchKey_NumEntries(searchKey, status);
	}

	@Override
	public List<Activity> getActivitySearchKey(String searchKey, String status,
			int startIndx, int endIndx) throws Exception
	{
		return activityDAO.getActivitySearchKey(searchKey, status, startIndx,
				endIndx);
	}

	public int getActivitySearchKeyNumEntries(String searchKey, String status)
			throws Exception
	{
		return activityDAO.getActivitySearchKeyNumEntries(searchKey, status);
	}

	public List<Trip> getTripDetailsBasedId(int tripId) throws Exception
	{
		return tripDetailsDAO.getTripDetailsBasedId(tripId);
	}

	@Override
	public List<Login> getUserDetailsBasedEmail(String userEmail)
			throws Exception
	{
		return adminDAO.getUserDetailsBasedEmail(userEmail);
	}

	@Override
	public List<Login> getUsers_Status(String isApproved)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public void addMetaKeywords(String keywords, int tripId) throws Exception
	{
		adminDAO.addMetaKeywords(keywords, tripId);
	}

	public void updateMetaKeywords(String keywords, int tripId)
			throws Exception
	{
		adminDAO.updateMetaKeywords(keywords, tripId);
	}

	@Override
	public void addSubActivity(String subActivityName) throws Exception
	{
		adminDAO.addSubActivity(subActivityName);
	}

	@Override
	public void addBulkActivity(HttpServletRequest request) throws Exception
	{
		String UpdateVideoResult = "";
		FileOutputStream fileOutputStream = null;
		Map<Object, Object> map = new HashMap<Object, Object>();
		List<String> list = new ArrayList<String>();
		String tempFileName = "";
		String fileName = "";
		List<FileItem> items =
				new ServletFileUpload(new DiskFileItemFactory())
						.parseRequest(request);
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		try
		{
			if (isMultipart)
			{
				File file;
				for (FileItem item : items)
				{
					if (!item.isFormField())
					{

						fileName = item.getName();
						String oldFileName = "";
						boolean isInMemory = item.isInMemory();
						long sizeInBytes = item.getSize();
						String finalImageURL = "";

						if (fileName.lastIndexOf("\\") >= 0)
						{

							oldFileName =
									fileName.substring(fileName
											.lastIndexOf("\\"));
							tempFileName =
									appProp.getUploadCSVPath() + oldFileName;
							file = new File(tempFileName);
						} else
						{
							oldFileName =
									fileName.substring(fileName
											.lastIndexOf("\\") + 1);
							tempFileName =
									appProp.getUploadCSVPath() + oldFileName;
							file = new File(tempFileName);
						}
						item.write(file);
						CSVProcess(oldFileName, true);
					}
				}

			}
		} catch (Exception ex)
		{
			throw ex;
		}

	}

	public void CSVProcess(String csvFileName, boolean isActivity)
			throws Exception
	{
		Connection connection = null;
		ResultSet resultSet = null;
		Statement statement = null;
		List<Activity> list = new ArrayList<Activity>();
		try
		{
			csvFileName = csvFileName.split("\\.")[0];
			String query = "SELECT name from " + csvFileName;
			connection =
					csvConnection.getCsvConnection(appProp.getUploadCSVPath());
			if (connection != null)
			{
				statement = connection.createStatement();
				resultSet = statement.executeQuery(query);
				while (resultSet.next())
				{
					String name = resultSet.getString(1);
					Activity activity = new Activity(name);
					list.add(activity);
				}
				if (isActivity)
				{
					activityDAO.addBulkActivity(list);
				} else
				{
					adminDAO.addBulkSubActivity(list);
				}

			}
		} catch (Exception ex)
		{
			throw ex;
		} finally
		{
			csvConnection.closeCSVConnection(connection, statement, resultSet);
		}
	}

	@Override
	public void addBulkSubActivity(HttpServletRequest request) throws Exception
	{
		String UpdateVideoResult = "";
		FileOutputStream fileOutputStream = null;
		Map<Object, Object> map = new HashMap<Object, Object>();
		List<String> list = new ArrayList<String>();
		String tempFileName = "";
		String fileName = "";
		List<FileItem> items =
				new ServletFileUpload(new DiskFileItemFactory())
						.parseRequest(request);
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);

		try
		{
			if (isMultipart)
			{
				File file;
				for (FileItem item : items)
				{
					if (!item.isFormField())
					{

						fileName = item.getName();
						String oldFileName = "";
						boolean isInMemory = item.isInMemory();
						long sizeInBytes = item.getSize();
						String finalImageURL = "";

						if (fileName.lastIndexOf("\\") >= 0)
						{

							oldFileName =
									fileName.substring(fileName
											.lastIndexOf("\\"));
							tempFileName =
									appProp.getUploadCSVPath() + oldFileName;
							file = new File(tempFileName);
						} else
						{
							oldFileName =
									fileName.substring(fileName
											.lastIndexOf("\\") + 1);
							tempFileName =
									appProp.getUploadCSVPath() + oldFileName;
							file = new File(tempFileName);
						}
						item.write(file);
						CSVProcess(oldFileName, false);
					}
				}

			}
		} catch (Exception ex)
		{
			throw ex;
		}

	}

	@Override
	public List<Activity> getSubActivity(String activityStatus, int startIndx,
			int maxIndx) throws Exception
	{
		return adminDAO.getSubActivity(activityStatus, startIndx, maxIndx);
	}

	@Override
	public int getSubActivity_NumEntries(String activityStatus)
			throws Exception
	{
		return adminDAO.getSubActivity_NumEntries(activityStatus);
	}

	@Override
	public List<Activity> getSubActivity_All(int startIndx, int maxIndx)
			throws Exception
	{
		return adminDAO.getSubActivity_All(startIndx, maxIndx);
	}

	@Override
	public void updateSubActivityStatus(int activityId, String status)
			throws Exception
	{
		adminDAO.updateSubActivityStatus(activityId, status);
	}
}
