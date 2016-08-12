package org.osgidemo.uservalidatorwebbundle.web;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgidemo.uservalidatorbundle.service.user.Validator;

public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    
    private BundleContext context;
    
    public LoginServlet(BundleContext context){
        this.context=context;
    }
    
    /** 
     * Web Post Method 
     */
    public void doPost(HttpServletRequest request,  HttpServletResponse response)
              throws IOException{
        doGet(request, response);
    }

    /** 
     * Web GET Method 
     */
    public void doGet(HttpServletRequest request,  HttpServletResponse response) throws IOException {
        String username=request.getParameter("username");
        String userpass=request.getParameter("userpass");
        response.setContentType("text/html");
        ServletOutputStream output=response.getOutputStream();
        ServiceReference serviceRef=context.getServiceReference(Validator.class.getName());
        Validator validator=(Validator) context.getService(serviceRef);
        if(validator==null){
            output.println("No usable validator service");
            output.close();
            return;
        }
        boolean result=false;
        try {
            result=validator.validate(username, userpass);
            if(result)
                output.println("Login success");
            else
                output.println("Login Fail,please check username and password");
            output.close();
            return;
        } 
        catch (Exception e) {
            output.println("Login error:");
            output.println(e.toString());
            return;
        }
    }

}