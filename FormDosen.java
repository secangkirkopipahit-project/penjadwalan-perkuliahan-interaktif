import javax.swing.*;
import javax.swing.table.*;
import java.awt.print.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class FormDosen extends JFrame //InformasiDosen
{
   JLabel lblnip=new JLabel("NIP");
   JLabel lblgelardepan=new JLabel("Gelar Depan");
   JLabel lblnama=new JLabel("Nama Dosen");
   JLabel lblgelarbelakang=new JLabel("Gelar Belakang");
   JLabel lblkodedosen=new JLabel("Kode Dosen");
   JLabel lblfakkode=new JLabel("Area Mengajar");
   JLabel lblalamat=new JLabel("Alamat");

   JTextField txtnip=new JTextField("");
   JTextField txtgelardepan=new JTextField("");
   JTextField txtnama=new JTextField("");
   JTextField txtgelarbelakang=new JTextField("");
   JTextField txtkodedosen=new JTextField("");
   JTextField txtfakkode=new JTextField("");
   JTextField txtalamat;

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
   Object[] row1 = {"Kode Dosen","Nama Dosen","NIP"};
   DefaultTableModel tabMode1=new DefaultTableModel(null, row1);

   /*
   String nip;
   String gelardepan;
   String nama;
   String gelarbelakang;
   String kodedosen;
   */
   String alamat;
   String fakkode;
   
  
   public Connection connection;
   public Statement statement;
   
   Dosen dosen;

   JLabel lblsoftwarehouse=new JLabel("SecangkirKopiPahit 2014");

   public FormDosen()
   {
      Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
      setTitle("Data Dosen");
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

      //deklarasi event handler
      ActionListener handler = new MyHandler();
      KeyListener handler2 = new MyHandler2();

      sortingkode.addActionListener(handler);
      sortingnama.addActionListener(handler);
      txtnip.addKeyListener(handler2);
      txtkodedosen.addKeyListener(handler2);
      txtgelardepan.addKeyListener(handler2);
      txtnama.addKeyListener(handler2);
      txtgelarbelakang.addKeyListener(handler2);
      txtkodedosen.addKeyListener(handler2);
      btnadd.addActionListener(handler);
      btnadd.addKeyListener(handler2);
      btnsave.addKeyListener(handler2);
      btnupdate.addKeyListener(handler2);
      btndelete.addKeyListener(handler2);
      
      komponenVisual();
   }

   public void komponenVisual()
   {
      setJMenuBar(menuinfodosen);
      getContentPane().setLayout(null);

      menuinfodosen.add(sorting);
      sorting.setMnemonic('S');
      sorting.add(sortingkode);
      sorting.add(sortingnama);

      getContentPane().add(lblnip);
      lblnip.setBounds(10,10,100,20);
      getContentPane().add(txtnip);
      txtnip.setBounds(110,10,100,20);

      getContentPane().add(btnadd);
      btnadd.setBounds(212,10,50,19);

      getContentPane().add(lblgelardepan);
      lblgelardepan.setBounds(10,30,100,20);
      getContentPane().add(txtgelardepan);
      txtgelardepan.setBounds(110,30,200,20);

      getContentPane().add(lblnama);
      lblnama.setBounds(10,50,100,20);
      getContentPane().add(txtnama);
      txtnama.setBounds(110,50,200,20);

      getContentPane().add(lblgelarbelakang);
      lblgelarbelakang.setBounds(10,70,100,20);
      getContentPane().add(txtgelarbelakang);
      txtgelarbelakang.setBounds(110,70,200,20);

      getContentPane().add(lblkodedosen);
      lblkodedosen.setBounds(10,90,100,20);
      getContentPane().add(txtkodedosen);
      txtkodedosen.setBounds(110,90,100,20);

      getContentPane().add(pnltombol);
      pnltombol.setBounds(10,120,370,45);
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
      colkode.setPreferredWidth(77);
      TableColumn colnama=tabel1.getColumnModel().getColumn(1);
      colnama.setPreferredWidth(200);
      //
      getContentPane().add(scroll_panel1);
      scroll_panel1.setBounds(10,185,370,330);
      scroll_panel1.getViewport().add(tabel1);
      tabel1.setEnabled(false);
      tampilDataDosen();

      getContentPane().add(lblsoftwarehouse);
      lblsoftwarehouse.setBounds(264,515,150,20);
      lblsoftwarehouse.setFont(new Font("Courier New",Font.PLAIN,9));

      setVisible(true);
   }
   
   public void setData()
   {
      String ni=txtnip.getText();
      String gd=txtgelardepan.getText();
      String na=txtnama.getText();
      String gb=txtgelarbelakang.getText();
      String kd=txtkodedosen.getText();
      
      dosen=new Dosen(ni,gd,na,gb,kd);
      
   }

   public void tampilDataDosen()
   {
      String kodenol="0";
      try
      {
          Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
          String sql="select DOS_KODE, DOS_NAMA,DOS_NIP from Dosen order by DOS_NAMA";
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
      txtnip.setText("");
      txtgelardepan.setText("");
      txtnama.setText("");
      txtgelarbelakang.setText("");
      txtkodedosen.setText("");
      txtnip.requestFocus();
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
            tampilDataDosen();
         }
         else if (e.getSource() == btnadd)
         {
            bersih();
            txtnip.requestFocus();
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
          setData();
          
         if (event.getSource() == txtnip)
         {
            if(event.getKeyCode()==event.VK_ENTER)
            {
               try
               {
                  //nip=txtnip.getText();

                  Statement statement = connection.createStatement();
                  String sql="select DOS_KODE,DOS_GELARDEPAN,DOS_NAMA,DOS_GELARBELAKANG,Fak_Kode,DOS_ALAMAT,DOS_NIP from Dosen where DOS_NIP like '"+dosen.getNip()+"'";
                  ResultSet rs=statement.executeQuery(sql);

                  if(rs.next())
                  {
                     txtkodedosen.setText(rs.getString(1));
                     txtgelardepan.setText(rs.getString(2));
                     txtnama.setText(rs.getString(3));
                     txtgelarbelakang.setText(rs.getString(4));
                  }
                  else
                  {
                     System.out.println(dosen.getNip()+" tidak ada");
                  }
                  statement.close();
               }
               catch(Exception e)
               {
                  JOptionPane.showMessageDialog(null,"Data telah ada dalam database","Konfirmasi",JOptionPane.INFORMATION_MESSAGE);
               }
               txtgelardepan.requestFocus();
            }
         }

         else if(event.getSource() == txtkodedosen)
         {
            if(event.getKeyCode()==event.VK_ENTER)
            {
               try
               {
                  //kodedosen=txtkodedosen.getText();

                  Statement statement = connection.createStatement();
                  String sql="select DOS_KODE,DOS_GELARDEPAN,DOS_NAMA,DOS_GELARBELAKANG,Fak_Kode,DOS_ALAMAT,DOS_NIP from Dosen where DOS_KODE='"+dosen.getKodeDosen()+"'";
                  ResultSet rs=statement.executeQuery(sql);

                  if(rs.next())
                  {
                     txtkodedosen.setText(rs.getString(1));
                     txtgelardepan.setText(rs.getString(2));
                     txtnama.setText(rs.getString(3));
                     txtgelarbelakang.setText(rs.getString(4));
                     txtnip.setText(rs.getString(7));
                  }
                  else
                  {
                     System.out.println(dosen.getNip()+" tidak ada");
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

         else if(event.getSource() == txtgelardepan)
         {
            if(event.getKeyCode()==event.VK_ENTER)
            {
               txtnama.requestFocus();
            }
         }
         else if(event.getSource() == txtnama)
         {
            if(event.getKeyCode()==event.VK_ENTER)
            {
               txtgelarbelakang.requestFocus();
            }
         }
         else if(event.getSource() == txtgelarbelakang)
         {
            if(event.getKeyCode()==event.VK_ENTER)
            {
               txtkodedosen.requestFocus();
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
                  //nip=txtnip.getText();
                  //gelardepan=txtgelardepan.getText();
                  //nama=txtnama.getText();
                  //gelarbelakang=txtgelarbelakang.getText();
                  //kodedosen=txtkodedosen.getText();
                  fakkode="";
                  alamat="";

                  Statement statement = connection.createStatement();
                  String sql="insert into Dosen values ('"+dosen.getKodeDosen()+"','"+dosen.getGelarDepan()+"','"+dosen.getNama()+"','"+dosen.getGelarBelakang()+"','"+fakkode+"','"+alamat+"','"+dosen.getNip()+"');";
                  statement.executeUpdate(sql);
                  statement.close();

                  System.out.println("Data telah masuk");
                  tampilDataDosen();
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
               //nip=txtnip.getText();
               //gelardepan=txtgelardepan.getText();
               //nama=txtnama.getText();
               //gelarbelakang=txtgelarbelakang.getText();
               //kodedosen=txtkodedosen.getText();
               fakkode="";
               alamat="";

               AksesDatabase ad=new AksesDatabase();
               String s="update Dosen set DOS_KODE='"+dosen.getKodeDosen()+"',DOS_GELARDEPAN='"+dosen.getGelarDepan()+"',DOS_NAMA='"+dosen.getNama()+"',DOS_GELARBELAKANG='"+dosen.getGelarBelakang()+"',Fak_Kode='"+fakkode+"',DOS_ALAMAT='"+alamat+"' where DOS_NIP='"+dosen.getNip()+"'";
               ad.ubah(s);
               tampilDataDosen();
               bersih();
            }
         }
         else if(event.getSource() == btndelete)
         {
            if(event.getKeyCode()==event.VK_ENTER)
            {
               AksesDatabase ad=new AksesDatabase();
               //kodedosen=txtkodedosen.getText();
               //nip=txtnip.getText();

               String s="delete from Dosen where DOS_KODE='"+dosen.getKodeDosen()+"' or DOS_NIP='"+dosen.getNip()+"'";
               ad.hapus(s);
               tampilDataDosen();
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
      FormDosen fd=new FormDosen();

      fd.komponenVisual();
   }
}