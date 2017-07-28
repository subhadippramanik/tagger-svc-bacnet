package gw.bif.tagger.bacnet.enricher;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableList;

import gw.bif.tagger.core.message.Tag;

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
	
	public List<Tag> enrich(Tag message) {
		
		if("bacnet:present-value".equals(message.getKey())) {
			return ImmutableList.of(message, new Tag("bacnet:currVal", message.getValue()));
		}
		logger.info(message);
		return ImmutableList.of(message);
	}
}
