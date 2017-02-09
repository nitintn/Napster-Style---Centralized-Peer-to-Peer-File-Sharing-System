package server;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ListIterator;
import java.util.Scanner;


public class P2PServer {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			ServerSocket socket = new ServerSocket(5004);
			System.out.println("Server is up and running!!!");
			while(true){
				Socket serverSocket = socket.accept();
				//thread creation
				Thread t = new Thread(new PeerConnect(serverSocket));
				t.start();
			}		
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
}

/*
 * Class PeerConnect has all the functionality
 * It runs on the thread 
 */
class PeerConnect implements Runnable{
	
	public static HashMap<String,ArrayList<String>> fileMap = new HashMap<String, ArrayList<String>>();
	
	public Socket clientSocket;
	int count=0;
	// constructor to initialize socket
	public PeerConnect(Socket socket)throws IOException{
		this.clientSocket = socket;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Boolean condition = true;
		String check = "" ;
		Scanner sc = new Scanner(System.in);
		
			while(condition){
				try{
					String peerId, fileName, searchResult, error, checkId;
					String option;
					
					String location = "";
					ArrayList <String> fileLocation = new ArrayList<String>();
					
					Scanner serverIn = new Scanner(clientSocket.getInputStream());
					PrintStream serverOut = new PrintStream(clientSocket.getOutputStream());
					
					peerId = serverIn.nextLine();
					
					//Automatically registering files as the peer is connected
					File peerDirectory = new File("E:/Java/Workspace/PA1_CentralizedP2PSystem/sharedfolder/"+peerId+"/");
					File[] sharedFiles = peerDirectory.listFiles();
					
					for(int i = 0; i < sharedFiles.length; i++){
							registry(peerId, sharedFiles[i].getName());
					}
					
					//getting the option selected at client side
					option = serverIn.nextLine();
					switch(option){
				
					case "1": // case to register a file.
						//double startTime = System.currentTimeMillis();
						//registering the remaining files that are not already registered.
						for(int i = 0; i < sharedFiles.length; i++){
								registry(peerId, sharedFiles[i].getName());
						}
						/*double endTime = System.currentTimeMillis();
						System.out.println("Time taken to register: "+ (endTime-startTime)+"msec");*/
						serverOut.println(option);
						break;
						
					case "2": // case to search a file.
						
						fileName = serverIn.nextLine();
						fileLocation = lookup(fileName);
						
						try{
							for(int i = 0; i<fileLocation.size();i++){
								location += fileLocation.get(i)+" ";
							}
							searchResult = "The file is present with these peers: "+location;
							System.out.println(location);
							serverOut.println(searchResult);
							
						}
						catch(Exception e){
							error = "File not registered";
							serverOut.println(error);
						}
						
						/* ***!!!PERFORMANCE EVALUATION!!! ***/
						/*
						System.out.println("# of files registered: " + sharedFiles.length);
						//System.out.println("Registering the File(s) ...");
						double startTime =System.currentTimeMillis();
						double endTime;
						
						for(int i=0;i<sharedFiles.length;i++)
						{
							fileLocation = lookup(sharedFiles[i].getName());
							//System.out.println("The file is present with these peers: "+fileLocation);
						}
						
						for(int i = 0; i<fileLocation.size();i++){
							location += fileLocation.get(i)+" ";
						}
						
						String sendDetails = "The file is present with these Peers: "+location;
						serverOut.println(sendDetails);
						
						System.out.println("Search Complete found at: "+location);
						endTime = System.currentTimeMillis();
						double totalTime = endTime - startTime;
						System.out.println("Total time to search files: "+totalTime+"msec");
						*/
						break;
					
						
					case "3": // case to download a file
						
						serverOut.println(option);
						break;
					
					case "4": // exit case

						System.out.println("Client Connection Closing");
						condition = false;
						serverOut.println(option);
						break;
					}
				}
				catch(IOException e){
					e.printStackTrace();
				}
				
		}		
	}
	
	/*
	 * Register function - To register file present with each peer  
	 */
	public void registry(String peerId, String fileName) throws IOException{
		
		ArrayList<String> peerList = new ArrayList<String>();
		ArrayList<String> checkList = new ArrayList<String>();
		
		peerList.add(peerId);
		checkList = fileMap.get(fileName);
		
		if(checkList == null || checkList.isEmpty()){
			fileMap.put(fileName, peerList);
		}
		else{
			for(int i = 0; i <checkList.size();i++){
				if(checkList.get(i).equals(peerId)){
					checkList.remove(i);
				}
			}
			checkList.add(peerId);
			fileMap.put(fileName, checkList);
		}
	}
	
	/*
	 * Lookup file - Search file return the peer with which it is present 
	 */
	public ArrayList<String> lookup(String fileName)throws IOException{
		
		ArrayList<String> peerList = new ArrayList<String>();
		peerList = fileMap.get(fileName);
		return peerList;
		
	}
	
}