package org.lopatka.idonc.computation.pi;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lopatka.idonc.computation.IComputation;
import org.lopatka.idonc.model.data.IdoncLongData;
import org.lopatka.idonc.model.data.IdoncPart;
import org.lopatka.idonc.model.data.IdoncResult;

public class PiComputation implements IComputation {

	public List<IdoncResult> computeData(IdoncPart part) {
		List<IdoncLongData> list = part.getLongDataList();
		Integer iterations = Integer.valueOf(list.get(0).getValue());
		Double PI = computePI(iterations);
		IdoncResult result = new IdoncResult();
		result.setValue(PI.toString());

		List<IdoncResult> res = new ArrayList<IdoncResult>();
		res.add(result);
		return res;
	}

	@Override
	public boolean isResultConfirmationRequired() {
		return false;
	}

	private boolean isInside(double xPos, double yPos) {
		double distance = Math.sqrt((xPos * xPos) + (yPos * yPos));

		return (distance < 1.0);
	}

	private double computePI(int numThrows) {
		Random randomGen = new Random(System.currentTimeMillis());
		int hits = 0;
		double PI = 0;

		for (int i = 1; i <= numThrows; i++) {
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
