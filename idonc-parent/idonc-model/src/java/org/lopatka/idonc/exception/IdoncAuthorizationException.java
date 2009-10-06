/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.lopatka.idonc.exception;

import java.io.Serializable;

/**
 *
 * @author Bartek
 */
public class IdoncAuthorizationException extends IdoncException implements Serializable {
    private static final long serialVersionUID = 810445236110474300L;

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
