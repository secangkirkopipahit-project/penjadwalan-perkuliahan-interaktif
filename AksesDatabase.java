import java.sql.*;

public class AksesDatabase
{
   public Connection connection;
   public Statement statement;
   public ResultSet rs;

   public AksesDatabase()
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

   public void simpan(String s)
   {
      try
      {
         statement = connection.createStatement();
         statement.executeUpdate(s);
         statement.close();
      }
      catch(Exception e)
      {
         System.out.println("Error :"+e);
      }
   }

   public void hapus(String s)
   {
      try
      {
         statement = connection.createStatement();
         statement.executeUpdate(s);
         statement.close();
         System.out.print("Data telah dihapus");
      }
      catch(Exception e)
      {
         System.out.println("Error :"+e);
      }
   }

   public void ubah(String s)
   {
      try
      {
         statement = connection.createStatement();
         statement.executeUpdate(s);
         statement.close();
         System.out.print("Data telah diupdate");
      }
      catch(Exception e)
      {
         System.out.println("Error :"+e);
      }
   }
}