import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class Baglanti {
    private String kullanici_adi = "root";
    private String parola = "";
    
    private String db_ismi = "demo";
    
    private String host = "localhost";
    
    private int port = 3306;
    
    private Connection con = null;
    
    private Statement statement = null;
    
    private PreparedStatement preparedStatement = null;

    public void calisanlariGetir()
    {
        String sorgu = "Select * From calisanlar";
        
        try {
            statement = (Statement) con.createStatement();
            
            ResultSet rs = statement.executeQuery(sorgu);
            
            int id;
            String ad, soyad, mail;
            String mesaj;
            
            while(rs.next())
            {
                id = rs.getInt("id");
                ad = rs.getString("ad");
                soyad = rs.getString("soyad");
                mail = rs.getString("email");
            
                mesaj = id + " " + ad + " " + soyad + " " + mail + "\n";
                System.out.println(mesaj);
            }
            
        } catch (SQLException ex) {
            System.getLogger(Baglanti.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
           
    }
    
    public void calisanSil()
    {
        try {
            statement = con.createStatement();
            
            String sorgu = "Delete from calisanlar where id > 3";
            
            int deger = statement.executeUpdate(sorgu);
            System.out.println(deger + " kadar veri etkilendi.");
            
        } catch (SQLException ex) {
            System.getLogger(Baglanti.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
    
    
    public void calisanGuncelle()
    {
        try {
            statement = con.createStatement();
            
            String sorgu = "Update calisanlar Set email = 'hba.com' where id = 1";
            
            statement.executeUpdate(sorgu);
            
        } catch (SQLException ex) {
            System.getLogger(Baglanti.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
    
    public void preparedCalisanlariGetir(int id)
    {
        String sorgu = "Select * From calisanlar where id = ?";
        
        try {
            preparedStatement = con.prepareStatement(sorgu);
            preparedStatement.setInt(1, id);
            
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next())
            {
                String ad = rs.getString("ad");
                String soyad = rs.getString("soyad");
                String emil = rs.getString("email");
            }
            
            
        } catch (SQLException ex) {
            System.getLogger(Baglanti.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }

        




            /*try {
            statement = con.createStatement();
        
            String sorgu = "Select * From calisanlar where ad like 'h%'";
            
            ResultSet rs = statement.executeQuery(sorgu);
            
            while(rs.next())
            {
            System.out.println("Ad: " + rs.getString("ad"));
            }
        
            } catch (SQLException ex) {
            System.getLogger(Baglanti.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }*/
    }
    
    public void calisanEkle()
    {
        try
        {
            statement = con.createStatement();
            String ad = "hb";
            String soyad = "a";
            String mail = "hba";
            
            String sorgu = "Insert Into calisanlar (ad, soyad, email) Values(" + "'" + ad + "'," + "'" + soyad + "'," + "'" + mail + "')";   
        
            statement.executeUpdate(sorgu);
            
        }catch (SQLException ex) {
            System.out.println("Bağlantı başarısız");
        }
    }
    
    
    public Baglanti()
    {
        // jdbc üzerinden mysql veritabanına bağlanacağız
        // jdbc:mysql://localhost:3306/demo
        String url = "jdbc:mysql://" + host + ":" + port + "/" + db_ismi + "?useUnicode=true&charaterEncoding=utf8";
    
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver bulunamadı");
        }
        
        try {
            con = DriverManager.getConnection(url, kullanici_adi, parola);
            System.out.println("Bağlantı başarılı");
        } catch (SQLException ex) {
            System.out.println("Bağlantı başarısız");
        }
        
    }
    
    public void commitVeRollback()
    {
        Scanner scanner = new Scanner(System.in);
        try {
            con.setAutoCommit(false);
            
            String sorgu = "Delete from calisanlar where id = 3";
            String sorgu2 = "Update calisanlar set email = 'deneme' where id = 10";
            
            System.out.println("güncellemeden önce");
            calisanlariGetir();
            
            Statement statement = con.createStatement();
            statement.execute(sorgu);
            statement.execute(sorgu2);
            
            System.out.println("İşlemelr kaydedilsin mi ");
            String cevap = scanner.nextLine();
            
            if(cevap.equals("y"))
            {
                con.commit();
                calisanlariGetir();
                System.out.println("Veritabanı güncellendi");
            }
            else
            {
                con.rollback();
                System.out.println("güncelleme yapılmadı");
                calisanlariGetir();
            }
            
        } catch (SQLException ex) {
            System.getLogger(Baglanti.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
    
    public static void main(String[] args)
    {
        Baglanti baglanti = new Baglanti();
        
        baglanti.commitVeRollback();
        baglanti.preparedCalisanlariGetir(1);
        
        //baglanti.calisanlariGetir();
        
        //baglanti.calisanEkle();
        
        //baglanti.calisanlariGetir();
        
        //baglanti.calisanGuncelle();
        
        //baglanti.calisanlariGetir();
        
        //baglanti.calisanSil();
        
        //baglanti.calisanlariGetir();
    }
    
}
