package gw.bif.tagger.bacnet.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import gw.bif.tagger.bacnet.enricher.BacnetTagEnricher;
import gw.bif.tagger.bacnet.enricher.ModbusTagEnricher;

@RestController
public class TaggerBacnetController {
	
	@Autowired
	BacnetTagEnricher bacnetTagEnricher;
	
	@Autowired
	ModbusTagEnricher modbusTagEnricher;
	
	@RequestMapping(value = "/bacnet", method = RequestMethod.POST)
	public Map<String, String> bacnet(@RequestBody Map<String, String> message) {		
		return bacnetTagEnricher.enrich(message);
	}
	
	@RequestMapping(value = "/modbus", method = RequestMethod.POST)
	public Map<String, String> modbus(@RequestBody Map<String, String> message) {		
		return modbusTagEnricher.enrich(message);
	}
	
}
