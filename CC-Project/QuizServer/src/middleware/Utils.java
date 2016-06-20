/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middleware;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author carlosmorais
 */

//este código não foi feito por mim

public class Utils {    
    
    public static byte[] toBytes(Object o){
        byte[] data = null;
        try{
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(o);
            oos.flush();
            oos.close();
            bos.close();
            data = bos.toByteArray();
        }
        catch(Exception e){System.out.println(e);}
        return data;
    }

    public static Object fromBytes(byte[] bytes){
	Object object = null;
	try{
	    object = new java.io.ObjectInputStream(new java.io.ByteArrayInputStream(bytes)).readObject();
	    }catch(java.io.IOException ioe){
	    java.util.logging.Logger.global.log(java.util.logging.Level.SEVERE,ioe.getMessage());
	}catch(java.lang.ClassNotFoundException cnfe){
	java.util.logging.Logger.global.log(java.util.logging.Level.SEVERE,cnfe.getMessage());
	}
	return object;
    }

}
