package ua.com.company.component;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import ua.com.company.entity.WeatherInfo;

@Component
public class EntitySuplier {

	public List<WeatherInfo> getEntites() {
		List<WeatherInfo> entities = new ArrayList<>();
		double kelvinToCelsius = 273.15;
		String JsonString = getJsonFromWebSite();
		JSONObject coreObject = new JSONObject(JsonString);
		JSONArray jsonArray = coreObject.getJSONArray("list");

		for (int i = 0; i < jsonArray.length(); i++) {
			WeatherInfo info = new WeatherInfo();
			JSONObject object = jsonArray.getJSONObject(i);
			JSONObject obj_temp = object.getJSONObject("main");

			info.setTimeInSecond(object.getLong("dt"));
			info.setAverageTemp(threePointPrecision(obj_temp.getDouble("temp") - kelvinToCelsius));
			info.setMinTemp(threePointPrecision(obj_temp.getDouble("temp_min") - kelvinToCelsius));
			info.setMaxTemp(threePointPrecision(obj_temp.getDouble("temp_max") - kelvinToCelsius));
			info.setDateAndTime(dateConverter(info.getTimeInMilis()));
			entities.add(info);
		}
		return entities;
	}

	private String getJsonFromWebSite() {
		RestTemplate restTemplate = new RestTemplate();
		String baseURL = "http://api.openweathermap.org/data/2.5/forecast?id=702550&APPID=";
		String token = "12aa0cefbe05d89b5afbcb9a05684c0d";
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> request = new HttpEntity<>(headers);
		ResponseEntity<String> responce = restTemplate.exchange(baseURL + token, HttpMethod.GET, request, String.class);

		return responce.getBody();
	}

	private double threePointPrecision(double number) {
		DecimalFormat format = new DecimalFormat("#.###");
		format.setRoundingMode(RoundingMode.CEILING);
		Double result = Double.valueOf(format.format(number).replace(',', '.'));
		return result;
	}

	private String dateConverter(long seconds) {
		long milisecinds = seconds * 1000;
		Date date = new Date(milisecinds);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String result = format.format(date);
		return result;
	}

}
