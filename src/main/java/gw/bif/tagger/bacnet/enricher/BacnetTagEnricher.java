package gw.bif.tagger.bacnet.enricher;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class BacnetTagEnricher {

	Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	Environment env;

	public Map<String, String> enrich(Map<String, String> message) {
		Map<String, String> richMessage = new HashMap<>();
		message.keySet().forEach(key -> {
			richMessage.put(key, message.get(key));
			Optional<String> extraTag = findTagFor(key);
			if (extraTag.isPresent()) {
				richMessage.put(extraTag.get(), message.get(key));
			}

		});
		logger.info(richMessage);
		return richMessage;
	}

	private Optional<String> findTagFor(String key) {
		String namespace = key.split(":")[0];
		String propertyName = key.split(":")[1];
		String tag = new TagFinder().forProperty(propertyName).in(env).find();
		if(tag.equals(TagFinder.NOT_FOUND)) {
			return Optional.empty();
		}
		return Optional.of(namespace + ":" + tag);
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

		private String find() {
			return environment.getProperty(originalTag, defaultValue);
		}
	}
}
