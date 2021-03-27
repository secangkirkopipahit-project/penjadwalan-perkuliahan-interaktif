import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;
import java.sql.*;
import java.util.*;

public class FormRuang extends JFrame
{
   JLabel lbljurusan=new JLabel("Jurusan");
   JComboBox cbjurusan;

   JLabel lblruang=new JLabel("Ruang");
   JTextField txtruang=new JTextField();

   JLabel lblkapasitas=new JLabel("Kapasitas");
   JTextField txtkapasitas=new JTextField();

   JTable tabel1=new JTable();
   JScrollPane scroll_panel1=new JScrollPane();
   Object[] row1 = {"No","Kode Ruang","Kapasitas"};
   DefaultTableModel tabMode1=new DefaultTableModel(null, row1);

   JPanel pnltombol=new JPanel();
   JButton btnsave=new JButton("Save");
   JButton btnupdate=new JButton("Update");
   JButton btndelete=new JButton("Delete");

   public Connection connection;
   public Statement statement;

   Reusable re=new Reusable();

   String nmjur;
   String ruang;
   String kapasitas;

   JLabel lblsoftwarehouse=new JLabel("SecangkirKopiPahit 2013");
   
   Ruang ru;

   public FormRuang()
   {
      Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
      setTitle("Data Ruang Kuliah");
      setSize(400,600);
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

      String ssql="select Jur_Nama from Jurusan";
      String[] namajur=re.getArray1Dimensi(connection,statement,ssql);

      String[] namakelola=new String[namajur.length+1];
      int z=namakelola.length;
      namakelola[z-1]="GENERAL";

      for(int i=0; i<namakelola.length-1; i++)
      {
         namakelola[i]=namajur[i];
      }

      cbjurusan=new JComboBox(namakelola);

      //deklarasi event handler
      ActionListener handler = new MyHandler();
      KeyListener handler2 = new MyHandler2();

      cbjurusan.addActionListener(handler);
      cbjurusan.addKeyListener(handler2);
      txtruang.addKeyListener(handler2);
      txtkapasitas.addKeyListener(handler2);
      btnsave.addKeyListener(handler2);
      btnupdate.addKeyListener(handler2);
      btndelete.addKeyListener(handler2);
      
      komponenVisual();
   }
   
   public void setData()
   {
       String nmjur=(String) cbjurusan.getSelectedItem();
       String ruang=txtruang.getText();
       String kapasitas=txtkapasitas.getText();
       
       ru=new Ruang(nmjur,ruang,kapasitas);
   }

   public static void main(String args[])
   {
      FormRuang fr=new FormRuang();
      fr.komponenVisual();
   }

   public void komponenVisual()
   {
      getContentPane().setLayout(null);

      getContentPane().add(lbljurusan);
      lbljurusan.setBounds(10,20,100,20);
      getContentPane().add(cbjurusan);
      cbjurusan.setBounds(120,20,260,20);

      getContentPane().add(lblruang);
      lblruang.setBounds(10,42,100,20);
      getContentPane().add(txtruang);
      txtruang.setBounds(120,42,260,20);

      getContentPane().add(lblkapasitas);
      lblkapasitas.setBounds(10,63,100,20);
      getContentPane().add(txtkapasitas);
      txtkapasitas.setBounds(120,63,60,20);

      getContentPane().add(pnltombol);
      pnltombol.setBounds(10,100,370,45);
      pnltombol.setBorder(BorderFactory.createEtchedBorder(1));

      pnltombol.setLayout(null);
      pnltombol.add(btnsave);
      btnsave.setBounds(8,10,80,20);
      pnltombol.add(btnupdate);
      btnupdate.setBounds(90,10,80,20);
      pnltombol.add(btndelete);
      btndelete.setBounds(172,10,80,20);

      //--------------------bagian untuk menampilkan jadwal per ruang
      tabel1.setModel(tabMode1);
      tabel1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

      TableColumn colno=tabel1.getColumnModel().getColumn(0);
      colno.setPreferredWidth(40);
      TableColumn colkode=tabel1.getColumnModel().getColumn(1);
      colkode.setPreferredWidth(210);
      TableColumn colkapasitas=tabel1.getColumnModel().getColumn(2);
      colkapasitas.setPreferredWidth(100);
      //
      getContentPane().add(scroll_panel1);
      scroll_panel1.setBounds(10,170,370,370);
      scroll_panel1.getViewport().add(tabel1);
      tabel1.setEnabled(false);

      getContentPane().add(lblsoftwarehouse);
      lblsoftwarehouse.setBounds(263,540,150,20);
      lblsoftwarehouse.setFont(new Font("Courier New",Font.PLAIN,9));

      setVisible(true);
   }

   public void simpandata()
   {
      setData();      
      Jurusan jur=new Jurusan();
      String kodejurusan=jur.getKodejurusan(ru.getNamaJurusan());
      
      if(ru.getRuang().equals(""))
      {
         JOptionPane.showMessageDialog(null,"Nama Ruang belum diisi.\n"+"Silahkan Masukkan Data Ruang.","Konfirmasi",JOptionPane.INFORMATION_MESSAGE);
         txtruang.requestFocus();
      }
      else
      {
         AksesDatabase ad=new AksesDatabase();
         String ssql="insert into Ruang values('"+ru.getRuang()+"','"+ru.getKapasitas()+"','"+kodejurusan+"');";
         ad.simpan(ssql);
         bersih();
         txtruang.requestFocus();
      }
   }

   public void bersih()
   {
      txtruang.setText("");
      txtkapasitas.setText("");
      txtruang.requestFocus();
   }

   public void hapusTabel1()
   {
      int row1 = tabMode1.getRowCount();

      for (int i = 0; i < row1; i++)
      {
         tabMode1.removeRow(0);
      }
   }

   public void tampilRuang()
   {
       setData();
       Jurusan jur=new Jurusan();
       String kodejurusan=jur.getKodejurusan(ru.getNamaJurusan());

       try
       {
           //--- untuk menampilkan jadwal pada tabel
           statement = connection.createStatement();
           String sql="SELECT a.KodeRuang,a.Kapasitas FROM Ruang AS a where a.JurusanUser='"+kodejurusan+"'";
           ResultSet rs=statement.executeQuery(sql);

           hapusTabel1();
           int no_baris=0;

           while (rs.next())
           {
               no_baris=no_baris+1;
               Object[] data = {no_baris,rs.getString(1),rs.getString(2)};
               tabMode1.addRow(data);
           }
         statement.close();
      }
      catch (Exception DBException)
      {
         System.err.println("Error : " + DBException);
      }
   }

   class MyHandler implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         setData();
         
         if (e.getSource() == cbjurusan)
         {
            tampilRuang();
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
          setData();
          Jurusan jur=new Jurusan();
          String kodejurusan=jur.getKodejurusan(ru.getNamaJurusan());
          
          if (event.getSource() == cbjurusan)
          {
              if(event.getKeyCode()==event.VK_ENTER)
              {
                  tampilRuang();
                  txtruang.requestFocus();
              }
          }

          else if(event.getSource() == txtruang)
          {
              if(event.getKeyCode()==event.VK_ENTER)
              {
                  try
                  {
                      Statement statement = connection.createStatement();
                      String sql="select Kapasitas from Ruang where KodeRuang='"+ru.getRuang()+"' and JurusanUser='"+kodejurusan+"'";
                      ResultSet rs=statement.executeQuery(sql);

                      if(rs.next())
                      {
                          txtkapasitas.setText(rs.getString(1));
                      }
                      else
                      {
                          System.out.println(ruang+" tidak ada");
                      }
                      statement.close();
                  }
                  catch(Exception e)
                  {
                      JOptionPane.showMessageDialog(null,"Blok Try tidak dilaksanakan","Konfirmasi",JOptionPane.INFORMATION_MESSAGE);
                  }
                  txtkapasitas.requestFocus();
              }
         }
         else if(event.getSource() == txtkapasitas)
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
                 simpandata();
                 tampilRuang();
             }
         }
         else if(event.getSource() == btnupdate)
         {
             if(event.getKeyCode()==event.VK_ENTER)
             {
                 AksesDatabase ad=new AksesDatabase();
               
                 String s="update Ruang set Kapasitas='"+ru.getKapasitas()+"',JurusanUser='"+kodejurusan+"' where KodeRuang='"+ru.getRuang()+"'";
                 ad.ubah(s);
                 tampilRuang();
                 bersih();
             }
         }
         else if(event.getSource() == btndelete)
         {
             if(event.getKeyCode()==event.VK_ENTER)
             {
                 AksesDatabase ad=new AksesDatabase();
                 
                 String s="delete from Ruang where KodeRuang='"+ru.getRuang()+"' and JurusanUser='"+kodejurusan+"'";
                 ad.hapus(s);
                 tampilRuang();
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
}