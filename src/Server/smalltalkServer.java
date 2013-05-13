package Server;

import java.net.*;

public class smalltalkServer extends Thread {

	DatagramPacket pkt;

	public smalltalkServer(DatagramPacket pkt) {
		this.pkt = pkt;
	}

	public void run() {

	}

	public static void main(String [] args) {
		try {
			@SuppressWarnings("resource")
			DatagramSocket srv = new DatagramSocket(9203);
			while (true) {
				byte [] buf = new byte[1024];
				DatagramPacket pkt = new DatagramPacket(buf, 1024);
				srv.receive(pkt);
				new smalltalkServer(pkt).start();	
			}
		} catch (Exception e) {
			System.err.println(e);
		}
	}
}
