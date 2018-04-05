package fi.uta.csjola.oope.lista;

/**
  * Yhteen suuntaan linkitetyn listan solmu.
  * <p>
  * Olio-ohjelmoinnin perusteet.
  * <p>
  * Viimeksi muutettu 5.2.2012 13.03.
  * <p>
  * @author Jorma Laurikkala (jorma.laurikkala@uta.fi),
  * Informaatiotieteiden yksikkö, Tampereen yliopisto.
  */

public class Solmu {

   /*
    * Attribuutit.
    *
    */

   /** Solmun sisältämä tieto, joka voi olla mitä tahansa luokkatyyppiä. */
   private Object alkio;

   /** Viite seuraavaan solmuun. */
   private Solmu seuraava;

   /*
    * Rakentaja
    *
    */

   public Solmu(Object uusi) {
      // Hyväksytään, että voidaan rakentaa null-arvoisella alkiolla.
      alkio = uusi;
      seuraava = null;
   }

   /*
    * Aksessorit
    *
    */

   public void alkio(Object uusi) {
      // Solmun voi tyhjentää null-arvolla.
      alkio = uusi;
   }

   public Object alkio() { return alkio; }

   public void seuraava(Solmu s) {
      seuraava = s;
   }

   public Solmu seuraava() { return seuraava; }

   /*
    * Object-luokan korvatut metodit.
    *
    */

   /** Korvataan Object-luokan metodi siten, että kutsutaan alkion metodia.
     * Jos tämä ei ole mahdollista, niin käytetään sittenkin kiltisti
     * Object-luokan metodia.
     */
   public String toString() {
      if (alkio != null)
         return alkio.toString();
      else
         return super.toString();
   }
}
