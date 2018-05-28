package ua.com.company.service;

import java.util.List;

import ua.com.company.entity.WeatherInfo;

public interface WetherServiceIntrf {

	List<WeatherInfo> getAll();

	void save(WeatherInfo entity);

	Long getMinDateSecond();

	Long getMaxDateSecond();

	List<WeatherInfo> getInRange(String min, String max);
	
	double getAVGTemp(String min, String max);
	
	double getMinTemp(String min, String max);
	
	double getMaxTemp(String min, String max);

}
