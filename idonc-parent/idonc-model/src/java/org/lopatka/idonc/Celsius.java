package org.lopatka.idonc;

import java.io.Serializable;

public class Celsius implements Temperature, Serializable{

	private static final long serialVersionUID = 4365405855855043899L;
	private float celsius;
	
	public float getTemperature() {
		return celsius;
	}

	public void setTemperatur(float temperature) {
		this.celsius = temperature;
	}

}
