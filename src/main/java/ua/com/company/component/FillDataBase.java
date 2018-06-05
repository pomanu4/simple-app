package ua.com.company.component;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ua.com.company.entity.WeatherInfo;
import ua.com.company.service.WetherServiceIntrf;

@Component
public class FillDataBase {

	@Autowired
	private EntitySuplier suplier;

	@Autowired
	private WetherServiceIntrf weatherServ;

	private void getAndSaveEntities() {

		List<WeatherInfo> entities = suplier.getEntites();
		for (WeatherInfo entity : entities) {
			weatherServ.save(entity);
		}
	}

	@PostConstruct
	public void initDB() {
		Thread initThresd = new Thread(new Runnable() {
			public void run() {
				while (true) {
					getAndSaveEntities();
					try {
						TimeUnit.HOURS.sleep(24);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

		});
		initThresd.setDaemon(true);
		initThresd.start();
	}
}
