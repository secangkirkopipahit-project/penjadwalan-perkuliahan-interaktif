import java.sql.*;
import java.awt.event.*;
import javax.swing.*;

class FormTahunAjaran extends Form5Textfield
{
   FormTahunAjaran(Object[] dat)
   {
      super("Data Tahun Ajaran",dat);
      ActionListener handler = new MyHandler();
      KeyListener handler2 = new MyHandler2();

      btnadd.addActionListener(handler);
      btnadd.addKeyListener(handler2);
      text1.addKeyListener(handler2);
      text2.addKeyListener(handler2);
      btnsave.addKeyListener(handler2);
      btndelete.addKeyListener(handler2);
      btnupdate.addKeyListener(handler2);
   }

   public void setProperti()
   {
      label1.setText("Kode");
      label2.setText("Tahun Ajaran");
      text2.setBounds(110,30,100,20);

      label3.setVisible(false);
      text3.setVisible(false);
      label4.setVisible(false);
      text4.setVisible(false);
      label5.setVisible(false);
      text5.setVisible(false);

      menuinfodosen.setVisible(false);

      tampilData("select kodeTahun, tahunAjaran from DataTahunAjaran order by kodeTahun");
   }

   public void tampilData(String sql)
   {
      String kodenol="0";
      try
      {
         Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
         ResultSet rs=statement.executeQuery(sql);

         hapusTabel1();

         while (rs.next())
         {
            Object[] data = {rs.getString(1), rs.getString(2)};
            tabMode1.addRow(data);
         }

         statement.close();
      }
      catch (Exception DBException)
      {
         System.err.println("Error : " + DBException);
      }
   }

   public static void main(String args[])
   {
      Object[] ob={"Kode","Tahun Ajaran"};
      FormTahunAjaran fta=new FormTahunAjaran(ob);

      fta.komponenVisual();
      fta.setProperti();
   }

   class MyHandler implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         if (e.getSource() == btnadd)
         {
            bersih();
            text1.requestFocus();
         }
         else
         {
            //System.out.println("Button 2");
         }
      }
   }

   class MyHandler2 implements KeyListener
   {
      public void keyPressed(KeyEvent event)
      {
         if (event.getSource() == text1)
         {
            if(event.getKeyCode()==event.VK_ENTER)
            {
               try
               {
                  String kode=text1.getText();

                    Statement statement = connection.createStatement();
                  String sql="select tahunAjaran from DataTahunAjaran where kodeTahun='"+kode+"'";
                  ResultSet rs=statement.executeQuery(sql);

                  if(rs.next())
                  {
                     text2.setText(rs.getString(1));
                  }
                  else
                  {
                     System.out.println(kode+" tidak ada");
                  }
                  statement.close();

               }
               catch(Exception e)
               {
                  JOptionPane.showMessageDialog(null,"Data telah ada dalam database","Konfirmasi",JOptionPane.INFORMATION_MESSAGE);
               }
               text2.requestFocus();
            }
         }
         else if(event.getSource() == text2)
         {
            if(event.getKeyCode()==event.VK_ENTER)
            {
               btnsave.requestFocus();
            }
         }
         else if(event.getSource() == btnadd)
         {
            if(event.getKeyCode()==event.VK_ENTER)
            {
               bersih();
            }
         }
         else if(event.getSource() == btnsave)
         {
            if(event.getKeyCode()==event.VK_ENTER)
            {
               AksesDatabase ad=new AksesDatabase();

               String kodetahun=text1.getText();
               String tahunajaran=text2.getText();

               String sql="insert into DataTahunAjaran values ('"+kodetahun+"','"+tahunajaran+"');";
               ad.simpan(sql);

               System.out.println("Data telah masuk");
               tampilData("select kodeTahun, tahunAjaran from DataTahunAjaran order by kodeTahun");
               bersih();
            }
         }
         else if(event.getSource() == btndelete)
         {
            if(event.getKeyCode()==event.VK_ENTER)
            {
               AksesDatabase ad=new AksesDatabase();

               String kodetahun=text1.getText();

               String s="delete from DataTahunAjaran where kodeTahun='"+kodetahun+"'";
               ad.hapus(s);
               tampilData("select kodeTahun, tahunAjaran from DataTahunAjaran order by kodeTahun");
               bersih();
            }
         }
         else if(event.getSource() == btnupdate)
         {
            if(event.getKeyCode()==event.VK_ENTER)
            {
               AksesDatabase ad=new AksesDatabase();

               String kodetahun=text1.getText();
               String tahunajaran=text2.getText();

               String s="update DataTahunAjaran set tahunAjaran='"+tahunajaran+"' where kodeTahun='"+kodetahun+"'";
               ad.ubah(s);
               tampilData("select kodeTahun, tahunAjaran from DataTahunAjaran order by kodeTahun");
               bersih();
            }
         }
         else
         {
            //System.out.println("Button 2");
         }
      }

      public void keyReleased(KeyEvent event)
      {

      }

      public void keyTyped(KeyEvent event)
      {

      }
   }
}