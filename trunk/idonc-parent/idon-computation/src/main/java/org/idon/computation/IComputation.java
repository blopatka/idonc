package org.idon.computation;

import org.lopatka.idonc.model.data.IdoncPart;
import org.lopatka.idonc.model.data.IdoncResult;

public interface IComputation {

	IdoncResult computeData(IdoncPart part);
}
