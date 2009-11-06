package org.idonc.computation.poc;

import org.idonc.computation.IComputation;
import org.lopatka.idonc.model.data.IdoncPart;
import org.lopatka.idonc.model.data.IdoncResult;

public class PocComputation implements IComputation {

	public IdoncResult computeData(IdoncPart part) {
		// TODO Napisac metode do obliczania w projekcie POC
		return null;
	}

	public boolean isResultConfirmationRequired() {
		return true;
	}

}
