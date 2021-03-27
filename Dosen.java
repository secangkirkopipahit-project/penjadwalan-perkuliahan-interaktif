
/**
 * Write a description of class Dosen here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Dosen
{
    private String nip;
    private String gelardepan;
    private String nama;
    private String gelarbelakang;
    private String kodedosen;
    
    Dosen()
    {
    }
    
    Dosen(String ni,String gd,String nm,String gb,String kddosen)
    {
        nip=ni;
        gelardepan=gd;
        nama=nm;
        gelarbelakang=gb;
        kodedosen=kddosen;
    }
    
    public String getNip()
    {
        return nip;
    }
    
    public String getGelarDepan()
    {
        return gelardepan;
    }
    
    public String getNama()
    {
        return nama;
    }
    
    public String getGelarBelakang()
    {
        return gelarbelakang;
    }
    
    public String getKodeDosen()
    {
        return kodedosen;
    }
    
    
    
    
    
}
