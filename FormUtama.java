import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class FormUtama extends JFrame
{
   public FormUtama()
   {
      System.out.println("Sistem Penjadwalan Perkuliahan");
      System.out.println("Versi 1.2.5");
      System.out.println("Tim Secangkir Kopi Pahit (SKP)");
      System.out.println("2008 - 2020");

      new SplashScreen(5000);
      FormPenjadwalan fp=new FormPenjadwalan();
      fp.komponenVisual();
      
   }

   public static void main(String[] args)
   {
      //new SplashScreen(5000);

      SwingUtilities.invokeLater(new Runnable()
      {
         public void run()
         {
            new FormUtama();
         }
      });
   }
}