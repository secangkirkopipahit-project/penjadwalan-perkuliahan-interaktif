
import java.sql.*;
import java.util.*;

class Reusable
{
   public String getOneItem(Connection connection,Statement statement,String ssql)
   {
      String yangdicari="";
      try
      {
         statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
         ResultSet rs=statement.executeQuery(ssql);

         if(rs.next())
         {
            yangdicari=rs.getString(1);
         }
         statement.close();
      }
      catch (Exception DBException)
      {
         System.err.println("Error : " + DBException);
      }
      return yangdicari;
   }

   public String[] getArray1Dimensi(Connection connection,Statement statement,String ssql)
   {
      int banyakdata=0;
      String[] yangdicari=new String[banyakdata];
      Vector<String> v=new Vector<String>(10);

      try
      {
         statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
         String sql=ssql;
         ResultSet rs=statement.executeQuery(sql);

         while(rs.next())
         {
            v.add(rs.getString(1));
         }

         banyakdata=v.size();
         yangdicari=new String[banyakdata];

         for(int i=0; i<banyakdata; i++)
         {
            yangdicari[i]=v.get(i);
         }

         statement.close();
      }
      catch (Exception DBException)
      {
         System.err.println("Error 2: " + DBException);
      }
      return yangdicari;
   }
}