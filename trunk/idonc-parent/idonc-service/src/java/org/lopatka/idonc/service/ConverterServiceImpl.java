package org.lopatka.idonc.service;

import org.lopatka.idonc.Celsius;
import org.lopatka.idonc.Farenheit;
import org.lopatka.idonc.Temperature;

public class ConverterServiceImpl implements ConverterService {

	public Temperature celsiusToFarenheit (Temperature celsius) {
		Temperature ret = new Farenheit();
		ret.setTemperatur((celsius.getTemperature() * 9 / 5) +32);
		return ret;
	}
	
	public Temperature farenheitToCelsius(Temperature farenheit) {
		Temperature ret = new Celsius();
		ret.setTemperatur((farenheit.getTemperature() - 32) * 5 /9);
		return ret;
	}
}
