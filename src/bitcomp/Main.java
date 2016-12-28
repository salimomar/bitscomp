/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bitcomp;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

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
        FileOutputStream fouts = new FileOutputStream("c:/Users/somar/temp/test1..result.txt");
        
        Mapper m = new Mapper(new BitInputStream( fins ));
        
        m.process();
        
        
        m.dump( fouts );
    }
}

class Entry
{
    private int r,c;
    
    public Entry(int r, int c)
    {
        this.r = r;
        this.c = c;
    }
    
    public int r()
    {
        return getR();
    }
    
    public int c()
    {
        return getC();
    }
    
    @Override
    public boolean equals(Object o)
    {
        if( o == null ) return false;
        if( !( o instanceof Entry ) ) return false;
        
        Entry eo = (Entry) o;
        
        return  (getR() == eo.getR()) && (getC() == eo.getC());
    }

    
    @Override
    public int hashCode()
    {
        return toString().hashCode();
    }
    
    @Override
    public String toString()
    {
        return "[" + getR() + ':' + getC() + ']';
    }

    /**
     * @return the r
     */
    public int getR() {
        return r;
    }

    /**
     * @param r the r to set
     */
    public void setR(int r) {
        this.r = r;
    }

    /**
     * @return the c
     */
    public int getC() {
        return c;
    }

    /**
     * @param c the c to set
     */
    public void setC(int c) {
        this.c = c;
    }
}
class Mapper
{
    private BitInputStream seq;
    
    private int min_r = 0;
    private int max_r = 0;
    private int max_c = 0;
    
    private HashMap<Entry,Character> map = new HashMap<>();
    
    public Mapper(BitInputStream seq)
    {
        this.seq = seq;
    }
    
    public void process() throws IOException
    {
        int r = 0;
        int c = 0;
        
        char b = '2';
        
        try
        {
            for( ;; )
            {
                final boolean v = seq.readBit();

                char d;

                if( v )
                {
                    d = '1';
                    --r;
                }
                else
                {
                    d = '0';
                    ++r;
                }

                if( b != d )
                {
                    r += (v) ? 1 : -1;
                }

                b = d;

                Entry e = new Entry( r, c );

                min_r = Math.min( min_r, r);
                max_r = Math.max( max_r, r);
                max_c = c;

                map.put( e, d );

                //System.out.println( e + " -> "  + d  +  " r = " +  r);

                ++c;
            }
        }
        catch( final EOFException e )
        {
        }
    }
    
    public void dump(final OutputStream outs) throws IOException
    {
        for( int r = min_r; r <= max_r; ++r)
        {
            StringBuilder result  = new StringBuilder();
            
            boolean foundOne = false;
            Entry e = new Entry(0,0);
            for( int c = 0; c <= max_c; ++c )
            {
                e.setC(c);
                e.setR(r);
                
                if( map.containsKey( e ) )
                {
                    result.append( map.get(e) );
                    foundOne = true;
                }
                else
                {
                    result.append(' ');
                }
            }
            
            if( foundOne ) {
                result.append('\n');
                outs.write( result.toString().getBytes() );
            }
        }
    }
    
}
