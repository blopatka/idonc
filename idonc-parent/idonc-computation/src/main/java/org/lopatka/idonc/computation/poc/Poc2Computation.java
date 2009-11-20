package org.lopatka.idonc.computation.poc;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingWorker;

import org.lopatka.idonc.computation.IComputation;
import org.lopatka.idonc.model.data.IdoncLongData;
import org.lopatka.idonc.model.data.IdoncPart;
import org.lopatka.idonc.model.data.IdoncResult;

public class Poc2Computation implements IComputation {

	private SwingWorker thread;
	
	@Override
	public List<IdoncResult> computeData(IdoncPart part, SwingWorker thread) {
		this.thread = thread;
		boolean doCancel = false;
		List<IdoncLongData> list = part.getLongDataList();
		long resultValue = 0;
		for (IdoncLongData data : list) {
			if(thread.isCancelled()) {
				return null;
			}
			try {
				final long val = Long.parseLong(data.getValue());

				System.out.println("sleeping: "+val);
				doCancel = !waiting(val);
				System.out.println("waking");

				resultValue += val;
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		if(!doCancel) {
			IdoncResult result = new IdoncResult();
			result.setValue(Long.toString(resultValue));

			List<IdoncResult> res = new ArrayList<IdoncResult>();
			res.add(result);
			return res;
		} else {
			return null;
		}
	}

	@Override
	public boolean isResultConfirmationRequired() {
		return false;
	}

	private boolean waiting(long n) {
		long t0, t1;

		t0 = System.currentTimeMillis();

		do {
			if(thread.isCancelled()) {
				return false;
			}
			t1 = System.currentTimeMillis();
		} while (t1 - t0 < n);
		return true;
	}

}
