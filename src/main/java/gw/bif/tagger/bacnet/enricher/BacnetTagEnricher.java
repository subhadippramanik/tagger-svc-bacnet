package gw.bif.tagger.bacnet.enricher;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

@Component
public class BacnetTagEnricher {

	Log logger = LogFactory.getLog(this.getClass());
	
	public Map<String, String> enrich(Map<String, String> message) {
		if(message.containsKey("bacnet:present-value")) {
			message.put("bacnet:currVal", message.get("bacnet:present-value"));
		}
		logger.info(message);
		return message;
	}
}
