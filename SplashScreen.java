import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JWindow;
import javax.swing.JLabel;
import javax.swing.ImageIcon;


public class SplashScreen extends JWindow 
{
  public SplashScreen(int timeOut)
  {
      // Menambahkan label gambar
      add(new JLabel(new ImageIcon("splash125.jpg")));
      // Asumsi: ukuran gambar sudah disesuaikan untuk splash
      // sehingga window tinggal di-pack
      pack();

      // Mendapatkan ukuran layar
      Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
      // Mendapatkan posisi tengah
      int x = (dim.width - getSize().width)/2;
      int y = (dim.height - getSize().height)/2;
      // Menetapkan lokasi window
      setLocation(x, y);

      // Tampilkan window
      setVisible(true);

      // Menunggu hingga waktu yang telah ditetapkan
      try 
      {
          Thread.sleep(timeOut);
      } 
      catch (InterruptedException ex) 
      {
          System.err.println(ex.getMessage());
      }

      // Tutup dan bersihkan resource window
      dispose();
  }

}