/**
 * 
 */
package controller;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Main to run weave application.
 */
public class WeaveMain {
    /**
     * Runs weave application
     * @param args main arguments
     * @throws IOException Exception for repository access failure. 
     * @throws FileNotFoundException Exception for repository access failure.
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        Controls control = new WeaveControls();
        control.start();
    }

}
