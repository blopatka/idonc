package org.lopatka.idonc.computation.ising;

import java.util.ArrayList;
import java.util.List;

import org.lopatka.idonc.computation.IComputation;
import org.lopatka.idonc.model.data.IdoncLongData;
import org.lopatka.idonc.model.data.IdoncPart;
import org.lopatka.idonc.model.data.IdoncResult;

public class IsingComputation implements IComputation {

	public List<IdoncResult> computeData(IdoncPart part) {
		// FIXME napisac metode do obliczania danych w projekcie ISING
		List<IdoncLongData> data = part.getLongDataList();
		Integer steps = Integer.parseInt(data.get(0).getValue());
		Double temp = 2.27;
		int size = data.get(1).getValue().length();
		int[][] spins = readSpinArray(data, size);


		int i, j;       // indices into array of spins
        double Ediff;   // energy change upon flipping

        for(int dx = 0; dx < steps; dx++) {
            i = (int) (Math.random() * size);  // choose a random lattice site
            j = (int) (Math.random() * size);
            Ediff = deltaU(i,j, size, spins);               // compute hypothetical energy change upon flipping
            if (Ediff <= 0.0) {                // if energy would decrease...
                spins[i][j] = -spins[i][j];            // flip it!
            }
            else {                                          // otherwise...
                if (Math.random() < Math.exp(-Ediff/temp)) {   // Boltzmann factor gives flipping probability
                    spins[i][j] = -spins[i][j];
                }
            }
        }

        List<IdoncResult> result = createResultFromArray(spins, size);

		return result;
	}

	public boolean isResultConfirmationRequired() {
		return false;
	}

	private List<IdoncResult> createResultFromArray(int [][]spins, int size) {
		List<IdoncResult> results = new ArrayList<IdoncResult>();
		for(int i = 0; i < size; i ++) {
			IdoncResult res = new IdoncResult();
			StringBuffer buf = new StringBuffer();
			for(int j = 0; j <size; j++) {
				if(spins[i][j] == 1) {
					buf.append('1');
				} else {
					buf.append('0');
				}
			}
			res.setValue(buf.toString());
			results.add(res);
		}
		return results;
	}

	private int[][] readSpinArray(List<IdoncLongData> list, int size) {
		int[][] array = new int[size][size];
		for(int i = 0; i < size; i++) {
			String row = list.get(i+1).getValue();
			for(int j = 0; j < size; j++) {
				array[i][j] = row.charAt(j) == '1' ? -1 : 1;
			}
		}

		return array;
	}

    // given a lattice site, compute energy change from hypothetical flip; note pbc:
    private static double deltaU(int i, int j, int size, int[][] spins) {
        int leftS, rightS, topS, bottomS;  // values of neighboring spins
        if (i == 0) leftS = spins[size-1][j]; else leftS = spins[i-1][j];
        if (i == size-1) rightS = spins[0][j]; else rightS = spins[i+1][j];
        if (j == 0) topS = spins[i][size-1]; else topS = spins[i][j-1];
        if (j == size-1) bottomS = spins[i][0]; else bottomS = spins[i][j+1];
        return 2.0 * spins[i][j] * (leftS + rightS + topS + bottomS);
    }


}
