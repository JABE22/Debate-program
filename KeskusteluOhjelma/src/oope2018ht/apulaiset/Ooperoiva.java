package oope2018ht.apulaiset;

/**
  * Pakolliset uudet listaoperaatiot määrittelevä geneerinen rajapinta. Kiinnitä
  * tyyppimuuttuja T tyypiksi OmaLista, kun toteutat tämän rajapinnan OmaLista
  * -luokassa.
  * <p>
  * Harjoitustyö, Olio-ohjelmoinnin perusteet, kevät 2018.
  * <p>
  * @author Jorma Laurikkala (jorma.laurikkala@uta.fi), Luonnontieteiden tiedekunta,
  * Tampereen yliopisto.
  * <p>
  * @version 1.0.
  */

public interface Ooperoiva<T> {

   /** Tutkii onko listalla haettavaa oliota equals-mielessä vastaava alkio.     
     * Haku aloitetaan listan alusta ja se etenee kohti listan loppua.
     * Haku pysähtyy ensimmäiseen osumaan, jos listalla on useita haettavaa oliota
     * vastaavia alkioita. Operaatio ei muuta listan sisältöä millään tavalla.
     *
     * Jos parametri liittyy esimerkiksi merkkijonoon "ab" ja listan tietoalkiot
     * ovat [ "ab", "AB, "Ab", "aB", "ab" ], palauttaa operaatio viitteen listan
     * ensimmäiseen tietoalkioon, koska "ab".equals("ab") == true ja kysessä on
     * haun ensimmäinen osuma.
     *
     * @param haettava olio, jota vastaavaa alkiota listalta haetaan. Olion luokan
     * tai luokan esivanhemman oletetaan korvanneen Object-luokan equals-metodin.
     * @return viite löydettyyn alkioon. Paluuarvo on null, jos parametri on
     * null-arvoinen, vastaavaa alkiota ei löydy tai lista on tyhjä.
     */
   public abstract Object hae(Object haettava);
   
   /** Listan alkioiden välille muodostuu kasvava suuruusjärjestys, jos lisäys
     * tehdään vain tällä operaatiolla, koska uusi alkion lisätään listalle siten,
     * että alkio sijoittuu kaikkien itseään pienempien tai yhtä suurien alkioiden
     * jälkeen ja ennen kaikkia itseään suurempia alkioita. Alkioiden suuruus-
     * järjestys selvitetään Comparable-rajapinnan compareTo-metodilla.
     *
     * Jos parametri liittyy esimerkiksi kokonaislukuun 2 ja listan tietoalkiot
     * ovat [ 0, 3 ], on listan sisältö lisäyksen jälkeen [ 0, 2, 3 ],
     * koska 0 < 2 < 3.
     *
     * Sijoita toteuttamasi metodin yleisten kommenttien ja metodin otsikon väliin
     * annotaatio "@SuppressWarnings({"unchecked"})", jolla kääntäjälle vakuutetaan,
     * että metodin koodi on turvallista. Ilman määrettä kääntäjä varoittaa,
     * että Comparable-rajapintaa käytetään ei-geneerisellä tavalla. Esimerkki
     * annotaation käytöstä on annettu harjoitustehtävän 7.3. mallivastauksessa.
     *
     * @param uusi viite olioon, jonka luokan tai luokan esivanhemman oletetaan
     * toteuttaneen Comparable-rajapinnan.
     * @return true, jos lisäys onnistui ja false, jos uutta alkiota ei voitu
     * vertailla. Vertailu epäonnistuu, kun parametri on null-arvoinen tai sillä
     * ei ole vastoin oletuksia Comparable-rajapinnan toteutusta.
     */
   public abstract boolean lisaa(Object uusi);
   
   /** Luo ja palauttaa uuden listan, jolla on viitteet n ensimmäiseen listan
     * tietoalkioon. Palautettavan listan ensimmäinen viite liittyy listan
     * ensimmäiseen alkioon, palautettavan listan toinen viite liittyy listan
     * toiseen alkioon ja niin edelleen. Palautettavan listan viimeinen viite
     * liittyy listan n. alkioon. Operaatio ei muuta listan sisältöä millään
     * tavalla.
     *
     * Jos parametrin arvo on esimerkiksi kaksi ja listan tietoalkiot ovat
     * [ "AB", "Ab", "aB", "ab" ], on palautetulta listalta viitteet listan
     * ensimmäiseen ja toiseen alkioon, jolloin palautetun listan tietoalkiot
     * ovat [ "AB, "Ab" ].
     *
     * @param n palautettavalle listalle lisättävien viitteiden lukumäärä.
     * @return viite listaan, joka sisältää viitteet listan n ensimmäiseen
     * tietoalkioon. Paluuarvo on null, jos lista on tyhjä tai jos parametrin
     * arvo on pienempi kuin yksi tai suurempi kuin listan alkioiden lukumäärä.
     */
   public abstract T annaAlku(int n);

   /** Luo ja palauttaa uuden listan, jolla on viitteet n viimeiseen listan
     * tietoalkioon. Palautettavan listan ensimmäinen viite liittyy listan m.
     * (koko - n + 1) alkioon, palautettavan listan toinen viite liittyy listan
     * m + 1. (koko - n + 2) alkioon ja niin edelleen. Palautettavan listan
     * viimeinen viite liittyy listan viimeiseen alkioon. Operaatio ei muuta
     * listan sisältöä millään tavalla.
     *
     * Jos parametrin arvo on esimerkiksi kaksi ja listan tietoalkiot ovat
     * [ "AB", "Ab", "aB", "ab" ], on palautetulta listalta viitteet listan
     * kolmanteen ja neljänteen alkioon, jolloin palautetun listan tietoalkiot
     * ovat [ "aB, "ab" ].
     *
     * @param n palautettavalle listalle lisättävien viitteiden lukumäärä.
     * @return viite listaan, joka sisältää viitteet listan n viimeiseen
     * tietoalkioon. Paluuarvo on null, jos lista on tyhjä tai jos parametrin
     * arvo on pienempi kuin yksi tai suurempi kuin listan alkioiden lukumäärä.
     */
   public abstract T annaLoppu(int n);
}
