
/**
 * Write a description of class Matakuliah here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Matakuliah
{
    private String kodejur;
    private String kodemk;
    private String namamk;
    private String sks;
    
    Matakuliah()
    {
    }
    
    Matakuliah(String kdjur,String kdmk,String nmmk,String s)
    {
        kodejur=kdjur;
        kodemk=kdmk;
        namamk=nmmk;
        sks=s;
    }
    
    public void setKodejur(String kdjur)
    {
        kodejur=kdjur;
    }
    
    public void setKodemk(String kdmk)
    {
        kodemk=kdmk;
    }
    
    public void setNamamk(String nmmk)
    {
        namamk=nmmk;
    }
    
    public void setSks(String s)
    {
        sks=s;
    }
    
    public String getKodejur()
    {
        return kodejur;
    }
    
    public String getKodemk()
    {
        return kodemk;
    }
    
    public String getNamamk()
    {
        return namamk;
    }
    
    public String getSks()
    {
        return sks;
    }
}
    
    
    
    
    
  
