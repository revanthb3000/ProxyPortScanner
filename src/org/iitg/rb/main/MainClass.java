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
		getOpenPorts("127.0.0.1");
	}
	
	public static void getOpenPorts(String ipAddress){
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
