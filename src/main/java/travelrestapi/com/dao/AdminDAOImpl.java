package travelrestapi.com.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;

import travelrestapi.com.model.Activity;
import travelrestapi.com.model.Admin;
import travelrestapi.com.model.AdminAuth;
import travelrestapi.com.model.Login;
import travelrestapi.com.model.Trip;
import travelrestapi.com.service.AdminService;

@SuppressWarnings(
{ "unchecked", "unused" })
public class AdminDAOImpl implements AdminDAO
{

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	final String GET_VENDOR_LIST =
			"SELECT email, firstname, lastname, isapproved from users where role =:role order by createdat DESC LIMIT :startIndx, :endIndx";
	final String GET_VENDOR_NUMENTRIES =
			"Select count(*) from users where role =:role";
	final String ADMIN_LOGIN_VALIDATE =
			"Select * from admin_user where name =:name and password =:password";
	final String ADD_AUTHTOKEN =
			"INSERT INTO admin_authtoken (userid, token) values (:userid, :token)";
	final String GET_ACCESSTOKEN =
			"SELECT * from admin_authtoken where token =:token and status =:status";
	final String UPDATE_APPROVED_STATUS =
			"update users set isapproved =:approvedStatus where email =:email";
	final String GET_USER_BASED_APPROVED_STATUS =
			"Select email, firstname, lastname, isapproved from users where role =:role AND isapproved =:isapproved LIMIT :startIndx, :endIndx";

	final String GET_NUMENTRIES_USERAPPROVED =
			"Select count(*) from users where isapproved =:isapproved AND role =:role";

	final String GET_VENDOR_LIST_SEARCH_KEY =
			"SELECT email, firstname, lastname, isapproved from users where (email  LIKE :searchKey) "
					+ "AND role =:role AND isapproved =:status order by createdat DESC LIMIT :startIndx, :endIndx";
	final String GET_VENDOR_LIST_SEARCH_KEY_ALL =
			"SELECT email, firstname, lastname, isapproved from users where (email  LIKE :searchKey) "
					+ "AND role =:role  order by createdat DESC LIMIT :startIndx, :endIndx";
	final String GET_VENDOR_LIST_SEARCH_KEY_NUMENTRIES =
			"SELECT count(*)  from users where (email  LIKE :searchKey) "
					+ "AND role =:role AND isapproved =:status";
	final String GET_VENDOR_LIST_SEARCH_KEY_NUMENTRIES_ALL =
			"SELECT count(*)  from users where (email  LIKE :searchKey) "
					+ "AND role =:role";
	final String GET_USER_DETAILS_BASED_EMAIL =
			"SELECT email, firstname, lastname, address, phoneno, pincode, state, "
					+ "	pancardno, mobile, organizationname, role, credits from users where email =:email";
	final String ADD_META_TAG =
			"INSERT into metatag (keywords, tripid) values (:keywords, :tripid)";
	final String UPDATE_META_TAG =
			"UPDATE metatag set keywords =:keywords where tripid =:tripid";
	final String ADD_SUBACTIVITY =
			"INSERT INTO subactivity (name) values (:name)";
	final String ADD_BULK_SUB_ACTIVITY =
			"INSERT IGNORE INTO subactivity (name) values (:name)";
	final String GET_SUB_ACTIVITY =
			"SELECT id, name, status from subactivity where status =:status LIMIT :startIndx, :endIndx";
	final String GET_SUB_ACTIVITY_NUMENTRIES =
			"SELECT count(*) from subactivity where status =:status";

	final String GET_SUB_ACTIVITY_ALL =
			"SELECT id, name, status from subactivity where LIMIT :startIndx, :endIndx";
	final String UPDATE_SUB_ACTIVITY_STATUS =
			"Update subactivity set status =:status where id =:id";

	final String ADD_TOP_ACTIVITY_LIST =
			"INSERT INTO admin_topactivity (tripid) values (:tripId)";
	final String DELETE_TOP_ACTIVITY_LIST =
			"DELETE from admin_topactivity where tripid =:tripId";

	final String GET_TOP_ACTIVITYS =
			"SELECT t.id, t.title, t.duration, t.fromdate, u.email, t.todate, t.status "
					+ "FROM admin_topactivity a " + "INNER JOIN tripdetails t "
					+ "ON a.tripid = t.id " + "INNER JOIN users u "
					+ "ON u.id = t.userid "
					+ "WHERE t.status =:status LIMIT :startIndx, :endIndx";
	final String GET_TOP_ACTIVITYS_NUMENTRIES = "SELECT count(*)  "
			+ "FROM admin_topactivity a " + "INNER JOIN tripdetails t "
			+ "ON a.tripid = t.id " + "INNER JOIN users u "
			+ "ON u.id = t.userid " + "WHERE t.status =:status";

	final String STATUS_ACTIVE = "active";
	final String ROLE_VENDOR = "ROLE_VENDOR";

	@Override
	public List<Admin> getVendorList(int startIndx, int endIndx)
			throws Exception
	{
		Map paramMap = new HashMap();
		paramMap.put("startIndx", startIndx);
		paramMap.put("endIndx", endIndx);
		paramMap.put("role", ROLE_VENDOR);

		return namedParameterJdbcTemplate.query(GET_VENDOR_LIST, paramMap,
				new BeanPropertyRowMapper(Login.class));

	}

	@Override
	public List<Admin> getAdminLoginValidate(Admin admin) throws Exception
	{
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("name", admin.getName());
		paramMap.put("password", admin.getPassword());

		return namedParameterJdbcTemplate.query(ADMIN_LOGIN_VALIDATE, paramMap,
				new BeanPropertyRowMapper(Admin.class));
	}

	@Override
	public void setAuthToken(AdminAuth adminAuth) throws Exception
	{
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userid", adminAuth.getUserid());
		paramMap.put("token", adminAuth.getToken());

		namedParameterJdbcTemplate.update(ADD_AUTHTOKEN, paramMap);
	}

	@Override
	public List<AdminAuth> getUserId_AuthToken(String authToken, String status)
	{
		Map paramMap = new HashMap();
		paramMap.put("token", authToken);
		paramMap.put("status", status);

		return namedParameterJdbcTemplate.query(GET_ACCESSTOKEN, paramMap,
				new BeanPropertyRowMapper(AdminAuth.class));
	}

	@Override
	public int getVendorListCount() throws Exception
	{
		Map paramMap = new HashMap();
		paramMap.put("role", ROLE_VENDOR);
		return namedParameterJdbcTemplate.queryForInt(GET_VENDOR_NUMENTRIES,
				paramMap);
	}

	@Override
	public void updateApprovedStatus(String userEmail, String approvedStatus)
	{
		Map paramMap = new HashMap();
		paramMap.put("email", userEmail);
		paramMap.put("approvedStatus", approvedStatus);

		namedParameterJdbcTemplate.update(UPDATE_APPROVED_STATUS, paramMap);

	}

	@Override
	public List<Admin> getUserBasedApprovedType(String approvedStatus,
			int startIndx, int endIndx) throws Exception
	{
		Map paramMap = new HashMap();
		paramMap.put("isapproved", approvedStatus);
		paramMap.put("startIndx", startIndx);
		paramMap.put("endIndx", endIndx);
		paramMap.put("role", ROLE_VENDOR);

		return namedParameterJdbcTemplate.query(GET_USER_BASED_APPROVED_STATUS,
				paramMap, new BeanPropertyRowMapper(Login.class));
	}

	@Override
	public int getNumEntries_UserStatus(String approvedStatus) throws Exception
	{
		Map paramMap = new HashMap();
		paramMap.put("role", ROLE_VENDOR);
		paramMap.put("isapproved", approvedStatus);

		return namedParameterJdbcTemplate.queryForInt(
				GET_NUMENTRIES_USERAPPROVED, paramMap);
	}

	@Override
	public List<Admin> getVendorListSearch(String searchKey, String status,
			int startIndx, int maxIndx) throws Exception
	{
		Map paramMap = new HashMap();
		paramMap.put("searchKey", searchKey + "%");
		paramMap.put("role", ROLE_VENDOR);
		paramMap.put("startIndx", startIndx);
		paramMap.put("endIndx", maxIndx);

		if (status.equals(Login.APPROVED_STATUS_ALL))
		{
			return namedParameterJdbcTemplate.query(
					GET_VENDOR_LIST_SEARCH_KEY_ALL, paramMap,
					new BeanPropertyRowMapper(Login.class));
		} else
		{
			paramMap.put("status", status);
			return namedParameterJdbcTemplate.query(GET_VENDOR_LIST_SEARCH_KEY,
					paramMap, new BeanPropertyRowMapper(Login.class));
		}

	}

	@Override
	public int getVendorListSearchNumEntries(String searchKey, String status,
			int startIndx, int maxIndx) throws Exception
	{
		Map paramMap = new HashMap();
		paramMap.put("searchKey", searchKey);
		paramMap.put("role", ROLE_VENDOR);
		paramMap.put("startIndx", startIndx);
		paramMap.put("endIndx", maxIndx);

		if (status.equals(Login.APPROVED_STATUS_ALL))
		{

			return namedParameterJdbcTemplate.queryForInt(
					GET_VENDOR_LIST_SEARCH_KEY_NUMENTRIES_ALL, paramMap);
		} else
		{
			paramMap.put(status, status);
			return namedParameterJdbcTemplate.queryForInt(
					GET_VENDOR_LIST_SEARCH_KEY_NUMENTRIES, paramMap);
		}

	}

	@Override
	public List<Login> getUserDetailsBasedEmail(String userEmail)
			throws Exception
	{
		Map paramMap = new HashMap();
		paramMap.put("email", userEmail);

		return namedParameterJdbcTemplate.query(GET_USER_DETAILS_BASED_EMAIL,
				paramMap, new BeanPropertyRowMapper(Login.class));

	}

	public void addMetaKeywords(String keywords, int tripId) throws Exception
	{
		Map paramMap = new HashMap();
		paramMap.put("keywords", keywords);
		paramMap.put("tripid", tripId);
		namedParameterJdbcTemplate.update(ADD_META_TAG, paramMap);
	}

	public void updateMetaKeywords(String keywords, int tripId)
			throws Exception
	{
		Map paramMap = new HashMap();
		paramMap.put("keywords", keywords);
		paramMap.put("tripid", tripId);
		namedParameterJdbcTemplate.update(UPDATE_META_TAG, paramMap);
	}

	@Override
	public void addSubActivity(String subActivityName) throws Exception
	{
		Map paramMap = new HashMap();
		paramMap.put("name", subActivityName);

		namedParameterJdbcTemplate.update(ADD_SUBACTIVITY, paramMap);

	}

	@Override
	public void addBulkSubActivity(List<Activity> list) throws Exception
	{
		SqlParameterSource[] params =
				SqlParameterSourceUtils.createBatch(list.toArray());

		namedParameterJdbcTemplate.batchUpdate(ADD_BULK_SUB_ACTIVITY, params);

	}

	@Override
	public List<Activity> getSubActivity(String activityStatus, int startIndx,
			int maxIndx) throws Exception
	{
		Map paramMap = new HashMap();
		paramMap.put("status", activityStatus);
		paramMap.put("startIndx", startIndx);
		paramMap.put("endIndx", maxIndx);

		return namedParameterJdbcTemplate.query(GET_SUB_ACTIVITY, paramMap,
				new BeanPropertyRowMapper(Activity.class));
	}

	@Override
	public int getSubActivity_NumEntries(String activityStatus)
			throws Exception
	{
		Map paramMap = new HashMap();
		paramMap.put("status", activityStatus);

		return namedParameterJdbcTemplate.queryForInt(
				GET_SUB_ACTIVITY_NUMENTRIES, paramMap);

	}

	@Override
	public List<Activity> getSubActivity_All(int startIndx, int maxIndx)
			throws Exception
	{
		Map paramMap = new HashMap();
		paramMap.put("startIndx", startIndx);
		paramMap.put("endIndx", maxIndx);
		return namedParameterJdbcTemplate.query(GET_SUB_ACTIVITY_ALL, paramMap,
				new BeanPropertyRowMapper(Activity.class));
	}

	@Override
	public void updateSubActivityStatus(int activityId, String status)
			throws Exception
	{
		Map paramMap = new HashMap();
		paramMap.put("id", activityId);
		paramMap.put("status", status);
		namedParameterJdbcTemplate.update(UPDATE_SUB_ACTIVITY_STATUS, paramMap);
	}

	@Override
	public void addTopActivityList(int tripId) throws Exception
	{
		Map paramMap = new HashMap();
		paramMap.put("tripId", tripId);
		namedParameterJdbcTemplate.update(ADD_TOP_ACTIVITY_LIST, paramMap);

	}

	public void deleteTopActivityList(int tripId) throws Exception
	{
		Map paramMap = new HashMap();
		paramMap.put("tripId", tripId);
		namedParameterJdbcTemplate.update(DELETE_TOP_ACTIVITY_LIST, paramMap);

	}

	@Override
	public List<Trip> getTopActivity(int startIndx, int endIndx,
			String sTATUS_ACTIVE) throws Exception
	{
		Map paramMap = new HashMap();
		paramMap.put("startIndx", startIndx);
		paramMap.put("endIndx", endIndx);
		paramMap.put("status", sTATUS_ACTIVE);

		return namedParameterJdbcTemplate.query(GET_TOP_ACTIVITYS, paramMap,
				new BeanPropertyRowMapper(Trip.class));
	}

	@Override
	public int getTopActivityNumEntries(String sTATUS_ACTIVE) throws Exception
	{
		Map paramMap = new HashMap();
		paramMap.put("status", sTATUS_ACTIVE);
		return namedParameterJdbcTemplate.queryForInt(
				GET_TOP_ACTIVITYS_NUMENTRIES, paramMap);
	}

}
