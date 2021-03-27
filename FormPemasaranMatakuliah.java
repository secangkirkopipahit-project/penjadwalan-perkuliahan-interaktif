import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;
import java.sql.*;
//import java.util.Date;
//import java.util.Calendar;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
import java.util.*;

public class FormPemasaranMatakuliah extends JFrame
{
   JLabel lblsemester=new JLabel("Semester");
   String[] namasemester={"GASAL","GENAP"};
   JComboBox cbsemester=new JComboBox(namasemester);
   JLabel lblthnajaran=new JLabel("Tahun Ajaran");
   JComboBox cbthnajaran=new JComboBox();

   JLabel lbljurusan=new JLabel("Jurusan");
   JComboBox cbjurusan;

   JLabel lblkodemk=new JLabel("Kode Matakuliah");
   JComboBox cbkodemk=new JComboBox();
   JLabel lblnamamk=new JLabel("Nama Matakuliah");
   JTextField txtnamamk=new JTextField();

   JPanel pnltombol=new JPanel();
   JButton btnsave=new JButton("Save");
   JButton btnupdate=new JButton("Update");
   JButton btndelete=new JButton("Delete");
   JButton btnplus=new JButton("+");

   JButton btnpilih=new JButton("Pilih");

   JTable tabel1=new JTable();
   JScrollPane scroll_panel1=new JScrollPane();
   Object[] row1 = {"Kode MK","Nama Matakuliah","SKS"};
   DefaultTableModel tabMode1=new DefaultTableModel(null, row1);

   JLabel lblsoftwarehouse=new JLabel("SecangkirKopiPahit 2013");

   public Connection connection;
   public Statement statement;

   Reusable re=new Reusable();

   public FormPemasaranMatakuliah()
   {
      Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
      setTitle("Pemasaran Matakuliah");
      setSize(415,610);
      int lebar = (screen.width - getSize().width) / 2;
      int tinggi = (screen.height - getSize().height) / 2;
      setLocation(lebar, tinggi);
      setResizable(false);
      setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      try
      {
         Class.forName("org.sqlite.JDBC");
         connection = DriverManager.getConnection("jdbc:sqlite:DatabaseJadwalKuliah.db3");
      }
      catch (Exception e)
      {
      }

      String ssql="select tahunAjaran from DataTahunAjaran order by tahunAjaran";
      String[] namathnajaran=re.getArray1Dimensi(connection,statement,ssql);
      cbthnajaran=new JComboBox(namathnajaran);

      ssql="select Jur_Nama from Jurusan";
      String[] namajur=re.getArray1Dimensi(connection,statement,ssql);
      cbjurusan=new JComboBox(namajur);

      //deklarasi event handler
      ActionListener handler = new MyHandler();
      KeyListener handler2 = new MyHandler2();

      cbsemester.addKeyListener(handler2);
      cbthnajaran.addKeyListener(handler2);
      cbjurusan.addKeyListener(handler2);
      cbjurusan.addActionListener(handler);
      cbkodemk.addKeyListener(handler2);
      cbkodemk.addActionListener(handler);
      btnsave.addKeyListener(handler2);
      btnsave.addActionListener(handler);
      btndelete.addKeyListener(handler2);
      btndelete.addActionListener(handler);
      
      komponenVisual();
   }

   public void komponenVisual()
   {
      getContentPane().setLayout(null);

      getContentPane().add(lblsemester);
      lblsemester.setBounds(10,10,100,20);
      getContentPane().add(cbsemester);
      cbsemester.setBounds(120,10,100,20);

      getContentPane().add(lblthnajaran);
      lblthnajaran.setBounds(230,10,100,20);
      getContentPane().add(cbthnajaran);
      cbthnajaran.setBounds(315,10,70,20);
      //getContentPane().add(btnplus);
      //btnplus.setBounds(345,31,40,20);

      getContentPane().add(lbljurusan);
      lbljurusan.setBounds(10,60,100,20);
      getContentPane().add(cbjurusan);
      cbjurusan.setBounds(120,60,265,20);

      getContentPane().add(lblkodemk);
      lblkodemk.setBounds(10,81,100,20);
      getContentPane().add(cbkodemk);
      cbkodemk.setBounds(120,81,80,20);

      getContentPane().add(txtnamamk);
      txtnamamk.setBounds(202,81,184,20);
      txtnamamk.setEditable(false);
      txtnamamk.setBorder(BorderFactory.createEtchedBorder(1));

      getContentPane().add(pnltombol);
      pnltombol.setBounds(10,120,375,45);
      pnltombol.setBorder(BorderFactory.createEtchedBorder(1));

      pnltombol.setLayout(null);
      pnltombol.add(btnsave);
      btnsave.setBounds(8,10,80,20);
      pnltombol.add(btndelete);
      btndelete.setBounds(90,10,80,20);

      tabel1.setModel(tabMode1);
      //------------------------
      tabel1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
      TableColumn colkode=tabel1.getColumnModel().getColumn(0);
      colkode.setPreferredWidth(80);
      TableColumn colnama=tabel1.getColumnModel().getColumn(1);
      colnama.setPreferredWidth(230);
      TableColumn colsks=tabel1.getColumnModel().getColumn(2);
      colsks.setPreferredWidth(47);
      //------------------------
      getContentPane().add(scroll_panel1);
      scroll_panel1.setBounds(10,175,375,370);
      scroll_panel1.getViewport().add(tabel1);
      tabel1.setEnabled(false);

      getContentPane().add(lblsoftwarehouse);
      lblsoftwarehouse.setBounds(268,545,150,20);
      lblsoftwarehouse.setFont(new Font("Courier New",Font.PLAIN,9));

      setVisible(true);
   }

   public void ambilKodeMK()
   {
      String namajurusan=(String) cbjurusan.getSelectedItem();

      Jurusan jur=new Jurusan();
      String kj=jur.getKodejurusan(namajurusan);

      try
      {
         Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
         String sql="SELECT Mtk_Kode FROM Matakuliah WHERE Jur_Kode='"+kj+"' order by Mtk_KODE";
         ResultSet rs=statement.executeQuery(sql);

         while(rs.next())
         {
            cbkodemk.addItem(rs.getString(1));
         }
         statement.close();
      }
      catch (Exception DBException)
      {
         System.err.println("Error : " + DBException);
      }
   }

   public void ambilNamaMK()
   {
      String kdmk=(String) cbkodemk.getSelectedItem();

      try
      {
         Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
         String sql="SELECT Mtk_NAMA FROM Matakuliah WHERE Mtk_KODE='"+kdmk+"'";
         ResultSet rs=statement.executeQuery(sql);

         if(rs.next())
         {
            txtnamamk.setText(rs.getString(1));
         }
         statement.close();
      }
      catch (Exception DBException)
      {
         System.err.println("Error : " + DBException);
      }
   }

   public void tampilMatakuliah(String kodejurusan)
   {
      String smt=(String)cbsemester.getSelectedItem();
      String semester="";
      if(smt.equals("GASAL"))
      {
         semester="1";
      }
      else
      {
         semester="2";
      }

      String thn=(String) cbthnajaran.getSelectedItem();

      try
      {
         Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
         String sql="select a.Mtk_KODE, b.Mtk_NAMA, b.Mtk_SKS from Matakuliah_Pemasaran a, Matakuliah b where a.Jur_Kode='"+kodejurusan+"' and a.Mtk_KODE=b.Mtk_KODE and a.Thn_Kode='"+thn+"' and Smt_Kode='"+semester+"' order by a.Mtk_Kode";
         ResultSet rs=statement.executeQuery(sql);

         hapusTabel1();

         while (rs.next())
         {
            Object[] data = {rs.getString(1),rs.getString(2),rs.getString(3)};
            tabMode1.addRow(data);
         }

         statement.close();
      }
      catch (Exception DBException)
      {
         System.err.println("Error : " + DBException);
      }
   }

   public void hapusTabel1()
   {
      int row1 = tabMode1.getRowCount();

      for (int i = 0; i < row1; i++)
      {
         tabMode1.removeRow(0);
      }
   }

   public void bersih()
   {
      txtnamamk.setText("");
      cbkodemk.requestFocus();
   }

   class MyHandler implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
          String namajurusan=(String) cbjurusan.getSelectedItem();
          Jurusan jur=new Jurusan();
          String kodejurusan=jur.getKodejurusan(namajurusan);
          
          String smt=(String)cbsemester.getSelectedItem();
          String semester="";
          if(smt.equals("GASAL"))
          {
              semester="1";
          }
          else
          {
              semester="2";
          }

          String thnajaran=(String)cbthnajaran.getSelectedItem();

          if (e.getSource() == cbjurusan)
          {
            tampilMatakuliah(kodejurusan);
            cbkodemk.removeAllItems();
            ambilKodeMK();
         }
         else if (e.getSource() == cbkodemk)
         {
            ambilNamaMK();
         }
         else if (e.getSource() == btnsave)
         {
             String kodemk=(String)cbkodemk.getSelectedItem();
             boolean flag=false;

             try
             {
                 //tambah method untuk memeriksa perulangan matakuliah
                 statement = connection.createStatement();
                 String sql="SELECT * FROM Matakuliah_Pemasaran";
                 ResultSet rs=statement.executeQuery(sql);
                 while(rs.next())
                 {
                     if(kodemk.equals(rs.getString(1)) && kodejurusan.equals(rs.getString(2)) && thnajaran.equals(rs.getString(3)) && semester.equals(rs.getString(4)))
                     {
                         flag=true;
                         break;
                     }
                 }
                 statement.close();

                 if(flag==true)
                 {
                     JOptionPane.showMessageDialog(null,"Matakuliah ini telah dipasarkan semester ini","Konfirmasi",JOptionPane.INFORMATION_MESSAGE);
                     bersih();
                 }
                 else
                 {
                     AksesDatabase ad=new AksesDatabase();

                     sql="insert into Matakuliah_Pemasaran values('"+kodemk+"','"+kodejurusan+"','"+thnajaran+"','"+semester+"');";
                     ad.simpan(sql);
                     tampilMatakuliah(kodejurusan);
                     bersih();
                 }
             }
             catch(Exception ex)
             {
                 System.out.println("Error :"+ex);
             }
         }
         else if (e.getSource() == btndelete)
         {
            AksesDatabase ad=new AksesDatabase();

            String kodemk=(String)cbkodemk.getSelectedItem();
            

            String s="delete from Matakuliah_Pemasaran where Mtk_KODE='"+kodemk+"' and Jur_Kode='"+kodejurusan+"' and Thn_Kode='"+thnajaran+"' and Smt_Kode='"+semester+"'";
            ad.hapus(s);
            tampilMatakuliah(kodejurusan);
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
          String namajurusan=(String) cbjurusan.getSelectedItem();
          Jurusan jur=new Jurusan();
          String kodejurusan=jur.getKodejurusan(namajurusan);
          
          String smt=(String)cbsemester.getSelectedItem();
          String semester="";
          if(smt.equals("GASAL"))
          {
              semester="1";
          }
          else
          {
              semester="2";
          }

          String thnajaran=(String)cbthnajaran.getSelectedItem();
               
         if (event.getSource() == cbsemester)
         {
            if(event.getKeyCode()==event.VK_ENTER)
            {
               cbthnajaran.requestFocus();
            }
         }
         else if(event.getSource() == cbthnajaran)
         {
            if(event.getKeyCode()==event.VK_ENTER)
            {
               cbjurusan.requestFocus();
            }
         }
         else if(event.getSource() == cbjurusan)
         {
            if(event.getKeyCode()==event.VK_ENTER)
            {               
               tampilMatakuliah(kodejurusan);
               cbkodemk.removeAllItems();
               ambilKodeMK();
               cbkodemk.requestFocus();
            }
         }
         else if(event.getSource() == cbkodemk)
         {
            if(event.getKeyCode()==event.VK_ENTER)
            {
               ambilNamaMK();
               btnsave.requestFocus();
            }
         }
         else if(event.getSource() == btnsave)
         {
            if(event.getKeyCode()==event.VK_ENTER)
            {
               String kodemk=(String)cbkodemk.getSelectedItem();
               boolean flag=false;

               try
               {
                  //tambah method untuk memeriksa perulangan matakuliah
                  statement = connection.createStatement();
                  String sql="SELECT * FROM Matakuliah_Pemasaran";
                  ResultSet rs=statement.executeQuery(sql);
                  while(rs.next())
                  {
                     if(kodemk.equals(rs.getString(1)) && kodejurusan.equals(rs.getString(2)) && thnajaran.equals(rs.getString(3)) && semester.equals(rs.getString(4)))
                     {
                        flag=true;
                        break;
                     }
                  }
                  statement.close();

                  if(flag==true)
                  {
                     JOptionPane.showMessageDialog(null,"Matakuliah ini telah dipasarkan semester ini","Konfirmasi",JOptionPane.INFORMATION_MESSAGE);
                     bersih();
                  }
                  else
                  {
                     AksesDatabase ad=new AksesDatabase();

                     sql="insert into Matakuliah_Pemasaran values('"+kodemk+"','"+kodejurusan+"','"+thnajaran+"','"+semester+"');";
                     ad.simpan(sql);
                     tampilMatakuliah(kodejurusan);
                     bersih();
                  }
               }
               catch(Exception e)
               {
                  System.out.println("Error :"+e);
               }
            }
         }
         else if(event.getSource() == btndelete)
         {
            if(event.getKeyCode()==event.VK_ENTER)
            {
               AksesDatabase ad=new AksesDatabase();

               String kodemk=(String)cbkodemk.getSelectedItem();

               String s="delete from Matakuliah_Pemasaran where Mtk_KODE='"+kodemk+"' and Jur_Kode='"+kodejurusan+"' and Thn_Kode='"+thnajaran+"' and Smt_Kode='"+semester+"'";
               ad.hapus(s);
               tampilMatakuliah(kodejurusan);
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
      FormPemasaranMatakuliah fpmk=new FormPemasaranMatakuliah();

      fpmk.komponenVisual();
   }
}