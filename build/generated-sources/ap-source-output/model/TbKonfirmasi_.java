package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.TbPemesanan;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-23T15:50:57")
@StaticMetamodel(TbKonfirmasi.class)
public class TbKonfirmasi_ { 

    public static volatile SingularAttribute<TbKonfirmasi, Integer> jumlahBayar;
    public static volatile SingularAttribute<TbKonfirmasi, Integer> norekPengirim;
    public static volatile SingularAttribute<TbKonfirmasi, String> namaPengirim;
    public static volatile SingularAttribute<TbKonfirmasi, Integer> idKonfirmasi;
    public static volatile SingularAttribute<TbKonfirmasi, TbPemesanan> idPemesanan;

}