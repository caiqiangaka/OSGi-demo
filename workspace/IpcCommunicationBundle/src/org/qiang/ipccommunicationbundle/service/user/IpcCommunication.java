package org.qiang.ipccommunicationbundle.service.user;

public interface IpcCommunication {
	public boolean init( String dev ) throws Exception;
	public int send( byte[] send_buf_byte, int send_len ) throws Exception;
	public byte[] receiv() throws Exception;
	public boolean close() throws Exception;
}