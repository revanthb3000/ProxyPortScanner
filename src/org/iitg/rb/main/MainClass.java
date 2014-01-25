package org.iitg.rb.main;

import org.iitg.rb.core.PortScanner;

public class MainClass {
	
	public static void main(String[] args){
		PortScanner portScanner = new PortScanner("172.16.27.15", 200);
		System.out.println(portScanner.findOpenPorts(3500, 3600));
	}

}
