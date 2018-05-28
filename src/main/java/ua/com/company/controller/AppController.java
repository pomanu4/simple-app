package ua.com.company.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ua.com.company.component.DateConverter;
import ua.com.company.component.FillDataBase;
import ua.com.company.entity.WeatherInfo;
import ua.com.company.service.WetherServiceIntrf;

@Controller
public class AppController {

	@Autowired
	private WetherServiceIntrf weatherServ;

	@Autowired
	private FillDataBase fillDB;

	@RequestMapping(path = { "/" }, method = RequestMethod.GET)
	public String indexPage(Model model) {
		fillDB.getAndSaveEntities();
		List<WeatherInfo> entities = weatherServ.getAll();

		String minDate = DateConverter.getStringDateRepresent(weatherServ.getMinDateSecond());
		String maxDate = DateConverter.getStringDateRepresent(weatherServ.getMaxDateSecond());
		model.addAttribute("min", minDate);
		model.addAttribute("max", maxDate);
		model.addAttribute("objects", entities);
		return "index";
	}

	@RequestMapping(value = "dateVal", method = RequestMethod.POST)
	public String test(Model model, @RequestParam("min") String min, @RequestParam("max") String max) {
		String minDate = DateConverter.getStringDateRepresent(weatherServ.getMinDateSecond());
		String maxDate = DateConverter.getStringDateRepresent(weatherServ.getMaxDateSecond());
		List<WeatherInfo> entities = weatherServ.getInRange(min, max);
		double avgTemp = weatherServ.getAVGTemp(min, max);
		double minTemp = weatherServ.getMinTemp(min, max);
		double maxTemp = weatherServ.getMaxTemp(min, max);

		model.addAttribute("min", minDate);
		model.addAttribute("max", maxDate);
		model.addAttribute("objects", entities);
		model.addAttribute("avg", avgTemp);
		model.addAttribute("minTemp", minTemp);
		model.addAttribute("maxTemp", maxTemp);

		return "index";
	}

}
