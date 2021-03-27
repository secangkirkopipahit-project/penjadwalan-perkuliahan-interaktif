
/**
 * Write a description of class Ruang here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Ruang
{
    private String nmjur;
    private String ruang;
    private String kapasitas;
    
    Ruang()
    {   
    }
    
    Ruang(String njur,String ru,String kap)
    {
        nmjur=njur;
        ruang=ru;
        kapasitas=kap;
    }
    
    public String getNamaJurusan()
    {
        return nmjur;
    }
    
    public String getRuang()
    {
        return ruang;
    }
    
    public String getKapasitas()
    {
        return kapasitas;
    }
}
