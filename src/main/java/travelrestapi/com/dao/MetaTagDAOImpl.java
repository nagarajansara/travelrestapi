package travelrestapi.com.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import travelrestapi.com.model.MetaTag;

@SuppressWarnings(
{ "unchecked", "unused" })
public class MetaTagDAOImpl implements MetaTagDAO
{

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	final String ADD_META_TAG =
			"INSERT into metatag (keywords, tripid) values (:keywords, :tripid)";

}
