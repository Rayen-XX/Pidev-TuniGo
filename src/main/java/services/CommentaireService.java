package services;

import models.Commentaire;
import models.Publication;
import utils.MyDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CommentaireService implements Crud<Commentaire> {
    Connection conn;

    public CommentaireService() {
        this.conn = MyDB.getInstance().getConn();
    }


    @Override
    public void create(Commentaire obj) throws Exception {
        String sql = "INSERT INTO commentaire (utilisateurid,publicationid,contenu) VALUES (?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, obj.getUserId());
        ps.setInt(2, obj.getPublicationId());
        ps.setString(3, obj.getContenu());
        ps.executeUpdate();
        ps.close();
    }

    @Override
    public void update(Commentaire obj) throws Exception {
        String sql = "UPDATE commentaire SET contenu=? , publicationid = ? , utilisateurid = ?  WHERE id=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, obj.getContenu());
        ps.setInt(2, obj.getPublicationId());
        ps.setInt(3, obj.getUserId());
        ps.setInt(4, obj.getId());
        ps.executeUpdate();
        ps.close();
    }

    @Override
    public void delete(Commentaire obj) throws Exception {
        String sql = "DELETE FROM commentaire WHERE id=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, obj.getId());
        ps.executeUpdate();
        ps.close();
    }

    @Override
    public List<Commentaire> getAll() throws Exception {
        String sql = "select * from commentaire";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        List<Commentaire> commentaires = new ArrayList<>();
        while (rs.next()) {
            Commentaire commentaire = new Commentaire();
            commentaire.setId(rs.getInt("id"));
            commentaire.setUserId(rs.getInt("utilisateurid"));
            commentaire.setContenu(rs.getString("contenu"));
            commentaire.setPublicationId(rs.getInt("publicationid"));
            commentaire.setDate(rs.getString("date"));
            commentaires.add(commentaire);
        }
        return commentaires;
    }
}
