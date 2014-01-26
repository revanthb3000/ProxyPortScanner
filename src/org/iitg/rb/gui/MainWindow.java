package org.iitg.rb.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;

import org.iitg.rb.main.MainClass;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainWindow {

	private JFrame frmPortscanner;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frmPortscanner.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frmPortscanner = new JFrame();
		frmPortscanner.setTitle("PortScanner");
		frmPortscanner.setBounds(100, 100, 525, 271);
		frmPortscanner.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmPortscanner.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(104, 69, 135, 20);
		frmPortscanner.getContentPane().add(textField);
		textField.setColumns(10);
		
		final JTextArea textArea = new JTextArea();
		textArea.setBounds(274, 36, 208, 180);
		frmPortscanner.getContentPane().add(textArea);
		MainClass.setTextArea(textArea);
		
		
		JLabel lblIpAddress = new JLabel("IP Address");
		lblIpAddress.setHorizontalAlignment(SwingConstants.TRAILING);
		lblIpAddress.setBounds(10, 71, 81, 17);
		frmPortscanner.getContentPane().add(lblIpAddress);
		
		JLabel lblOpenPorts = new JLabel("Open Ports:");
		lblOpenPorts.setBounds(340, 11, 86, 14);
		frmPortscanner.getContentPane().add(lblOpenPorts);
		
		JButton btnScan = new JButton("Scan");
		btnScan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MainClass.getOpenPorts(textField.getText());
			}
		});
		btnScan.setBounds(104, 100, 89, 23);
		frmPortscanner.getContentPane().add(btnScan);
	}
}
