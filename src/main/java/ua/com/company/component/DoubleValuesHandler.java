package ua.com.company.component;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class DoubleValuesHandler {
	
	public static double threePointPrecision(double number) {
		DecimalFormat format = new DecimalFormat("#.###");
		format.setRoundingMode(RoundingMode.CEILING);
		return Double.valueOf(format.format(number).replace(',', '.'));
	}
}
