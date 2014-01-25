package org.iitg.rb.core;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;

/**
 * This the port scanner class. Contains functions that will scan the ports and find an open one !
 * @author RB
 *
 */
public class PortScanner {

	/**
	 * Proxy IP Address
	 */
	private String ipAddress;

	/**
	 * TimeOut value. Set it to 200ms for good performence.
	 */
	private int timeOut;

	public PortScanner(String ipAddress, int timeOut) {
		super();
		this.ipAddress = ipAddress;
		this.timeOut = timeOut;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}

	/**
	 * Iterates through a range and finds open ports.
	 * @param startPort Starting value of range.
	 * @param endPort Last value of range.
	 * @return ArrayList of Integers that are open ports on this IP Address.
	 */
	public ArrayList<Integer> findOpenPorts(int startPort, int endPort) {
		ArrayList<Integer> openPorts = new ArrayList<Integer>();
		for (int port = startPort; port <= endPort; port++) {
			System.out.println(port);
			if(isPortOpen(port))
				openPorts.add(port);
		}
		return openPorts;
	}

	/**
	 * Simple logic. Try connecting to the port and if there's no exception, then it's open !! 
	 * @param port Port to be scanned
	 * @return True if the port is open. False, otherwise.
	 */
	public boolean isPortOpen(int port) {
		try {
			Socket socket = new Socket();
			socket.connect(new InetSocketAddress(this.ipAddress, port),
					this.timeOut);
			socket.close();
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

}
