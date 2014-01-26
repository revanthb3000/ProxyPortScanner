package org.iitg.rb.concurrency;

import org.iitg.rb.core.PortScanner;

public class ScannerRunnable implements Runnable {

	private PortScanner portScanner;
	
	private int startPort;
	
	private int endPort;

	public ScannerRunnable(PortScanner portScanner, int startPort, int endPort) {
		super();
		this.portScanner = portScanner;
		this.startPort = startPort;
		this.endPort = endPort;
		//System.out.println("Created a Task <"+startPort + " , " + endPort +">");
	}

	@Override
	public void run() {
		portScanner.findOpenPorts(startPort, endPort);
	}
}