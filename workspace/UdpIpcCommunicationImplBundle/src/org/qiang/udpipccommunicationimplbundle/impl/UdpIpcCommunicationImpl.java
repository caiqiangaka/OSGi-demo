package org.qiang.udpipccommunicationimplbundle.impl;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.qiang.ipccommunicationbundle.service.user.*;

class DatagramSocketNode {
	int dev_id;
	DatagramSocket client_sock;
}

class ServerIpNode {
	int dev_id;
	String server_ip;
}

class ServerPortNode {
	int dev_id;
	int server_port;
}

public class UdpIpcCommunicationImpl implements IpcCommunication {
	private List<DatagramSocketNode> client_sock_list = new ArrayList<DatagramSocketNode>();
	private List<ServerIpNode> server_ip_list = new ArrayList<ServerIpNode>();
	private static List<ServerPortNode> server_port_list = new ArrayList<ServerPortNode>();
	private final int DefaultUdpServerPort = 44245;
	
	private boolean check_dev_id_valid( int dev_id ) {
		int i = 0;
		
		for ( i = 0; i < client_sock_list.size(); i++ ) {
			if ( dev_id == client_sock_list.get( i ).dev_id ) {
				return false;
			}
		}
		
		return true;
	}
	
	private DatagramSocket get_client_socket( int dev_id ) {
		int i = 0;
		
		for ( i = 0; i < client_sock_list.size(); i++ ) {
			if ( dev_id == client_sock_list.get( i ).dev_id ) {
				return client_sock_list.get( i ).client_sock;
			}
		}
		
		return null;
	}
	
	private String get_server_ip( int dev_id ) {
		int i = 0;
		
		for ( i = 0; i < server_ip_list.size(); i++ ) {
			if ( dev_id == server_ip_list.get( i ).dev_id ) {
				return server_ip_list.get( i ).server_ip;
			}
		}
		
		return null;
	}
	
	private int get_server_port( int dev_id ) {
		return DefaultUdpServerPort;
	}
	
	private boolean delete_client_sock( int dev_id ) {
		int i = 0;
		
		for ( i = 0; i < client_sock_list.size(); i++ ) {
			if ( dev_id == client_sock_list.get( i ).dev_id ) {
				client_sock_list.remove( i );
				return true;
			}
		}
		
		return false;
	}
	
	private boolean delete_server_ip( int dev_id ) {
		int i = 0;
		
		for ( i = 0; i < server_ip_list.size(); i++ ) {
			if ( dev_id == server_ip_list.get( i ).dev_id ) {
				server_ip_list.remove( i );
				return true;
			}
		}
		
		return false;
	}
	
	private boolean delete_server_port( int dev_id ) {
		int i = 0;
		
		for ( i = 0; i < server_port_list.size(); i++ ) {
			if ( dev_id == server_port_list.get( i ).dev_id ) {
				server_port_list.remove( i );
				return true;
			}
		}
		
		return false;
	}
	
	public boolean init( int dev_id, String dev ) throws Exception {
		
		DatagramSocket clientSock;
		ServerIpNode servIpTmp;
		DatagramSocketNode clientSockTmp;
		ServerPortNode servPortTmp;
		
		// check param dev_id
		if ( false == check_dev_id_valid( dev_id ) ) {
			System.err.println( "Device ID is already in the list" );
			return false;
		}
		
		try {
			clientSock = new DatagramSocket();
			clientSock.setSoTimeout(5 * 1000);
		} catch (SocketException e) {
            System.err.println( "Can't open socket" );
            e.printStackTrace();
            return false;
        }
		
		servIpTmp = new ServerIpNode();
		servIpTmp.dev_id = dev_id;
		servIpTmp.server_ip = dev;
		server_ip_list.add( servIpTmp );
		
		clientSockTmp = new DatagramSocketNode();
		clientSockTmp.dev_id = dev_id;
		clientSockTmp.client_sock = clientSock;
		client_sock_list.add( clientSockTmp );
		
		servPortTmp = new ServerPortNode();
		servPortTmp.dev_id = dev_id;
		servPortTmp.server_port = DefaultUdpServerPort;
		server_port_list.add( servPortTmp );
		
		return true;
	}
	
	public int send( int dev_id, byte[] send_buf_byte, int send_len ) throws Exception {
		String server_ip;
		DatagramSocket client_sock;
		int server_port;
		InetAddress server_addr;
		
		System.out.println( "Udp Ipc Communication Send Data ..." );
		
		int i = 0;
		for ( i = 0; i < send_len; i++ ) {
			System.out.printf("%x ", send_buf_byte[i] );
		}
		System.out.printf( "\n" );
		
		server_ip = get_server_ip( dev_id );
		if ( null == server_ip ) {
			System.err.println( "Get server ip failed" );
			return -1;
		}
		
		client_sock = get_client_socket( dev_id );
		if ( null == client_sock ) {
			System.err.println( "Get socket failed" );
			return -1;
		}
		
		server_port = get_server_port( dev_id );
		if ( -1 == server_port ) {
			System.err.println( "Get server port failed" );
			return -1;
		}
		
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
	
	public byte[] receiv( int dev_id )  throws Exception {
		DatagramSocket client_sock;
		byte[] recv_buf = new byte[4096];
		DatagramPacket recv_packet = new DatagramPacket( recv_buf , recv_buf.length );
		String recv_data_str;
		
		System.out.println( "Udp Ipc Communication Recv Data ..." );
		
		client_sock = get_client_socket( dev_id );
		if ( null == client_sock ) {
			System.err.println( "Get socket failed" );
			return null;
		}
		
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
	
	public boolean close( int dev_id ) throws Exception {
		DatagramSocket client_sock;
		
		client_sock = get_client_socket( dev_id );
		if ( null == client_sock ) {
			System.err.println( "Get socket failed" );
			return false;
		}
		
		client_sock.close();
		
		if ( false == delete_client_sock( dev_id ) ) {
			System.err.println( "Delete socket failed" );
		}
		
		if ( false == delete_server_ip( dev_id ) ) {
			System.err.println( "Delete server ip failed" );
		}
		
		if ( false == delete_server_port( dev_id ) ) {
			System.err.println( "Delete server port failed" );
		}
		
		return true;
	}
}