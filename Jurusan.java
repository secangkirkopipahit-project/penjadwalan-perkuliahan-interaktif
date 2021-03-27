import java.sql.*;

class Jurusan
{
   private String kdjur;
   private String nmjur;
   private String nmfak;

   public Connection connection;
   public Statement statement;

   Jurusan()
   {
      try
      {
         Class.forName("org.sqlite.JDBC");
         connection = DriverManager.getConnection("jdbc:sqlite:DatabaseJadwalKuliah.db3");
      }
      catch (Exception e)
      {
      }
   }

   Jurusan(String kodejurusan,String namajurusan,String namafakultas)
   {
      kdjur=kodejurusan;
      nmjur=namajurusan;
      nmfak=namafakultas;

      try
      {
         Class.forName("org.sqlite.JDBC");
         connection = DriverManager.getConnection("jdbc:sqlite:DatabaseJadwalKuliah.db3");
      }
      catch (Exception e)
      {
      }
   }

   public void setKodejur(String kodejurusan)
   {
      kdjur=kodejurusan;
   }

   public String getKodejurusan()
   {
      return kdjur;
   }

   public String getKodejurusan(String namajurusan)
   {
      Reusable re=new Reusable();

      if(namajurusan.equals("GENERAL"))
      {
         kdjur="00";
      }
      else
      {
         String ssql="select Jur_Kode from Jurusan where Jur_Nama='"+namajurusan+"'";
         kdjur=re.getOneItem(connection,statement,ssql);
      }
      return kdjur;
   }

   public void setNamajur(String namajurusan)
   {
      nmjur=namajurusan;
   }

   public String getNamajurusan()
   {
      return nmjur;
   }

   public void setKodefak(String kodefakultas)
   {
      nmfak=kodefakultas;
   }
   
   public String getKodefak()
   {
       return nmfak;
   }

   public static void main(String args[])
   {
      Jurusan jur=new Jurusan();
      String kodejurusan=jur.getKodejurusan("TEKNIK INFORMATIKA");

      System.out.println(kodejurusan);
   }
}