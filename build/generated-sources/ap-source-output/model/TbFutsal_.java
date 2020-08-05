package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.TbLapangan;
import model.TbPengelola;
import model.TbRating;
import model.TbSewalapangan;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-23T15:50:57")
@StaticMetamodel(TbFutsal.class)
public class TbFutsal_ { 

    public static volatile SingularAttribute<TbFutsal, String> alamatFutsal;
    public static volatile SingularAttribute<TbFutsal, String> deskripsiFutsal;
    public static volatile SingularAttribute<TbFutsal, Double> latitude;
    public static volatile SingularAttribute<TbFutsal, Double> rating;
    public static volatile SingularAttribute<TbFutsal, String> fasilitas;
    public static volatile SingularAttribute<TbFutsal, String> noRekening;
    public static volatile SingularAttribute<TbFutsal, String> gambar;
    public static volatile CollectionAttribute<TbFutsal, TbPengelola> tbPengelolaCollection;
    public static volatile SingularAttribute<TbFutsal, Integer> idFutsal;
    public static volatile CollectionAttribute<TbFutsal, TbRating> tbRatingCollection;
    public static volatile SingularAttribute<TbFutsal, String> namaFutsal;
    public static volatile SingularAttribute<TbFutsal, String> notelpFutsal;
    public static volatile SingularAttribute<TbFutsal, String> namaBank;
    public static volatile SingularAttribute<TbFutsal, String> namaRekening;
    public static volatile CollectionAttribute<TbFutsal, TbLapangan> tbLapanganCollection;
    public static volatile CollectionAttribute<TbFutsal, TbSewalapangan> tbSewalapanganCollection;
    public static volatile SingularAttribute<TbFutsal, Integer> status;
    public static volatile SingularAttribute<TbFutsal, Double> longitude;

}