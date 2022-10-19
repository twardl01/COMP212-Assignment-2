package COMP212A2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class tests {

    public static void main(String[] args) {
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Enter an input set:");
        try {
            String simType = stdIn.readLine();
            System.out.println("Entered: " + simType);
            String[] simComponents = simType.split(" ");
            
            String[] simIDs = new String[simComponents.length - 1];


            for (int i = 1; i < simComponents.length; i++) {
                simIDs[i-1] = simComponents[i];
                System.out.println("ID " + i + ": '" + simComponents[i] + "'");
            }

            Processor[] simProc = new Processor[simIDs.length]; 
            simProc = procBuilder(simIDs);

            long timeStart = System.nanoTime();
            int electedProc = 0;

            if (simComponents[0].equals("LCR")) {
                LCR LCRsimulator = new LCR();
                electedProc = LCRsimulator.simulate(simProc);
            } else if (simComponents[0].equals("HC")) {
                HC HCsimulator = new HC();
                //electedProc = HCsimulator.simulate(simProc);
                System.out.println("currently unimplemented");
            }

            long timeElapsed = System.nanoTime() - timeStart;

            System.out.println("Time taken in millseconds: " + (timeElapsed/1000000));
            System.out.println("ID of Processor elected: " + electedProc);
        } catch (IOException e) {
            System.out.println("invalid!");
        }

    }

    public static int[] numArrayConv(String[] var) {
        int[] IDs = new int[var.length];

        for (int i = 0; i < var.length; i++) {
            try {
                IDs[i] = Integer.valueOf(var[i]);

            } catch (NumberFormatException e) {
                return new int[1];
            }
        }

        return IDs;
    }

    private static Processor[] procBuilder(String[] input) {
		Processor[] deviceStorage = new Processor[input.length];

		try {
			for (int i = 0; i < input.length; i++) {
				deviceStorage[i] = new Processor(Integer.parseInt(input[i]));
			}	
		} catch (NumberFormatException e) {
            Processor[] ID = new Processor[1];
            ID[0] = new Processor(1);
            return ID;
		}
		return deviceStorage;
	}
    
}
