package ua.com.company.dao;

import java.util.List;

import ua.com.company.entity.WeatherInfo;

public interface WeatherDAOInterf {

	List<WeatherInfo> getAllEntities();

	void saveEntity(WeatherInfo entity);

	Long getMinDateSecond();

	Long getMaxDateSecond();

	List<WeatherInfo> getInRange(String minDate, String maxDate);
	
	double getAWGTemp(String min, String max);
	
	double getMinTemp(String min, String max);
	
	double getMaxTemp(String min, String max);

}
