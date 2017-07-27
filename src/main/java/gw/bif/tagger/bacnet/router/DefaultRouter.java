package gw.bif.tagger.bacnet.router;

import java.util.Map;

import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;


public class DefaultRouter extends RouteBuilder {
	
	@Autowired
	ProducerTemplate producerTemplate;

	public void rout(Map<String, String> message) {
		producerTemplate.sendBody("direct:in", message);
	}
	
	@Override
	public void configure() throws Exception {
		from("direct:in")//
		.log("${body}")//
		//.transform(new SimpleExpression("Hello"))//
		.process((exchange) -> {
			Map<String, String> body = exchange.getIn().getBody(Map.class);
			exchange.getOut().setBody(body + 
				    "response; ID : " + exchange.getExchangeId());
		} );
	}

}
