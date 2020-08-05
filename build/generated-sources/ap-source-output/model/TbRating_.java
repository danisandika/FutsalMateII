package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.TbFutsal;
import model.TbPemain;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-23T15:50:57")
@StaticMetamodel(TbRating.class)
public class TbRating_ { 

    public static volatile SingularAttribute<TbRating, TbFutsal> idFutsal;
    public static volatile SingularAttribute<TbRating, String> komentar;
    public static volatile SingularAttribute<TbRating, Double> rating;
    public static volatile SingularAttribute<TbRating, TbPemain> idPemain;
    public static volatile SingularAttribute<TbRating, Integer> idRating;

}