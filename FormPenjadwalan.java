import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;
import java.sql.*;
import java.util.Date;
import java.util.Calendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class FormPenjadwalan extends JFrame
{
   JMenuBar menupenjadwalan=new JMenuBar();

   JMenu mfile=new JMenu("Data");
   JMenuItem dataprodi=new JMenuItem("Program Studi");
   JMenuItem infodosen=new JMenuItem("Dosen");
   JMenuItem dataruang=new JMenuItem("Ruang Kuliah");
   JMenuItem infomatakuliah=new JMenuItem("Matakuliah");
   JMenuItem pemasaran=new JMenuItem("Pemasaran Matakuliah");
   JMenuItem gen_ruangWaktu=new JMenuItem("Generate Ruang-Waktu");
   JMenuItem datatahunajaran=new JMenuItem("Data Tahun Ajaran");
   JMenuItem exit=new JMenuItem("Exit");

   JMenu report=new JMenu("Laporan");
   JMenuItem jadwalkuliah=new JMenuItem("Jadwal Kuliah");
   JMenuItem statistik=new JMenuItem("Statistik");

   JMenu help=new JMenu("Bantuan");
   JMenuItem mfitur=new JMenuItem("Fitur");
   JMenuItem mguide=new JMenuItem("Petunjuk Penggunaan");
   JMenuItem mabout=new JMenuItem("About");

   JPanel panel1=new JPanel();
   JPanel panel2=new JPanel();

   JLabel lblsemester=new JLabel("Semester");
   String[] namasemester={"GASAL","GENAP"};
   JComboBox cbsemester=new JComboBox(namasemester);

   JLabel lblthnajaran=new JLabel("Tahun Ajaran");
   JComboBox cbthnajaran;

   JLabel lbljurusan=new JLabel("Jurusan");
   JComboBox cbjurusan;

   JLabel lblkodemk=new JLabel("Kode Matakuliah");
   JComboBox cbkodemk=new JComboBox();

   JLabel lblnamamk=new JLabel("Nama Matakuliah");
   JTextField txtnamamk=new JTextField();

   JLabel lblkddosen=new JLabel("Kode Dosen");
   JComboBox cbkddosen=new JComboBox();
   JLabel lblnamadosen=new JLabel("Nama Dosen");
   JTextField txtnamadosen=new JTextField();

   JLabel lblkelas=new JLabel("Kelas");
   String[] jeniskelas={"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P"};
   JComboBox cbkelas=new JComboBox(jeniskelas);

   JLabel lblkapasitaskelas=new JLabel("Kapasitas Kelas");
   String[] kapasitaskelas={"45","40","35","30","25","20","10"};
   JComboBox cbkapasitaskelas=new JComboBox(kapasitaskelas);

   JLabel lblhari=new JLabel("Hari/Jam");
   String[] hari={"Senin","Selasa","Rabu","Kamis","Jumat","Sabtu"};
   JComboBox cbhari=new JComboBox(hari);

   JLabel lbljam=new JLabel("Jam");
   JComboBox cbjam=new JComboBox();

   JLabel lblruang=new JLabel("Ruang");
   JComboBox cbruang=new JComboBox();

   //JPanel pnltombol=new JPanel();
   JButton btnsave=new JButton("Save");
   JButton btnupdate=new JButton("Update");
   JButton btndelete=new JButton("Delete");
   JButton btnclear=new JButton("Clear");
   JButton btnsearch=new JButton("Search");
   JButton btnclose=new JButton("Close");
   JButton btnrefresh=new JButton("Refresh");
   JButton btnplus=new JButton("+");

   JTable tabel1=new JTable();
   JScrollPane scroll_panel1=new JScrollPane();
   Object[] row1 = {"Jam","Senin","Selasa","Rabu","Kamis","Jumat","Sabtu"};
   DefaultTableModel tabMode1=new DefaultTableModel(null, row1);

   JLabel lblsoftwarehouse=new JLabel("www.secangkirkopipahit.com");

   public Connection connection;
   public Statement statement;

   String nmjur;
   String jam="00.00 - 00.00";
   String jammulai="00.00";
   String jamakhir="00.00";
   String ruang;
   Date jammu;
   Date jamak;

   String pola="H.mm";
   SimpleDateFormat sdf=new SimpleDateFormat(pola);
   long ljm=0;
   long lja=0;

   Reusable re=new Reusable();

   public FormPenjadwalan()
   {
      Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
      setTitle("Penjadwalan Perkuliahan 1.2.5");
      setSize(600,620);
      int lebar = (screen.width - getSize().width) / 2;
      int tinggi = (screen.height - getSize().height) / 2;
      setLocation(lebar, tinggi);
      setResizable(false);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
      cbruang.addKeyListener(handler2);
      cbruang.addActionListener(handler);
      cbkodemk.addKeyListener(handler2);
      cbkodemk.addActionListener(handler);
      cbkelas.addKeyListener(handler2);
      cbkddosen.addKeyListener(handler2);
      cbkddosen.addActionListener(handler);
      cbhari.addKeyListener(handler2);
      cbhari.addActionListener(handler);
      cbjam.addKeyListener(handler2);
      btnsave.addKeyListener(handler2);
      btnsave.addActionListener(handler);
      btnupdate.addKeyListener(handler2);
      btnupdate.addActionListener(handler);
      btnrefresh.addKeyListener(handler2);
      btnrefresh.addActionListener(handler);
      btnsearch.addKeyListener(handler2);
      btnsearch.addActionListener(handler);
      btndelete.addKeyListener(handler2);
      btndelete.addActionListener(handler);
      dataprodi.addActionListener(handler);
      infodosen.addActionListener(handler);
      dataruang.addActionListener(handler);
      infomatakuliah.addActionListener(handler);
      pemasaran.addActionListener(handler);
      gen_ruangWaktu.addActionListener(handler);
      datatahunajaran.addActionListener(handler);
      exit.addActionListener(handler);
      jadwalkuliah.addActionListener(handler);
      mabout.addActionListener(handler);


   }

   // method 1 : komponenVisual()
   // digunakan untuk mengatur GUI
   public void komponenVisual()
   {
      getContentPane().setLayout(null);

      setJMenuBar(menupenjadwalan);
      menupenjadwalan.add(mfile);
      mfile.setMnemonic('D');
      mfile.add(dataprodi);
      mfile.add(infodosen);
      mfile.add(dataruang);
      mfile.add(infomatakuliah);
      mfile.add(pemasaran);
      mfile.addSeparator();
      mfile.add(gen_ruangWaktu);
      mfile.add(datatahunajaran);
      mfile.addSeparator();
      mfile.add(exit);

      menupenjadwalan.add(report);
      report.setMnemonic('L');
      report.add(jadwalkuliah);

      menupenjadwalan.add(help);
      help.setMnemonic('B');
      help.add(mabout);

      //panel 1
      getContentPane().add(panel1);
      panel1.setBounds(20,10,550,85);
      panel1.setBorder(BorderFactory.createEtchedBorder());

      panel1.setLayout(null);
      panel1.add(lblsemester);
      lblsemester.setBounds(20,10,100,20);
      panel1.add(cbsemester);
      cbsemester.setBounds(130,10,100,20);

      panel1.add(lblthnajaran);
      lblthnajaran.setBounds(20,32,100,20);
      panel1.add(cbthnajaran);
      cbthnajaran.setBounds(130,32,100,20);

      panel1.add(lbljurusan);
      lbljurusan.setBounds(20,54,100,20);
      panel1.add(cbjurusan);
      cbjurusan.setBounds(130,54,270,20);

      panel1.add(btnrefresh);
      btnrefresh.setBounds(410,54,80,20);

      //panel 2
      panel2.setLayout(null);
      getContentPane().add(panel2);
      panel2.setBounds(20,100,550,135);
      panel2.setBorder(BorderFactory.createEtchedBorder());

      panel2.add(lblruang);
      lblruang.setBounds(20,10,100,20);
      panel2.add(cbruang);
      cbruang.setBounds(130,10,270,20);

      panel2.add(lblkodemk);
      lblkodemk.setBounds(20,32,100,20);
      panel2.add(cbkodemk);
      cbkodemk.setBounds(130,32,100,20);
      panel2.add(txtnamamk);
      txtnamamk.setBounds(235,32,300,20);
      txtnamamk.setEditable(false);
      //txtnamamk.setBorder(BorderFactory.createEtchedBorder(1));
      txtnamamk.setBorder(BorderFactory.createEmptyBorder());


      panel2.add(lblkelas);
      lblkelas.setBounds(20,54,100,20);
      panel2.add(cbkelas);
      cbkelas.setBounds(130,54,100,20);

      panel2.add(lblkddosen);
      lblkddosen.setBounds(20,76,100,20);
      panel2.add(cbkddosen);
      cbkddosen.setBounds(130,76,100,20);
      panel2.add(txtnamadosen);
      txtnamadosen.setBounds(235,76,170,20);
      txtnamadosen.setEditable(false);
      txtnamadosen.setBorder(BorderFactory.createEmptyBorder());

      panel2.add(lblhari);
      lblhari.setBounds(20,98,100,20);
      panel2.add(cbhari);
      cbhari.setBounds(130,98,100,20);
      panel2.add(cbjam);
      cbjam.setBounds(235,98,165,20);

      panel2.add(btnsave);
      btnsave.setBounds(410,10,80,20);
      panel2.add(btnsearch);
      btnsearch.setBounds(410,76,80,20);
      panel2.add(btndelete);
      btndelete.setBounds(410,98,80,20);

      //--------------------bagian untuk menampilkan jadwal per ruang
      tabel1.setModel(tabMode1);
      //
      tabel1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
      TableColumn coljam=tabel1.getColumnModel().getColumn(0);
      coljam.setPreferredWidth(57);
      TableColumn colsenin=tabel1.getColumnModel().getColumn(1);
      colsenin.setPreferredWidth(70);
      TableColumn colselasa=tabel1.getColumnModel().getColumn(2);
      colselasa.setPreferredWidth(70);
      TableColumn colrabu=tabel1.getColumnModel().getColumn(3);
      colrabu.setPreferredWidth(70);
      TableColumn colkamis=tabel1.getColumnModel().getColumn(4);
      colkamis.setPreferredWidth(70);
      TableColumn coljumat=tabel1.getColumnModel().getColumn(5);
      coljumat.setPreferredWidth(70);
      TableColumn colsabtu=tabel1.getColumnModel().getColumn(6);
      colsabtu.setPreferredWidth(70);
      //
      getContentPane().add(scroll_panel1);
      scroll_panel1.setBounds(20,250,550,280);
      scroll_panel1.getViewport().add(tabel1);
      tabel1.setEnabled(false);

      getContentPane().add(lblsoftwarehouse);
      lblsoftwarehouse.setBounds(20,530,150,20);
      lblsoftwarehouse.setFont(new Font("Courier New",Font.PLAIN,9));

      setVisible(true);
   }

   /** Method 2 :
    method ini digunakan untuk menyiapkan slot waktu per ruang
    slot waktu ini akan digunakan untuk proses penyusunan jadwal
    tanpa method ini maka penjadwalan tidak dapat dilakukan
    bila slot waktu pada semester dan tahun ajaran tertentu telah dibuat
    maka slot waktu tidak dapat dibuat lagi
    -----
    perlu dibuat method untuk menghapus slot waktu yang telah dibuat
    bila akan di generate slot baru pada semester dan tahun ajaran
    yang telah dibuat
    -----
   **/
   public void inisialisasiRuangWaktu()
   {
      int banyakdata=0;
      Vector<String> v=new Vector<String>(10);
      String ssql="select KodeJam from AturJam";
      String[] kodejam=re.getArray1Dimensi(connection,statement,ssql);

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

      String tahunajaran=(String)cbthnajaran.getSelectedItem();
      String senin="0";
      String selasa="0";
      String rabu="0";
      String kamis="0";
      String jumat="0";
      String sabtu="0";

      boolean flag=false;
      String gen="00";

      String namajurusan=(String) cbjurusan.getSelectedItem();
      Jurusan jur=new Jurusan();
      String kodejurusan=jur.getKodejurusan(namajurusan);

      try
      {
         Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
         String sql="SELECT KodeRuang from Ruang where JurusanUser='"+kodejurusan+"' or JurusanUser='"+gen+"'";
         ResultSet rs=statement.executeQuery(sql);

         while(rs.next())
         {
             v.add(rs.getString(1));
         }

         banyakdata=v.size();
         String[] druang=new String[banyakdata];
         System.out.println(banyakdata);

         for(int i=0; i<banyakdata; i++)
         {
            druang[i]=v.get(i);
         }

         statement.close();

         for(int i=0; i<banyakdata; i++)
         {
            System.out.print(druang[i]+" ");
         }

         statement = connection.createStatement();
         sql="select koderuang from data_conv_tes where (TahunAjaran='"+tahunajaran+"' and Semester='"+semester+"')";
         rs=statement.executeQuery(sql);

         int d=0;

         while(rs.next())
         {
            if(druang[d].equals(rs.getString(1)))
            {
               flag=true;
               break;
            }
            d++;
         }

         if(flag==true)
         {
            System.out.print("Data Telah Ada");
            JOptionPane.showMessageDialog(null,"Data Ruang-Waktu jurusan ini telah di ada.","Konfirmasi",JOptionPane.INFORMATION_MESSAGE);
         }
         else
         {
            for(int i=0; i<druang.length; i++)
            {
               for(int j=0; j<kodejam.length; j++)
               {
                  sql="insert into data_conv_tes values ('"+tahunajaran+"','"+semester+"','"+druang[i]+"','"+kodejam[j]+"','"+senin+"','"+selasa+"','"+rabu+"','"+kamis+"','"+jumat+"','"+sabtu+"');";
                  statement.executeUpdate(sql);
               }
            }
            System.out.println("Data telah masuk");
         }

         statement.close();

      }
      catch (Exception DBException)
      {
         System.err.println("Error 13: " + DBException);
      }
   }

   public void ambilKodeMK()
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

      String thnajaran=(String)cbthnajaran.getSelectedItem();

      String namajurusan=(String) cbjurusan.getSelectedItem();
      Jurusan jur=new Jurusan();
      String kodejurusan=jur.getKodejurusan(namajurusan);

      try
      {
         Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
         String sql="SELECT Mtk_Kode FROM Matakuliah_Pemasaran WHERE Jur_Kode='"+kodejurusan+"' and Thn_Kode='"+thnajaran+"' and Smt_Kode='"+semester+"' order by Mtk_KODE";
         ResultSet rs=statement.executeQuery(sql);

         while(rs.next())
         {
            cbkodemk.addItem(rs.getString(1));
         }
         statement.close();
      }
      catch (Exception DBException)
      {
         System.err.println("Error 12: " + DBException);
      }
   }

   public void ambilKodeRuang()
   {
      String umum="00";

      String namajurusan=(String) cbjurusan.getSelectedItem();
      Jurusan jur=new Jurusan();
      String kodejurusan=jur.getKodejurusan(namajurusan);

      try
      {
         Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
         String sql="SELECT KodeRuang FROM Ruang WHERE JurusanUser='"+kodejurusan+"' or JurusanUser='"+umum+"' order by KodeRuang";
         ResultSet rs=statement.executeQuery(sql);

         while(rs.next())
         {
            cbruang.addItem(rs.getString(1));
         }
         statement.close();
      }
      catch (Exception DBException)
      {
         System.err.println("Error 11: " + DBException);
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
         System.err.println("Error 10: " + DBException);
      }
   }

   public void ambilKodeDosen()
   {
      cbkddosen.removeAllItems();

      try
      {
         Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
         String sql="SELECT DOS_KODE FROM Dosen order by DOS_KODE";
         ResultSet rs=statement.executeQuery(sql);

         while(rs.next())
         {
            cbkddosen.addItem(rs.getString(1));
         }

         statement.close();
      }
      catch (Exception DBException)
      {
         System.err.println("Error 9: " + DBException);
      }
   }

   public void ambilNamaDosen()
   {
      String kddosen=(String) cbkddosen.getSelectedItem();

      try
      {
         Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
         String sql="SELECT DOS_GELARDEPAN,DOS_NAMA,DOS_GELARBELAKANG FROM Dosen where DOS_KODE='"+kddosen+"'";
         ResultSet rs=statement.executeQuery(sql);

         if(rs.next())
         {
            if(rs.getString(1)==null)
            {
               txtnamadosen.setText(rs.getString(2)+" "+rs.getString(3));
            }
            else
            {
               txtnamadosen.setText(rs.getString(1)+" "+rs.getString(2)+" "+rs.getString(3));
            }
         }
         statement.close();
      }
      catch (Exception DBException)
      {
         System.err.println("Error 8: " + DBException);
      }
   }

   public void ambilJamKuliah()
   {
      try
      {
         Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
         String sql="SELECT Range FROM JamKuliah order by KodeJam";
         ResultSet rs=statement.executeQuery(sql);

         while(rs.next())
         {
            cbjam.addItem(rs.getString(1));
         }

         statement.close();
      }
      catch (Exception DBException)
      {
         System.err.println("Error 7: " + DBException);
      }
   }

   public void simpanData()
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

      String tahunajaran=(String)cbthnajaran.getSelectedItem();
      String ruang=(String)cbruang.getSelectedItem();

      //menyiapkan data jammu dan jamak
      System.out.println("----------");
      jam=(String) cbjam.getSelectedItem();
      System.out.println("test : "+jam);

      jammulai=jam.substring(0,5);
      System.out.println("test : "+jammulai);
      jamakhir=jam.substring(8,13);
      System.out.println("test : "+jamakhir);

      try
      {
         jammu=sdf.parse(jammulai);
         jamak=sdf.parse(jamakhir);
      }
      catch(Exception e)
      {
      }

      System.out.println(jammu.getTime());
      System.out.println(jamak.getTime());
      System.out.println("-----------");

      boolean constraint_dosen=cek_dosen2tempat();

      if(constraint_dosen==true)
      {
         JOptionPane.showMessageDialog(null,"Dosen Bersangkutan Telah Mengajar di Ruang yang lain !!!\n"+"Silahkan memilih ruang atau waktu yang lain.","Konfirmasi",JOptionPane.INFORMATION_MESSAGE);
         cbruang.requestFocus();
      }
      else
      {
         cekBentrok();
      }
      tampilJadwalPerRuang(tahunajaran, semester, ruang);
   }

   public void cekBentrok()
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

      String tahunajaran=(String)cbthnajaran.getSelectedItem();

      //String kdjurusan=ambilKodeJur();
      String namajurusan=(String) cbjurusan.getSelectedItem();
      Jurusan jur=new Jurusan();
      String kodejurusan=jur.getKodejurusan(namajurusan);

      String kodemk=(String)cbkodemk.getSelectedItem();
      String kelas=(String)cbkelas.getSelectedItem();
      String kodedosen=(String)cbkddosen.getSelectedItem();
      String hari=(String)cbhari.getSelectedItem();
      String ruang=(String)cbruang.getSelectedItem();
      String kapasitas=(String)cbkapasitaskelas.getSelectedItem();

      boolean test=false;
      String h="";
      String jm="";
      String ja="";
      String r="";
      String t="";
      String s="";

      if(kodejurusan==null || kodemk==null || kelas==null || kodedosen==null || hari==null || jammulai.equals("00.00") || jamakhir.equals("00.00") || ruang==null)
      {
         JOptionPane.showMessageDialog(null,"Jadwal gagal disimpan karena data tidak lengkap.\n"+"Silahkan Masukkan Kembali Jadwal Anda.","Konfirmasi",JOptionPane.INFORMATION_MESSAGE);
         bersih();
         cbjurusan.requestFocus();
      }
      else
      {
         try
         {
            statement = connection.createStatement();
            String sql="select Hari,JamMulai,JamAkhir,Ruang,TahunAjaran,Semester from JadwalKuliah";
            ResultSet rs=statement.executeQuery(sql);

            while(rs.next())
            {
               h=rs.getString(1);
               jm=rs.getString(2);
               ja=rs.getString(3);
               r=rs.getString(4);
               t=rs.getString(5);
               s=rs.getString(6);

               ljm=sdf.parse(jm).getTime();
               lja=sdf.parse(ja).getTime();

               if(hari.equals(h) && jammu.getTime()>=ljm && jammu.getTime()<lja && ruang.equals(r) && tahunajaran.equals(t) && semester.equals(s))
               {
                  test=true;
                  break;
               }
               else if(hari.equals(h) && jammu.getTime()<lja && jamak.getTime()>ljm && ruang.equals(r) && tahunajaran.equals(t) && semester.equals(s))
               {
                  test=true;
                  break;
               }
               else if(hari.equals(h) && ljm>=jammu.getTime() && lja<=jamak.getTime() && ruang.equals(r) && tahunajaran.equals(t) && semester.equals(s))
               {
                  test=true;
                  break;
               }
               else
               {
               }
            }
            statement.close();
         }
         catch(Exception e)
         {
            System.out.println("Ada Error Lho:"+e);
         }

         if(test==true)
         {
            JOptionPane.showMessageDialog(null,"Jadwal Mata Kuliah ini bersamaan waktunya dengan Mata Kuliah lain !!!\n"+"Silahkan memilih ruang atau waktu yang lain.","Konfirmasi",JOptionPane.INFORMATION_MESSAGE);
            cbruang.requestFocus();
         }
         else
         {
            try
            {
               int banyakdata=0;
               Vector<String> v=new Vector<String>(10);

               statement = connection.createStatement();

               String sql="insert into JadwalKuliah values('"+kodemk+"','"+kodejurusan+"','"+tahunajaran+"','"+semester+"','"+kelas+"','"+kodedosen+"','"+hari+"','"+jammulai+"','"+jamakhir+"','"+ruang+"','"+kapasitas+"');";
               statement.executeUpdate(sql);
               statement.close();

               //---------konversi data
               String range=(String) cbjam.getSelectedItem();
               String temp_kodejam="";

               Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
               sql="SELECT KodeJam from JamKuliah where Range='"+range+"'";
               ResultSet rs=statement.executeQuery(sql);

               if(rs.next())
               {
                  temp_kodejam=rs.getString(1);
               }

               System.out.print("range "+range);
               System.out.print("temp "+temp_kodejam);

               sql="SELECT Konversi FROM KonversiJam WHERE KodeJam='"+temp_kodejam+"'";
               rs=statement.executeQuery(sql);

               while(rs.next())
               {
                  v.add(rs.getString(1));
               }

               banyakdata=v.size();
               String[] xy=new String[banyakdata];

               for(int i=0; i<banyakdata; i++)
               {
                  xy[i]=v.get(i);
               }

               for(int i=0; i<banyakdata; i++)
               {
                  System.out.println("data"+i+":"+xy[i]);
               }

               //----------- update nilai
               String tanda1="1";

               for(int i=0; i<banyakdata; i++)
               {
                  String jam_kode=xy[i];

                  if(hari.equals("Senin"))
                  {
                     sql="UPDATE data_conv_tes SET Senin='"+kodemk+"' WHERE TahunAjaran='"+tahunajaran+"' and Semester='"+semester+"' and kodejam='"+jam_kode+"' and koderuang='"+ruang+"'";
                     statement.executeUpdate(sql);
                  }
                  else if(hari.equals("Selasa"))
                  {
                     sql="UPDATE data_conv_tes SET Selasa='"+kodemk+"' WHERE TahunAjaran='"+tahunajaran+"' and Semester='"+semester+"' and kodejam='"+jam_kode+"' and koderuang='"+ruang+"'";
                     statement.executeUpdate(sql);
                  }
                  else if(hari.equals("Rabu"))
                  {
                     sql="UPDATE data_conv_tes SET Rabu='"+kodemk+"' WHERE TahunAjaran='"+tahunajaran+"' and Semester='"+semester+"' and kodejam='"+jam_kode+"' and koderuang='"+ruang+"'";
                     statement.executeUpdate(sql);
                  }
                  else if(hari.equals("Kamis"))
                  {
                     sql="UPDATE data_conv_tes SET Kamis='"+kodemk+"' WHERE TahunAjaran='"+tahunajaran+"' and Semester='"+semester+"' and kodejam='"+jam_kode+"' and koderuang='"+ruang+"'";
                     statement.executeUpdate(sql);
                  }
                  else if(hari.equals("Jumat"))
                  {
                     sql="UPDATE data_conv_tes SET Jumat='"+kodemk+"' WHERE TahunAjaran='"+tahunajaran+"' and Semester='"+semester+"' and kodejam='"+jam_kode+"' and koderuang='"+ruang+"'";
                     statement.executeUpdate(sql);
                  }
                  else if(hari.equals("Sabtu"))
                  {
                     sql="UPDATE data_conv_tes SET Sabtu='"+kodemk+"' WHERE TahunAjaran='"+tahunajaran+"' and Semester='"+semester+"' and kodejam='"+jam_kode+"' and koderuang='"+ruang+"'";
                     statement.executeUpdate(sql);
                  }
                  else
                  {
                  }
               }
               statement.close();
            }
            catch(Exception e)
            {
               System.out.println("Error 6:"+e);
            }
         }
      }
   }

   public void updateData()
   {
      boolean constraint_dosen=cek_dosen2tempat();

      if(constraint_dosen==true)
      {
         JOptionPane.showMessageDialog(null,"Dosen Bersangkutan Telah Mengajar di Ruang yang lain !!!\n"+"Silahkan memilih ruang atau waktu yang lain.","Konfirmasi",JOptionPane.INFORMATION_MESSAGE);
         cbruang.requestFocus();
      }
      else
      {
         bahanUpdate();
      }
   }

   public void bahanUpdate()
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

      String tahunajaran=(String)cbthnajaran.getSelectedItem();

      String namajurusan=(String) cbjurusan.getSelectedItem();
      Jurusan jur=new Jurusan();
      String kodejurusan=jur.getKodejurusan(namajurusan);

      String kodemk=(String)cbkodemk.getSelectedItem();
      String kelas=(String)cbkelas.getSelectedItem();
      String kodedosen=(String)cbkddosen.getSelectedItem();
      String hari=(String)cbhari.getSelectedItem();
      String ruang=(String)cbruang.getSelectedItem();
      String kapasitas=(String)cbkapasitaskelas.getSelectedItem();

      boolean test=false;
      String h="";
      String jm="";
      String ja="";
      String r="";
      String t="";
      String s="";

      if(kodejurusan==null || kodemk==null || kelas==null || kodedosen==null || hari==null || jammulai.equals("00.00") || jamakhir.equals("00.00") || ruang==null)
      {
         JOptionPane.showMessageDialog(null,"Jadwal gagal disimpan karena data tidak lengkap.\n"+"Silahkan Masukkan Kembali Jadwal Anda.","Konfirmasi",JOptionPane.INFORMATION_MESSAGE);
         bersih();
         cbjurusan.requestFocus();
      }
      else
      {
         try
         {
            statement = connection.createStatement();
            String sql="select Hari,JamMulai,JamAkhir,Ruang,TahunAjaran,Semester from JadwalKuliah";
            ResultSet rs=statement.executeQuery(sql);

            while(rs.next())
            {
               h=rs.getString(1);
               jm=rs.getString(2);
               ja=rs.getString(3);
               r=rs.getString(4);
               t=rs.getString(5);
               s=rs.getString(6);

               ljm=sdf.parse(jm).getTime();
               lja=sdf.parse(ja).getTime();

               if(hari.equals(h) && jammu.getTime()>=ljm && jammu.getTime()<lja && ruang.equals(r) && tahunajaran.equals(t) && semester.equals(s))
               {
                  test=true;
                  break;
               }
               else if(hari.equals(h) && jammu.getTime()<lja && jamak.getTime()>ljm && ruang.equals(r) && tahunajaran.equals(t) && semester.equals(s))
               {
                  test=true;
                  break;
               }
               else if(hari.equals(h) && ljm>=jammu.getTime() && lja<=jamak.getTime() && ruang.equals(r) && tahunajaran.equals(t) && semester.equals(s))
               {
                  test=true;
                  break;
               }
               else
               {
               }
            }
            statement.close();
         }
         catch(Exception e)
         {
            System.out.println("Error 5:"+e);
         }

         if(test==true)
         {
            JOptionPane.showMessageDialog(null,"Jadwal Mata Kuliah ini bersamaan waktunya dengan Mata Kuliah lain !!!\n"+"Silahkan memilih ruang atau waktu yang lain.","Konfirmasi",JOptionPane.INFORMATION_MESSAGE);
            cbruang.requestFocus();
         }
         else
         {
            try
            {
               int banyakdata=0;
               Vector<String> v=new Vector<String>(10);

               AksesDatabase ad=new AksesDatabase();

               String ss="update JadwalKuliah set KodeDosen='"+kodedosen+"',Kapasitas='"+kapasitas+"' where Semester='"+semester+"' and TahunAjaran='"+tahunajaran+"' and JurKode='"+kodejurusan+"' and Ruang='"+ruang+"' and KodeMK='"+kodemk+"' and Hari='"+hari+"' and JamMulai='"+jammulai+"' and JamAkhir='"+jamakhir+"'";
               ad.ubah(ss);

               //---------konversi data
               String range=(String) cbjam.getSelectedItem();
               String temp_kodejam="";

               Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
               String sql="SELECT KodeJam from JamKuliah where Range='"+range+"'";
               ResultSet rs=statement.executeQuery(sql);

               if(rs.next())
               {
                  temp_kodejam=rs.getString(1);
               }

               System.out.print("range "+range);
               System.out.print("temp "+temp_kodejam);

               sql="SELECT Konversi FROM KonversiJam WHERE KodeJam='"+temp_kodejam+"'";
               rs=statement.executeQuery(sql);

               while(rs.next())
               {
                  v.add(rs.getString(1));
               }

               banyakdata=v.size();
               String[] xy=new String[banyakdata];

               for(int i=0; i<banyakdata; i++)
               {
                  xy[i]=v.get(i);
               }

               for(int i=0; i<banyakdata; i++)
               {
                  System.out.println("data"+i+":"+xy[i]);
               }

               //----------- update nilai
               String tanda1="1";

               for(int i=0; i<banyakdata; i++)
               {
                  String jam_kode=xy[i];

                  if(hari.equals("Senin"))
                  {
                     sql="UPDATE data_conv_tes SET Senin='"+kodemk+"' WHERE TahunAjaran='"+tahunajaran+"' and Semester='"+semester+"' and kodejam='"+jam_kode+"' and koderuang='"+ruang+"'";
                     statement.executeUpdate(sql);
                  }
                  else if(hari.equals("Selasa"))
                  {
                     sql="UPDATE data_conv_tes SET Selasa='"+kodemk+"' WHERE TahunAjaran='"+tahunajaran+"' and Semester='"+semester+"' and kodejam='"+jam_kode+"' and koderuang='"+ruang+"'";
                     statement.executeUpdate(sql);
                  }
                  else if(hari.equals("Rabu"))
                  {
                     sql="UPDATE data_conv_tes SET Rabu='"+kodemk+"' WHERE TahunAjaran='"+tahunajaran+"' and Semester='"+semester+"' and kodejam='"+jam_kode+"' and koderuang='"+ruang+"'";
                     statement.executeUpdate(sql);
                  }
                  else if(hari.equals("Kamis"))
                  {
                     sql="UPDATE data_conv_tes SET Kamis='"+kodemk+"' WHERE TahunAjaran='"+tahunajaran+"' and Semester='"+semester+"' and kodejam='"+jam_kode+"' and koderuang='"+ruang+"'";
                     statement.executeUpdate(sql);
                  }
                  else if(hari.equals("Jumat"))
                  {
                     sql="UPDATE data_conv_tes SET Jumat='"+kodemk+"' WHERE TahunAjaran='"+tahunajaran+"' and Semester='"+semester+"' and kodejam='"+jam_kode+"' and koderuang='"+ruang+"'";
                     statement.executeUpdate(sql);
                  }
                  else if(hari.equals("Sabtu"))
                  {
                     sql="UPDATE data_conv_tes SET Sabtu='"+kodemk+"' WHERE TahunAjaran='"+tahunajaran+"' and Semester='"+semester+"' and kodejam='"+jam_kode+"' and koderuang='"+ruang+"'";
                     statement.executeUpdate(sql);
                  }
                  else
                  {
                  }
               }
               statement.close();
            }
            catch(Exception e)
            {
               System.out.println("Error 4:"+e);
            }
         }
      }
   }

   public boolean cek_dosen2tempat()
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

      String tahunajaran=(String)cbthnajaran.getSelectedItem();
      String kodedosen=(String)cbkddosen.getSelectedItem();
      String hari=(String)cbhari.getSelectedItem();

      boolean test=false;
      String h="";
      String jm="";
      String ja="";
      String k="";
      String t="";
      String s="";

      try
      {
         statement = connection.createStatement();
         String sql="select Hari,JamMulai,JamAkhir,KodeDosen,TahunAjaran,Semester from JadwalKuliah";
         ResultSet rs=statement.executeQuery(sql);

         while(rs.next())
         {
            h=rs.getString(1);
            jm=rs.getString(2);
            ja=rs.getString(3);
            k=rs.getString(4);
            t=rs.getString(5);
            s=rs.getString(6);

            ljm=sdf.parse(jm).getTime();
            lja=sdf.parse(ja).getTime();

            if(hari.equals(h) && jammu.getTime()>=ljm && jammu.getTime()<lja && kodedosen.equals(k) && tahunajaran.equals(t) && semester.equals(s))
            {
               test=true;
               break;
            }
            else if(hari.equals(h) && jammu.getTime()<lja && jamak.getTime()>ljm && kodedosen.equals(k) && tahunajaran.equals(t) && semester.equals(s))
            {
               test=true;
               break;
            }
            else if(hari.equals(h) && ljm>=jammu.getTime() && lja<=jamak.getTime() && kodedosen.equals(k) && tahunajaran.equals(t) && semester.equals(s))
            {
               test=true;
               break;
            }
            else
            {
            }
         }
         statement.close();
      }
      catch(Exception e)
      {
         System.out.println("Ada Error Bos:"+e);
      }
      return test;
   }

   public void bersih()
   {
      cbruang.removeAllItems();
      cbkodemk.removeAllItems();
      txtnamamk.setText("");
      cbkelas.setSelectedIndex(0);
      cbkapasitaskelas.setSelectedIndex(0);
      cbkddosen.removeAllItems();
      txtnamadosen.setText("");
      cbhari.setSelectedIndex(0);
      cbjam.setSelectedIndex(0);
      cbjam.removeAllItems();
      cbjurusan.requestFocus();
   }

   public void tampilJadwalPerRuang(String tahunajaran, String semester,String koderuang)
   {
      try
      {
         statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
         String sql="SELECT b.Range, a.Senin, a.Selasa, a.Rabu, a.Kamis, a.Jumat, a.Sabtu FROM data_conv_tes a, JamKuliah b WHERE a.kodejam=b.KodeJam and a.TahunAjaran='"+tahunajaran+"' and a.Semester='"+semester+"' and a.koderuang='"+koderuang+"'";
         ResultSet rs=statement.executeQuery(sql);

         hapusTabel1();

         while (rs.next())
         {
            String zzz=rs.getString(1).substring(0,5);
            Object[] data = {zzz,rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7)};
            tabMode1.addRow(data);

         }
         statement.close();
      }
      catch (Exception DBException)
      {
         System.err.println("Error 3: " + DBException);
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

   class MyHandler implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {

         if (e.getSource() == cbjurusan)
         {
            cbruang.removeAllItems();
            ambilKodeRuang();
            cbkodemk.removeAllItems();
            ambilKodeMK();
         }
         else if(e.getSource() == cbruang)
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

            String tahunajaran=(String)cbthnajaran.getSelectedItem();
            String ruang=(String)cbruang.getSelectedItem();

            tampilJadwalPerRuang(tahunajaran, semester, ruang);
         }
         else if(e.getSource() == cbkodemk)
         {
            ambilNamaMK();
            ambilKodeDosen();
         }
         else if(e.getSource() == cbkddosen)
         {
            ambilNamaDosen();
         }
         else if(e.getSource() == cbhari)
         {
            cbjam.removeAllItems();
            ambilJamKuliah();
         }
         else if(e.getSource() == btnsave)
         {
            simpanData();
         }
         else if(e.getSource() == btnupdate)
         {
            updateData();
         }
         else if(e.getSource() == btnrefresh)
         {
            bersih();
         }
         else if(e.getSource() == btnsearch)
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
            String ruang=(String) cbruang.getSelectedItem();
            String kodemk=(String) cbkodemk.getSelectedItem();
            String hari=(String) cbhari.getSelectedItem();
            String jam=(String) cbjam.getSelectedItem();

            try
            {
              jammulai=jam.substring(0,5);
              jamakhir=jam.substring(8,13);
            }
            catch(Exception ex)
            {
            }

            if(kodemk.equals("") || hari.equals("") || jammulai.equals("00.00") || jamakhir.equals("00.00") || ruang.equals(""))
            {
               JOptionPane.showMessageDialog(null,"Kriteria Pencarian Belum Ditentukan.\n"+"Silahkan Masukkan Kriteria Pencarian Anda.","Konfirmasi",JOptionPane.INFORMATION_MESSAGE);
               bersih();
            }
            else
            {
               try
               {
                  Statement statement = connection.createStatement();
                  String sql="select Kelas,KodeDosen,Kapasitas from JadwalKuliah where Ruang='"+ruang+"' and KodeMK='"+kodemk+"' and Hari='"+hari+"' and JamMulai='"+jammulai+"' and JamAkhir='"+jamakhir+"' and Semester='"+semester+"' and TahunAjaran='"+thn+"'";
                  ResultSet rs=statement.executeQuery(sql);

                  if(rs.next())
                  {
                     cbkelas.setSelectedItem(rs.getString(1));
                     cbkddosen.setSelectedItem(rs.getString(2));
                     cbkapasitaskelas.setSelectedItem(rs.getString(3));
                     ambilNamaDosen();
                  }
                  statement.close();
               }
               catch(Exception ex)
               {
                  System.out.println("Error :"+ex);
               }
            }
         }
         else if(e.getSource() == btndelete)
         {
            try
            {
               int banyakdata=0;
               Vector<String> v=new Vector<String>(10);

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

               String tahunajaran=(String)cbthnajaran.getSelectedItem();
               String ruang=(String) cbruang.getSelectedItem();
               String kodemk=(String) cbkodemk.getSelectedItem();
               String hari=(String) cbhari.getSelectedItem();
               String jam=(String) cbjam.getSelectedItem();

               jammulai=jam.substring(0,5);
               jamakhir=jam.substring(8,13);

               Statement statement = connection.createStatement();
               String sql="DELETE from JadwalKuliah where Ruang='"+ruang+"' and hari='"+hari+"' and JamMulai='"+jammulai+"' and JamAkhir='"+jamakhir+"'";
               statement.executeUpdate(sql);

               //---------bagian ini digunakan untuk update ruang bila terjadi penghapusan sebuah jadwal
               String range=(String) cbjam.getSelectedItem();
               String temp_kodejam="";

               statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
               sql="SELECT KodeJam from JamKuliah where Range='"+range+"'";
               ResultSet rs=statement.executeQuery(sql);
               if(rs.next())
               {
                  temp_kodejam=rs.getString(1);
               }

               sql="SELECT Konversi FROM KonversiJam WHERE KodeJam='"+temp_kodejam+"'";
               rs=statement.executeQuery(sql);

               while(rs.next())
               {
                  v.add(rs.getString(1));
               }

               banyakdata=v.size();
               String[] xy=new String[banyakdata];

               for(int i=0; i<banyakdata; i++)
               {
                  xy[i]=v.get(i);
               }

               for(int i=0; i<banyakdata; i++)
               {
                  System.out.println("data"+i+":"+xy[i]);
               }

               //----------- update nilai
               String tanda1="0";

               for(int i=0; i<banyakdata; i++)
               {
                  String jam_kode=xy[i];

                  if(hari.equals("Senin"))
                  {
                     sql="UPDATE data_conv_tes SET Senin='"+tanda1+"' WHERE TahunAjaran='"+tahunajaran+"' and Semester='"+semester+"' and kodejam='"+jam_kode+"' and koderuang='"+ruang+"'";
                     statement.executeUpdate(sql);
                  }
                  else if(hari.equals("Selasa"))
                  {
                     sql="UPDATE data_conv_tes SET Selasa='"+tanda1+"' WHERE TahunAjaran='"+tahunajaran+"' and Semester='"+semester+"' and kodejam='"+jam_kode+"' and koderuang='"+ruang+"'";
                     statement.executeUpdate(sql);
                  }
                  else if(hari.equals("Rabu"))
                  {
                     sql="UPDATE data_conv_tes SET Rabu='"+tanda1+"' WHERE TahunAjaran='"+tahunajaran+"' and Semester='"+semester+"' and kodejam='"+jam_kode+"' and koderuang='"+ruang+"'";
                     statement.executeUpdate(sql);
                  }
                  else if(hari.equals("Kamis"))
                  {
                     sql="UPDATE data_conv_tes SET Kamis='"+tanda1+"' WHERE TahunAjaran='"+tahunajaran+"' and Semester='"+semester+"' and kodejam='"+jam_kode+"' and koderuang='"+ruang+"'";
                     statement.executeUpdate(sql);
                  }
                  else if(hari.equals("Jumat"))
                  {
                     sql="UPDATE data_conv_tes SET Jumat='"+tanda1+"' WHERE TahunAjaran='"+tahunajaran+"' and Semester='"+semester+"' and kodejam='"+jam_kode+"' and koderuang='"+ruang+"'";
                     statement.executeUpdate(sql);
                  }
                  else if(hari.equals("Sabtu"))
                  {
                     sql="UPDATE data_conv_tes SET Sabtu='"+tanda1+"' WHERE TahunAjaran='"+tahunajaran+"' and Semester='"+semester+"' and kodejam='"+jam_kode+"' and koderuang='"+ruang+"'";
                     statement.executeUpdate(sql);
                  }
                  else
                  {
                  }
               }
               statement.close();
               tampilJadwalPerRuang(tahunajaran, semester, ruang);
            }
            catch(Exception ex)
            {
               System.out.println("Error :"+ex);
            }
         }
         else if(e.getSource() == dataprodi)
         {
            Object[] o={"Kode","Nama Program Studi","Fakultas"};
            FormJurusan id=new FormJurusan(o);

            id.setProperti();
            id.komponenVisual();
            //id.aksiReaksi();
         }
         else if(e.getSource() == infodosen)
         {
            FormDosen fd=new FormDosen();

            fd.komponenVisual();
         }
         else if(e.getSource() == dataruang)
         {
            FormRuang fr=new FormRuang();

            fr.komponenVisual();
            //isiruang.aksiReaksi();
         }
         else if(e.getSource() == infomatakuliah)
         {
            FormMatakuliah fmk=new FormMatakuliah();

            fmk.komponenVisual();
         }
         else if(e.getSource() == pemasaran)
         {
            FormPemasaranMatakuliah fpmk=new FormPemasaranMatakuliah();

            fpmk.komponenVisual();
         }
         else if(e.getSource() == gen_ruangWaktu)
         {
            inisialisasiRuangWaktu();
         }
         else if(e.getSource() == datatahunajaran)
         {
            Object[] ob={"Kode","Tahun Ajaran"};
            FormTahunAjaran fta=new FormTahunAjaran(ob);

            fta.komponenVisual();
            fta.setProperti();

            //mengambil data tahun ajaran dan ditampilkan pada combobox
            String ssql="select tahunAjaran from DataTahunAjaran order by tahunAjaran";
            String[] namathnajaran=re.getArray1Dimensi(connection,statement,ssql);
            cbthnajaran=new JComboBox(namathnajaran);
         }
         else if(e.getSource() == exit)
         {
            System.exit(0);
         }
         else if(e.getSource() == jadwalkuliah)
         {
            ViewJadwal vjm=new ViewJadwal();

            vjm.komponenVisual();
         }
         else if(e.getSource() == mabout)
         {
            FormAbout fa=new FormAbout();

            fa.komponenVisual();
            fa.aksiReaksi();
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
               cbruang.removeAllItems();
               ambilKodeRuang();
               cbkodemk.removeAllItems();
               ambilKodeMK();
               cbruang.requestFocus();
            }
         }
         else if(event.getSource() == cbruang)
         {
            if(event.getKeyCode()==event.VK_ENTER)
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

               String tahunajaran=(String)cbthnajaran.getSelectedItem();
               String ruang=(String)cbruang.getSelectedItem();

               System.out.println(semester);
               System.out.println(tahunajaran);
               System.out.println(ruang);


               tampilJadwalPerRuang(tahunajaran,semester,ruang);
               cbkodemk.requestFocus();
            }
         }
         else if(event.getSource() == cbkodemk)
         {
            if(event.getKeyCode()==event.VK_ENTER)
            {
              ambilNamaMK();
              ambilKodeDosen();
              cbkelas.requestFocus();
            }
         }
         else if(event.getSource() == cbkelas)
         {
            if(event.getKeyCode()==event.VK_ENTER)
            {
               cbkddosen.requestFocus();
            }
         }
         else if(event.getSource() == cbkddosen)
         {
            if(event.getKeyCode()==event.VK_ENTER)
            {
              ambilNamaDosen();
              cbhari.requestFocus();
            }
         }
         else if(event.getSource() == cbhari)
         {
            if(event.getKeyCode()==event.VK_ENTER)
            {
              cbjam.removeAllItems();
              ambilJamKuliah();
              cbjam.requestFocus();
            }
         }
         else if(event.getSource() == cbjam)
         {
            if(event.getKeyCode()==event.VK_ENTER)
            {
              jam=(String) cbjam.getSelectedItem();
              jammulai=jam.substring(0,5);
              jamakhir=jam.substring(8,13);

              try
              {
                 jammu=sdf.parse(jammulai);
                 jamak=sdf.parse(jamakhir);
              }
              catch(Exception e)
              {
              }

              btnsave.requestFocus();

              System.out.println(jammu.getTime());
              System.out.println(jamak.getTime());
            }
         }
         else if(event.getSource() == btnsave)
         {
            if(event.getKeyCode()==event.VK_ENTER)
            {
               simpanData();
               cbruang.requestFocus();
            }
         }
         else if(event.getSource() == btnupdate)
         {
            if(event.getKeyCode()==event.VK_ENTER)
            {
               updateData();
            }
         }
         else if(event.getSource() == btnrefresh)
         {
            if(event.getKeyCode()==event.VK_ENTER)
            {
               bersih();
            }
         }
         else if(event.getSource() == btnsearch)
         {
            if(event.getKeyCode()==event.VK_ENTER)
            {
               String ruang=(String) cbruang.getSelectedItem();
               String kodemk=(String) cbkodemk.getSelectedItem();
               String hari=(String) cbhari.getSelectedItem();
               String jam=(String) cbjam.getSelectedItem();

               try
               {
                  jammulai=jam.substring(0,5);
                  jamakhir=jam.substring(8,13);
               }
               catch(Exception e)
               {
               }

               if(kodemk.equals("") || hari.equals("") || jammulai.equals("00.00") || jamakhir.equals("00.00") || ruang.equals(""))
               {
                  JOptionPane.showMessageDialog(null,"Kriteria Pencarian Belum Ditentukan.\n"+"Silahkan Masukkan Kriteria Pencarian Anda.","Konfirmasi",JOptionPane.INFORMATION_MESSAGE);
                  bersih();
               }
               else
               {
                  try
                  {
                     Statement statement = connection.createStatement();
                     String sql="select Kelas,KodeDosen from JadwalKuliah where Ruang='"+ruang+"' and KodeMK='"+kodemk+"' and Hari='"+hari+"' and JamMulai='"+jammulai+"' and JamAkhir='"+jamakhir+"'";
                     ResultSet rs=statement.executeQuery(sql);

                     if(rs.next())
                     {
                        cbkelas.setSelectedItem(rs.getString(1));
                        cbkddosen.setSelectedItem(rs.getString(2));
                        ambilNamaDosen();
                     }
                     statement.close();
                  }
                  catch(Exception ex)
                  {
                     System.out.println("Error :"+ex);
                  }
               }
            }
         }
         else if(event.getSource() == btndelete)
         {
            if(event.getKeyCode()==event.VK_ENTER)
            {
               try
               {
                  int banyakdata=0;
                  Vector<String> v=new Vector<String>(10);

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

                  String tahunajaran=(String)cbthnajaran.getSelectedItem();

                  String ruang=(String) cbruang.getSelectedItem();
                  String kodemk=(String) cbkodemk.getSelectedItem();
                  String hari=(String) cbhari.getSelectedItem();
                  String jam=(String) cbjam.getSelectedItem();

                  jammulai=jam.substring(0,5);
                  jamakhir=jam.substring(8,13);

                  Statement statement = connection.createStatement();
                  String sql="DELETE from JadwalKuliah where Ruang='"+ruang+"' and hari='"+hari+"' and JamMulai='"+jammulai+"' and JamAkhir='"+jamakhir+"'";
                  statement.executeUpdate(sql);

                  //---------bagian ini digunakan untuk update ruang bila terjadi penghapusan sebuah jadwal
                  String range=(String) cbjam.getSelectedItem();
                  String temp_kodejam="";

                  statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
                  sql="SELECT KodeJam from JamKuliah where Range='"+range+"'";
                  ResultSet rs=statement.executeQuery(sql);
                  if(rs.next())
                  {
                     temp_kodejam=rs.getString(1);
                  }

                  sql="SELECT Konversi FROM KonversiJam WHERE KodeJam='"+temp_kodejam+"'";
                  rs=statement.executeQuery(sql);

                  while(rs.next())
                  {
                     v.add(rs.getString(1));
                  }

                  banyakdata=v.size();
                  String[] xy=new String[banyakdata];

                  for(int i=0; i<banyakdata; i++)
                  {
                     xy[i]=v.get(i);
                  }

                  for(int i=0; i<banyakdata; i++)
                  {
                     System.out.println("data"+i+":"+xy[i]);
                  }

                  //----------- update nilai
                  String tanda1="0";

                  for(int i=0; i<banyakdata; i++)
                  {
                     String jam_kode=xy[i];

                     if(hari.equals("Senin"))
                     {
                        sql="UPDATE data_conv_tes SET Senin='"+tanda1+"' WHERE TahunAjaran='"+tahunajaran+"' and Semester='"+semester+"' and kodejam='"+jam_kode+"' and koderuang='"+ruang+"'";
                        statement.executeUpdate(sql);
                     }
                     else if(hari.equals("Selasa"))
                     {
                        sql="UPDATE data_conv_tes SET Selasa='"+tanda1+"' WHERE TahunAjaran='"+tahunajaran+"' and Semester='"+semester+"' and kodejam='"+jam_kode+"' and koderuang='"+ruang+"'";
                        statement.executeUpdate(sql);
                     }
                     else if(hari.equals("Rabu"))
                     {
                        sql="UPDATE data_conv_tes SET Rabu='"+tanda1+"' WHERE TahunAjaran='"+tahunajaran+"' and Semester='"+semester+"' and kodejam='"+jam_kode+"' and koderuang='"+ruang+"'";
                        statement.executeUpdate(sql);
                     }
                     else if(hari.equals("Kamis"))
                     {
                        sql="UPDATE data_conv_tes SET Kamis='"+tanda1+"' WHERE TahunAjaran='"+tahunajaran+"' and Semester='"+semester+"' and kodejam='"+jam_kode+"' and koderuang='"+ruang+"'";
                        statement.executeUpdate(sql);
                     }
                     else if(hari.equals("Jumat"))
                     {
                        sql="UPDATE data_conv_tes SET Jumat='"+tanda1+"' WHERE TahunAjaran='"+tahunajaran+"' and Semester='"+semester+"' and kodejam='"+jam_kode+"' and koderuang='"+ruang+"'";
                        statement.executeUpdate(sql);
                     }
                     else if(hari.equals("Sabtu"))
                     {
                        sql="UPDATE data_conv_tes SET Sabtu='"+tanda1+"' WHERE TahunAjaran='"+tahunajaran+"' and Semester='"+semester+"' and kodejam='"+jam_kode+"' and koderuang='"+ruang+"'";
                        statement.executeUpdate(sql);
                     }
                     else
                     {
                     }
                  }
                  statement.close();
                  tampilJadwalPerRuang(tahunajaran, semester, ruang);
               }
               catch(Exception ex)
               {
                  System.out.println("Error :"+ex);
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