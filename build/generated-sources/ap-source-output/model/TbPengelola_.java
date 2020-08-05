package model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.TbFutsal;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-23T15:50:57")
@StaticMetamodel(TbPengelola.class)
public class TbPengelola_ { 

    public static volatile SingularAttribute<TbPengelola, TbFutsal> idFutsal;
    public static volatile SingularAttribute<TbPengelola, String> notelp;
    public static volatile SingularAttribute<TbPengelola, Date> tglLahir;
    public static volatile SingularAttribute<TbPengelola, String> password;
    public static volatile SingularAttribute<TbPengelola, String> nama;
    public static volatile SingularAttribute<TbPengelola, String> foto;
    public static volatile SingularAttribute<TbPengelola, String> fotoKtp;
    public static volatile SingularAttribute<TbPengelola, Integer> idPengelola;
    public static volatile SingularAttribute<TbPengelola, String> email;
    public static volatile SingularAttribute<TbPengelola, String> alamat;
    public static volatile SingularAttribute<TbPengelola, Integer> status;

}