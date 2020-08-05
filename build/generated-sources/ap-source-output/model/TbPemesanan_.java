package model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.TbKonfirmasi;
import model.TbLapangan;
import model.TbMatchteam;
import model.TbPemain;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-23T15:50:57")
@StaticMetamodel(TbPemesanan.class)
public class TbPemesanan_ { 

    public static volatile CollectionAttribute<TbPemesanan, TbMatchteam> tbMatchteamCollection;
    public static volatile CollectionAttribute<TbPemesanan, TbKonfirmasi> tbKonfirmasiCollection;
    public static volatile SingularAttribute<TbPemesanan, Date> jamMainMulai;
    public static volatile SingularAttribute<TbPemesanan, TbPemain> idPemain;
    public static volatile SingularAttribute<TbPemesanan, String> idPemesanan;
    public static volatile SingularAttribute<TbPemesanan, Integer> durasi;
    public static volatile SingularAttribute<TbPemesanan, Date> tglPemesanan;
    public static volatile SingularAttribute<TbPemesanan, Date> tglMain;
    public static volatile SingularAttribute<TbPemesanan, Date> jamMainSelesai;
    public static volatile SingularAttribute<TbPemesanan, TbLapangan> idLapangan;
    public static volatile SingularAttribute<TbPemesanan, Integer> status;

}