package travelrestapi.com.controller;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.*;
import org.hibernate.HibernateException;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.json.simple.parser.JSONParser;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.*;
import java.net.*;
import java.text.*;
import java.util.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import travelrestapi.com.service.*;
import travelrestapi.com.JMS.JMSProducer;
import travelrestapi.com.dao.*;
import travelrestapi.com.model.*;
import travelrestapi.com.util.*;

@RestController
@RequestMapping("/admin")
@SuppressWarnings(
{ "unused", "unchecked" })
public class AdminController extends BaseController
{
	private static final Logger logger = Logger.getLogger(AdminController.class
			.getName());

	@Autowired
	@Qualifier("adminService")
	AdminService adminService;

	@Autowired
	@Qualifier("utilities")
	Utilities utilities;

	@Autowired
	@Qualifier("response")
	Response response;

	@Autowired
	@Qualifier("appProp")
	AppProp appProp;

	@Autowired
	@Qualifier("tripDetailsDAO")
	TripDetailsDAO tripDetailsDAO;

	@RequestMapping(value = "/getVendorList/{authToken}", method =
	{ RequestMethod.GET, RequestMethod.POST })
	public Response getVendorList(HttpServletRequest request,
			@PathVariable String authToken, HttpServletResponse res,
			ModelMap model)
	{
		try
		{
			int startIndx = utilities.getDefaultMinIndx();
			int endIndx = utilities.getDefaultMaxIndx();
			int numEntries = 0;

			int userId = getUserId(authToken);
			List<Admin> list = adminService.getVendorList(startIndx, endIndx);
			numEntries = adminService.getVendorListCount();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("vendorList", list);
			map.put("numEntries", numEntries);
			utilities.setSuccessResponse(response, map);
		} catch (Exception ex)
		{
			logger.error("getVendorList :" + ex.getMessage());
			utilities.setErrResponse(ex, response);
		}
		model.addAttribute("model", response);
		return response;
	}

	@RequestMapping(value = "/getVendorListpagination/{startIndx}/{authToken}",
			method =
			{ RequestMethod.GET, RequestMethod.POST })
	public Response getVendorListPagination(HttpServletRequest request,
			@PathVariable int startIndx, @PathVariable String authToken,
			HttpServletResponse res, ModelMap model)
	{
		try
		{
			startIndx = startIndx - 1;
			startIndx = getStartIdx(startIndx, utilities.getDefaultMaxIndx());
			int endIndx = utilities.getDefaultMaxIndx();
			int numEntries = 0;

			int userId = getUserId(authToken);
			List<Admin> list = adminService.getVendorList(startIndx, endIndx);
			numEntries = adminService.getVendorListCount();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("vendorList", list);
			map.put("numEntries", numEntries);
			utilities.setSuccessResponse(response, map);
		} catch (Exception ex)
		{
			logger.error("getVendorListPagination :" + ex.getMessage());
			utilities.setErrResponse(ex, response);
		}
		model.addAttribute("model", response);
		return response;
	}

	@RequestMapping(value = "/sessionfailure", method =
	{ RequestMethod.GET, RequestMethod.POST })
	public Response sessionfailure(HttpServletRequest request,
			HttpServletResponse res, ModelMap model)
	{
		try
		{
			utilities.setSuccessResponse(response);
			throw new ConstException(ConstException.ERR_CODE_INVALID_LOGIN,
					ConstException.ERR_MSG_INVALID_LOGIN);
		} catch (Exception ex)
		{
			logger.error("getAdminLoginStatus :" + ex.getMessage());
			utilities.setErrResponse(ex, response);
		}
		model.addAttribute("model", response);
		return response;
	}

	@RequestMapping(value = "/getAdminLoginValidate/{userName}/{password}",
			method =
			{ RequestMethod.GET, RequestMethod.POST })
	public Response getAdminLoginValidate(@PathVariable String userName,
			@PathVariable String password, HttpServletRequest request,
			HttpServletResponse res, ModelMap model)
	{
		try
		{

			String STATUS_ACTIVE = "active";
			Admin admin = new Admin(userName, password, STATUS_ACTIVE);
			List<Admin> list = adminService.getAdminLoginValidate(admin);
			Map<String, Object> map = new HashMap<String, Object>();
			if (list != null && list.size() > 0)
			{
				Admin adminList = (Admin) list.get(0);
				UUID uniqueKey = UUID.randomUUID();
				AdminAuth adminAuth =
						new AdminAuth(uniqueKey.toString(), adminList.getId(),
								STATUS_ACTIVE);
				adminService.setAuthToken(adminAuth);

				map.put("userId", adminList.getId());
				map.put("authToken", uniqueKey.toString());

				utilities.setSuccessResponse(response, map);
			} else
			{
				throw new ConstException(ConstException.ERR_CODE_INVALID_LOGIN,
						ConstException.ERR_MSG_INVALID_LOGIN);
			}

		} catch (Exception ex)
		{
			logger.error("getAdminLoginValidate :" + ex.getMessage());
			utilities.setErrResponse(ex, response);
		}
		model.addAttribute("model", response);
		return response;
	}

	@RequestMapping(value = "/getAdminLoginStatus/{authToken}", method =
	{ RequestMethod.GET, RequestMethod.POST })
	public
			Response getAdminLoginStatus(@PathVariable String userName,
					HttpServletRequest request, HttpServletResponse res,
					ModelMap model)
	{
		try
		{
			String email = getSessionAttr(request, ATTR_EMAIL);
			if (email != null && email.equals(userName))
			{
				utilities.setSuccessResponse(response);
			} else
			{
				throw new ConstException(ConstException.ERR_CODE_INVALID_LOGIN,
						ConstException.ERR_MSG_INVALID_LOGIN);
			}

		} catch (Exception ex)
		{
			logger.error("getAdminLoginStatus :" + ex.getMessage());
			utilities.setErrResponse(ex, response);
		}
		model.addAttribute("model", response);
		return response;
	}

	@RequestMapping(value = "/updateApprovedStatus/{email}/{approvedStatus}",
			method =
			{ RequestMethod.GET, RequestMethod.POST })
	public Response updateApprovedStatus(@PathVariable String email,
			@PathVariable String approvedStatus, HttpServletRequest request,
			HttpServletResponse res, ModelMap model)
	{
		try
		{
			adminService.updateApprovedStatus(email, approvedStatus);
			if (approvedStatus.equals("yes"))
			{
				// Send Mail
				utilities.setJMS_Enqueued(email, "Verified successfully",
						email.split("@")[0], "Activation Mail",
						JMSProducer.EMAIL_QUEUE);
			}

			utilities.setSuccessResponse(response);
		} catch (Exception ex)
		{
			logger.error("updateApprovedStatus :" + ex.getMessage());
			utilities.setErrResponse(ex, response);
		}
		model.addAttribute("model", response);
		return response;
	}

	@RequestMapping(value = "/getUserBasedApprovedType/{approvedStatus}",
			method =
			{ RequestMethod.GET, RequestMethod.POST })
	public Response getUserBasedApprovedType(
			@PathVariable String approvedStatus, HttpServletRequest request,
			HttpServletResponse res, ModelMap model)
	{
		try
		{
			Map<String, Object> map = new HashMap<String, Object>();
			List<Admin> list = null;
			int numEntries = 0;
			int startIndx = utilities.getDefaultMinIndx();
			int endIndx = utilities.getDefaultMaxIndx();
			if (approvedStatus.equals(Login.APPROVED_STATUS_APPROVED))
			{
				approvedStatus = Login.APPROVED_STATUS_YES;
				list =
						adminService.getUserBasedApprovedType(approvedStatus,
								startIndx, endIndx);
				numEntries =
						adminService.getNumEntries_UserStatus(approvedStatus);
				map.put("vendorList", list);
				map.put("numEntries", numEntries);
			}
			if (approvedStatus.equals(Login.APPROVED_STATUS_NONAPPROVED))
			{
				approvedStatus = Login.APPROVED_STATUS_NO;
				list =
						adminService.getUserBasedApprovedType(approvedStatus,
								startIndx, endIndx);
				numEntries =
						adminService.getNumEntries_UserStatus(approvedStatus);
				map.put("vendorList", list);
				map.put("numEntries", numEntries);
			}
			if (approvedStatus.equals(Login.APPROVED_STATUS_ALL))
			{
				list = adminService.getVendorList(startIndx, endIndx);
				numEntries = adminService.getVendorListCount();
				map.put("vendorList", list);
				map.put("numEntries", numEntries);
			}

			utilities.setSuccessResponse(response, map);

		} catch (Exception ex)
		{
			logger.error("getUserBasedApprovedType :" + ex.getMessage());
			utilities.setErrResponse(ex, response);
		}
		model.addAttribute("model", response);
		return response;
	}

	@RequestMapping(
			value = "/getUserBasedApprovedTypePagination/{approvedStatus}/{startIndx}",
			method =
			{ RequestMethod.GET, RequestMethod.POST })
	public
			Response getUserBasedApprovedTypePagination(
					@PathVariable String approvedStatus,
					@PathVariable int startIndx, HttpServletRequest request,
					HttpServletResponse res, ModelMap model)
	{
		try
		{
			Map<String, Object> map = new HashMap<String, Object>();
			List<Admin> list = null;
			int numEntries = 0;
			startIndx = startIndx - 1;
			startIndx = getStartIdx(startIndx, utilities.getDefaultMaxIndx());
			int endIndx = utilities.getDefaultMaxIndx();
			if (approvedStatus.equals(Login.APPROVED_STATUS_APPROVED))
			{
				approvedStatus = Login.APPROVED_STATUS_YES;
				list =
						adminService.getUserBasedApprovedType(approvedStatus,
								startIndx, endIndx);
				numEntries =
						adminService.getNumEntries_UserStatus(approvedStatus);
				map.put("vendorList", list);
				map.put("numEntries", numEntries);
			}
			if (approvedStatus.equals(Login.APPROVED_STATUS_NONAPPROVED))
			{
				approvedStatus = Login.APPROVED_STATUS_NO;
				list =
						adminService.getUserBasedApprovedType(approvedStatus,
								startIndx, endIndx);
				numEntries =
						adminService.getNumEntries_UserStatus(approvedStatus);
				map.put("vendorList", list);
				map.put("numEntries", numEntries);
			}
			if (approvedStatus.equals(Login.APPROVED_STATUS_ALL))
			{
				list = adminService.getVendorList(startIndx, endIndx);
				numEntries = adminService.getVendorListCount();
				map.put("vendorList", list);
				map.put("numEntries", numEntries);
			}

			utilities.setSuccessResponse(response, map);

		} catch (Exception ex)
		{
			logger.error("getUserBasedApprovedType :" + ex.getMessage());
			utilities.setErrResponse(ex, response);
		}
		model.addAttribute("model", response);
		return response;
	}

	@RequestMapping(value = "/getTripDetails/{tripStatus}/{authToken}", method =
	{ RequestMethod.GET, RequestMethod.POST })
	public Response getTripDetails(@PathVariable String authToken,
			@PathVariable String tripStatus, HttpServletRequest request,
			HttpServletResponse res, ModelMap model)
	{
		try
		{
			int startIndx = utilities.getDefaultMinIndx();
			int endIndx = utilities.getDefaultMaxIndx();
			int numEntries = 0;
			String STATUS_ACTIVE = "active";
			List<Trip> list = null;

			Map<String, Object> map = new HashMap<String, Object>();

			int userId = getUserId(authToken);

			if (Trip.STATUS_TYPE_ALL.equals(tripStatus))
			{
				list = adminService.getTripDetails_ALL(startIndx, endIndx);
				numEntries = adminService.getTripDetails_ALL_numEntries();
			} else if (tripStatus.equals("topActivity"))
			{
				list =
						adminService.getTopActivity(startIndx, endIndx,
								STATUS_ACTIVE);
				numEntries =
						adminService.getTopActivityNumEntries(STATUS_ACTIVE);
			} else
			{
				list =
						adminService.getTripDetails(tripStatus, startIndx,
								endIndx);
				numEntries =
						adminService
								.getTripDetails_Status_numEntries(tripStatus);
			}
			map.put("tripDetails", list);
			map.put("numEntries", numEntries);

			utilities.setSuccessResponse(response, map);

		} catch (Exception ex)
		{
			logger.error("getTripDetails :" + ex.getMessage());
			utilities.setErrResponse(ex, response);
		}
		model.addAttribute("model", response);
		return response;
	}

	@RequestMapping(
			value = "/getTripDetailsPagination/{tripStatus}/{startIndx}/{authToken}",
			method =
			{ RequestMethod.GET, RequestMethod.POST })
	public
			Response getTripDetailsPagination(@PathVariable String authToken,
					@PathVariable String tripStatus,
					@PathVariable int startIndx, HttpServletRequest request,
					HttpServletResponse res, ModelMap model)
	{
		try
		{
			startIndx = startIndx - 1;
			startIndx = getStartIdx(startIndx, utilities.getDefaultMaxIndx());
			int endIndx = utilities.getDefaultMaxIndx();
			int numEntries = 0;
			List<Trip> list = null;

			Map<String, Object> map = new HashMap<String, Object>();

			int userId = getUserId(authToken);

			if (Trip.STATUS_TYPE_ALL.equals(tripStatus))
			{
				list = adminService.getTripDetails_ALL(startIndx, endIndx);
				numEntries = adminService.getTripDetails_ALL_numEntries();
			} else
			{
				list =
						adminService.getTripDetails(tripStatus, startIndx,
								endIndx);
				numEntries =
						adminService
								.getTripDetails_Status_numEntries(tripStatus);
			}
			map.put("tripDetails", list);
			map.put("numEntries", numEntries);

			utilities.setSuccessResponse(response, map);

		} catch (Exception ex)
		{
			logger.error("getTripDetailsPagination :" + ex.getMessage());
			utilities.setErrResponse(ex, response);
		}
		model.addAttribute("model", response);
		return response;

	}

	@RequestMapping(
			value = "/updateTripdetails/{tripStatus}/{tripId}/{authToken}",
			method =
			{ RequestMethod.GET, RequestMethod.POST })
	public
			Response updateTripdetails(@PathVariable String authToken,
					@PathVariable String tripStatus, @PathVariable int tripId,
					HttpServletRequest request, HttpServletResponse res,
					ModelMap model)
	{
		try
		{
			if (tripStatus.equals("true"))
			{
				adminService.addTopActivityList(tripId);

			} else if (tripStatus.equals("false"))
			{
				adminService.deleteTopActivityList(tripId);
			} else
			{
				adminService.updateTripdetails(tripStatus, tripId);
			}
			int userId = getUserId(authToken);
			utilities.setSuccessResponse(response);
		} catch (Exception ex)
		{
			logger.error("updateTripdetails :" + ex.getMessage());
			utilities.setErrResponse(ex, response);
		}
		model.addAttribute("model", response);
		return response;
	}

	@RequestMapping(value = "/addActivity/{activityName}/{authToken}", method =
	{ RequestMethod.GET, RequestMethod.POST })
	public Response addActivity(@PathVariable String authToken,
			@PathVariable String activityName, HttpServletRequest request,
			HttpServletResponse res, ModelMap model)
	{
		try
		{
			int userId = getUserId(authToken);
			adminService.addActivity(activityName);
			utilities.setSuccessResponse(response);
		} catch (Exception ex)
		{
			logger.error("addActivity" + ex.getMessage());
			utilities.setErrResponse(ex, response);
		}
		model.addAttribute("model", response);
		return response;
	}

	@RequestMapping(value = "/addBulkActivity", method =
	{ RequestMethod.GET, RequestMethod.POST })
	public Response addBulkActivity(HttpServletRequest request,
			HttpServletResponse res, ModelMap model)
	{
		try
		{
			adminService.addBulkActivity(request);
			utilities.setSuccessResponse(response);
		} catch (Exception ex)
		{
			logger.error("addBulkActivity" + ex.getMessage());
			utilities.setErrResponse(ex, response);
		}
		model.addAttribute("model", response);
		return response;
	}

	@RequestMapping(value = "/addBulkSubActivity", method =
	{ RequestMethod.GET, RequestMethod.POST })
	public Response addBulkSubActivity(HttpServletRequest request,
			HttpServletResponse res, ModelMap model)
	{
		try
		{
			adminService.addBulkSubActivity(request);
			utilities.setSuccessResponse(response);
		} catch (Exception ex)
		{
			logger.error("addBulkSubActivity" + ex.getMessage());
			utilities.setErrResponse(ex, response);
		}
		model.addAttribute("model", response);
		return response;
	}

	@RequestMapping(value = "/getActivitys/{activityStatus}/{authToken}",
			method =
			{ RequestMethod.GET, RequestMethod.POST })
	public Response getActivitys(@PathVariable String authToken,
			@PathVariable String activityStatus, HttpServletRequest request,
			HttpServletResponse res, ModelMap model)
	{
		try
		{
			int userId = getUserId(authToken);
			Map<String, Object> map = new HashMap<String, Object>();
			int numEntries = 0;
			List<Activity> list = null;
			int startIndx = utilities.getDefaultMinIndx();
			int maxIndx = utilities.getDefaultMaxIndx();

			{
				list =
						adminService.getActivity(activityStatus, startIndx,
								maxIndx);
				numEntries =
						adminService.getActivity_NumEntries(activityStatus);
			}
			map.put("activityDetails", list);
			map.put("numEntries", numEntries);

			utilities.setSuccessResponse(response, map);
		} catch (Exception ex)
		{
			logger.error("addActivity" + ex.getMessage());
			utilities.setErrResponse(ex, response);
		}
		model.addAttribute("model", response);
		return response;
	}

	@RequestMapping(
			value = "/getActivitysPagination/{activityStatus}/{startIndx}/{authToken}",
			method =
			{ RequestMethod.GET, RequestMethod.POST })
	public
			Response getActivitysPagination(@PathVariable String authToken,
					@PathVariable String activityStatus,
					@PathVariable int startIndx, HttpServletRequest request,
					HttpServletResponse res, ModelMap model)
	{
		try
		{
			int userId = getUserId(authToken);
			Map<String, Object> map = new HashMap<String, Object>();
			int numEntries = 0;
			List<Activity> list = null;
			startIndx = startIndx - 1;
			startIndx = getStartIdx(startIndx, utilities.getDefaultMaxIndx());
			int maxIndx = utilities.getDefaultMaxIndx();

			if (Activity.GET_ACTIVITY_STATUS_ALL.equals(activityStatus))
			{
				list = adminService.getActivitys_All(startIndx, maxIndx);
				numEntries = adminService.getTripDetails_ALL_numEntries();
			} else
			{
				list =
						adminService.getActivity(activityStatus, startIndx,
								maxIndx);
				numEntries =
						adminService.getActivity_NumEntries(activityStatus);
			}
			map.put("activityDetails", list);
			map.put("numEntries", numEntries);

			utilities.setSuccessResponse(response, map);
		} catch (Exception ex)
		{
			logger.error("getActivitysPagination " + ex.getMessage());
			utilities.setErrResponse(ex, response);
		}
		model.addAttribute("model", response);
		return response;
	}

	@RequestMapping(value = "/getSubActivitys/{activityStatus}/{authToken}",
			method =
			{ RequestMethod.GET, RequestMethod.POST })
	public Response getSubActivitys(@PathVariable String authToken,
			@PathVariable String activityStatus, HttpServletRequest request,
			HttpServletResponse res, ModelMap model)
	{
		try
		{
			int userId = getUserId(authToken);
			Map<String, Object> map = new HashMap<String, Object>();
			int numEntries = 0;
			List<Activity> list = null;
			int startIndx = utilities.getDefaultMinIndx();
			int maxIndx = utilities.getDefaultMaxIndx();

			{
				list =
						adminService.getSubActivity(activityStatus, startIndx,
								maxIndx);
				numEntries =
						adminService.getSubActivity_NumEntries(activityStatus);
			}
			map.put("subActivityDetails", list);
			map.put("numEntries", numEntries);

			utilities.setSuccessResponse(response, map);
		} catch (Exception ex)
		{
			logger.error("getSubActivitys" + ex.getMessage());
			utilities.setErrResponse(ex, response);
		}
		model.addAttribute("model", response);
		return response;
	}

	@RequestMapping(
			value = "/updateSubActivityStatus/{activityId}/{status}/{authToken}",
			method =
			{ RequestMethod.GET, RequestMethod.POST })
	public
			Response updateSubActivityStatus(@PathVariable String authToken,
					@PathVariable int activityId, @PathVariable String status,
					HttpServletRequest request, HttpServletResponse res,
					ModelMap model)
	{
		try
		{
			int userId = getUserId(authToken);
			adminService.updateSubActivityStatus(activityId, status);
			utilities.setSuccessResponse(response);

		} catch (Exception ex)
		{
			logger.error("updateSubActivityStatus :" + ex.getMessage());
			utilities.setErrResponse(ex, response);
		}
		model.addAttribute("model", response);
		return response;
	}

	@RequestMapping(
			value = "/getSubActivitysPagination/{activityStatus}/{startIndx}/{authToken}",
			method =
			{ RequestMethod.GET, RequestMethod.POST })
	public
			Response getSubActivitysPagination(@PathVariable String authToken,
					@PathVariable String activityStatus,
					@PathVariable int startIndx, HttpServletRequest request,
					HttpServletResponse res, ModelMap model)
	{
		try
		{
			int userId = getUserId(authToken);
			Map<String, Object> map = new HashMap<String, Object>();
			int numEntries = 0;
			List<Activity> list = null;
			startIndx = startIndx - 1;
			startIndx = getStartIdx(startIndx, utilities.getDefaultMaxIndx());
			int maxIndx = utilities.getDefaultMaxIndx();

			{
				list =
						adminService.getSubActivity(activityStatus, startIndx,
								maxIndx);
				numEntries =
						adminService.getSubActivity_NumEntries(activityStatus);
			}
			map.put("subActivityDetails", list);
			map.put("numEntries", numEntries);

			utilities.setSuccessResponse(response, map);
		} catch (Exception ex)
		{
			logger.error("getSubActivitysPagination " + ex.getMessage());
			utilities.setErrResponse(ex, response);
		}
		model.addAttribute("model", response);
		return response;
	}

	@RequestMapping(
			value = "/updateActivityStatus/{activityId}/{status}/{authToken}",
			method =
			{ RequestMethod.GET, RequestMethod.POST })
	public
			Response updateActivityStatus(@PathVariable String authToken,
					@PathVariable int activityId, @PathVariable String status,
					HttpServletRequest request, HttpServletResponse res,
					ModelMap model)
	{
		try
		{
			int userId = getUserId(authToken);
			adminService.updateActivityStatus(activityId, status);
			utilities.setSuccessResponse(response);

		} catch (Exception ex)
		{
			logger.error("");
			utilities.setErrResponse(ex, response);
		}
		model.addAttribute("model", response);
		return response;
	}

	@RequestMapping(
			value = "/getVendorListSearch/{status}/{searchKey}/{authToken}",
			method =
			{ RequestMethod.GET, RequestMethod.POST })
	public
			Response getVendorListSearch(@PathVariable String authToken,
					@PathVariable String status,
					@PathVariable String searchKey, HttpServletRequest request,
					HttpServletResponse res, ModelMap model)
	{
		try
		{
			Map<String, Object> map = new HashMap<String, Object>();
			int numEntries = 0;
			List<Admin> list = null;
			int startIndx = utilities.getDefaultMinIndx();
			int maxIndx = utilities.getDefaultMaxIndx();
			int userId = getUserId(authToken);
			list =
					adminService.getVendorListSearch(searchKey, status,
							startIndx, maxIndx);
			numEntries =
					adminService.getVendorListSearchNumEntries(searchKey,
							status, startIndx, maxIndx);
			map.put("vendorList", list);
			map.put("numEntries", numEntries);
			utilities.setSuccessResponse(response, map);

		} catch (Exception ex)
		{
			logger.error("getVendorListSearch :" + ex.getMessage());
			utilities.setErrResponse(ex, response);
		}
		model.addAttribute("model", response);
		return response;

	}

	@RequestMapping(
			value = "/getVendorListSearchPagination/{searchKey}/{status}/{startIndx}/{authToken}",
			method =
			{ RequestMethod.GET, RequestMethod.POST })
	public
			Response getVendorListSearchPagination(
					@PathVariable String authToken,
					@PathVariable String status, @PathVariable int startIndx,
					@PathVariable String searchKey, HttpServletRequest request,
					HttpServletResponse res, ModelMap model)
	{
		try
		{
			Map<String, Object> map = new HashMap<String, Object>();
			int numEntries = 0;
			List<Admin> list = null;
			startIndx = getStartIdx(startIndx, utilities.getDefaultMaxIndx());
			int maxIndx = utilities.getDefaultMaxIndx();
			int userId = getUserId(authToken);
			list =
					adminService.getVendorListSearch(searchKey, status,
							startIndx, maxIndx);
			numEntries =
					adminService.getVendorListSearchNumEntries(searchKey,
							status, startIndx, maxIndx);
			map.put("vendorList", list);
			map.put("numEntries", numEntries);
			utilities.setSuccessResponse(response, map);

		} catch (Exception ex)
		{
			logger.error("getVendorListSearchPagination :" + ex.getMessage());
			utilities.setErrResponse(ex, response);
		}
		model.addAttribute("model", response);
		return response;
	}

	@RequestMapping(
			value = "/getTripSearchKey/{searchKey}/{status}/{authToken}",
			method =
			{ RequestMethod.GET, RequestMethod.POST })
	public
			Response getTripSearchKey(@PathVariable String authToken,
					@PathVariable String status,
					@PathVariable String searchKey, HttpServletRequest request,
					HttpServletResponse res, ModelMap model)
	{
		try
		{
			Map<String, Object> map = new HashMap<String, Object>();
			int numEntries = 0;
			List<Trip> list = null;
			int maxIndx = utilities.getDefaultMaxIndx();
			int startIndx = utilities.getDefaultMinIndx();
			int userId = getUserId(authToken);

			list =
					adminService.getTripSearchKey(searchKey, status, startIndx,
							maxIndx);

			numEntries =
					adminService.getTripSearchKey_NumEntries(searchKey, status);

			map.put("tripDetails", list);
			map.put("numEntries", numEntries);

			utilities.setSuccessResponse(response, map);

		} catch (Exception ex)
		{
			logger.error("getTripSearchKey :" + ex.getMessage());
			utilities.setErrResponse(ex, response);
		}
		model.addAttribute("model", response);
		return response;
	}

	@RequestMapping(
			value = "/getTripSearchKeyPagination/{searchKey}/{status}/{startIndx}/{authToken}",
			method =
			{ RequestMethod.GET, RequestMethod.POST })
	public
			Response getTripSearchKeyPagination(@PathVariable String authToken,
					@PathVariable String status, @PathVariable int startIndx,
					@PathVariable String searchKey, HttpServletRequest request,
					HttpServletResponse res, ModelMap model)
	{
		try
		{
			Map<String, Object> map = new HashMap<String, Object>();
			int numEntries = 0;
			List<Trip> list = null;
			int maxIndx = utilities.getDefaultMaxIndx();
			startIndx = getStartIdx(startIndx, utilities.getDefaultMaxIndx());
			int userId = getUserId(authToken);

			list =
					adminService.getTripSearchKey(searchKey, status, startIndx,
							maxIndx);

			numEntries =
					adminService.getTripSearchKey_NumEntries(searchKey, status);

			map.put("tripDetails", list);
			map.put("numEntries", numEntries);

			utilities.setSuccessResponse(response, map);

		} catch (Exception ex)
		{
			logger.error("getTripSearchKeyPagination :" + ex.getMessage());
			utilities.setErrResponse(ex, response);
		}
		model.addAttribute("model", response);
		return response;
	}

	@RequestMapping(
			value = "/getActivitySearchKey/{searchKey}/{status}/{authToken}",
			method =
			{ RequestMethod.GET, RequestMethod.POST })
	public
			Response getActivitySearchKey(@PathVariable String authToken,
					@PathVariable String status,
					@PathVariable String searchKey, HttpServletRequest request,
					HttpServletResponse res, ModelMap model)
	{
		try
		{
			Map<String, Object> map = new HashMap<String, Object>();
			int numEntries = 0;
			List<Activity> list = null;
			int maxIndx = utilities.getDefaultMaxIndx();
			int startIndx = utilities.getDefaultMinIndx();
			int userId = getUserId(authToken);

			list =
					adminService.getActivitySearchKey(searchKey, status,
							startIndx, maxIndx);

			numEntries =
					adminService.getActivitySearchKeyNumEntries(searchKey,
							status);

			map.put("activityDetails", list);
			map.put("numEntries", numEntries);

			utilities.setSuccessResponse(response, map);

		} catch (Exception ex)
		{
			logger.error("getActivitySearchKey :" + ex.getMessage());
			utilities.setErrResponse(ex, response);
		}

		model.addAttribute("model", response);
		return response;
	}

	@RequestMapping(value = "/getTripDetailsBasedId/{tripId}/{authToken}",
			method =
			{ RequestMethod.GET, RequestMethod.POST })
	public Response getTripDetailsBasedId(@PathVariable String authToken,
			@PathVariable int tripId, HttpServletRequest request,
			HttpServletResponse res, ModelMap model)
	{
		try
		{
			Map<String, Object> map = new HashMap<String, Object>();
			int userId = getUserId(authToken);
			List<Trip> list = adminService.getTripDetailsBasedId(tripId);
			utilities.setSuccessResponse(response, list);

		} catch (Exception ex)
		{
			logger.error("getTripDetailsBasedId :" + ex.getMessage());
			utilities.setErrResponse(ex, response);
		}

		model.addAttribute("model", response);
		return response;
	}

	@RequestMapping(
			value = "/getUserDetailsBasedEmail/{userEmail}/{authToken}",
			method =
			{ RequestMethod.GET, RequestMethod.POST })
	public Response getUserDetailsBasedEmail(@PathVariable String authToken,
			@PathVariable String userEmail, HttpServletRequest request,
			HttpServletResponse res, ModelMap model)
	{
		try
		{
			Map<String, Object> map = new HashMap<String, Object>();
			int userId = getUserId(authToken);

			List<Login> list = adminService.getUserDetailsBasedEmail(userEmail);

			utilities.setSuccessResponse(response, list);

		} catch (Exception ex)
		{
			logger.error("getTripDetailsBasedId :" + ex.getMessage());
			utilities.setErrResponse(ex, response);
		}
		model.addAttribute("model", response);
		return response;
	}

	@RequestMapping(value = "/getDashboardDetails/{authToken}", method =
	{ RequestMethod.GET, RequestMethod.POST })
	public Response getDashboardDetails(@PathVariable String authToken,
			@PathVariable String userEmail, HttpServletRequest request,
			HttpServletResponse res, ModelMap model)
	{
		try
		{
			String isApproved = "no";

			Map<String, Object> map = new HashMap<String, Object>();
			int totalNonApprovedUsers =
					adminService.getNumEntries_UserStatus(isApproved);
		} catch (Exception ex)
		{
			logger.error("getDashboardDetails :" + ex.getMessage());
			utilities.setErrResponse(ex, response);
		}
		model.addAttribute("model", response);
		return response;
	}

	@RequestMapping(value = "/addMetaKeywords/{keywords}/{tripId}/{authToken}",
			method =
			{ RequestMethod.GET, RequestMethod.POST })
	public
			Response addMetaKeywords(@PathVariable String authToken,
					@PathVariable int tripId, @PathVariable String keywords,
					HttpServletRequest request, HttpServletResponse res,
					ModelMap model)
	{
		try
		{
			int userId = getUserId(authToken);
			adminService.addMetaKeywords(keywords, tripId);
			utilities.setSuccessResponse(response);

		} catch (Exception ex)
		{
			logger.error("addMetaKeywords :" + ex.getMessage());
			utilities.setErrResponse(ex, response);
		}
		model.addAttribute("model", response);
		return response;
	}

	@RequestMapping(
			value = "/updateMetaKeywords/{keywords}/{tripId}/{authToken}",
			method =
			{ RequestMethod.GET, RequestMethod.POST })
	public
			Response updateMetaKeywords(@PathVariable String authToken,
					@PathVariable int tripId, @PathVariable String keywords,
					HttpServletRequest request, HttpServletResponse res,
					ModelMap model)
	{
		try
		{
			int userId = getUserId(authToken);
			adminService.updateMetaKeywords(keywords, tripId);
			utilities.setSuccessResponse(response);

		} catch (Exception ex)
		{
			logger.error("updateMetaKeywords :" + ex.getMessage());
			utilities.setErrResponse(ex, response);
		}
		model.addAttribute("model", response);
		return response;
	}

	@RequestMapping(value = "/addSubActivity/{subActivityName}/{authToken}",
			method =
			{ RequestMethod.GET, RequestMethod.POST })
	public Response addSubActivity(@PathVariable String subActivityName,
			@PathVariable String authToken, HttpServletRequest request,
			HttpServletResponse res, ModelMap model)
	{
		try
		{
			int userId = getUserId(authToken);
			adminService.addSubActivity(subActivityName);
			utilities.setSuccessResponse(response);
		} catch (Exception ex)
		{
			logger.error("addSubActivity :" + ex.getMessage());
			utilities.setErrResponse(ex, response);
		}
		model.addAttribute("model", response);
		return response;
	}

	@RequestMapping(
			value = "/addTopActivityList/{tripId}/{isTopActivityList}/{authToken}",
			method =
			{ RequestMethod.GET, RequestMethod.POST })
	public
			Response addTopActivityList(
					@PathVariable boolean isTopActivityList,
					@PathVariable String authToken, @PathVariable int tripId,
					HttpServletRequest request, HttpServletResponse res,
					ModelMap model)
	{
		try
		{
			int userId = getUserId(authToken);
			if (isTopActivityList)
			{
				adminService.addTopActivityList(tripId);
			} else
			{
				adminService.deleteTopActivityList(tripId);
			}
			utilities.setSuccessResponse(response);
		} catch (Exception ex)
		{
			logger.error("addTopActivityList :" + ex.getMessage());
			utilities.setErrResponse(ex, response);
		}
		model.addAttribute("model", response);
		return response;
	}
}
