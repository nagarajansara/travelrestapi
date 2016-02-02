package travelrestapi.com.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

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


}
