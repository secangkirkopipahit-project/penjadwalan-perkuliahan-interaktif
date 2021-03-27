import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FormAbout extends JFrame
{
   JLabel latar=new JLabel(new ImageIcon("about124.jpg"));
   JLabel lbljudul=new JLabel("Aplikasi Penjadwalan Perkuliahan Versi 1.2.5");
   JLabel lblperusahaan=new JLabel("Tim Secangkir Kopi Pahit (SKP) 2020");
   JLabel lblkontak=new JLabel("fatchur70@gmail.com");
   JLabel lblweb=new JLabel("www.secangkirkopipahit.com");
   JPanel panel=new JPanel();

   //JButton btnclose=new JButton("Close");

   int lebar;
   int tinggi;

   public FormAbout()
   {
      Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
      setTitle("About");
      setSize(425,330);
      lebar = (screen.width - getSize().width) / 2;
      tinggi = (screen.height - getSize().height) / 2;
      setLocation(lebar, tinggi);
      setResizable(false);
      setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      
      komponenVisual();
   }

   public void komponenVisual()
   {
      getContentPane().setLayout(null);

      getContentPane().add(lbljudul);
      lbljudul.setBounds(25,234,400,12);
      lbljudul.setFont(new Font("Courier New",Font.PLAIN,12));
      lbljudul.setForeground(Color.lightGray);

      //getContentPane().add(lblperusahaan);
      //lblperusahaan.setBounds(25,234,400,12);
      //lblperusahaan.setFont(new Font("Courier New",Font.PLAIN,12));
      //lblperusahaan.setForeground(Color.lightGray);

      getContentPane().add(lblkontak);
      lblkontak.setBounds(25,248,400,12);
      lblkontak.setFont(new Font("Courier New",Font.PLAIN,12));
      lblkontak.setForeground(Color.lightGray);

      getContentPane().add(lblweb);
      lblweb.setBounds(25,260,400,12);
      lblweb.setFont(new Font("Courier New",Font.PLAIN,12));
      lblweb.setForeground(Color.lightGray);

      getContentPane().add(latar);
      latar.setBounds(-20,-30,500,400);
      //latar.setBorder(BorderFactory.createEtchedBorder(0));

      setVisible(true);
   }

   public void aksiReaksi()
   {
      /*
      btnclose.addKeyListener(new KeyAdapter()
      {
         public void keyPressed(KeyEvent event)
         {
            if(event.getKeyCode()==event.VK_ENTER)
            {
               dispose();
            }
         }
      });

      btnclose.addActionListener(new ActionListener()
      {
         public void actionPerformed(ActionEvent event)
         {
            dispose();
         }
      });
      */
   }

   public static void main(String args[])
   {
      FormAbout fa=new FormAbout();

      fa.komponenVisual();
   }
}