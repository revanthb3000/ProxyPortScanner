package org.iitg.rb.main;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;

import org.iitg.rb.concurrency.ScannerRunnable;
import org.iitg.rb.core.PortScanner;

public class MainClass {
	
	private static JTextArea textArea;
	
	public static JTextArea getTextArea() {
		return textArea;
	}

	public static void setTextArea(JTextArea textArea) {
		MainClass.textArea = textArea;
	}

	public static void main(String[] args) {
		//usualCase("172.16.27.15");
		//getOpenPorts("127.0.0.1");
		scanRange("172.16.52.80", "172.16.52.300");
	}
	
	public static void scanRange(String startingIpAddress, String endingIpAddress){
		String currentIpAddress = startingIpAddress;
		getOpenPorts(currentIpAddress);
		while(true){
			currentIpAddress = getNextIpAddress(currentIpAddress);
			getOpenPorts(currentIpAddress);
			if(currentIpAddress.equals(endingIpAddress)){
				break;
			}
		}
	}
	
	public static String getNextIpAddress(String ipAddress){
		String newIpAddress = "";
		ArrayList<Integer> ipComponents = new ArrayList<Integer>();
		for(String fragment : ipAddress.split("\\.")){
			ipComponents.add(Integer.parseInt(fragment));
		}
		int carryOver = 120;
		int component = ipComponents.get(3) + 1;
		carryOver = component/256;
		component = component%256;
		newIpAddress = "." + component +  newIpAddress;
		
		component = ipComponents.get(2);
		if(carryOver!=0){
			component++;
			carryOver = component/256;
			component = component%256;			
		}
		newIpAddress = "." + component +  newIpAddress;
		
		component = ipComponents.get(1);
		if(carryOver!=0){
			component++;
			carryOver = component/256;
			component = component%256;			
		}
		newIpAddress = "." + component +  newIpAddress;
		
		component = ipComponents.get(0);
		if(carryOver!=0){
			component++;
			carryOver = component/256;
			component = component%256;			
		}
		newIpAddress = component +  newIpAddress;
		return newIpAddress;
	}
	
	public static void getOpenPorts(String ipAddress){
		System.out.println("Now scanning : " + ipAddress + "\n");
		int timeOut = 200;
		int numOfThreads = 100;
		
		int startPort = 1000;
		int endPort = 5000;
		scanPortsParallel(ipAddress, timeOut, numOfThreads, startPort, endPort);
		
		startPort = 5001;
		endPort = 9999;
		scanPortsParallel(ipAddress, timeOut, numOfThreads, startPort, endPort);		
	}

	public static void scanPortsParallel(String ipAddress, int timeOut,
							int numOfThreads, int startPort, int endPort) {

		PortScanner portScanner = new PortScanner(ipAddress, timeOut);
		List<Thread> threads = new ArrayList<Thread>();
		int portRangeSize = (endPort - startPort) / numOfThreads;
		for (int i = 0; i < numOfThreads; i++) {
			Runnable task = new ScannerRunnable(portScanner, startPort, startPort + portRangeSize);
			Thread worker = new Thread(task);
			worker.setName(String.valueOf(i));
			worker.start();
			threads.add(worker);
			startPort += (portRangeSize + 1);
		}
		int running = 0;
		do {
			running = 0;
			for (Thread thread : threads) {
				if (thread.isAlive()) {
					running++;
				}
			}
		} while (running > 0);
	}

}
