package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.TbMatchteam;
import model.TbPemain;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-23T15:50:57")
@StaticMetamodel(TbTeam.class)
public class TbTeam_ { 

    public static volatile CollectionAttribute<TbTeam, TbMatchteam> tbMatchteamCollection1;
    public static volatile CollectionAttribute<TbTeam, TbMatchteam> tbMatchteamCollection;
    public static volatile SingularAttribute<TbTeam, Double> rate;
    public static volatile SingularAttribute<TbTeam, Integer> lose;
    public static volatile SingularAttribute<TbTeam, String> namaTeam;
    public static volatile SingularAttribute<TbTeam, String> logo;
    public static volatile SingularAttribute<TbTeam, String> deskripsi;
    public static volatile SingularAttribute<TbTeam, TbPemain> captain;
    public static volatile SingularAttribute<TbTeam, Integer> win;
    public static volatile SingularAttribute<TbTeam, Integer> idTeam;
    public static volatile CollectionAttribute<TbTeam, TbPemain> tbPemainCollection;

}