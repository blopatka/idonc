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
public class IdoncException extends Error implements Serializable {
    private static final long serialVersionUID = -430743025385372476L;

    /**
     * Creates a new instance of <code>IdoncException</code> without detail message.
     */
    public IdoncException() {
    }


    /**
     * Constructs an instance of <code>IdoncException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public IdoncException(String msg) {
        super(msg);
    }
}
