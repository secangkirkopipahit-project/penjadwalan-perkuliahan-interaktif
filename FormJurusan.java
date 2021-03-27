import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

class FormJurusan extends Form5Textfield
{  
   Jurusan jur;

   FormJurusan(Object[] dat)
   {
      super("Program Studi",dat);

      ActionListener handler = new MyHandler();
      KeyListener handler2 = new MyHandler2();

      sortingkode.addActionListener(handler);
      sortingnama.addActionListener(handler);
      text1.addKeyListener(handler2);
      text2.addKeyListener(handler2);
      text3.addKeyListener(handler2);
      btnsave.addKeyListener(handler2);
      btnupdate.addKeyListener(handler2);
      btndelete.addKeyListener(handler2);
      btnadd.addActionListener(handler);
      btnadd.addKeyListener(handler2);

   }

   public void setProperti()
   {
      label1.setText("Kode");
      label2.setText("Program Studi");
      label3.setText("Fakultas");
   }

   public void setData()
   {
      jur=new Jurusan(text1.getText(),text2.getText(),text3.getText());
   }

   public void komponenVisual()
   {
      setJMenuBar(menuinfodosen);
      getContentPane().setLayout(null);

      menuinfodosen.add(sorting);
      sorting.setMnemonic('S');
      sorting.add(sortingkode);
      sorting.add(sortingnama);

      getContentPane().add(label1);
      label1.setBounds(10,10,100,20);
      getContentPane().add(text1);
      text1.setBounds(110,10,100,20);

      getContentPane().add(btnadd);
      btnadd.setBounds(212,10,50,19);

      getContentPane().add(label2);
      label2.setBounds(10,30,100,20);
      getContentPane().add(text2);
      text2.setBounds(110,30,200,20);

      getContentPane().add(label3);
      label3.setBounds(10,50,100,20);
      getContentPane().add(text3);
      text3.setBounds(110,50,100,20);

      getContentPane().add(pnltombol);
      pnltombol.setBounds(10,90,370,45);
      pnltombol.setBorder(BorderFactory.createEtchedBorder(1));

      pnltombol.setLayout(null);
      pnltombol.add(btnsave);
      btnsave.setBounds(8,10,80,25);
      pnltombol.add(btnupdate);
      btnupdate.setBounds(90,10,80,25);
      pnltombol.add(btndelete);
      btndelete.setBounds(172,10,80,25);

      tabel1.setModel(tabMode1);
      //
      tabel1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
      TableColumn colkode=tabel1.getColumnModel().getColumn(0);
      colkode.setPreferredWidth(77);
      TableColumn colnama=tabel1.getColumnModel().getColumn(1);
      colnama.setPreferredWidth(200);
      //
      getContentPane().add(scroll_panel1);
      scroll_panel1.setBounds(10,145,370,370);
      scroll_panel1.getViewport().add(tabel1);
      tabel1.setEnabled(false);
      tampilData("select Jur_kode, Jur_Nama,Fak_Kode from Jurusan order by Jur_Kode");

      getContentPane().add(lblsoftwarehouse);
      lblsoftwarehouse.setBounds(264,515,150,20);
      lblsoftwarehouse.setFont(new Font("Courier New",Font.PLAIN,9));

      setVisible(true);
   }

   public void bersih()
   {
      text1.setText("");
      text2.setText("");
      text3.setText("");
      text1.requestFocus();
   }

   class MyHandler implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {

         if (e.getSource() == sortingkode)
         {
            tampilData("select Jur_kode, Jur_Nama,Fak_Kode from Jurusan order by Jur_Kode");
         }
         else if (e.getSource() == sortingnama)
         {
            tampilData("select Jur_kode, Jur_Nama,Fak_Kode from Jurusan order by Jur_Nama");
         }
         else if (e.getSource() == btnadd)
         {
            bersih();
         }
         else
         {
         }

      }
   }

   class MyHandler2 implements KeyListener
   {
      public void keyPressed(KeyEvent event)
      {
         if(event.getSource() == text1)
         {
            if(event.getKeyCode()==event.VK_ENTER)
            {
               try
               {
                  //String kodejur=text1.getText();
                  setData();

                  Statement statement = connection.createStatement();
                  String sql="select Jur_Nama,Fak_Kode from Jurusan where Jur_Kode='"+jur.getKodejurusan()+"'";
                  ResultSet rs=statement.executeQuery(sql);

                  if(rs.next())
                  {
                     text2.setText(rs.getString(1));
                     text3.setText(rs.getString(2));
                  }
                  else
                  {
                     System.out.println(jur.getKodejurusan()+" tidak ada");
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
               text3.requestFocus();
            }
         }
         else if(event.getSource() == text3)
         {
            if(event.getKeyCode()==event.VK_ENTER)
            {
               btnsave.requestFocus();
            }
         }
         else if(event.getSource() == btnsave)
         {
            if(event.getKeyCode()==event.VK_ENTER)
            {
               try
               {
                  setData();
                  AksesDatabase ad=new AksesDatabase();

                  String sql="insert into Jurusan values ('"+jur.getKodejurusan()+"','"+jur.getNamajurusan()+"','"+jur.getKodefak()+"');";
                  ad.simpan(sql);

                  System.out.println("Data telah masuk");
                  tampilData("select Jur_kode,Jur_Nama,Fak_Kode from Jurusan order by Jur_Nama");
                  bersih();
               }
               catch(Exception e)
               {
                  JOptionPane.showMessageDialog(null,"Data telah ada dalam database","Konfirmasi",JOptionPane.INFORMATION_MESSAGE);
               }
            }
         }
         else if(event.getSource() == btnupdate)
         {
            if(event.getKeyCode()==event.VK_ENTER)
            {
               setData();
               System.out.println(jur.getNamajurusan());
               System.out.println(jur.getKodefak());

               AksesDatabase ad=new AksesDatabase();

               String s="update Jurusan set Jur_Nama='"+jur.getNamajurusan()+"',Fak_Kode='"+jur.getKodefak()+"' where Jur_kode='"+jur.getKodejurusan()+"'";
               ad.ubah(s);
               tampilData("select Jur_kode,Jur_Nama,Fak_Kode from Jurusan order by Jur_Nama");
               bersih();
            }
         }
         else if(event.getSource() == btndelete)
         {
            if(event.getKeyCode()==event.VK_ENTER)
            {
               setData();
               AksesDatabase ad=new AksesDatabase();

               String s="delete from Jurusan where Jur_kode='"+jur.getKodejurusan()+"' and Fak_Kode='"+jur.getKodefak()+"'";
               ad.hapus(s);
               tampilData("select Jur_kode,Jur_Nama,Fak_Kode from Jurusan order by Jur_Nama");
               bersih();
            }
         }
         else if(event.getSource() == btnadd)
         {
            if(event.getKeyCode()==event.VK_ENTER)
            {
               bersih();
            }
         }
         else
         {
         }
      }

      public void keyReleased(KeyEvent event)
      {
      }

      public void keyTyped(KeyEvent event)
      {
      }
   }

   public static void main(String args[])
   {
      Object[] o={"Kode","Nama Program Studi","Fakultas"};
      FormJurusan id=new FormJurusan(o);

      id.setProperti();
      id.komponenVisual();
   }
}