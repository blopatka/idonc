/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.lopatka.idonc.exception;

/**
 *
 * @author 129370
 */
public class IdoncAuthorizationException extends IdoncException {
    private static final long serialVersionUID = -8267186191161164462L;

    /**
     * Creates a new instance of <code>IdoncAuthorizationException</code> without detail message.
     */
    public IdoncAuthorizationException() {
    }


    /**
     * Constructs an instance of <code>IdoncAuthorizationException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public IdoncAuthorizationException(String msg) {
        super(msg);
    }
}
