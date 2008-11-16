package org.lopatka.idonc.service;

import org.lopatka.idonc.Temperature;

public interface ConverterService {

	Temperature celsiusToFarenheit (Temperature celsius);
	
	Temperature farenheitToCelsius(Temperature farenheit);
	
}
