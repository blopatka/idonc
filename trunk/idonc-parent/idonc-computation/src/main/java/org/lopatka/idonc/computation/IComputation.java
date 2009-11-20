package org.lopatka.idonc.computation;

import java.util.List;

import javax.swing.SwingWorker;

import org.lopatka.idonc.model.data.IdoncPart;
import org.lopatka.idonc.model.data.IdoncResult;

public interface IComputation {

	/**
	 * metoda przeprowadzajaca obliczenia na dostarczonym pakiecie danych wejsciowych
	 *
	 * @param part
	 * @return
	 */
	List<IdoncResult> computeData(IdoncPart part, SwingWorker thread);

	/**
	 * metoda okreslajaca, czy otrzymany wynik powinien byc powtorzony przez innego
	 * uzytkownika, zeby zostal uznany za poprawny
	 *
	 * @return
	 */
	boolean isResultConfirmationRequired();
}
