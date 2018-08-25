/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threadcompetition;
import java.io.IOException;
import javax.swing.SwingUtilities;
/**
 *
 * @author Sergio Hidalgo
 */
public class ThreadCompetition {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        SwingUtilities.invokeLater(new InitInterfaceAndThreads());
    }
    
}
