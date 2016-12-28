/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bitcomp;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author somar
 */
public class Main {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        
//        byte[] bytes = { 1,0 };
//        ByteArrayInputStream bins = new ByteArrayInputStream( bytes );
//        
        FileInputStream fins = new FileInputStream("c:/Users/somar/temp/test1.txt");
        FileOutputStream fouts = new FileOutputStream("c:/Users/somar/temp/test1.result.txt");
        
        Mapper m = new Mapper(new BitInputStream( fins ));
        
        m.process();
        
        
        m.dump( fouts );
    }
}
