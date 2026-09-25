import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;


public class Baglanti {
    private String kullanici_adi = "root";
    private String parola = "";
    
    private String db_ismi = "demo";
    
    private String host = "localhost";
    
    private int port = 3306;
    
    private Connection con = null;
    
    private Statement statement = null;

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
    
    public static void main(String[] args)
    {
        Baglanti baglanti = new Baglanti();
        
        baglanti.calisanlariGetir();
        
        baglanti.calisanEkle();
        
        baglanti.calisanlariGetir();
    }
    
}
