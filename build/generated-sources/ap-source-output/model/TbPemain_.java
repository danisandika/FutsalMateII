package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.TbMatchteam;
import model.TbPemesanan;
import model.TbRating;
import model.TbTeam;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-23T15:50:57")
@StaticMetamodel(TbPemain.class)
public class TbPemain_ { 

    public static volatile SingularAttribute<TbPemain, String> notelp;
    public static volatile SingularAttribute<TbPemain, String> password;
    public static volatile CollectionAttribute<TbPemain, TbRating> tbRatingCollection;
    public static volatile CollectionAttribute<TbPemain, TbPemesanan> tbPemesananCollection;
    public static volatile SingularAttribute<TbPemain, Integer> gender;
    public static volatile CollectionAttribute<TbPemain, TbMatchteam> tbMatchteamCollection;
    public static volatile SingularAttribute<TbPemain, String> nama;
    public static volatile SingularAttribute<TbPemain, Integer> idPemain;
    public static volatile SingularAttribute<TbPemain, TbTeam> idTeam;
    public static volatile SingularAttribute<TbPemain, String> email;
    public static volatile SingularAttribute<TbPemain, String> alamat;
    public static volatile CollectionAttribute<TbPemain, TbTeam> tbTeamCollection;

}