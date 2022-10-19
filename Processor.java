package COMP212A2;

public class Processor {
    int myID;
    int sendID;
    int inID;
    status currentStatus;

    enum status {
        UNKNOWN,
        LEADER
    }

    public Processor(int id) {
        myID = id;
        sendID = id;
        inID = 0;
        currentStatus = status.UNKNOWN;
    }

    public void changeSend(int newID) {
        sendID = newID;
    }

    public void elected() {
        currentStatus = status.LEADER;
    }

    public void unelected() {
        currentStatus = status.UNKNOWN;
    }
}
