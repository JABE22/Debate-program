package fi.uta.csjola.oope.lista;

/**
  * Yhteen suuntaan linkitetty lista.
  * <p>
  * Alkiot voivat olla null-arvoisia.
  * <p>
  * Olio-ohjelmoinnin perusteet.
  * <p>
  * Viimeksi muutettu 11.2.2014 14.37: lisaa-operaatioon lisätty
  * erikoistapauksen käsittely, jotta metodi toimisi samoin kuin
  * vastineensa Javan kokoelmaluokissa.
  * <p>
  * @author Jorma Laurikkala (jorma.laurikkala@uta.fi),
  * Informaatiotieteiden yksikkö, Tampereen yliopisto.
  */

public class LinkitettyLista extends AbstraktiLista {

   /*=============================================================
    *
    * Olioattribuutit
    *
    */

   /** Listan pää. */
   private Solmu paa;

   /** Listan häntä. */
   private Solmu hanta;

   /*=============================================================
    *
    * Rakentajat
    *
    */

   /** Alustetaan paa- ja hanta-attribuutit <b>null</b>-arvoihin.
     *
     */
   public LinkitettyLista() {
      paa = null;
      hanta = null;
   }

   /*=============================================================
    *
    * Aksessorit
    *
    */

   /** Palauttaa listan pään. Vain jälkeläisten käyttöön.
     *
     * @return paa-attribuutin arvo.
     */
   protected Solmu paa() {
      return paa;
   }

   /** Palauttaa listan hännän. Vain jälkeläisten käyttöön.
     *
     * @return hanta-attribuutin arvo.
     */
   protected Solmu hanta() {
      return hanta;
   }

   /*=============================================================
    *
    * Apumetodit.
    *
    */

   /** Palauttaa annetussa paikassa olevan solmun.
     * Mikäli paikka on virheellinen, paluuarvo on null.
     *
     * @param paikka solmun paikka listassa.
     * @return solmu tai null.
     */
   private Solmu haeSolmu(int paikka) {
      // Paikka kunnollinen.
      if (paikkaOK(paikka)) {

         // Aloitetaan listan päästä.
         Solmu paikassa = paa;

         // Kelataan oikeaan kohtaan.
         for (int i = 0; i < paikka; i++)
            paikassa = paikassa.seuraava();

         // Palautetaan.
         return paikassa;
      }

      // Virheellinen paikka.
      else
         return null;
   }

   /*=============================================================
    *
    * Listan operaatioiden korvaukset.
    *
    */

   /** {@inheritDoc}
     *
     * @param alkio {@inheritDoc}
     */
   public void lisaaAlkuun(Object alkio) {
      // Luodaan uusi solmu.
      Solmu uusi = new Solmu(alkio);

      // Tyhjä lista: pää ja häntä ovat sama alkio.
      if (onkoTyhja()) {
         paa = uusi;
         hanta = uusi;
      }
      // Listassa on jo alkioita.
      else {
         // Uusi solmu osoittamaan päähän.
         uusi.seuraava(paa);

         // Uusi solmu listan pääksi.
         paa = uusi;
      }

      // Kasvatetaan listan kokoa yhdellä.
      koko++;
   }

   /** {@inheritDoc}
     *
     * @param alkio {@inheritDoc}
     */
   public void lisaaLoppuun(Object alkio) {
      // Luodaan uusi solmu.
      Solmu uusi = new Solmu(alkio);

      // Tyhjä lista: pää ja häntä ovat sama alkio.
      if (onkoTyhja()) {
         paa = uusi;
         hanta = uusi;
      }
      // Listassa on jo alkioita.
      else {
         // Häntä osoittamaan uuteen alkioon.
         hanta.seuraava(uusi);

         // Uusi alkio hännäksi.
         hanta = uusi;
      }

      // Kasvatetaan listan kokoa yhdellä.
      koko++;
   }

   /** {@inheritDoc}
     *
     * @param paikka {@inheritDoc}
     * @param alkio {@inheritDoc}
     * @return {@inheritDoc}
     */
   public boolean lisaa(int paikka, Object alkio) {

      // Oikeellinen lisäyspaikka tai lisätään tyhjän listan "alkuun".
      if (paikkaOK(paikka) || (koko == 0 && paikka == 0)) {

         // Pään paikka tai listassa tasan yksi alkio.
         if (paikka == 0 || koko == 1)
            lisaaAlkuun(alkio);

         // Jokin muu kuin pään paikka listassa, jonka koko > 1.
         else {

            // Luodaan uusi solmu.
            Solmu uusi = new Solmu(alkio);

            // Haetaan paikassa olevaa solmua edeltävä solmu.
            Solmu edeltava = haeSolmu(paikka - 1);

            // Paikassa oleva solmu.
            Solmu paikassa = edeltava.seuraava();

            // Asetetaan uusi solmu viittaamaan paikan solmuun.
            uusi.seuraava(paikassa);

            // Asetetaan edellinen solmu viittaamaan uuteen solmuun
            // paikassa olevan solmun asemasta.
            edeltava.seuraava(uusi);

            // Kasvatetaan listan kokoa yhdellä.
            koko++;
         }

         // Lisääminen onnistui.
         return true;
      }

      // Virheellinen lisäyspaikka.
      else
         return false;
   }

   /** {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
   public Object poistaAlusta() {

      // On jotain poistettavaa.
      if(!onkoTyhja()) {
         // Asetetaan apuviite päähän, jotta poistettavaa solmua ei hukata.
         Solmu poistettava = paa;

         // Jos listassa vain yksi solmu, niin paa == hanta == null.
         if (koko == 1)
            paa = hanta = null;

         // Listassa on kaksi tai useampia solmuja.
         else
            // Seuraavasta solmusta uusi pää.
            paa = paa.seuraava();

         // Vähennetään listan kokoa yhdellä.
         koko--;

         // Palautetaan tietoalkio.
         return poistettava.alkio();
      }

      // Tyhjä lista.
      else
         return null;
   }

   /** {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
   public Object poistaLopusta() {

      // On jotain poistettavaa.
      if(!onkoTyhja()) {
         // Asetetaan apuviite häntään, jotta poistettavaa solmua ei hukata.
         Solmu poistettava = hanta;

         // Jos listassa vain yksi solmu, niin paa == hanta == null.
         if (koko == 1)
            paa = hanta = null;

         // Listassa on vielä solmuja.
         else {
            // Edellisestä solmusta uusi häntä.
            hanta = haeSolmu(koko - 2);

            // Poistetaan viite vanhaan häntään.
            hanta.seuraava(null);
         }

         // Vähennetään listan kokoa yhdellä.
         koko--;

         // Palautetaan tietoalkio.
         return poistettava.alkio();
      }

      // Tyhjä lista.
      else
         return null;
   }

   /** {@inheritDoc}
     *
     * @param paikka {@inheritDoc}
     * @return {@inheritDoc}
     */
   public Object poista(int paikka) {

      // Oikeellinen poistopaikka.
      if (paikkaOK(paikka)) {

         // Pään paikka tai listassa tasan yksi alkio.
         if (paikka == 0 || koko == 1)
            return poistaAlusta();

         // Hännän paikka.
         else if (paikka == koko - 1)
            return poistaLopusta();

         // Jokin muu kuin pään tai hännän paikka listassa, jonka koko > 1.
         else {
            // Haetaan poistettavaa solmua edeltävä solmu.
            Solmu edeltava = haeSolmu(paikka - 1);

            // Poistettava solmu.
            Solmu poistettava = edeltava.seuraava();

            // Asetetaan edellinen solmu viittaamaan poistettavaa solmua
            // seuraavaan solmuun, jolloin poistettava solmu linkitetään pois.
            edeltava.seuraava(poistettava.seuraava());

            // Vähennetään listan kokoa yhdellä.
            koko--;

            // Palautetaan alkio.
            return poistettava.alkio();
         }
      }

      // Virheellinen poistopaikka.
      else
         return null;
   }

   /** {@inheritDoc}
     *
     * @param paikka {@inheritDoc}
     * @return {@inheritDoc}
     */
   public Object alkio(int paikka) {
      Solmu paikassa = haeSolmu(paikka);
      if (paikassa != null)
         return paikassa.alkio();
      else
         return null;
   }

   /** {@inheritDoc}
     *
     * @param paikka {@inheritDoc}
     * @param alkio {@inheritDoc}
     * @return {@inheritDoc}
     */
   public Object korvaa(int paikka, Object alkio) {

      // Haetaan annetussa paikassa oleva solmu.
      Solmu paikassa = haeSolmu(paikka);

      // Paikka oli kunnollinen.
      if (paikassa != null) {

         // Otetaan vanha alkio talteen.
         Object vanhaAlkio  = paikassa.alkio();

         // Korvataan vanha alkio uudella.
         paikassa.alkio(alkio);

         // Palautetaan vanha alkio.
         return vanhaAlkio;
      }

      // Paikka oli virheellinen.
      else
         return null;
   }
}
