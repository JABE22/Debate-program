package fi.uta.csjola.oope.lista;

/**
  * Lista-rajapinnan suppea toteutus abstraktissa luokassa.
  * <p>
  * Olio-ohjelmoinnin perusteet.
  * <p>
  * Viimeksi muutettu 5.2.2013 12.58.
  * <p>
  * @author Jorma Laurikkala (jorma.laurikkala@uta.fi),
  * Informaatiotieteiden yksikkö, Tampereen yliopisto.
  */

public abstract class AbstraktiLista implements Lista {
   /*
    * Attribuutit.
    *
    */

   /** Listan alkioiden lukumäärä. Vain jälkeläisten käyttöön. */
   protected int koko;

   /*
    * Rakentajat
    *
    */

   /** Asetetaan koko-attribuutin arvoksi nolla.
     *
     */
   public AbstraktiLista() {
     koko = 0;
   }

   /*
    * Apumetodit.
    *
    */

   /** Tarkistetaan onko paikka kunnollinen. Vain jälkeläisten käyttöön.
     *
     * @param paikka listan indeksi.
     * @return true, jos paikka ok, muuten false.
     */
   protected boolean paikkaOK(int paikka) {
      return (0 <= paikka) && (paikka < koko);
   }

   /*
    * Listan operaatioiden korvaus toteuttamalla.
    *
    */

   /** {@inheritDoc}
     *
     * @param alkio {@inheritDoc}
     */
   public void lisaaAlkuun(Object alkio) { };

   /** {@inheritDoc}
     *
     * @param alkio {@inheritDoc}
     */
   public void lisaaLoppuun(Object alkio) { };

   /** {@inheritDoc}
     *
     * @param paikka {@inheritDoc}
     * @param alkio {@inheritDoc}
     * @return {@inheritDoc}
     */
   public boolean lisaa(int paikka, Object alkio) {
      return false;
   }

   /** {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
   public Object poistaAlusta() {
      return null;
   }

   /** {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
   public Object poistaLopusta() {
      return null;
   }

   /** {@inheritDoc}
     *
     * @param paikka {@inheritDoc}
     * @return {@inheritDoc}
     */
   public Object poista(int paikka) {
      return null;
   }

   /** {@inheritDoc}
     *
     * @param paikka {@inheritDoc}
     * @return {@inheritDoc}
     */
   public Object alkio(int paikka) {
      return null;
   }

   /** {@inheritDoc}
     *
     * @param paikka {@inheritDoc}
     * @param alkio {@inheritDoc}
     * @return {@inheritDoc}
     */
   public Object korvaa(int paikka, Object alkio) {
      return null;
   }

   /** {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
   public int koko() {
      return koko;
   }

   /** {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
   public boolean onkoTyhja() {
      return koko == 0;
   }
}
