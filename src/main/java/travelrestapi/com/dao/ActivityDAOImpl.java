package travelrestapi.com.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.deser.impl.BeanPropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import travelrestapi.com.model.Activity;

@SuppressWarnings(
{ "unchecked", "unused" })
public class ActivityDAOImpl implements ActivityDAO
{
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	final String ADD_ACTIVITY_NAME =
			"INSERT IGNORE into activity (name) values (:activityName)";

	final String GET_ALL_ACTIVITY =
			"Select * from activity ORDER BY createdat DESC LIMIT :startIndx, :endIndx";

	final String GET_ACTIVITY =
			"Select * from activity where status =:status  ORDER BY createdat DESC LIMIT :startIndx, :endIndx";

	final String GET_ALL_ACTIVITY_NUMENTRIES =
			"Select count(*) As totalactivity from activity";
	final String GET_ACTIVITY_NUMENTRIES =
			"Select count(*) As totalactivity from activity where status =:status";
	final String UPDATE_ACTIVITY_STATUS =
			"Update activity set status =:status where id =:id";
	final String GET_ACTIVITY_SEARCH_KEY =
			"Select * from activity where status =:status  AND name LIKE :searchKey ORDER BY createdat DESC LIMIT :startIndx, :endIndx";
	final String GET_ACTIVITY_SEARCH_KEY_ALL =
			"Select * from activity where name LIKE :searchKey ORDER BY createdat DESC LIMIT :startIndx, :endIndx";
	final String GET_ACTIVITY_SEARCH_KEY_NUMENTRIES =
			"Select count(*) from activity where status =:status  AND name LIKE :searchKey";
	final String GET_ACTIVITY_SEARCH_KEY_ALL_NUMENTRIES =
			"Select count(*) from activity where name LIKE :searchKey";

	public void addActivity(String activityName) throws Exception
	{
		Map paramMap = new HashMap();

		paramMap.put("activityName", activityName);
		namedParameterJdbcTemplate.update(ADD_ACTIVITY_NAME, paramMap);

	}

	@Override
	public List<Activity> getActivitys_All(int startIndx, int maxIndx)
			throws Exception
	{
		Map paramMap = new HashMap();
		paramMap.put("startIndx", startIndx);
		paramMap.put("endIndx", maxIndx);

		return namedParameterJdbcTemplate.query(GET_ALL_ACTIVITY, paramMap,
				new BeanPropertyRowMapper(Activity.class));

	}

	@Override
	public List<Activity> getActivity(String activityStatus, int startIndx,
			int endIndx) throws Exception
	{
		Map paramMap = new HashMap();
		paramMap.put("startIndx", startIndx);
		paramMap.put("endIndx", endIndx);
		paramMap.put("status", activityStatus);

		return namedParameterJdbcTemplate.query(GET_ACTIVITY, paramMap,
				new BeanPropertyRowMapper(Activity.class));

	}

	@Override
	public int getActivityAll_NumEntries() throws Exception
	{
		Map paramMap = new HashMap();

		return namedParameterJdbcTemplate.queryForInt(
				GET_ALL_ACTIVITY_NUMENTRIES, paramMap);
	}

	@Override
	public int getActivity_NumEntries(String status) throws Exception
	{
		Map paramMap = new HashMap();
		paramMap.put("status", status);

		return namedParameterJdbcTemplate.queryForInt(GET_ACTIVITY_NUMENTRIES,
				paramMap);
	}

	@Override
	public void updateActivityStatus(int activityId, String status)
			throws Exception
	{
		Map paramMap = new HashMap();
		paramMap.put("id", activityId);
		paramMap.put("status", status);

		namedParameterJdbcTemplate.update(UPDATE_ACTIVITY_STATUS, paramMap);
	}

	@Override
	public List<Activity> getActivitySearchKey(String searchKey, String status,
			int startIndx, int endIndx)
	{
		Map paramMap = new HashMap();
		paramMap.put("searchKey", searchKey + "%");
		paramMap.put("startIndx", startIndx);
		paramMap.put("endIndx", endIndx);

		if (status.equals(Activity.GET_ACTIVITY_STATUS_ALL))
		{

			return namedParameterJdbcTemplate.query(
					GET_ACTIVITY_SEARCH_KEY_ALL, paramMap,
					new BeanPropertyRowMapper(Activity.class));
		} else
		{
			paramMap.put("status", status);
			return namedParameterJdbcTemplate.query(GET_ACTIVITY_SEARCH_KEY,
					paramMap, new BeanPropertyRowMapper(Activity.class));
		}
	}

	public int getActivitySearchKeyNumEntries(String searchKey, String status)
			throws Exception
	{
		Map paramMap = new HashMap();
		paramMap.put("searchKey", searchKey + "%");
		if (status.equals(Activity.GET_ACTIVITY_STATUS_ALL))
		{
			return namedParameterJdbcTemplate.queryForInt(
					GET_ACTIVITY_SEARCH_KEY_ALL_NUMENTRIES, paramMap);
		} else
		{
			paramMap.put("status", status);
			return namedParameterJdbcTemplate.queryForInt(
					GET_ACTIVITY_SEARCH_KEY_NUMENTRIES, paramMap);
		}

	}

}
