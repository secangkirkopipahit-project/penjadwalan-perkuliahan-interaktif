import javax.swing.*;
import javax.swing.table.*;
import java.awt.print.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Form5Textfield extends JFrame
{
   JLabel label1=new JLabel("NIP");
   JLabel label2=new JLabel("Gelar Depan");
   JLabel label3=new JLabel("Nama Dosen");
   JLabel label4=new JLabel("Gelar Belakang");
   JLabel label5=new JLabel("Kode Dosen");

   JTextField text1=new JTextField("");
   JTextField text2=new JTextField("");
   JTextField text3=new JTextField("");
   JTextField text4=new JTextField("");
   JTextField text5=new JTextField("");

   JPanel pnltombol=new JPanel();
   JButton btnadd=new JButton("+");
   JButton btnsave=new JButton("Save");
   JButton btnupdate=new JButton("Update");
   JButton btndelete=new JButton("Delete");

   JMenuBar menuinfodosen=new JMenuBar();
   JMenu sorting=new JMenu("Sorting");
   JMenuItem sortingkode=new JMenuItem("Berdasar Kode");
   JMenuItem sortingnama=new JMenuItem("Berdasar Nama");

   JTable tabel1=new JTable();
   JScrollPane scroll_panel1=new JScrollPane();
   DefaultTableModel tabMode1;
   Object[] row1 ={};

   String nip;
   String gelardepan;
   String nama;
   String gelarbelakang;
   String kodedosen;
   String alamat;
   String fakkode;

   public Connection connection;
   public Statement statement;

   JLabel lblsoftwarehouse=new JLabel("SecangkirKopiPahit 2014");

   public Form5Textfield(String title,Object[] ob)
   {
      Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
      setTitle(title);
      setSize(400,600);
      int lebar = (screen.width - getSize().width) / 2;
      int tinggi = (screen.height - getSize().height) / 2;
      setLocation(lebar, tinggi);
      setResizable(false);
      setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

      //AksesDatabase akses = new AksesDatabase();


      try
      {
         Class.forName("org.sqlite.JDBC");
         connection = DriverManager.getConnection("jdbc:sqlite:DatabaseJadwalKuliah.db3");
      }
      catch (Exception e)
      {
      }


      row1 = ob;
      tabMode1=new DefaultTableModel(null, row1);

      ActionListener handler = new MyHandler();
      KeyListener handler2 = new MyHandler2();

      /*
      sortingkode.addActionListener(handler);
      sortingnama.addActionListener(handler);
      text1.addKeyListener(handler2);
      text2.addKeyListener(handler2);
      text3.addKeyListener(handler2);
      text4.addKeyListener(handler2);
      text5.addKeyListener(handler2);
      btnadd.addActionListener(handler);
      btnadd.addKeyListener(handler2);
      btnsave.addKeyListener(handler2);
      btnupdate.addKeyListener(handler2);
      btndelete.addKeyListener(handler2);
      */
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
      text3.setBounds(110,50,200,20);

      getContentPane().add(label4);
      label4.setBounds(10,70,100,20);
      getContentPane().add(text4);
      text4.setBounds(110,70,200,20);

      getContentPane().add(label5);
      label5.setBounds(10,90,100,20);
      getContentPane().add(text5);
      text5.setBounds(110,90,100,20);

      getContentPane().add(pnltombol);
      pnltombol.setBounds(10,120,370,45);
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
      scroll_panel1.setBounds(10,185,370,330);
      scroll_panel1.getViewport().add(tabel1);
      tabel1.setEnabled(false);
      tampilData("select DOS_KODE, DOS_NAMA,DOS_NIP from Dosen order by DOS_NAMA");

      getContentPane().add(lblsoftwarehouse);
      lblsoftwarehouse.setBounds(264,515,150,20);
      lblsoftwarehouse.setFont(new Font("Courier New",Font.PLAIN,9));

      setVisible(true);
   }



   public void tampilData(String sql)
   {
      String kodenol="0";
      try
      {
         //Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
         Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
         //String sql="select DOS_KODE, DOS_NAMA,DOS_NIP from Dosen order by DOS_NAMA";
         ResultSet rs=statement.executeQuery(sql);

         hapusTabel1();

         while (rs.next())
         {
            Object[] data = {rs.getString(1), rs.getString(2),rs.getString(3)};
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
      text1.setText("");
      text2.setText("");
      text3.setText("");
      text4.setText("");
      text5.setText("");
      text1.requestFocus();
   }

   class MyHandler implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         if (e.getSource() == sortingkode)
         {
            String kodenol="0";

            try
            {
               Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
               String sql="select DOS_KODE, DOS_NAMA,DOS_NIP from Dosen order by DOS_KODE";
               ResultSet rs=statement.executeQuery(sql);

               hapusTabel1();

               while (rs.next())
               {
                  Object[] data = {rs.getString(1), rs.getString(2),rs.getString(3)};
                  tabMode1.addRow(data);
               }

               statement.close();
            }
            catch (Exception DBException)
            {
               System.err.println("Error : " + DBException);
            }
         }
         else if (e.getSource() == sortingnama)
         {
            tampilData("select DOS_KODE, DOS_NAMA,DOS_NIP from Dosen order by DOS_NAMA");
         }
         else if (e.getSource() == btnadd)
         {
            bersih();
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

         if(event.getSource() == text1)
         {
            if(event.getKeyCode()==event.VK_ENTER)
            {
               try
               {
                  nip=text1.getText();

                  Statement statement = connection.createStatement();
                  String sql="select DOS_KODE,DOS_GELARDEPAN,DOS_NAMA,DOS_GELARBELAKANG,Fak_Kode,DOS_ALAMAT,DOS_NIP from Dosen where DOS_NIP like '"+nip+"'";
                  ResultSet rs=statement.executeQuery(sql);

                  if(rs.next())
                  {
                     text5.setText(rs.getString(1));
                     text2.setText(rs.getString(2));
                     text3.setText(rs.getString(3));
                     text4.setText(rs.getString(4));
                     text1.setText(rs.getString(7));
                  }
                  else
                  {
                     System.out.println(nip+" tidak ada");
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
               text4.requestFocus();
            }
         }
         else if(event.getSource() == text4)
         {
            if(event.getKeyCode()==event.VK_ENTER)
            {
               text5.requestFocus();
            }
         }
         else if(event.getSource() == text5)
         {
            if(event.getKeyCode()==event.VK_ENTER)
            {
               try
               {
                  kodedosen=text5.getText();

                  Statement statement = connection.createStatement();
                  String sql="select DOS_KODE,DOS_GELARDEPAN,DOS_NAMA,DOS_GELARBELAKANG,Fak_Kode,DOS_ALAMAT,DOS_NIP from Dosen where DOS_KODE='"+kodedosen+"'";
                  ResultSet rs=statement.executeQuery(sql);

                  if(rs.next())
                  {
                     text5.setText(rs.getString(1));
                     text2.setText(rs.getString(2));
                     text3.setText(rs.getString(3));
                     text4.setText(rs.getString(4));
                     text1.setText(rs.getString(7));
                  }
                  else
                  {
                     System.out.println(nip+" tidak ada");
                  }
                  statement.close();

               }
               catch(Exception e)
               {
                  JOptionPane.showMessageDialog(null,"Data telah ada dalam database","Konfirmasi",JOptionPane.INFORMATION_MESSAGE);
               }
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
               try
               {
                  nip=text1.getText();
                  gelardepan=text2.getText();
                  nama=text3.getText();
                  gelarbelakang=text4.getText();
                  kodedosen=text5.getText();
                  fakkode="";
                  alamat="";

                  AksesDatabase ad=new AksesDatabase();

                  //Statement statement = connection.createStatement();
                  String sql="insert into Dosen values ('"+kodedosen+"','"+gelardepan+"','"+nama+"','"+gelarbelakang+"','"+fakkode+"','"+alamat+"','"+nip+"');";
                  //statement.executeUpdate(sql);
                  //statement.close();
                  ad.simpan(sql);

                  System.out.println("Data telah masuk");
                  tampilData("select DOS_KODE, DOS_NAMA,DOS_NIP from Dosen order by DOS_NAMA");
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
               AksesDatabase ad=new AksesDatabase();
               nip=text1.getText();
               gelardepan=text2.getText();
               nama=text3.getText();
               gelarbelakang=text4.getText();
               kodedosen=text5.getText();
               fakkode="";
               alamat="";

               String s="update Dosen set DOS_KODE='"+kodedosen+"',DOS_GELARDEPAN='"+gelardepan+"',DOS_NAMA='"+nama+"',DOS_GELARBELAKANG='"+gelarbelakang+"',Fak_Kode='"+fakkode+"',DOS_ALAMAT='"+alamat+"' where DOS_NIP='"+nip+"'";
               ad.ubah(s);
               tampilData("select DOS_KODE, DOS_NAMA,DOS_NIP from Dosen order by DOS_NAMA");
               bersih();
            }
         }
         else if(event.getSource() == btndelete)
         {
            if(event.getKeyCode()==event.VK_ENTER)
            {
               AksesDatabase ad=new AksesDatabase();
               kodedosen=text5.getText();
               nip=text1.getText();

               String s="delete from Dosen where DOS_KODE='"+kodedosen+"' or DOS_NIP='"+nip+"'";
               ad.hapus(s);
               tampilData("select DOS_KODE, DOS_NAMA,DOS_NIP from Dosen order by DOS_NAMA");
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

   public static void main(String args[])
   {
      Object[] h={"Kode Dosen","Nama Dosen","NIP"};
      Form5Textfield id=new Form5Textfield("Data Dosen",h);

      id.komponenVisual();
      //id.aksiReaksi();
   }
}