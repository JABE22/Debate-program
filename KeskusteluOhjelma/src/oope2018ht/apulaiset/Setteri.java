package oope2018ht.apulaiset;

import java.lang.annotation.*;

/**
  * Annotaatio, jolla autetaan WETOa tunnistamaan attribuuttien asetusmetodit (getterit).
  * 
  * <p>
  * Harjoitustyö, Olio-ohjelmoinnin perusteet, kevät 2018.
  * <p>
  * @author Jorma Laurikkala (jorma.laurikkala@uta.fi), Luonnontieteiden tiedekunta,
  * Tampereen yliopisto.
  *
  */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Setteri {
}
