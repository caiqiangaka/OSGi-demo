package org.osgidemo.uservalidatorbundle.service.user;

public interface Validator {
	public boolean validate( String username, String password ) throws Exception;
}