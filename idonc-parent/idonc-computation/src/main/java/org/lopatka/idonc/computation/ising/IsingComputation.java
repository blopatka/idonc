package org.lopatka.idonc.computation.ising;

import java.util.List;

import org.lopatka.idonc.computation.IComputation;
import org.lopatka.idonc.model.data.IdoncLongData;
import org.lopatka.idonc.model.data.IdoncPart;
import org.lopatka.idonc.model.data.IdoncResult;

public class IsingComputation implements IComputation {

	public IdoncResult computeData(IdoncPart part) {
		// FIXME napisac metode do obliczania danych w projekcie ISING


		return null;
	}

	public boolean isResultConfirmationRequired() {
		return false;
	}

	private int[][] readSpinArray(List<IdoncLongData> list) {
		int dim = list.get(1).getValue().length();
		int[][] array = new int[dim][dim];
		for(int i = 1; i <= dim; i++) {
			String row = list.get(i).getValue();
			for(int j = 0; i < dim; j++) {
				array[i][j] = row.charAt(j) == '1' ? -1 : 1;
			}
		}

		return array;
	}

	// change the following globals to suit your fancy:
    static int size = 100;          // lattice size; can be any divisor of maxSize
    static double T = 2.27;        // temperature in units of epsilon/k


    // other globals:

    static int[][] s;   // the array of spins

    public static void main(String[] args) {

        int i, j;       // indices into array of spins
        double Ediff;   // energy change upon flipping


        // initialize array of spins:
        //losowanie wartosci spinow (mozna przeslac w usludze)
        s = new int[size][size];
        for (i = 0; i < size; i++) {
            for (j = 0; j < size; j++) {
                if (Math.random() < 0.5) s[i][j] = 1; else s[i][j] = -1;

            }
        }

        // main loop:
        while (true) {
            i = (int) (Math.random() * size);  // choose a random lattice site
            j = (int) (Math.random() * size);
            Ediff = deltaU(i,j);               // compute hypothetical energy change upon flipping
            if (Ediff <= 0.0) {                // if energy would decrease...
                s[i][j] = -s[i][j];            // flip it!
            }
            else {                                          // otherwise...
                if (Math.random() < Math.exp(-Ediff/T)) {   // Boltzmann factor gives flipping probability
                    s[i][j] = -s[i][j];
                    //colorSquare(i,j);
                }
            }
        }
    }

    // given a lattice site, compute energy change from hypothetical flip; note pbc:
    private static double deltaU(int i, int j) {
        int leftS, rightS, topS, bottomS;  // values of neighboring spins
        if (i == 0) leftS = s[size-1][j]; else leftS = s[i-1][j];
        if (i == size-1) rightS = s[0][j]; else rightS = s[i+1][j];
        if (j == 0) topS = s[i][size-1]; else topS = s[i][j-1];
        if (j == size-1) bottomS = s[i][0]; else bottomS = s[i][j+1];
        return 2.0 * s[i][j] * (leftS + rightS + topS + bottomS);
    }


}
