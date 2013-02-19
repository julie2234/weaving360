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
        Controls control = new WeaveControls();
        control.start();
    }

}
