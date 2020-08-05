package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.TbSewalapangan;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-23T15:50:57")
@StaticMetamodel(TbAdmin.class)
public class TbAdmin_ { 

    public static volatile SingularAttribute<TbAdmin, String> notelp;
    public static volatile SingularAttribute<TbAdmin, String> tglLahir;
    public static volatile SingularAttribute<TbAdmin, Integer> idAdmin;
    public static volatile SingularAttribute<TbAdmin, String> password;
    public static volatile SingularAttribute<TbAdmin, String> nama;
    public static volatile SingularAttribute<TbAdmin, String> foto;
    public static volatile CollectionAttribute<TbAdmin, TbSewalapangan> tbSewalapanganCollection;
    public static volatile SingularAttribute<TbAdmin, String> email;
    public static volatile SingularAttribute<TbAdmin, String> alamat;
    public static volatile SingularAttribute<TbAdmin, Integer> status;

}