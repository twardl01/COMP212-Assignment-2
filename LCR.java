package COMP212A2;

public class LCR {

    int messages = 0;
    int rounds = 1;
    boolean terminate = false;
    int electedProc = 0;
    Processor[] procArray;

    private void elect(Processor currentProc, int procNum) {

        //First round: Every processor sends the 
        if (rounds == 1) {
            send(procNum, currentProc.myID);
        } else if (rounds > 1){
            if (currentProc.inID > currentProc.myID) {
                procArray[procNum].sendID = procArray[procNum].inID;
                send(procNum, currentProc.sendID);

            } else if (currentProc.inID == currentProc.myID) {

                currentProc.elected();
                terminate = true;
                electedProc = currentProc.myID;

            }
        }
    }

    private void send(int procNum, int sentID) {
        int nextProc = procNum + 1;
        
        //Sends sentID to the next processor
        //"else" statement handles instances where the processor is the last in the ring.
        if (nextProc < procArray.length) {
            procArray[nextProc].inID = sentID;
        } else {
            procArray[0].inID = sentID;
        }

        messages++;
    }

    public int simulate(Processor[] procsIn) {
        procArray = procsIn;

        //Simulates process until leader is elected (represented by the terminate bool)
        while (!terminate){
            for (int i = (procArray.length - 1); i >= 0; i--) {
                elect(procArray[i], i);
            }

            //Increments the "rounds" variable.
            rounds++;
        }

        //Prints the rounds/messages/elected processor.
        System.out.println("LCR Rounds: " + rounds);
        System.out.println("LCR Messages: " + messages);
        System.out.println("Processor " + electedProc + " was elected.");

        return electedProc;
    }
}