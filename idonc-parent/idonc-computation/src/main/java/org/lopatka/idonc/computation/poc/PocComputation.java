package org.lopatka.idonc.computation.poc;

import org.lopatka.idonc.computation.IComputation;
import org.lopatka.idonc.model.data.IdoncPart;
import org.lopatka.idonc.model.data.IdoncResult;

public class PocComputation implements IComputation {

	public IdoncResult computeData(IdoncPart part) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isResultConfirmationRequired() {
		return true;
	}

}
