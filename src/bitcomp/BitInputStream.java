/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bitcomp;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

public class BitInputStream extends InputStream { 
    private final InputStream in; 
    private int bitField = -1; 
    private int mask; 
 
    public BitInputStream(InputStream in) { 
        this.in = in; 
    } 
 
    /**
     * Wrap or cast an InputStream to a BitInputStream. 
     * @param in
     * @return 
     */ 
    public static BitInputStream toBitInputStream(InputStream in) { 
        return in instanceof BitInputStream ? (BitInputStream) in : new BitInputStream(in); 
    } 
 
    @Override 
    public int read() throws IOException { 
        bitField = -1; 
        return in.read(); 
    } 
 
    @Override 
    public int read(byte[] b, int off, int len) throws IOException { 
        bitField = -1; 
        return in.read(b, off, len); 
    } 
 
    /**
     * Read one bit from the stream in little endian order. 
     * <p> 
     * Consecutive calls to this method will consume one bit at a time from the underlying stream. If any of the other 
     * read methods are called all remaining bits up to the next byte boundary will be discarded. 
     * 
     * @return the next bit from the stream in little endian order 
     */ 
    public boolean readBit() throws IOException { 
        if (bitField == -1) { 
            bitField = in.read(); 
            if (bitField == -1) { 
                throw new EOFException(); 
            } 
            mask = 1; 
        } 
 
        boolean bit = (bitField & mask) != 0; 
        if ((mask <<= 1) == 0x100) { 
            bitField = -1; 
        } 
        return bit; 
    } 
 
    @Override 
    public void close() throws IOException { 
        in.close(); 
    } 
}