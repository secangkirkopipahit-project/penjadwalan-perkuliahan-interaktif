import javax.swing.*;
import javax.swing.table.*;
import java.awt.print.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;

public class ViewJadwal extends JFrame
{
   JLabel lblsemester=new JLabel("Semester");
   String[] namasemester={"GASAL","GENAP"};
   JComboBox cbsemester=new JComboBox(namasemester);
   JLabel lblthnajaran=new JLabel("Tahun Ajaran");
   JComboBox cbthnajaran;

   JLabel lbljurusan=new JLabel("Jurusan");
   JComboBox cbjurusan;

   JRadioButton rball=new JRadioButton("All");
   JRadioButton rbperdosen=new JRadioButton("Per Dosen");
   JRadioButton rbperkelas=new JRadioButton("Per Kelas");
   JRadioButton rbpermatakuliah=new JRadioButton("Per Matakuliah");
   JRadioButton rbperruang=new JRadioButton("Per Ruang");
   ButtonGroup grupreport=new ButtonGroup();

   JComboBox cbnamadosen=new JComboBox();
   JComboBox cbnamakelas=new JComboBox();
   JComboBox cbnamaruang=new JComboBox();
   JComboBox cbnamamatakuliah=new JComboBox();

   JButton btnrun=new JButton("Run");
   JButton btnprint=new JButton("Print");

   JTable tabel1=new JTable();
   JScrollPane scroll_panel1=new JScrollPane();
   Object[] row1 = {"Hari","Jam","Kode MK","Nama MK","SKS","Kelas","Kode Dosen","Nama Dosen","Ruang"};
   DefaultTableModel tabMode1=new DefaultTableModel(null, row1);

   public Connection connection;
   public Statement statement;

   Reusable re=new Reusable();

   public ViewJadwal()
   {
      setVisible(true);
      Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
      setTitle("Jadwal Kuliah");
      setSize(1000,600);
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

      ssql="select tahunAjaran from DataTahunAjaran order by tahunAjaran";
      String[] namathnajaran=re.getArray1Dimensi(connection,statement,ssql);
      cbthnajaran=new JComboBox(namathnajaran);

      //deklarasi event handler
      ActionListener handler = new MyHandler();
      KeyListener handler2 = new MyHandler2();

      cbsemester.addKeyListener(handler2);
      cbthnajaran.addKeyListener(handler2);
      cbjurusan.addKeyListener(handler2);
      btnrun.addKeyListener(handler2);
      btnrun.addActionListener(handler);
      btnprint.addActionListener(handler);
      btnprint.addKeyListener(handler2);
      rbperdosen.addActionListener(handler);
      rbperkelas.addActionListener(handler);
      rbpermatakuliah.addActionListener(handler);
      rbperruang.addActionListener(handler);
      rball.addActionListener(handler);
   }

   public void komponenVisual()
   {
      getContentPane().setLayout(null);

      getContentPane().add(lblsemester);
      lblsemester.setBounds(10,10,100,20);
      getContentPane().add(cbsemester);
      cbsemester.setBounds(120,10,100,20);

      getContentPane().add(lblthnajaran);
      lblthnajaran.setBounds(10,31,100,20);
      getContentPane().add(cbthnajaran);
      cbthnajaran.setBounds(120,31,100,20);

      getContentPane().add(lbljurusan);
      lbljurusan.setBounds(10,52,100,20);
      getContentPane().add(cbjurusan);
      cbjurusan.setBounds(120,52,270,20);
      getContentPane().add(btnrun);
      btnrun.setBounds(120,73,100,20);
      getContentPane().add(btnprint);
      btnprint.setBounds(221,73,100,20);

      getContentPane().add(rball);
      rball.setBounds(550,10,50,20);

      getContentPane().add(rbperdosen);
      rbperdosen.setBounds(600,10,100,20);
      getContentPane().add(cbnamadosen);
      cbnamadosen.setBounds(720,10,175,20);
      cbnamadosen.setVisible(false);

      getContentPane().add(rbperkelas);
      rbperkelas.setBounds(600,30,100,20);
      getContentPane().add(cbnamakelas);
      cbnamakelas.setBounds(720,30,175,20);
      cbnamakelas.setVisible(false);

      getContentPane().add(rbpermatakuliah);
      rbpermatakuliah.setBounds(600,50,120,20);
      getContentPane().add(cbnamamatakuliah);
      cbnamamatakuliah.setBounds(720,50,175,20);
      cbnamamatakuliah.setVisible(false);

      getContentPane().add(rbperruang);
      rbperruang.setBounds(600,70,120,20);
      getContentPane().add(cbnamaruang);
      cbnamaruang.setBounds(720,70,175,20);
      cbnamaruang.setVisible(false);

      grupreport.add(rball);
      rball.setSelected(true);
      grupreport.add(rbperdosen);
      grupreport.add(rbperkelas);
      grupreport.add(rbpermatakuliah);
      grupreport.add(rbperruang);

      tabel1.setModel(tabMode1);
      //
      tabel1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
      TableColumn colhari=tabel1.getColumnModel().getColumn(0);
      colhari.setPreferredWidth(70);
      TableColumn coljam=tabel1.getColumnModel().getColumn(1);
      coljam.setPreferredWidth(100);
      TableColumn colkodemk=tabel1.getColumnModel().getColumn(2);
      colkodemk.setPreferredWidth(80);
      TableColumn colnamamk=tabel1.getColumnModel().getColumn(3);
      colnamamk.setPreferredWidth(220);
      TableColumn colsks=tabel1.getColumnModel().getColumn(4);
      colsks.setPreferredWidth(50);
      TableColumn colkelas=tabel1.getColumnModel().getColumn(5);
      colkelas.setPreferredWidth(50);
      TableColumn colkodedosen=tabel1.getColumnModel().getColumn(6);
      colkodedosen.setPreferredWidth(80);
      TableColumn colnamadosen=tabel1.getColumnModel().getColumn(7);
      colnamadosen.setPreferredWidth(230);
      TableColumn colruang=tabel1.getColumnModel().getColumn(8);
      colruang.setPreferredWidth(80);
      //
      getContentPane().add(scroll_panel1);
      scroll_panel1.setBounds(10,100,975,450);
      scroll_panel1.getViewport().add(tabel1);
      tabel1.setEnabled(false);

      setVisible(true);
   }

   public void tampilMatakuliah(String kodejurusan)
   {
      String smt=(String) cbsemester.getSelectedItem();

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
      String kodedos="";

      try
      {
         Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);

         if(rball.isSelected()==true)
         {
            String sql="SELECT a.Hari, a.JamMulai, a.JamAkhir, a.KodeMK, b.Mtk_NAMA, b.Mtk_SKS, a.Kelas, a.KodeDosen, c.DOS_GELARDEPAN AS GD, c.DOS_NAMA, c.DOS_GELARBELAKANG AS GB, a.Ruang FROM JadwalKuliah AS a, Matakuliah AS b, Dosen AS c, Hari as d WHERE a.JurKode='"+kodejurusan+"' and a.KodeMK=b.Mtk_KODE and a.KodeDosen=c.DOS_KODE and a.Hari=d.Hari_Nama and TahunAjaran='"+thn+"' and Semester='"+semester+"' ORDER BY d.Hari_Kode, a.JamMulai";
            ResultSet rs=statement.executeQuery(sql);

            hapusTabel1();

            while (rs.next())
            {
               Object[] data = {rs.getString(1),rs.getString(2)+" - "+rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9)+" "+rs.getString(10)+" "+rs.getString(11),rs.getString(12)};
               tabMode1.addRow(data);
            }
         }

         if(rbperdosen.isSelected()==true)
         {
            String nmdos=(String) cbnamadosen.getSelectedItem();
            try
            {
               String sql="SELECT DOS_KODE from Dosen where DOS_NAMA='"+nmdos+"'";
               ResultSet rs=statement.executeQuery(sql);

               if (rs.next())
               {
                  kodedos=rs.getString(1);
               }
            }
            catch (Exception DBException)
            {
               System.err.println("Error : " + DBException);
            }

            String sql="SELECT a.Hari, a.JamMulai, a.JamAkhir, a.KodeMK, b.Mtk_NAMA, b.Mtk_SKS, a.Kelas, a.KodeDosen, c.DOS_GELARDEPAN AS GD, c.DOS_NAMA, c.DOS_GELARBELAKANG AS GB, a.Ruang FROM JadwalKuliah AS a, Matakuliah AS b, Dosen AS c, Hari as d WHERE a.JurKode='"+kodejurusan+"' and a.KodeMK=b.Mtk_KODE and a.KodeDosen=c.DOS_KODE and a.KodeDosen='"+kodedos+"' and a.Hari=d.Hari_Nama and TahunAjaran='"+thn+"' and Semester='"+semester+"' ORDER BY d.Hari_Kode, a.JamMulai";
            ResultSet rs=statement.executeQuery(sql);

            hapusTabel1();

            while (rs.next())
            {
               Object[] data = {rs.getString(1),rs.getString(2)+" - "+rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9)+" "+rs.getString(10)+" "+rs.getString(11),rs.getString(12)};
               tabMode1.addRow(data);
            }
         }

         if(rbperkelas.isSelected()==true)
         {
            String nmkelas=(String) cbnamakelas.getSelectedItem();

            String sql="SELECT a.Hari, a.JamMulai, a.JamAkhir, a.KodeMK, b.Mtk_NAMA, b.Mtk_SKS, a.Kelas, a.KodeDosen, c.DOS_GELARDEPAN AS GD, c.DOS_NAMA, c.DOS_GELARBELAKANG AS GB, a.Ruang FROM JadwalKuliah AS a, Matakuliah AS b, Dosen AS c, Hari as d WHERE a.JurKode='"+kodejurusan+"' and a.KodeMK=b.Mtk_KODE and a.KodeDosen=c.DOS_KODE and a.Kelas='"+nmkelas+"' and a.Hari=d.Hari_Nama and TahunAjaran='"+thn+"' and Semester='"+semester+"' ORDER BY d.Hari_Kode, a.JamMulai";
            ResultSet rs=statement.executeQuery(sql);

            hapusTabel1();

            while (rs.next())
            {
               Object[] data = {rs.getString(1),rs.getString(2)+" - "+rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9)+" "+rs.getString(10)+" "+rs.getString(11),rs.getString(12)};
               tabMode1.addRow(data);
            }
         }
         if(rbpermatakuliah.isSelected()==true)
         {
            String nmmatakuliah=(String) cbnamamatakuliah.getSelectedItem();
            String kode_mtk="";
            String kur="07";

            try
            {
               String sql="SELECT a.Mtk_KODE from Matakuliah a, JadwalKuliah b where a.Mtk_NAMA='"+nmmatakuliah+"' and a.Jur_Kode='"+kodejurusan+"' and b.TahunAjaran='"+thn+"' and b.Semester='"+semester+"' and a.Mtk_KODE=b.KodeMK ";
               ResultSet rs=statement.executeQuery(sql);

               if (rs.next())
               {
                  kode_mtk=rs.getString(1);
               }
            }
            catch (Exception DBException)
            {
               System.err.println("Error : " + DBException);
            }

            String sql="SELECT a.Hari, a.JamMulai, a.JamAkhir, a.KodeMK, b.Mtk_NAMA, b.Mtk_SKS, a.Kelas, a.KodeDosen, c.DOS_GELARDEPAN AS GD, c.DOS_NAMA, c.DOS_GELARBELAKANG AS GB, a.Ruang FROM JadwalKuliah AS a, Matakuliah AS b, Dosen AS c, Hari as d WHERE a.JurKode='"+kodejurusan+"' and a.KodeMK=b.Mtk_KODE and a.KodeDosen=c.DOS_KODE and a.KodeMK='"+kode_mtk+"' and a.Hari=d.Hari_Nama and a.TahunAjaran='"+thn+"' and a.Semester='"+semester+"' and a.JurKode='"+kodejurusan+"' ORDER BY d.Hari_Kode, a.JamMulai";
            ResultSet rs=statement.executeQuery(sql);

            hapusTabel1();

            while (rs.next())
            {
               Object[] data = {rs.getString(1),rs.getString(2)+" - "+rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9)+" "+rs.getString(10)+" "+rs.getString(11),rs.getString(12)};
               tabMode1.addRow(data);
            }
         }

         if(rbperruang.isSelected()==true)
         {

            String nmruang=(String) cbnamaruang.getSelectedItem();
            String kode_mtk="";
            String kur="07";


            String sql="SELECT a.Hari, a.JamMulai, a.JamAkhir, a.KodeMK, b.Mtk_NAMA, b.Mtk_SKS, a.Kelas, a.KodeDosen, c.DOS_GELARDEPAN AS GD, c.DOS_NAMA, c.DOS_GELARBELAKANG AS GB, a.Ruang FROM JadwalKuliah AS a, Matakuliah AS b, Dosen AS c, Hari as d WHERE a.JurKode='"+kodejurusan+"' and a.KodeMK=b.Mtk_KODE and a.KodeDosen=c.DOS_KODE and a.Ruang='"+nmruang+"' and a.Hari=d.Hari_Nama and a.TahunAjaran='"+thn+"' and a.Semester='"+semester+"' and a.JurKode='"+kodejurusan+"' ORDER BY d.Hari_Kode, a.JamMulai";
            ResultSet rs=statement.executeQuery(sql);

            hapusTabel1();

            while (rs.next())
            {
               Object[] data = {rs.getString(1),rs.getString(2)+" - "+rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9)+" "+rs.getString(10)+" "+rs.getString(11),rs.getString(12)};
               tabMode1.addRow(data);
            }
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

   class bahancetak implements Printable
   {
      public int print(Graphics graphics,PageFormat pageFormat,int pageIndex) throws PrinterException
      {
         String smt=(String) cbsemester.getSelectedItem();
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
         String jurusan=(String) cbjurusan.getSelectedItem();
         String titik2=":";

         int i=0;
         int y=0;
         int ukuranfont=12;
         int JumlahBaris=35;
         int TotalHalaman=5;

         if(pageIndex>(TotalHalaman-1))
            return NO_SUCH_PAGE;

         Graphics2D g=(Graphics2D) graphics;
         double height=pageFormat.getImageableHeight();
         double width=pageFormat.getImageableWidth();

         g.translate(pageFormat.getImageableX(),pageFormat.getImageableY());
         g.translate(0f,-pageIndex*(int)height);
         g.setClip(0,(int)height*pageIndex,(int)width,(int)height);

         if (g != null)
         {
            g.setFont(new Font("Dialog", 1, 11));
            g.drawString("Semester", 10, 20);
            g.drawString(titik2, 100, 20);
            g.drawString(semester, 110, 20);
            g.drawString("Tahun Ajaran", 10, 35);
            g.drawString(titik2, 100, 35);
            g.drawString(thn, 110, 35);
            g.drawString("Jurusan", 10, 55);
            g.drawString(titik2, 100, 55);
            g.drawString(jurusan, 110, 55);

            g.setFont(new Font("Dialog", 1, 9));
            g.setFont(new Font("Dialog", 0, 8));

            String hr = tabel1.getColumnName(0);
            String jam = tabel1.getColumnName(1);
            String kodemk = tabel1.getColumnName(2);
            String namamk = tabel1.getColumnName(3);
            String sks = tabel1.getColumnName(4);
            String kelas = tabel1.getColumnName(5);
            String kodedos = tabel1.getColumnName(6);
            String namados = tabel1.getColumnName(7);
            String ruang = tabel1.getColumnName(8);

            //String sub_total = tabel.getColumnName(3);
            g.setFont(new Font("Dialog", 1, 8));
            g.drawString(hr, 10, 80);
            g.drawString(jam, 40, 80);
            g.drawString(kodemk, 90, 80);
            g.drawString(namamk, 130, 80);
            g.drawString(sks, 280, 80);
            g.drawString(kelas, 320, 80);
            g.drawString(kodedos, 350, 80);
            g.drawString(namados, 400, 80);
            g.drawString(ruang, 540, 80);

            //g.drawString(sub_total, 200, 100);
            g.drawLine(10, 90, 600, 90);
            int n = tabMode1.getRowCount();
            for (i = 0; i < n; i++)
            {
               int k = i + 1;
               int j = 10 * k;
               y = 100 + j;
               g.setFont(new Font("Dialog", 0, 8));
               String data_hari = tabMode1.getValueAt(i, 0).toString();
               String data_jam = tabMode1.getValueAt(i, 1).toString();
               String data_kodemk = tabMode1.getValueAt(i, 2).toString();
               String data_namamk = tabMode1.getValueAt(i, 3).toString();
               String data_sks = tabMode1.getValueAt(i, 4).toString();
               String data_kelas = tabMode1.getValueAt(i, 5).toString();
               String data_kodedos = tabMode1.getValueAt(i, 6).toString();
               String data_namados = tabMode1.getValueAt(i, 7).toString();
               String data_ruang = tabMode1.getValueAt(i, 8).toString();
               g.drawString(data_hari, 10, y);
               g.drawString(data_jam, 40, y);
               g.drawString(data_kodemk, 90, y);
               g.drawString(data_namamk, 130, y);
               g.drawString(data_sks, 280, y);
               g.drawString(data_kelas, 320, y);
               g.drawString(data_kodedos, 350, y);
               g.drawString(data_namados, 400, y);
               g.drawString(data_ruang, 540, y);
            }
            g.drawLine(10, y + 5, 600, y + 5);
         }
         return PAGE_EXISTS;
      }
   }

   public static void main(String args[])
   {
      ViewJadwal vj=new ViewJadwal();
      vj.komponenVisual();
   }

   class MyHandler implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         String namajurusan=(String) cbjurusan.getSelectedItem();
         Jurusan jur=new Jurusan();
         String kodejurusan=jur.getKodejurusan(namajurusan);

         if (e.getSource() == btnrun)
         {
            tampilMatakuliah(kodejurusan);
         }
         else if (e.getSource() == btnprint)
         {
            try
            {
               PrinterJob tugas = PrinterJob.getPrinterJob();
               tugas.setPrintable(new bahancetak());
               if (tugas.printDialog())
               {
                  try
                  {
                     tugas.print();
                  }
                  catch (PrinterException salah)
                  {
                     System.out.println(salah);
                  }
               }
            }
            catch(Exception f)
            {
            }
         }
         else if (e.getSource() == rbperdosen)
         {
            cbnamadosen.removeAllItems();
            cbnamadosen.setVisible(true);
            cbnamakelas.setVisible(false);
            cbnamamatakuliah.setVisible(false);
            cbnamaruang.setVisible(false);

            String thn=(String) cbthnajaran.getSelectedItem();
            String smt=(String) cbsemester.getSelectedItem();
            String semester="";
            if(smt.equals("GASAL"))
            {
               semester="1";
            }
            else
            {
               semester="2";
            }

            try
            {
               Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
               String sql="SELECT distinct(b.DOS_GELARDEPAN), b.DOS_NAMA, b.DOS_GELARBELAKANG from JadwalKuliah a, Dosen b where a.KodeDosen=b.DOS_KODE and a.TahunAjaran='"+thn+"' and a.Semester='"+semester+"'";
               ResultSet rs=statement.executeQuery(sql);

               while (rs.next())
               {
                  cbnamadosen.addItem(rs.getString(2));
               }

               statement.close();
            }
            catch (Exception DBException)
            {
               System.err.println("Error 1 : " + DBException);
            }
         }
         else if (e.getSource() == rbperkelas)
         {
            cbnamakelas.removeAllItems();
            cbnamadosen.setVisible(false);
            cbnamakelas.setVisible(true);
            cbnamamatakuliah.setVisible(false);
            cbnamaruang.setVisible(false);

            String thn=(String) cbthnajaran.getSelectedItem();
            String smt=(String) cbsemester.getSelectedItem();
            String semester="";
            if(smt.equals("GASAL"))
            {
               semester="1";
            }
            else
            {
               semester="2";
            }

            try
            {
               Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
               String sql="SELECT distinct(Kelas) from JadwalKuliah where TahunAjaran='"+thn+"' and Semester='"+semester+"'";
               ResultSet rs=statement.executeQuery(sql);

               while (rs.next())
               {
                  cbnamakelas.addItem(rs.getString(1));
               }

               statement.close();
            }
            catch (Exception DBException)
            {
               System.err.println("Error 2 : " + DBException);
            }
         }
         else if (e.getSource() == rbpermatakuliah)
         {
            cbnamamatakuliah.removeAllItems();
            cbnamadosen.setVisible(false);
            cbnamakelas.setVisible(false);
            cbnamamatakuliah.setVisible(true);
            cbnamaruang.setVisible(false);

            String thn=(String) cbthnajaran.getSelectedItem();
            String smt=(String) cbsemester.getSelectedItem();
            String semester="";
            if(smt.equals("GASAL"))
            {
               semester="1";
            }
            else
            {
               semester="2";
            }

            try
            {
               Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
               String sql="SELECT distinct(b.Mtk_KODE), b.Mtk_NAMA from JadwalKuliah a, Matakuliah b where a.KodeMK=b.Mtk_KODE and a.TahunAjaran='"+thn+"' and a.Semester='"+semester+"'";
               ResultSet rs=statement.executeQuery(sql);

               while (rs.next())
               {
                  cbnamamatakuliah.addItem(rs.getString(2));
               }

               statement.close();
            }
            catch (Exception DBException)
            {
               System.err.println("Error 3 : " + DBException);
            }

         }
         else if (e.getSource() == rbperruang)
         {
            cbnamamatakuliah.removeAllItems();
            cbnamadosen.setVisible(false);
            cbnamakelas.setVisible(false);
            cbnamamatakuliah.setVisible(false);
            cbnamaruang.setVisible(true);

            String thn=(String) cbthnajaran.getSelectedItem();
            String smt=(String) cbsemester.getSelectedItem();
            String semester="";
            if(smt.equals("GASAL"))
            {
               semester="1";
            }
            else
            {
               semester="2";
            }

            try
            {
               Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
               String sql="SELECT distinct(Ruang) from JadwalKuliah where JurKode='"+kodejurusan+"' and TahunAjaran='"+thn+"' and Semester='"+semester+"'";
               ResultSet rs=statement.executeQuery(sql);

               while (rs.next())
               {
                  cbnamaruang.addItem(rs.getString(1));
               }

               statement.close();
            }
            catch (Exception DBException)
            {
               System.err.println("Error 4 : " + DBException);
            }
         }
         else if (e.getSource() == rball)
         {
            cbnamadosen.setVisible(false);
            cbnamakelas.setVisible(false);
            cbnamamatakuliah.setVisible(false);
            cbnamaruang.setVisible(false);
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
               btnrun.requestFocus();
            }
         }
         else if(event.getSource() == btnrun)
         {
            if(event.getKeyCode()==event.VK_ENTER)
            {
               tampilMatakuliah(kodejurusan);
            }
         }
         else if(event.getSource() == btnprint)
         {
            if(event.getKeyCode()==event.VK_ENTER)
            {
               try
               {
                  PrinterJob tugas = PrinterJob.getPrinterJob();
                  tugas.setPrintable(new bahancetak());
                  if (tugas.printDialog())
                  {
                     try
                     {
                        tugas.print();
                     }
                     catch (PrinterException salah)
                     {
                        System.out.println(salah);
                     }
                  }
               }
               catch(Exception f)
               {
               }
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