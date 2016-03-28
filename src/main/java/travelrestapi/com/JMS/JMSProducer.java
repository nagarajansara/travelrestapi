package travelrestapi.com.JMS;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.core.SessionCallback;

@SuppressWarnings("unused")
public class JMSProducer
{

	@Autowired
	private JmsTemplate jmsTemplate;

	public final static String EMAIL_QUEUE = "emailQueue";

	private static final Logger logger = Logger.getLogger(JMSProducer.class
			.getName());

	public
			void
			SendJMS_Message(final String jsonObject, final String queueName)
					throws Exception
	{
		try
		{
			if (jmsTemplate != null)
			{

				jmsTemplate.send(queueName, new MessageCreator()
				{

					@Override
					public Message createMessage(Session session)
							throws JMSException
					{
						TextMessage textMessage = session.createTextMessage();
						textMessage.setText(jsonObject);
						return textMessage;
					}
				});

			} else
			{
				throw new Exception();
			}
		} catch (Exception e)
		{
			throw e;
		}
	}

}
