package travelrestapi.com.dao;

import java.util.List;

import travelrestapi.com.model.Activity;
import travelrestapi.com.model.Admin;
import travelrestapi.com.model.AdminAuth;
import travelrestapi.com.model.Login;
import travelrestapi.com.model.Trip;
import travelrestapi.com.service.AdminService;

public interface AdminDAO
{

	List<Admin> getVendorList(int startIndx, int endIndx) throws Exception;

	List<Admin> getAdminLoginValidate(Admin admin) throws Exception;

	void setAuthToken(AdminAuth adminAuth) throws Exception;

	List<AdminAuth> getUserId_AuthToken(String authToken, String status);

	int getVendorListCount() throws Exception;

	void updateApprovedStatus(String userEmail, String approvedStatus)
			throws Exception;

	List<Admin> getUserBasedApprovedType(String approvedStatus, int startIndx,
			int endIndx) throws Exception;

	int getNumEntries_UserStatus(String approvedStatus) throws Exception;

	List<Admin> getVendorListSearch(String searchKey, String status,
			int startIndx, int maxIndx) throws Exception;

	int getVendorListSearchNumEntries(String searchKey, String status,
			int startIndx, int maxIndx) throws Exception;

	List<Login> getUserDetailsBasedEmail(String userEmail) throws Exception;

	void addMetaKeywords(String keywords, int tripId) throws Exception;

	void updateMetaKeywords(String keywords, int tripId) throws Exception;

	void addSubActivity(String subActivityName) throws Exception;

	void addBulkSubActivity(List<Activity> list) throws Exception;

	List<Activity> getSubActivity(String activityStatus, int startIndx,
			int maxIndx) throws Exception;

	int getSubActivity_NumEntries(String activityStatus) throws Exception;

	List<Activity> getSubActivity_All(int startIndx, int maxIndx)
			throws Exception;

	void updateSubActivityStatus(int activityId, String status)
			throws Exception;

	void addTopActivityList(int tripId) throws Exception;

	void deleteTopActivityList(int tripId) throws Exception;

	List<Trip> getTopActivity(int startIndx, int endIndx, String sTATUS_ACTIVE)
			throws Exception;

	int getTopActivityNumEntries(String sTATUS_ACTIVE) throws Exception;

}
