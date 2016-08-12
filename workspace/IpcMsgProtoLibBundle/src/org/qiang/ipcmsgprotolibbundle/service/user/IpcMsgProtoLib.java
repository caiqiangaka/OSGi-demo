package org.qiang.ipcmsgprotolibbundle.service.user;

public interface IpcMsgProtoLib {
	public boolean init( String dev ) throws Exception;
	public byte[] cmd_ipc_lib( String cmd, byte[] data_byte, int data_len ) throws Exception;
	public boolean close() throws Exception;
}