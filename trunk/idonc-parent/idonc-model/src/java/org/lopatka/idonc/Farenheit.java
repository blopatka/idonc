package org.lopatka.idonc;

import java.io.Serializable;

public class Farenheit implements Temperature, Serializable {

	private static final long serialVersionUID = 7805724122524610414L;
	private float farenheit;

	public float getTemperature() {
		return farenheit;
	}

	public void setTemperatur(float temperature) {
		this.farenheit = temperature;
	}
	


}
