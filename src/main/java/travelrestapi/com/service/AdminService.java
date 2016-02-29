package travelrestapi.com.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import travelrestapi.com.model.Activity;
import travelrestapi.com.model.Admin;
import travelrestapi.com.model.AdminAuth;
import travelrestapi.com.model.Login;
import travelrestapi.com.model.MetaTag;
import travelrestapi.com.model.Trip;

public interface AdminService
{

	List<Admin> getVendorList(int startIndx, int endIndx) throws Exception;

	List<Admin> getAdminLoginValidate(Admin admin) throws Exception;

	void setAuthToken(AdminAuth adminAuth) throws Exception;

	List<AdminAuth> getUserId_AuthToken(String authToken, String status)
			throws Exception;

	int getVendorListCount() throws Exception;

	void updateApprovedStatus(String userEmail, String approvedStatus)
			throws Exception;

	List<Admin> getUserBasedApprovedType(String approvedStatus, int startIndx,
			int endIndx) throws Exception;

	int getNumEntries_UserStatus(String approvedStatus) throws Exception;

	List<Trip> getTripDetails_ALL(int startIndx, int endInx) throws Exception;

	List<Trip> getTripDetails(String status, int startINdx, int endindx)
			throws Exception;

	int getTripDetails_ALL_numEntries() throws Exception;

	int getTripDetails_Status_numEntries(String status) throws Exception;

	void updateTripdetails(String status, int id) throws Exception;

	void addActivity(String activityName) throws Exception;

	List<Activity> getActivitys_All(int startIndx, int maxIndx)
			throws Exception;

			List<Activity> getActivity(String activityStatus, int startIndx,
					int endIndx) throws Exception;

	int getActivityAll_NumEntries() throws Exception;

	int getActivity_NumEntries(String status) throws Exception;

	void updateActivityStatus(int activityId, String status) throws Exception;

	List<Admin> getVendorListSearch(String searchKey, String status,
			int startIndx, int maxIndx) throws Exception;

	int getVendorListSearchNumEntries(String searchKey, String status,
			int startIndx, int maxIndx) throws Exception;

	List<Trip> getTripSearchKey(String searchKey, String status, int startIndx,
			int maxIndx) throws Exception;

	int getTripSearchKey_NumEntries(String searchKey, String status)
			throws Exception;

	List<Activity> getActivitySearchKey(String searchKey, String status,
			int startIndx, int endIndx) throws Exception;

	int getActivitySearchKeyNumEntries(String searchKey, String status)
			throws Exception;

	List<Trip> getTripDetailsBasedId(int tripId) throws Exception;

	List<Login> getUserDetailsBasedEmail(String userEmail) throws Exception;

	List<Login> getUsers_Status(String isApproved);

	void addMetaKeywords(String keywords, int tripId) throws Exception;

	void updateMetaKeywords(String keywords, int tripId) throws Exception;

	void addSubActivity(String subActivityName) throws Exception;

	void addBulkActivity(HttpServletRequest request) throws Exception;

	void addBulkSubActivity(HttpServletRequest request) throws Exception;

}
