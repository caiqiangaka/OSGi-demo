package org.qiang.udpipccommunicationimplbundle.impl;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.qiang.ipccommunicationbundle.service.user.*;

public class UdpIpcCommunicationImpl implements IpcCommunication {
	private DatagramSocket client_sock;
	private String server_ip;
	private static int server_port = 44245;
	
	public boolean init( String dev ) throws Exception {
		server_ip = dev;
		try {
			client_sock = new DatagramSocket();
			client_sock.setSoTimeout(5 * 1000);
		} catch (SocketException e) {
            System.err.println( "Can't open socket" );
            e.printStackTrace();
            return false;
        }
		
		return true;
	}
	
	public int send( byte[] send_buf_byte, int send_len ) throws Exception {
		InetAddress server_addr;
		
		System.out.println( "Udp Ipc Communication Send Data ..." );
		
		int i = 0;
		for ( i = 0; i < send_len; i++ ) {
			System.out.printf("%x ", send_buf_byte[i] );
		}
		System.out.printf( "\n" );
		
		try {
			server_addr = InetAddress.getByName( server_ip );
		} catch (UnknownHostException e) {
			System.err.println( "Cannot find host" );
			e.printStackTrace();
			return -1;
		}
		
		DatagramPacket send_packet = new DatagramPacket( send_buf_byte, send_len, server_addr, server_port );
		
		try {
			client_sock.send( send_packet );
		} catch (IOException e) {
			e.printStackTrace();
            return -1;
        }
		
		return send_len;
	}
	
	public byte[] receiv()  throws Exception {
		byte[] recv_buf = new byte[4096];
		DatagramPacket recv_packet = new DatagramPacket( recv_buf , recv_buf.length );
		String recv_data_str;
		
		System.out.println( "Udp Ipc Communication Recv Data ..." );
		
		try {
			client_sock.receive( recv_packet );
		} catch (IOException e) {
			e.printStackTrace();
            return null;
        }
		
		recv_data_str = new String( recv_packet.getData(), 0, recv_packet.getLength() );
		
		int i = 0;
		byte[] recv_data_byte = recv_data_str.getBytes();
		for ( i = 0; i < recv_data_byte.length; i++ ) {
			System.out.printf("%x ", recv_data_byte[i] );
		}
		System.out.printf( "\n" );
		
		return recv_data_str.getBytes();
	}
	
	public boolean close() throws Exception {
		client_sock.close();
		
		return true;
	}
}