package services;

import models.Publication;
import utils.MyDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PublicationService implements Crud<Publication> {
    Connection conn;

    public PublicationService() {
        this.conn = MyDB.getInstance().getConn();
    }


    @Override
    public void create(Publication obj) throws Exception {
        String sql = "INSERT INTO publication (contenu, type_pub, utilisateurid, description, title) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, obj.getContenu());
        pstmt.setString(2, obj.getType_pub());
        pstmt.setInt(3, obj.getUserId());
        pstmt.setString(4, obj.getDescription());
        pstmt.setString(5, obj.getTitle());
        pstmt.executeUpdate();
        pstmt.close();

    }

    @Override
    public void update(Publication obj) throws Exception {
        String sql = "UPDATE publication SET contenu = ?, type_pub = ?, utilisateurid = ?, description = ?, title = ? WHERE id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, obj.getContenu());
        pstmt.setString(2, obj.getType_pub());
        pstmt.setInt(3, obj.getUserId());
        pstmt.setString(4, obj.getDescription());
        pstmt.setString(5, obj.getTitle());
        pstmt.setInt(6, obj.getId());
        pstmt.executeUpdate();

    }


    @Override
    public void delete(Publication obj) throws Exception {
        String sql = "DELETE FROM publication WHERE id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, obj.getId()); // Extracting the ID from the object
        pstmt.executeUpdate();
    }

    @Override
    public List<Publication> getAll() throws Exception {
        String sql = "select * from publication";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        List<Publication> publications = new ArrayList<>();
        while (rs.next()) {
            Publication publication = new Publication();
            publication.setId(rs.getInt("id"));
            publication.setUserId(rs.getInt("utilisateurid"));
            publication.setContenu(rs.getString("contenu"));
            publication.setDescription(rs.getString("description"));
            publication.setTitle(rs.getString("title"));
            publication.setType_pub(rs.getString("type_pub"));
            publication.setDate_pub(rs.getString("date"));
            publications.add(publication);
        }
        return publications;
    }
}
