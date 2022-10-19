package COMP212A2;

import java.net.*;
import java.io.*;

public class NetworkSimulatorClient {
	public static void main(String[] args) throws IOException {
		
		try (
			Socket myClientSocket = new Socket("127.0.0.1", 8000);
			PrintWriter output = new PrintWriter(myClientSocket.getOutputStream(),true);
			BufferedReader input = new BufferedReader(new InputStreamReader(myClientSocket.getInputStream()));
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		) {
			String userInput = "";
			System.out.println(input.readLine()); // reads the first message from the server and prints it
			System.out.println("Enter 1 for a LCR simulation, enter 2 for a HC simulation.");
			boolean validSim = false;

			//loops until an input is valid.
			while (!validSim) {
				try {
					//Inputs the line, then compares the input to either correct input.
					//If not valid, loop continues.
					String simType = stdIn.readLine();

					if (simType.equals("1")) {
						userInput += "LCR ";
						validSim = true;
						continue;
					}
					
					if (simType.equals("2")) {
						userInput += "HC ";
						validSim = true;
						continue;
					}	

					//Prints error message
					System.out.println("Error: Please enter 1 or 2.");
				} catch(IOException e) {
					//Handles error.
					System.out.println("Error: " + e);
				}
			}

			System.out.println("Enter the IDs of the devices in the simulation, seperating them with a space.");
			boolean validInput = false;
			System.out.println("Enter a set of IDs, seperating each ID with a space.");
			boolean validIDs = false;
	
			//loops until an input is valid.
			while (!validIDs) {
				try {
					//Inputs the line, then compares the input to either correct input.
					//If not valid, loop continues.
					String simType = stdIn.readLine();
					String[] simIDs = simType.split(" ");
					
					//Calls validNumbers to check if the string array produced can be converted
					//to an Int array.
					if (validNumbers(simIDs)) {
						validIDs = true;
						for (int i = 0; i < simIDs.length; i++) {
							userInput += (" " + simType);
						}
						System.out.println("IDs entered are valid.");
						continue;
					} 
	
					//Prints error message
					System.out.println("Error: Please enter a valid set of IDs.");
				} catch(IOException e) {
					//Handles error.
					System.out.println("Error: " + e);
				}
			}

			output.println(userInput); // user's input transmitted to server.
			System.out.println(input.readLine()); // reads server's ack and prints it.
			System.out.println(input.readLine()); // reads server's return of the time.
			System.out.println(input.readLine()); // reads server's return of the elected processor.
			System.out.println("-----------End of communication-----------");
			System.out.println("\nCommunication with server '127.0.0.1' was successful! Now closing...");
			
			
		} catch (UnknownHostException e) {
            System.err.println("Don't know about host '127.0.0.1'");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to 127.0.0.1");
			e.printStackTrace();
            System.exit(1);
        }
	
	}

	//Makes sure that the input taken in for the IDs is valid.
	private static boolean validNumbers(String[] var) {
		int[] c = new int[var.length];

		try {
			//Converts all strings within var into ints.
			for (int i = 0; i < var.length; i++) {
				c[i] = Integer.parseInt(var[i]);
			}
			return true;
		} catch (NumberFormatException e) {
			//If it can't convert, it's not valid.
			return false;
		}
	}


}