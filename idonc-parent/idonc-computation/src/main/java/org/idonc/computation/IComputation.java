package org.idonc.computation;

import org.lopatka.idonc.model.data.IdoncPart;
import org.lopatka.idonc.model.data.IdoncResult;

public interface IComputation {

	/**
	 * metoda przeprowadzajaca obliczenia na dostarczonym pakiecie danych wejsciowych
	 *
	 * @param part
	 * @return
	 */
	IdoncResult computeData(IdoncPart part);

	/**
	 * metoda okreslajaca, czy otrzymany wynik powinien byc powtorzony przez innego
	 * uzytkownika, zeby zostal uznany za poprawny
	 *
	 * @return
	 */
	boolean isResultConfirmationRequired();
}
