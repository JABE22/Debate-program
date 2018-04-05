package fi.uta.csjola.oope.lista;

/**
  * Määrittelee liittymän abstraktille tietotyypille lista.
  * <p>
  * null-arvoiset alkiot voivat olla joko sallittuja tai kiellettyjä
  * rajapinnan toteuttavan luokan tarpeiden mukaan.
  * <p>
  * Olio-ohjelmoinnin perusteet.
  * <p>
  * Viimeksi muutettu 5.2.2012 13:02.
  * <p>
  * @author Jorma Laurikkala (jorma.laurikkala@uta.fi),
  * Informaatiotieteiden yksikkö, Tampereen yliopisto.
  */

public interface Lista {

   /*
    * Listan operaatiot.
    *
    */

   /** Lisää uuden solmun linkitetyn listan alkuun.
     *
     * @param alkio lisättävä tieto.
     */
   public abstract void lisaaAlkuun(Object alkio);

   /** Lisää uuden solmun linkitetyn listan loppuun.
     *
     * @param alkio lisättävä tieto.
     */
   public abstract void lisaaLoppuun(Object alkio);

   /** Lisää annettuun linkitetyn listan kohtaan uuden solmun.
     * Paikan vanha solmu ja vanhaa solmua seuraavat solmut
     * siirtyvät yhden paikan hännän suuntaan.
     *
     * @param paikka lisäyspaikka.
     * @param alkio lisättävä tieto.
     * @return totuusarvo, joka ilmaisee lisäyksen onnistumisen.
     */
   public abstract boolean lisaa(int paikka, Object alkio);

   /** Poistetaan linkitetyn listan pää.
     *
     * @return listan pään tietoalkio tai null-arvo (tyhjä lista).
     */
   public abstract Object poistaAlusta();

   /** Poistetaan linkitetyn listan häntä.
     *
     * @return listan hännän tietoalkio tai null-arvo (tyhjä lista).
     */
   public abstract Object poistaLopusta();

   /** Poistaa annetusta paikasta solmun ja palauttaa poistetun
     * solmun alkion. Paluuarvo on null, mikäli paikka oli virheelinen.
     *
     * @param paikka poistopaikka.
     * @return solmun tietoalkio tai null-arvo.
     */
   public abstract Object poista(int paikka);

   /** Palauttaa annetussa paikassa olevan solmun alkion.
     * Mikäli paikka on virheellinen, paluuarvo on null.
     *
     * @param paikka solmun paikka listassa.
     * @return alkio tai null.
     */
   public abstract Object alkio(int paikka);

   /** Korvaa annetussa paikassa olevan solmun tietoalkion parametrina
     * annetulla uudella tietoalkiolla. Palauttaa korvatun (eli solmun
     * vanhan) tietoalkion, jos paikka on kunnollinen. Paluuarvo on null,
     * jos paikka on virheellinen.
     *
     * @param paikka solmun paikka listassa.
     * @param alkio uusi tietoalkio.
     * @return korvattu tietoalkio tai null.
     */
   public abstract Object korvaa(int paikka, Object alkio);

   /** Palauttaa listan solmujen lukumäärän.
     *
     * @return solmujen lkm.
     */
   public abstract int koko();

   /** Onko lista tyhjä?
     *
     * @return true, mikäli listassa ei ole solmuja, false muussa tapauksessa.
     */
   public abstract boolean onkoTyhja();
}
