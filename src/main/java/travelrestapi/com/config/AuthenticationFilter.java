package travelrestapi.com.config;

import java.io.IOException;
import java.util.*;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import travelrestapi.com.controller.BaseController;

@WebFilter("/AuthenticationFilter")
public class AuthenticationFilter extends BaseController implements Filter

{

	private String SESSION_FAILURE_URL =
			"/travelrestapi/travelrestapi/admin/sessionfailure";
	private List<String> AUTHENTICATED_URL = new ArrayList<String>();
	private List<String> AUTHENTICATED_URL_INDEXOF = new ArrayList<String>();
	private List<String> ROLE_CUSTOMER_URL = new ArrayList<String>();
	private List<String> ROLE_VENDOR_URL = new ArrayList<String>();
	private final String ROLE_CUSTOMER = "ROLE_CUSTOMER";
	private final String ROLE_VENDOR = "ROLE_VENDOR";

	private ServletContext context;

	public void init(FilterConfig fConfig) throws ServletException
	{
		this.context = fConfig.getServletContext();
		this.context.log("AuthenticationFilter initialized");

		/************ View pages *****************************/

		AUTHENTICATED_URL_INDEXOF.add("getTripDetailsBasedId");
		AUTHENTICATED_URL_INDEXOF.add("getTripDetailsPageno");
		AUTHENTICATED_URL_INDEXOF.add("getFilterTripsDetailsPageNo");
		AUTHENTICATED_URL_INDEXOF.add("getAdminLoginValidate");

		/************** Role based urls ***********************/

		ROLE_CUSTOMER_URL.add("customer.jsp");
		ROLE_VENDOR_URL.add("vendor.jsp");

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException
	{
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String uri = req.getRequestURI();

		HttpSession session = req.getSession(false);
		Object userDetails =
				(session != null) ? session.getAttribute(ATTR_USER_ID) : null;

		String lastURIWord = uri.substring(uri.lastIndexOf("/") + 1);

		res.setHeader("Access-Control-Allow-Origin", "*");
		res.setHeader("Access-Control-Allow-Methods",
				"POST, GET, OPTIONS, DELETE");
		res.setHeader("Access-Control-Max-Age", "3600");
		res.setHeader("Access-Control-Allow-Headers",
				"Origin, X-Requested-With, Content-Type, Accept,AUTH-TOKEN");

		chain.doFilter(request, response);

	}

	public void destroy()
	{

	}

}
