package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.TbSewalapangan;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-23T15:50:57")
@StaticMetamodel(TbBank.class)
public class TbBank_ { 

    public static volatile SingularAttribute<TbBank, String> kodeBank;
    public static volatile SingularAttribute<TbBank, String> namaBank;
    public static volatile SingularAttribute<TbBank, String> namaRekening;
    public static volatile CollectionAttribute<TbBank, TbSewalapangan> tbSewalapanganCollection;
    public static volatile SingularAttribute<TbBank, Integer> idBank;
    public static volatile SingularAttribute<TbBank, String> noRekening;
    public static volatile SingularAttribute<TbBank, Integer> status;

}