package ua.com.company.daoImpl;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ua.com.company.component.DateConverter;
import ua.com.company.component.DoubleValuesHandler;
import ua.com.company.dao.WeatherDAOInterf;
import ua.com.company.entity.WeatherInfo;

@Repository
public class WetherInfoDAO implements WeatherDAOInterf {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<WeatherInfo> getAllEntities() {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<WeatherInfo> criteria = criteriaBuilder.createQuery(WeatherInfo.class);
		Root<WeatherInfo> root = criteria.from(WeatherInfo.class);
		criteria.select(root);
		Query<WeatherInfo> querry = session.createQuery(criteria);
		return querry.getResultList();
	}

	@Override
	public void saveEntity(WeatherInfo entity) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(entity);
	}

	@Override
	public Long getMinDateSecond() {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
		Root<WeatherInfo> root = query.from(WeatherInfo.class);
		query.select(criteriaBuilder.min(root.get("timeInMilis")));
		Query<Long> simpleQuery = session.createQuery(query);
		Long minSeconds = simpleQuery.getSingleResult();

		return minSeconds;
	}

	@Override
	public Long getMaxDateSecond() {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
		Root<WeatherInfo> root = query.from(WeatherInfo.class);
		query.select(criteriaBuilder.max(root.get("timeInMilis")));
		Query<Long> simpleQuery = session.createQuery(query);
		Long maxSeconds = simpleQuery.getSingleResult();

		return maxSeconds;
	}

	@Override
	public List<WeatherInfo> getInRange(String minDate, String maxDate) {
		Session session = sessionFactory.getCurrentSession();
		long minSeconds = minValueCalculate(minDate, maxDate);
		long maxSeconds = maxValueCalculate(minDate, maxDate);
		String JPQLquerry = "SELECT w FROM WeatherInfo w WHERE w.timeInMilis BETWEEN (:min)"
				+ " AND (:max) ORDER BY w.timeInMilis ASC";
		TypedQuery<WeatherInfo> querry = session.createQuery(JPQLquerry, WeatherInfo.class);
		querry.setParameter("min", checkMin(minSeconds));
		querry.setParameter("max", checkMax(maxSeconds));

		return querry.getResultList();
	}

	@Override
	public double getAWGTemp(String min, String max) {
		Session session = sessionFactory.getCurrentSession();
		long minSeconds = minValueCalculate(min, max);
		long maxSeconds = maxValueCalculate(min, max);
		String JPQLquerry = "SELECT AVG(w.averageTemp) FROM WeatherInfo w WHERE w.timeInMilis BETWEEN (:min) AND (:max)";
		TypedQuery<Double> querry = session.createQuery(JPQLquerry, Double.class);
		querry.setParameter("min", checkMin(minSeconds));
		querry.setParameter("max", checkMax(maxSeconds));

		return DoubleValuesHandler.threePointPrecision(nullHandler(querry.getSingleResult()));
	}

	@Override
	public double getMinTemp(String min, String max) {
		Session session = sessionFactory.getCurrentSession();
		long minSeconds = minValueCalculate(min, max);
		long maxSeconds = maxValueCalculate(min, max);
		String JPQLquerry = "SELECT MIN(w.minTemp) FROM WeatherInfo w WHERE w.timeInMilis BETWEEN (:min) AND (:max)";
		TypedQuery<Double> querry = session.createQuery(JPQLquerry, Double.class);
		querry.setParameter("min", checkMin(minSeconds));
		querry.setParameter("max", checkMax(maxSeconds));

		return DoubleValuesHandler.threePointPrecision(nullHandler(querry.getSingleResult()));
	}

	@Override
	public double getMaxTemp(String min, String max) {
		Session session = sessionFactory.getCurrentSession();
		long minSeconds = minValueCalculate(min, max);
		long maxSeconds = maxValueCalculate(min, max);
		String JPQLquerry = "SELECT MAX(w.maxTemp) FROM WeatherInfo w WHERE w.timeInMilis BETWEEN (:min) AND (:max)";
		TypedQuery<Double> querry = session.createQuery(JPQLquerry, Double.class);
		querry.setParameter("min", checkMin(minSeconds));
		querry.setParameter("max", checkMax(maxSeconds));

		return DoubleValuesHandler.threePointPrecision(nullHandler(querry.getSingleResult()));
	}

	private long checkMin(long number) {
		long seconds = getMinDateSecond();
		if (number < seconds) {
			return seconds;
		}
		return number;
	}

	private long checkMax(long number) {
		long seconds = getMaxDateSecond();
		if (number > seconds) {
			return seconds;
		}
		return number;
	}

	private long minValueCalculate(String minValue, String maxValue) {
		long min = DateConverter.getDateInMilisecond(minValue);
		long max = DateConverter.getDateInMilisecond(maxValue);
		if (minValue.equals("")) {
			return DateConverter.getCurrentDate() / 1000;
		} else if (minValue.equals(maxValue)) {
			return min / 1000;
		} else if (min > max) {
			return max / 1000;
		}
		return min / 1000;
	}

	private long maxValueCalculate(String minValue, String maxValue) {
		long min = DateConverter.getDateInMilisecond(minValue);
		long max = DateConverter.getDateInMilisecond(maxValue);
		if (min < max) {
			return max / 1000;
		} else if (maxValue.equals("")) {
			return DateConverter.getThreeDayLater() / 1000;
		} else if (min == max & maxValue.equals(minValue)) {
			return DateConverter.get24HourLater(max) / 1000;
		} else if (min > max) {
			return min / 1000;
		}
		return max / 1000;
	}

	private double nullHandler(Double number) {
		if (number == null) {
			return 0;
		}
		return number;
	}
}
