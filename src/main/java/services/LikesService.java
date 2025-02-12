package services;

import models.Likes;
import models.Publication;
import utils.MyDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LikesService implements Crud<Likes> {
    Connection conn;

    public LikesService() {
        this.conn = MyDB.getInstance().getConn();
    }


    @Override
    public void create(Likes obj) throws Exception {
        String sql = "INSERT INTO likes(publicationid,utilisateurid) VALUES (?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1,obj.getPublicationId());
        ps.setInt(2,obj.getUserId());
        ps.executeUpdate();
        ps.close();
    }

    @Override
    public void update(Likes obj) throws Exception {
        String sql = "UPDATE likes SET publicationid=?,utilisateurid=? WHERE id=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1,obj.getPublicationId());
        ps.setInt(2,obj.getUserId());
        ps.setInt(3,obj.getId());
        ps.executeUpdate();
        ps.close();
    }

    @Override
    public void delete(Likes obj) throws Exception {
        String sql = "DELETE FROM likes WHERE id=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1,obj.getId());
        ps.executeUpdate();
        ps.close();
    }

    @Override
    public List<Likes> getAll() throws Exception {
        String sql = "SELECT * FROM likes";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        List<Likes> ListLikes = new ArrayList<>();
        while (rs.next()) {
            Likes Like = new Likes();
            Like.setId(rs.getInt("id"));
            Like.setUserId(rs.getInt("utilisateurid"));
            Like.setPublicationId(rs.getInt("publicationid"));
            Like.setDate(rs.getString("date"));
            ListLikes.add(Like);
        }
        return ListLikes;
    }
}
