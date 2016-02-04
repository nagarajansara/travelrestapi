package travelrestapi.com.dao;

import java.util.*;

import travelrestapi.com.model.Login;
import travelrestapi.com.model.Trip;

public interface TripDetailsDAO
{

	List<Trip> getTripDetails(String status, int startIndx, int endInx)
			throws Exception;

	List<Trip> getTripDetails_ALL(int startIndx, int endInx) throws Exception;

	List<Trip> getTripDetails(Trip trip) throws Exception;

	int getTripDetails_ALL_numEntries() throws Exception;

	int getTripDetails_Status_numEntries(String status) throws Exception;

	void updateTripdetails(String status, int id) throws Exception;

	// void addActivity(String activityName) throws Exception;

	List<Trip> getTripSearchKey(String searchKey, String status, int startIndx,
			int maxIndx) throws Exception;

	int getTripSearchKey_NumEntries(String searchKey, String status)
			throws Exception;

	List<Trip> getTripDetailsBasedId(int tripId) throws Exception;

	

}
