package travelrestapi.com.dao;

import java.util.List;

import travelrestapi.com.model.Activity;

public interface ActivityDAO
{
	void addActivity(String activityName) throws Exception;

	List<Activity> getActivitys_All(int startIndx, int maxIndx)
			throws Exception;

			List<Activity> getActivity(String activityStatus, int startIndx,
					int endIndx) throws Exception;

	int getActivityAll_NumEntries() throws Exception;

	int getActivity_NumEntries(String status) throws Exception;

	void updateActivityStatus(int activityId, String status) throws Exception;

	List<Activity> getActivitySearchKey(String searchKey, String status,
			int startIndx, int endIndx);

	int getActivitySearchKeyNumEntries(String searchKey, String status)
			throws Exception;

	void addBulkActivity(List<Activity> list) throws Exception;
}
