package COMP212A2;

import java.net.*;
import java.io.*;

public class NetworkSimulatorServer {
	public static void main(String[] args) throws IOException {
		
		System.out.println("Trying to connect to default port 8000...");
		
		while(true){ //in order to serve multiple clients but sequentially, one after the other
			try (
				ServerSocket myServerSocket = new ServerSocket(8000);
				Socket aClientSocket = myServerSocket.accept();
				PrintWriter output = new PrintWriter(aClientSocket.getOutputStream(),true);
				BufferedReader input = new BufferedReader(new InputStreamReader(aClientSocket.getInputStream()));
			) {
				System.out.println("Connection established with a new client with IP address: " + aClientSocket.getInetAddress() + "\n");
				output.println("Server says: Hello Client " + aClientSocket.getInetAddress() + ". This is server " + myServerSocket.getInetAddress() + 
				" speaking. Our connection has been successfully established!");
			
				String inputLine = input.readLine();
				System.out.println("Received a new message from client " + aClientSocket.getInetAddress());
				System.out.println("Client says: " + inputLine);
				output.println("Server says: Your message has been successfully received!");

				//Constructs a string containing each word in the input line.
				String[] splitInput = inputLine.split(" ");
				
				//IDs within the input are put into their own array for use in procBuilder.
				String[] simIDs = new String[splitInput.length - 1];
				for (int i = 1; i < splitInput.length; i++) {
					simIDs[i-1] = splitInput[i];
					System.out.println("ID " + i + ": " + splitInput[i]);
				}

				//Constructs an array of processors according to splitInput.
				Processor[] allIDs = procBuilder(splitInput);

				//Generates start time
				long startTime = System.nanoTime();
				int electedProc = 0;

				//Handles execution of algorithms according to input
				if (splitInput[0].equals("LCR")) {
					LCR LCRsimulator = new LCR();
					electedProc = LCRsimulator.simulate(allIDs);
				} else if (splitInput[0].equals("HC")) {
					HC HCsimulator = new HC();
					//electedProc = HCsimulator.simulate(allIDs);
					System.out.println("currently unimplemented");
				}

				//Calculates time taken by finding the time, and taking away the time found at the start 
				long timeTaken = System.nanoTime() - startTime;

				output.println("Simulator took " + timeTaken + " nanoseconds to complete.");
				output.println("Processor " + electedProc + " was elected.");
				System.out.println("Connection with client " + aClientSocket.getInetAddress() + " is now closing...\n");
			
			} catch (IOException e) {
				System.out.println("Exception caught when trying to listen on port 8000 or listening for a connection");
				System.out.println(e.getMessage());			
			}
		}
	}

	//Creates an array of the processor object class for usage in the LCR and HC algorithms
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