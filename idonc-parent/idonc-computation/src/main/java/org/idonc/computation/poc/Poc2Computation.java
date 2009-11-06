package org.idonc.computation.poc;

import org.idonc.computation.IComputation;
import org.lopatka.idonc.model.data.IdoncPart;
import org.lopatka.idonc.model.data.IdoncResult;

public class Poc2Computation implements IComputation {

	public IdoncResult computeData(IdoncPart part) {
		// TODO napisac metode do obliczania danych w projekcie POC bez potwierdzania wyniku
		return null;
	}

	public boolean isResultConfirmationRequired() {
		return false;
	}

}
