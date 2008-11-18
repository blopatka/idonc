/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.lopatka.idonc.exception;

/**
 *
 * @author 129370
 */
public class IdoncException extends Error {
    private static final long serialVersionUID = 3352807061548310065L;

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
