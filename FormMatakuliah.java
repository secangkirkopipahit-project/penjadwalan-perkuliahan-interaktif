import javax.swing.*;
import javax.swing.table.*;
import java.awt.print.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;

public class FormMatakuliah extends JFrame
{
   JLabel lbljurusan=new JLabel("Jurusan");
   JComboBox cbjurusan;

   JLabel lblkodemk=new JLabel("Kode Matakuliah");
   JTextField txtkodemk=new JTextField();

   JLabel lblnamamk=new JLabel("Nama Matakuliah");
   JTextField txtnamamk=new JTextField();

   JLabel lblsks=new JLabel("SKS");
   JTextField txtsks=new JTextField();

   JPanel pnltombol=new JPanel();
   JButton btnsave=new JButton("Save");
   JButton btnupdate=new JButton("Update");
   JButton btndelete=new JButton("Delete");

   JTable tabel1=new JTable();
   JScrollPane scroll_panel1=new JScrollPane();
   Object[] row1 = {"Kode MK","Nama Matakuliah","SKS"};
   DefaultTableModel tabMode1=new DefaultTableModel(null, row1);

   JLabel lblsoftwarehouse=new JLabel("SecangkirKopiPahit 2020");

   public Connection connection;
   public Statement statement;

   Reusable re=new Reusable();

   public FormMatakuliah()
   {
      Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
      setTitle("Data Matakuliah");
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
      cbjurusan=new JComboBox(namajur);

      //deklarasi event handler
      ActionListener handler = new MyHandler();
      KeyListener handler2 = new MyHandler2();

      cbjurusan.addKeyListener(handler2);
      cbjurusan.addActionListener(handler);
      txtkodemk.addKeyListener(handler2);
      txtnamamk.addKeyListener(handler2);
      txtsks.addKeyListener(handler2);
      btnsave.addKeyListener(handler2);
      btnupdate.addKeyListener(handler2);
      btndelete.addKeyListener(handler2);
      
      komponenVisual();
   }

   public void komponenVisual()
   {
      getContentPane().setLayout(null);

      getContentPane().add(lbljurusan);
      lbljurusan.setBounds(10,20,100,20);
      getContentPane().add(cbjurusan);
      cbjurusan.setBounds(120,20,260,20);

      getContentPane().add(lblkodemk);
      lblkodemk.setBounds(10,41,100,20);
      getContentPane().add(txtkodemk);
      txtkodemk.setBounds(120,41,100,20);

      getContentPane().add(lblnamamk);
      lblnamamk.setBounds(10,61,100,20);
      getContentPane().add(txtnamamk);
      txtnamamk.setBounds(120,61,260,20);

      getContentPane().add(lblsks);
      lblsks.setBounds(10,81,100,20);
      getContentPane().add(txtsks);
      txtsks.setBounds(120,81,60,20);

      getContentPane().add(pnltombol);
      pnltombol.setBounds(10,110,370,45);
      pnltombol.setBorder(BorderFactory.createEtchedBorder(1));

      pnltombol.setLayout(null);
      pnltombol.add(btnsave);
      btnsave.setBounds(8,10,80,20);
      pnltombol.add(btnupdate);
      btnupdate.setBounds(90,10,80,20);
      pnltombol.add(btndelete);
      btndelete.setBounds(172,10,80,20);

      tabel1.setModel(tabMode1);
      //
      tabel1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
      TableColumn colkode=tabel1.getColumnModel().getColumn(0);
      colkode.setPreferredWidth(80);
      TableColumn colnama=tabel1.getColumnModel().getColumn(1);
      colnama.setPreferredWidth(222);
      TableColumn colsks=tabel1.getColumnModel().getColumn(2);
      colsks.setPreferredWidth(50);
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

   public void tampilMatakuliah(String kodejurusan)
   {
      try
      {
          Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
          String sql="select Mtk_Kode, Mtk_NAMA, Mtk_SKS from Matakuliah where Jur_Kode='"+kodejurusan+"' order by Mtk_Kode";
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
      txtkodemk.setText("");
      txtnamamk.setText("");
      txtsks.setText("");
   }

   class MyHandler implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         String namajurusan=(String) cbjurusan.getSelectedItem();
         Jurusan jur=new Jurusan();
         String kodejur=jur.getKodejurusan(namajurusan); 
         
         if (e.getSource() == cbjurusan)
         {          
            tampilMatakuliah(kodejur);
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
         String kodejur=jur.getKodejurusan(namajurusan);
              
         Matakuliah mk=new Matakuliah(kodejur,txtkodemk.getText(),txtnamamk.getText(),txtsks.getText()); 
          
         if (event.getSource() == cbjurusan)
         {
            if(event.getKeyCode()==event.VK_ENTER)
            {
               tampilMatakuliah(kodejur);
               txtkodemk.requestFocus();
            }
         }
         else if(event.getSource() == txtkodemk)
         {
            if(event.getKeyCode()==event.VK_ENTER)
            {
               try
               {
                  Statement statement = connection.createStatement();
                  String sql="select Mtk_NAMA,Mtk_SKS from Matakuliah where Mtk_KODE='"+txtkodemk.getText()+"' and Jur_Kode='"+kodejur+"'";
                  ResultSet rs=statement.executeQuery(sql);

                  if(rs.next())
                  {
                     txtnamamk.setText(rs.getString(1));
                     txtsks.setText(rs.getString(2));
                  }
                  else
                  {
                     System.out.println(txtkodemk.getText()+" tidak ada");
                  }
                  statement.close();

               }
               catch(Exception e)
               {
                  JOptionPane.showMessageDialog(null,"Blok Try tidak dilaksanakan","Konfirmasi",JOptionPane.INFORMATION_MESSAGE);
               }
               txtnamamk.requestFocus();
            }
         }
         else if(event.getSource() == txtnamamk)
         {
            if(event.getKeyCode()==event.VK_ENTER)
            {
               txtsks.requestFocus();
            }
         }
         else if(event.getSource() == txtsks)
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
               AksesDatabase ad=new AksesDatabase();

               String mtknomor="";
               String mtkjenis="";
               String prasyarat1="";
               String prasyarat2="";

               String sql="insert into Matakuliah values ('"+mk.getKodemk()+"','"+mk.getNamamk()+"','"+mk.getSks()+"','"+mk.getKodejur()+"','"+mtknomor+"','"+mtkjenis+"','"+prasyarat1+"','"+prasyarat2+"');";
               ad.simpan(sql);
               tampilMatakuliah(kodejur);
               bersih();
               txtkodemk.requestFocus();

            }
         }
         else if(event.getSource() == btnupdate)
         {
            if(event.getKeyCode()==event.VK_ENTER)
            {
               AksesDatabase ad=new AksesDatabase();
           
               String mtknomor="";
               String mtkjenis="";
               String prasyarat1="";
               String prasyarat2="";

               String s="update Matakuliah set Mtk_NAMA='"+mk.getNamamk()+"',Mtk_SKS='"+mk.getSks()+"',Jur_Kode='"+mk.getKodejur()+"',Mtk_nomor='"+mtknomor+"',Mtk_Jenis='"+mtkjenis+"',MKPrasyarat='"+prasyarat1+"',MKPrasyarat2='"+prasyarat2+"' where Mtk_KODE='"+mk.getKodemk()+"' and Jur_Kode='"+mk.getKodejur()+"'";
               ad.ubah(s);
               tampilMatakuliah(kodejur);
               bersih();
               txtkodemk.requestFocus();
            }
         }
         else if(event.getSource() == btndelete)
         {
            if(event.getKeyCode()==event.VK_ENTER)
            {
               AksesDatabase ad=new AksesDatabase();
            
               String s="delete from Matakuliah where Mtk_KODE='"+txtkodemk.getText()+"' and Jur_Kode='"+kodejur+"'";
               ad.hapus(s);
               tampilMatakuliah(kodejur);
               bersih();
               txtkodemk.requestFocus();
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
      FormMatakuliah fmk=new FormMatakuliah();

      fmk.komponenVisual();
   }
}