package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.TbFutsal;
import model.TbPemesanan;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-23T15:50:57")
@StaticMetamodel(TbLapangan.class)
public class TbLapangan_ { 

    public static volatile SingularAttribute<TbLapangan, TbFutsal> idFutsal;
    public static volatile SingularAttribute<TbLapangan, String> jenisLapangan;
    public static volatile CollectionAttribute<TbLapangan, TbPemesanan> tbPemesananCollection;
    public static volatile SingularAttribute<TbLapangan, Integer> harga;
    public static volatile SingularAttribute<TbLapangan, String> namaLapangan;
    public static volatile SingularAttribute<TbLapangan, String> gambar;
    public static volatile SingularAttribute<TbLapangan, Integer> idLapangan;
    public static volatile SingularAttribute<TbLapangan, Integer> status;

}