package org.lopatka.idonc.computation.pi;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.SwingWorker;

import org.lopatka.idonc.computation.IComputation;
import org.lopatka.idonc.model.data.IdoncLongData;
import org.lopatka.idonc.model.data.IdoncPart;
import org.lopatka.idonc.model.data.IdoncResult;

public class PiComputation implements IComputation {

	private SwingWorker thread;
	
	public List<IdoncResult> computeData(IdoncPart part, SwingWorker thread) {
		this.thread = thread;
		List<IdoncLongData> list = part.getLongDataList();
		Integer iterations = Integer.valueOf(list.get(0).getValue());
		Double PI = computePI(iterations);
		if(PI != null) {
			IdoncResult result = new IdoncResult();
			result.setValue(PI.toString());

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

	private boolean isInside(double xPos, double yPos) {
		double distance = Math.sqrt((xPos * xPos) + (yPos * yPos));

		return (distance < 1.0);
	}

	private Double computePI(int numThrows) {
		Random randomGen = new Random(System.currentTimeMillis());
		int hits = 0;
		double PI = 0;

		for (int i = 1; i <= numThrows; i++) {
			if(thread.isCancelled()) {
				return null;
			}
			double xPos = (randomGen.nextDouble()) * 2 - 1.0;
			double yPos = (randomGen.nextDouble()) * 2 - 1.0;
			if (isInside(xPos, yPos)) {
				hits++;
			}
		}

		double dthrows = numThrows;

		PI = (4.0 * (hits / dthrows));

		return PI;
	}

}
