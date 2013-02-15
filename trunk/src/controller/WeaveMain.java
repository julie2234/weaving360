/**
 * 
 */
package controller;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 */
public class WeaveMain {
    /**
     * @param args
     * @throws IOException 
     * @throws FileNotFoundException 
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        ControlInterface control = new Controls();
        control.start();
    }

}
