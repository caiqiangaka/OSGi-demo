package org.qiang.ipccommunicationbundle.service.user;

public interface IpcCommunication {
	public boolean init( int dev_id, String dev ) throws Exception;
	public int send( int dev_id, byte[] send_buf_byte, int send_len ) throws Exception;
	public byte[] receiv( int dev_id ) throws Exception;
	public boolean close( int dev_id ) throws Exception;
}