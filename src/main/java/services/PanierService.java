package services;
import models.Panier;
import utils.MyDB;
import models.Equipement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PanierService implements Crud<Panier> {

    Connection conn;

    public PanierService() {
        this.conn = MyDB.getInstance().getConn();
    }

    public void create(Panier obj) throws Exception {
        String sql = "INSERT INTO panier (id_Equip,qte_com,id_utilisateur,prix_total) VALUES (?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, obj.getId_equip());
        pstmt.setInt(2, obj.getQte_com());
        pstmt.setInt(3, obj.getId_utilisateur());
        pstmt.setDouble(4, obj.getPrix_total());

        pstmt.executeUpdate();
        pstmt.close();
    }

    @Override
    public void update(Panier obj) throws Exception {
        String sql = "UPDATE panier SET id_Equip = ?, qte_com= ?, id_utilisateur = ?, prix_total = ?  WHERE id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, obj.getId_equip());
        pstmt.setInt(2, obj.getQte_com());
        pstmt.setInt(3, obj.getId_utilisateur());
        pstmt.setDouble(4, obj.getPrix_total());
        pstmt.setInt(5, obj.getId());
        pstmt.executeUpdate();

    }

    @Override
    public void delete(Panier obj) throws Exception {
        String sql = "DELETE FROM panier WHERE id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, obj.getId());
        pstmt.executeUpdate();

    }


    @Override
    public List<Panier> getAll() throws Exception {
        String sql = "select * from panier";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        List<Panier> paniers = new ArrayList<>();
        while (rs.next()) {
            Panier panier = new Panier();
            panier.setId(rs.getInt("id"));
            panier.setId_equip(rs.getInt("id_equip"));
            panier.setQte_com(rs.getInt("qte_com"));
            panier.setPrix_total(rs.getDouble("prix_total"));
            panier.setId_utilisateur(rs.getInt("id_utilisateur"));

            paniers.add(panier);

        }
        return paniers;
    }
}




