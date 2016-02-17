package travelrestapi.com.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import travelrestapi.com.dao.ActivityDAO;
import travelrestapi.com.dao.AdminDAO;
import travelrestapi.com.dao.TripDetailsDAO;
import travelrestapi.com.model.Activity;
import travelrestapi.com.model.Login;
import travelrestapi.com.model.Admin;
import travelrestapi.com.model.AdminAuth;
import travelrestapi.com.model.Trip;
import travelrestapi.com.service.AdminService;

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
}
