package ua.com.company.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class WeatherInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private long timeInMilis;

	private String dateAndTime;

	private double averageTemp;

	private double minTemp;

	private double maxTemp;

	public WeatherInfo() {
	}

	public WeatherInfo(long timeInMilis, String dateAndTime, double averageTemp, double minTemp, double maxTemp) {
		super();
		this.timeInMilis = timeInMilis;
		this.dateAndTime = dateAndTime;
		this.averageTemp = averageTemp;
		this.minTemp = minTemp;
		this.maxTemp = maxTemp;
	}

	public long getTimeInMilis() {
		return timeInMilis;
	}

	public void setTimeInSecond(long timeInMilis) {
		this.timeInMilis = timeInMilis;
	}

	public String getDateAndTime() {
		return dateAndTime;
	}

	public void setDateAndTime(String dateAndTime) {
		this.dateAndTime = dateAndTime;
	}

	public double getAverageTemp() {
		return averageTemp;
	}

	public void setAverageTemp(double averageTemp) {
		this.averageTemp = averageTemp;
	}

	public double getMinTemp() {
		return minTemp;
	}

	public void setMinTemp(double minTemp) {
		this.minTemp = minTemp;
	}

	public double getMaxTemp() {
		return maxTemp;
	}

	public void setMaxTemp(double maxTemp) {
		this.maxTemp = maxTemp;
	}

	@Override
	public String toString() {
		return "WeatherInfo [timeInMilis=" + timeInMilis + ", dateAndTime=" + dateAndTime + ", averageTemp="
				+ averageTemp + ", minTemp=" + minTemp + ", maxTemp=" + maxTemp + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(averageTemp);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((dateAndTime == null) ? 0 : dateAndTime.hashCode());
		temp = Double.doubleToLongBits(maxTemp);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(minTemp);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (int) (timeInMilis ^ (timeInMilis >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WeatherInfo other = (WeatherInfo) obj;

		return this.timeInMilis > other.timeInMilis;
	}
}
