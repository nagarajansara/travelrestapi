package travelrestapi.com.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.*;

import travelrestapi.com.model.Admin;
import travelrestapi.com.model.Trip;

@SuppressWarnings(
{ "unchecked", "unused" })
public class TripDetailsDAOImpl implements TripDetailsDAO
{
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	final String GET_TRIP_DETAILS =
			"SELECT t.id, t.title, t.duration, t.fromdate, u.email, t.todate, t.status FROM tripdetails t "
					+ "INNER JOIN users u "
					+ "ON u.id = t.userid WHERE t.status =:status  ORDER BY t.createdat DESC LIMIT :startIndx, :endIndx";

	final String GET_TRIP_DETAILS_ALL =
			"SELECT t.id, t.title, t.duration, t.fromdate, u.email, t.todate, t.status FROM tripdetails t "
					+ "INNER JOIN users u "
					+ "ON u.id = t.userid ORDER BY t.createdat DESC LIMIT :startIndx, :endIndx";

	final String GET_TRIP_DETAILS_ALL_NUMENTRIES =
			"SELECT count(*) as totaltrips FROM tripdetails t "
					+ "INNER JOIN users u " + "ON u.id = t.userid";

	final String GET_TRIP_DETAILS_NUMENTRIES =
			"SELECT count(*) as totaltrips FROM tripdetails t "
					+ "INNER JOIN users u "
					+ "ON u.id = t.userid WHERE t.status =:status";

	final String UPDATE_TRIP_DETAILS =
			"update tripdetails set status =:status where id =:id";
	final String GET_TRIP_SEARCH_KEY =
			"SELECT t.id, t.title, t.duration, t.fromdate, u.email, t.todate, t.status FROM tripdetails t "
					+ "INNER JOIN users u "
					+ "ON u.id = t.userid WHERE t.status =:status  AND (t.title LIKE :searchKey) ORDER BY t.createdat DESC LIMIT :startIndx, :endIndx";
	final String GET_TRIP_SEARCH_KEY_NUMENTRIES =
			"SELECT count(*) FROM tripdetails t "
					+ "INNER JOIN users u "
					+ "ON u.id = t.userid WHERE t.status =:status  AND (t.title LIKE :searchKey) ";
	final String GET_TRIP_DETAILS_BASED_ID =
			"SELECT t.id, t.title, t.fromdate, t.todate, t.route, "
					+ "t.price, t.guidelines, t.description, "
					+ "a.name, IFNULL (GROUP_CONCAT(DISTINCT ti.name), '') AS tripimages,"
					+ "IFNULL (GROUP_CONCAT(DISTINCT i.daywisedescription "
					+ "ORDER BY i.day SEPARATOR '@@'), '') AS daywisedescription "
					+ "FROM tripdetails t "
					+ "INNER JOIN activity a "
					+ "ON t.activityid = a.id "
					+ "INNER JOIN itenary i "
					+ "ON i.tripid = t.id "
					+ "INNER JOIN "
					+ "tripimages ti  "
					+ "ON t.id = ti.tripid "
					+ "WHERE t.id =:tripId "
					+ "GROUP BY t.title, t.fromdate, t.todate, t.route,  "
					+ "t.price, t.guidelines, t.description, t.description, t.id";

	@Override
	public List<Trip> getTripDetails(Trip trip) throws Exception
	{
		Map paramMap = new HashMap();
		paramMap.put("status", trip.getStatus());
		paramMap.put("startIndx", trip.getStartIndx());
		paramMap.put("endIndx", trip.getEndIndx());

		return namedParameterJdbcTemplate.query(GET_TRIP_DETAILS, paramMap,
				new BeanPropertyRowMapper(Trip.class));

	}

	@Override
	public List<Trip> getTripDetails(String status, int startIndx, int endInx)
			throws Exception
	{
		Map paramMap = new HashMap();
		paramMap.put("startIndx", startIndx);
		paramMap.put("endIndx", endInx);
		paramMap.put("status", status);

		return namedParameterJdbcTemplate.query(GET_TRIP_DETAILS, paramMap,
				new BeanPropertyRowMapper(Trip.class));
	}

	@Override
	public List<Trip> getTripDetails_ALL(int startIndx, int endInx)
			throws Exception
	{
		Map paramMap = new HashMap();
		paramMap.put("startIndx", startIndx);
		paramMap.put("endIndx", endInx);

		return namedParameterJdbcTemplate.query(GET_TRIP_DETAILS_ALL, paramMap,
				new BeanPropertyRowMapper(Trip.class));

	}

	@Override
	public int getTripDetails_ALL_numEntries() throws Exception
	{
		Map paramMap = new HashMap();
		return namedParameterJdbcTemplate.queryForInt(
				GET_TRIP_DETAILS_ALL_NUMENTRIES, paramMap);

	}

	@Override
	public int getTripDetails_Status_numEntries(String status) throws Exception
	{
		Map paramMap = new HashMap();
		paramMap.put("status", status);

		return namedParameterJdbcTemplate.queryForInt(
				GET_TRIP_DETAILS_NUMENTRIES, paramMap);

	}

	@Override
	public void updateTripdetails(String status, int id) throws Exception
	{
		Map paramMap = new HashMap();
		paramMap.put("status", status);
		paramMap.put("id", id);

		namedParameterJdbcTemplate.update(UPDATE_TRIP_DETAILS, paramMap);

	}

	@Override
	public List<Trip> getTripSearchKey(String searchKey, String status,
			int startIndx, int maxIndx) throws Exception
	{
		Map paramMap = new HashMap();
		paramMap.put("searchKey", searchKey + "%");
		paramMap.put("status", status);
		paramMap.put("startIndx", startIndx);
		paramMap.put("endIndx", maxIndx);

		return namedParameterJdbcTemplate.query(GET_TRIP_SEARCH_KEY, paramMap,
				new BeanPropertyRowMapper(Trip.class));

	}

	@Override
	public int getTripSearchKey_NumEntries(String searchKey, String status)
			throws Exception
	{
		Map paramMap = new HashMap();
		paramMap.put("searchKey", searchKey + "%");
		paramMap.put("status", status);

		return namedParameterJdbcTemplate.queryForInt(
				GET_TRIP_SEARCH_KEY_NUMENTRIES, paramMap);
	}

	@Override
	public List<Trip> getTripDetailsBasedId(int tripId) throws Exception
	{
		Map paramMap = new HashMap();
		paramMap.put("tripId", tripId);

		return namedParameterJdbcTemplate.query(GET_TRIP_DETAILS_BASED_ID,
				paramMap, new BeanPropertyRowMapper(Trip.class));
	}

}
