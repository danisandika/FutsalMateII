package model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.TbAdmin;
import model.TbBank;
import model.TbFutsal;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-23T15:50:57")
@StaticMetamodel(TbSewalapangan.class)
public class TbSewalapangan_ { 

    public static volatile SingularAttribute<TbSewalapangan, TbFutsal> idFutsal;
    public static volatile SingularAttribute<TbSewalapangan, Date> tglBerakhir;
    public static volatile SingularAttribute<TbSewalapangan, TbAdmin> idAdmin;
    public static volatile SingularAttribute<TbSewalapangan, Integer> waktuSewa;
    public static volatile SingularAttribute<TbSewalapangan, Integer> statusBayar;
    public static volatile SingularAttribute<TbSewalapangan, Date> tglSewa;
    public static volatile SingularAttribute<TbSewalapangan, TbBank> idBank;
    public static volatile SingularAttribute<TbSewalapangan, String> idSewalapangan;
    public static volatile SingularAttribute<TbSewalapangan, Integer> jumlahUang;
    public static volatile SingularAttribute<TbSewalapangan, Date> tglPembayaran;

}