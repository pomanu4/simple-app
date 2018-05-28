package ua.com.company.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.com.company.dao.WeatherDAOInterf;
import ua.com.company.entity.WeatherInfo;
import ua.com.company.service.WetherServiceIntrf;

@Service
@Transactional
public class WeatherService implements WetherServiceIntrf {

	@Autowired
	private WeatherDAOInterf weatherDao;

	@Override
	public List<WeatherInfo> getAll() {
		return weatherDao.getAllEntities();
	}

	@Override
	public void save(WeatherInfo entity) {
		weatherDao.saveEntity(entity);
	}

	@Override
	public Long getMinDateSecond() {
		return weatherDao.getMinDateSecond();
	}

	@Override
	public Long getMaxDateSecond() {
		return weatherDao.getMaxDateSecond();
	}

	@Override
	public List<WeatherInfo> getInRange(String min, String max) {
		return weatherDao.getInRange(min, max);
	}

	@Override
	public double getAVGTemp(String min, String max) {
		return weatherDao.getAWGTemp(min, max);
	}

	@Override
	public double getMinTemp(String min, String max) {
		return weatherDao.getMinTemp(min, max);
	}

	@Override
	public double getMaxTemp(String min, String max) {
		return weatherDao.getMaxTemp(min, max);
	}
}
