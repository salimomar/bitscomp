package bitcomp;

import java.io.EOFException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

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