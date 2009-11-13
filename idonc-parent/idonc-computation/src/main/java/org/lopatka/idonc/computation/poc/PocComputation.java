package org.lopatka.idonc.computation.poc;

import java.util.List;

import org.lopatka.idonc.computation.IComputation;
import org.lopatka.idonc.model.data.IdoncLongData;
import org.lopatka.idonc.model.data.IdoncPart;
import org.lopatka.idonc.model.data.IdoncResult;

public class PocComputation implements IComputation {

	@Override
	public IdoncResult computeData(IdoncPart part) {
		List<IdoncLongData> list = part.getLongDataList();
		long resultValue = 0;
		for (IdoncLongData data : list) {
			try {
				final long val = Long.parseLong(data.getValue());

				System.out.println("sleeping: "+val);
				waiting(val);
				System.out.println("waking");

				resultValue += val;
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		IdoncResult result = new IdoncResult();
		result.setValue(Long.toString(resultValue));
		return result;
	}

	@Override
	public boolean isResultConfirmationRequired() {
		return true;
	}

	private void waiting(long n) {
		//FIXME usun ponizsza linijke
		n = 1;
		long t0, t1;

		t0 = System.currentTimeMillis();

		do {
			t1 = System.currentTimeMillis();
		} while (t1 - t0 < n);
	}
}
