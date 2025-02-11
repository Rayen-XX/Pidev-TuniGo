package services;
import utils.MyDB;
import models.Equipement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EquipementService implements Crud<Equipement> {

    Connection conn;

    public EquipementService() {
        this.conn=MyDB.getInstance().getConn();
    }

    @Override
    public void create(Equipement obj) throws Exception {
        String sql = "INSERT INTO equipement (qte_dispo, nomEquip, prix,image) VALUES (?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, obj.getQteDispo());
        pstmt.setString(2, obj.getNomEquip());
        pstmt.setDouble(3, obj.getPrix());
        pstmt.setString(4, obj.getImage());
        pstmt.executeUpdate();
        pstmt.close();

    }

    @Override
    public void update(Equipement obj) throws Exception {
        String sql = "UPDATE equipement SET qte_dispo = ?, nomEquip= ?, prix = ?, image = ?  WHERE id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, obj.getQteDispo());
        pstmt.setString(2, obj.getNomEquip());
        pstmt.setDouble(3, obj.getPrix());
        pstmt.setString(4, obj.getImage());
        pstmt.setInt(5, obj.getIdEquipement());

        pstmt.executeUpdate();


    }

    @Override
    public void delete(Equipement obj) throws Exception {
        String sql = "DELETE FROM equipement WHERE id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, obj.getIdEquipement());
        int rowsDeleted = pstmt.executeUpdate();
        /*if (rowsDeleted > 0) {
            System.out.println("L'équipement avec ID " + obj.getIdEquipement() + " a été supprimé.");
        } else {
            System.out.println("Aucun équipement trouvé avec cet ID.");
        }*/

    }

    @Override
    public List<Equipement> getAll() throws Exception {
        String sql = "select * from equipement";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        List<Equipement> equipements = new ArrayList<>();
        while (rs.next()) {
            Equipement equipement = new Equipement();
            equipement.setIdEquipement(rs.getInt("id"));
            equipement.setQteDispo(rs.getInt("qte_dispo"));
            equipement.setNomEquip(rs.getString("nomEquip"));
            equipement.setPrix(rs.getDouble("prix"));
            equipement.setImage(rs.getString("image"));
            equipements.add(equipement);
        }
        return equipements;
    }
}
