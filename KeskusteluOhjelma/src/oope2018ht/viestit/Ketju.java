/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oope2018ht.viestit;

import oope2018ht.apulaiset.Getteri;
import oope2018ht.omalista.OmaLista;
import oope2018ht.tiedostot.Tiedosto;

/**
 *
 * @author jarnomata
 * 
 * Ketju -luokka kapseloi aloitusviestin, joka kuvaa ketjun aihetta sekä 
 * OmaListan, joka sisältää ketjun aiheeseen liittyvät uudet viestit (aloitusviestit)
 * ja niihin liittyvät vastaukset. Ketjuun lisättyjen viestien viitteet tallennetaan
 * siis sekä tämän luokan kapseloimaan "vastaukset" -OmaListaan, että kunkin 
 * vastaukset -listan sisältämän Viestin omaan vastaukset -listaan. Tämä helpottaa
 * viestien hakemista, läpikäyntiä ja tulostamista.
 * 
 * Ketju vastaa osittain myös itsensä tulostamisesta. Tämä siksi, että vältytään
 * turhien apumuuttujien, ylimääräisten metodien ja sitä kautta monimutkaisempien
 * rakenteiden luomiselta.
 */
public class Ketju implements Comparable<Ketju> {

    private final Viesti aloitusviesti; // Ketjun aiheviesti
    private final OmaLista vastaukset; // Ketjun kaikki viestit

    public Ketju(Viesti aloitusViesti) {
        this.aloitusviesti = aloitusViesti;
        this.vastaukset = new OmaLista();
    }
    
    @Getteri
    public Viesti getAloitusviesti() {
        return this.aloitusviesti;
    }
    
    // Palauttaa null, jos vastauksia vähemmän kuin parametrina annettu lukumäärä
    // ts. lkm ei sallitulla alueella (lkm < 0 || lkm > vastaukset.koko())
    public OmaLista getVanhatViestit(int lkm) {
        return this.vastaukset.annaAlku(lkm);
        
    }
    
    // Palauttaa null, jos vastauksia vähemmän kuin parametrina annettu lukumäärä
    // ts. lkm ei sallitulla alueella (lkm < 0 || lkm > vastaukset.koko())
    public OmaLista getUudetViestit(int lkm) {
        return this.vastaukset.annaLoppu(lkm);
    }

    public int vastauksia() {
        return this.vastaukset.koko();
    }
    
    // Lisää uuden aloitusviestin ketjuun (Ei vastausviestiä)
    public void lisaaViesti(String teksti, Tiedosto tiedosto) {
        Viesti uusi = luoViesti(teksti, null, tiedosto);
        this.vastaukset.lisaa(uusi);

    }
    
    // Lisää ketjuun viestin, joka vastaa aiemmin luotuun viestiin
    public void lisaaVastaus(int vastattava_id, String teksti, Tiedosto tiedosto) {
        // Luodaan viite aktiivisen ketjun viestiin, johon vastataan
        Viesti vastattava = (Viesti) this.vastaukset.alkio(vastattava_id);
        Viesti vastaus = luoViesti(teksti, vastattava, tiedosto);
        // Lisätään vastausviesti vastattavan vastaukset -listalle
        vastattava.lisaaVastaus(vastaus);
        this.vastaukset.lisaa(vastaus);
    }
    
    // Luo uuden viestin ja palauttaa sen
    private Viesti luoViesti(String teksti, Viesti vastaa, Tiedosto tiedosto) {
        // Luodaan viestin tunniste ja viesti
        Viesti uusiViesti = new Viesti(vastauksia() + 1, teksti, vastaa, tiedosto);

        return uusiViesti; // Palautetaan viesti
    }
    
    public void tyhjennaViesti(int id) {
        // Muutetaan tunniste vastaamaan indeksiä
        int viesti_indeksi = id - 1;
        Viesti tyhjennettava = (Viesti) this.vastaukset.alkio(viesti_indeksi);
        
        if (!(tyhjennettava == null)) {
            tyhjennettava.tyhjenna();
        }
    }
    
    // Tulostaa ketjun kaikki viestit järjestyksessä
    public void tulostaListana() {
        System.out.println("=\n== " + this + "\n===");

        if (this.vastaukset.koko() > 0) {
            int v_index = 0;
            while (v_index < this.vastauksia()) {
                System.out.println(this.vastaukset.alkio(v_index).toString());
                v_index++;
            }
        }
    }
    
    /** Tulostaa ketjun viestit hierarkisesti, eli puu rakenteena.
    * Huom! Ratkaisumallin hierarkiassa voi olla enintään kolme tasoa. Tasoja voi 
    * lisätä jatkamalla koodin kirjoittamista tai tekemällä kokonaan uuden mallin
    */
    public void tulostaPuuna() {
        System.out.println("=\n== " + this + "\n===");

        // Tarkistetaan, onko viestejä ketjussa (Aiheviestin lisäksi)
        if (this.vastauksia() > 0) {

            // Ensimmäisen (1) tason viestien (aloitusviestien) tulostus
            int v_index = 0;
            while (v_index < this.vastauksia()) {
                Viesti viesti = (Viesti) this.vastaukset.alkio(v_index);

                // Tulostetaan vain viestit jotka eivät ole vastauksia
                if (viesti.getVastaa() != null) {
                    v_index++;
                    continue;
                }
                System.out.println(viesti);
                OmaLista viestit = viesti.getVastaukset();

                //Jos ensimmäisen tason viestillä on vastauksia, tulostetaan ne.
                if (viestit.koko() > 0) {

                    // Toisen (2) tason viestien tulostus
                    int v2_index = 0;
                    while (v2_index < viestit.koko()) {
                        viesti = (Viesti) viestit.alkio(v2_index);
                        System.out.println("   " + viesti);

                        // Kolmannen (3) tason viestien tulostus
                        OmaLista viestit2 = viesti.getVastaukset();
                        if (viestit2.koko() > 0) {

                            int v3_index = 0;
                            while (v3_index < viestit2.koko()) {
                                viesti = (Viesti) viestit2.alkio(v3_index);
                                System.out.println("      " + viesti);

                                v3_index++;
                            }
                        }
                        v2_index++;
                    }
                }
                v_index++;
            }
        }
    }

    @Override
    public String toString() {
        return this.aloitusviesti + " (" + this.vastauksia() + " messages)";
    }

    @Override
    public int compareTo(Ketju k) {
        return aloitusviesti.getTunniste() - k.getAloitusviesti().getTunniste();
    }
}
