package ua.com.company.component;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ua.com.company.entity.WeatherInfo;
import ua.com.company.service.WetherServiceIntrf;

@Component
public class FillDataBase {

	private static boolean required = true;

	@Autowired
	private EntitySuplier suplier;

	@Autowired
	private WetherServiceIntrf weatherServ;

	public void getAndSaveEntities() {
		if (required) {
			List<WeatherInfo> entities = suplier.getEntites();
			for (WeatherInfo entity : entities) {
				weatherServ.save(entity);
			}
		}
		required = false;
	}
}
