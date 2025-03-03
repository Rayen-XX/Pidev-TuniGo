package com.esprit.gu.service;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.esprit.gu.entity.Reclamation;
import com.esprit.gu.util.DBUtil;

public class ServiceReclamation {
    private Connection cnx = DBUtil.getConnection();

    public ServiceReclamation() throws Exception {
    }

    public void ajouter(Reclamation reclamation) {
        String req = "INSERT INTO `reclamation`(`nom_utilisateur`, `prenom_utilisateur`, `typeReclamation`, `descriptionReclamation`, `statutReclamation`, `dateReclamation`) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement pstm = this.cnx.prepareStatement(req);

            try {
                pstm.setString(1, reclamation.getNom_utilisateur());
                pstm.setString(2, reclamation.getPrenom_utilisateur());
                pstm.setString(3, reclamation.getTypeReclamation());
                pstm.setString(4, reclamation.getDescriptionReclamation());
                pstm.setString(5, reclamation.getStatutReclamation());
                pstm.setDate(6, new Date(reclamation.getDateReclamation().getTime()));
                pstm.executeUpdate();
            } catch (Throwable var7) {
                if (pstm != null) {
                    try {
                        pstm.close();
                    } catch (Throwable var6) {
                        var7.addSuppressed(var6);
                    }
                }

                throw var7;
            }

            if (pstm != null) {
                pstm.close();
            }
        } catch (SQLException var8) {
            SQLException ex = var8;
            System.out.println("Erreur lors de l'ajout de la réclamation : " + ex.getMessage());
        }

    }

    public void modifier(Reclamation reclamation) {
        String req = "UPDATE `reclamation` SET `nom_utilisateur` = ?, `prenom_utilisateur` = ?, `typeReclamation` = ?, `descriptionReclamation` = ?, `statutReclamation` = ?, `dateReclamation` = ? WHERE `idReclamation` = ?";

        try {
            PreparedStatement pstm = this.cnx.prepareStatement(req);

            try {
                pstm.setString(1, reclamation.getNom_utilisateur());
                pstm.setString(2, reclamation.getPrenom_utilisateur());
                pstm.setString(3, reclamation.getTypeReclamation());
                pstm.setString(4, reclamation.getDescriptionReclamation());
                pstm.setString(5, reclamation.getStatutReclamation());
                pstm.setDate(6, new Date(reclamation.getDateReclamation().getTime()));
                pstm.setInt(7, reclamation.getIdReclamation());
                pstm.executeUpdate();
            } catch (Throwable var7) {
                if (pstm != null) {
                    try {
                        pstm.close();
                    } catch (Throwable var6) {
                        var7.addSuppressed(var6);
                    }
                }

                throw var7;
            }

            if (pstm != null) {
                pstm.close();
            }
        } catch (SQLException var8) {
            SQLException ex = var8;
            System.out.println("Erreur lors de la modification de la réclamation : " + ex.getMessage());
        }

    }

    public void supprimer(int id) {
        String req = "DELETE FROM `reclamation` WHERE `idReclamation` = ?";

        try {
            PreparedStatement pstm = this.cnx.prepareStatement(req);

            try {
                pstm.setInt(1, id);
                pstm.executeUpdate();
            } catch (Throwable var7) {
                if (pstm != null) {
                    try {
                        pstm.close();
                    } catch (Throwable var6) {
                        var7.addSuppressed(var6);
                    }
                }

                throw var7;
            }

            if (pstm != null) {
                pstm.close();
            }
        } catch (SQLException var8) {
            SQLException ex = var8;
            System.out.println("Erreur lors de la suppression de la réclamation : " + ex.getMessage());
        }

    }

    public Reclamation getOne(int id) {
        Reclamation reclamation = null;
        String req = "SELECT * FROM `reclamation` WHERE `idReclamation` = ?";

        try {
            PreparedStatement pstm = this.cnx.prepareStatement(req);

            try {
                pstm.setInt(1, id);
                ResultSet rs = pstm.executeQuery();
                if (rs.next()) {
                    reclamation = new Reclamation();
                    reclamation.setIdReclamation(rs.getInt("idReclamation"));
                    reclamation.setNom_utilisateur(rs.getString("nom_utilisateur"));
                    reclamation.setPrenom_utilisateur(rs.getString("prenom_utilisateur"));
                    reclamation.setTypeReclamation(rs.getString("typeReclamation"));
                    reclamation.setDescriptionReclamation(rs.getString("descriptionReclamation"));
                    reclamation.setStatutReclamation(rs.getString("statutReclamation"));
                    reclamation.setDateReclamation(rs.getDate("dateReclamation"));
                }
            } catch (Throwable var8) {
                if (pstm != null) {
                    try {
                        pstm.close();
                    } catch (Throwable var7) {
                        var8.addSuppressed(var7);
                    }
                }

                throw var8;
            }

            if (pstm != null) {
                pstm.close();
            }
        } catch (SQLException var9) {
            SQLException ex = var9;
            System.out.println("Erreur lors de la récupération de la réclamation : " + ex.getMessage());
        }

        return reclamation;
    }

    public List<Reclamation> getAll() {
        List<Reclamation> reclamations = new ArrayList();
        String req = "SELECT * FROM `reclamation`";

        try {
            Statement stm = this.cnx.createStatement();

            try {
                ResultSet rs = stm.executeQuery(req);

                try {
                    while(rs.next()) {
                        Reclamation reclamation = new Reclamation();
                        reclamation.setIdReclamation(rs.getInt("idReclamation"));
                        reclamation.setNom_utilisateur(rs.getString("nom_utilisateur"));
                        reclamation.setPrenom_utilisateur(rs.getString("prenom_utilisateur"));
                        reclamation.setTypeReclamation(rs.getString("typeReclamation"));
                        reclamation.setDescriptionReclamation(rs.getString("descriptionReclamation"));
                        reclamation.setStatutReclamation(rs.getString("statutReclamation"));
                        reclamation.setDateReclamation(rs.getDate("dateReclamation"));
                        reclamations.add(reclamation);
                    }
                } catch (Throwable var9) {
                    if (rs != null) {
                        try {
                            rs.close();
                        } catch (Throwable var8) {
                            var9.addSuppressed(var8);
                        }
                    }

                    throw var9;
                }

                if (rs != null) {
                    rs.close();
                }
            } catch (Throwable var10) {
                if (stm != null) {
                    try {
                        stm.close();
                    } catch (Throwable var7) {
                        var10.addSuppressed(var7);
                    }
                }

                throw var10;
            }

            if (stm != null) {
                stm.close();
            }
        } catch (SQLException var11) {
            SQLException ex = var11;
            System.out.println("Erreur lors de la récupération des réclamations : " + ex.getMessage());
        }

        return reclamations;
    }
}
