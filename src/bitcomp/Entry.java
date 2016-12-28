package bitcomp;

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