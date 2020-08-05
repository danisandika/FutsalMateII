package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.TbPemain;
import model.TbPemesanan;
import model.TbTeam;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-23T15:50:57")
@StaticMetamodel(TbMatchteam.class)
public class TbMatchteam_ { 

    public static volatile SingularAttribute<TbMatchteam, String> idMatchteam;
    public static volatile SingularAttribute<TbMatchteam, Integer> homeScore;
    public static volatile SingularAttribute<TbMatchteam, Integer> awayScore;
    public static volatile SingularAttribute<TbMatchteam, TbTeam> idAwayTeam;
    public static volatile SingularAttribute<TbMatchteam, String> deskripsi;
    public static volatile SingularAttribute<TbMatchteam, TbPemesanan> idPemesanan;
    public static volatile SingularAttribute<TbMatchteam, TbTeam> idHomeTeam;
    public static volatile CollectionAttribute<TbMatchteam, TbPemain> tbPemainCollection;

}