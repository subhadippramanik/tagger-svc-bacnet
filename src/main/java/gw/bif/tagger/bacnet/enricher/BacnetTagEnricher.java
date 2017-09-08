package gw.bif.tagger.bacnet.enricher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableList;

@Component
public class BacnetTagEnricher {

	private static final String NAMESPACE_SEPARATOR = ":";

	Log logger = LogFactory.getLog(this.getClass());
	

	@Autowired
	Environment env;

	public Map<String, String> enrich(Map<String, String> message) {
		Map<String, String> richMessage = new HashMap<>();
		message.keySet().forEach(key -> {
			richMessage.put(key, message.get(key));
			List<String> extraTags = findTagFor(key);
			if (!extraTags.isEmpty()) {
				extraTags.forEach(tag -> richMessage.put(tag, message.get(key)));				
			}

		});
		logger.info(richMessage);
		return richMessage;
	}

	private List<String> findTagFor(String key) {
		String[] splitBySeparator = key.split(NAMESPACE_SEPARATOR);
		if (splitBySeparator.length > 1) {
			String propertyName = splitBySeparator[1];
			List<String> tags = new TagFinder().forProperty(propertyName).in(env).find();
			if (!tags.get(0).equals(TagFinder.NOT_FOUND)) {
				return tags;
			}			
		}
		return ImmutableList.of();
	}

	private class TagFinder {

		private static final String NOT_FOUND = "NOT_FOUND"; 
		
		private String originalTag;
		private String defaultValue = NOT_FOUND;
		private Environment environment;

		private TagFinder forProperty(String propertyName) {
			this.originalTag = propertyName;
			return this;
		}

		private TagFinder in(Environment env) {
			this.environment = env;
			return this;
		}

		private TagFinder orDefault(String defaultValue) {
			this.defaultValue = defaultValue;
			return this;
		}

		private List<String> find() {
			return ImmutableList.copyOf(environment.getProperty(originalTag, defaultValue).split(","));
		}
	}
}
