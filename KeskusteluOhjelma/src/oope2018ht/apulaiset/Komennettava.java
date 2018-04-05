package oope2018ht.apulaiset;

/**
  * Viestien testaamiseen soveltuvia metodeja, joista on todennäköisesti paljon
  * hyötyä harjoistyössä myös sellaisenaan. Kiinnitä tyyppimuuttujan V tyypiksi
  * Viesti, kun toteutat tämän rajapinnan Viesti-luokassa.
  * <p>
  * Harjoitustyö, Olio-ohjelmoinnin perusteet, kevät 2018.
  * <p>
  * @author Jorma Laurikkala (jorma.laurikkala@uta.fi), Luonnontieteiden tiedekunta,
  * Tampereen yliopisto.
  * <p>
  * @version 1.0.
  */

public interface Komennettava<V> {

   /** Tyhjennetyn viestin teksti (10 x miinusmerkki).
     */
   public static final String POISTETTUTEKSTI = "----------";

   /** Hakee viestiä listalta, joka säilöö viitteet viestiin vastaaviin viesteihin.
     * Hyödyntää OmaLista-luokan hae-operaatiota.
     *
     * @param haettava viite haettavaan viestiin.
     * @return viite löydettyyn tietoon. Palauttaa null-arvon, jos haettavaa
     * viestiä ei löydetä.
     * @throws IllegalArgumentException jos parametri on null-arvoinen.
     */
   abstract public V hae(V haettava) throws IllegalArgumentException;
   
   /** Lisää uuden viitteen listalle, joka säilöö viitteet viestiin vastaaviin
     * viesteihin. Uusi viite lisätään listan loppuun. Viitettä ei lisätä,
     * jos listalla on jo viite lisättävään vastaukseen. Hyödyntää hae-metodia.
     *
     * @param lisattava viite lisättävään viestiin.
     * @throws IllegalArgumentException jos lisäys epäonnistui, koska parametri
     * on null-arvoinen tai koska vastaus on jo lisätty listalle.
     */
   abstract public void lisaaVastaus(V lisattava) throws IllegalArgumentException;

   /** Asettaa viestin tekstiksi vakion POISTETTUTEKSTI ja poistaa viestiin
     * mahdollisesti liitetyn tiedoston asettamalla attribuutin arvoksi null-arvon.
     */
   abstract public void tyhjenna();
}
