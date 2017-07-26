package gw.bif.tagger.bacnet.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import gw.bif.tagger.bacnet.router.DefaultRouter;

@RestController
public class TaggerBacnetController {
	
	@Autowired
	DefaultRouter router;
	
	@RequestMapping(value = "/bacnet", method = RequestMethod.POST)
	public Map<String, String> bacnet(@RequestBody Map<String, String> message) {
		router.rout(message);
		return message;
	}
	
}
