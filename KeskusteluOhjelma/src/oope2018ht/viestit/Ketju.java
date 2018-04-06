/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oope2018ht.viestit;

import fi.uta.csjola.oope.lista.Solmu;
import oope2018ht.omalista.OmaLista;
import oope2018ht.tiedostot.Tiedosto;

/**
 *
 * @author jarnomata
 */
public class Ketju {

    private final Viesti aloitusviesti;
    private final OmaLista vastaukset;

    public Ketju(Viesti aloitusViesti) {
        this.aloitusviesti = aloitusViesti;
        this.vastaukset = new OmaLista();
    }
    
    public Viesti getAloitusviesti() {
        return this.aloitusviesti;
    }
    
    public int vastauksia() {
        return this.vastaukset.koko();
    }

    public void lisaaViesti(String teksti, Tiedosto tiedosto) {
        Viesti uusi = luoViesti(teksti, null, tiedosto);
        this.vastaukset.lisaa(uusi);
        
    }

    public void lisaaVastaus(int vastattava_id, String teksti, Tiedosto tiedosto) {
        // Luodaan viite aktiivisen ketjun viestiin, johon vastataan
        Solmu solmu = (Solmu) this.vastaukset.alkio(vastattava_id);
        Viesti vastattava = (Viesti) solmu.alkio();
        Viesti vastaus = luoViesti(teksti, vastattava, tiedosto);
        // Lisätään vastausviesti vastattavan vastaukset -listalle
        vastattava.lisaaVastaus(vastaus);
        this.vastaukset.lisaa(vastaus);
    }

    private Viesti luoViesti(String teksti, Viesti vastaa, Tiedosto tiedosto) {
        // Luodaan viestin tunniste ja viesti
        int vastauksia = this.vastaukset.koko();
        Viesti uusiViesti = new Viesti(vastauksia + 1, teksti, vastaa, tiedosto);
     
        return uusiViesti; // Palautetaan viesti
    }

    public void tulostaListana() {
        System.out.println("=\n== " + this.aloitusviesti 
                + " (" + this.vastaukset.koko() + " messages)\n===");

        if (this.vastaukset.koko() > 0) {
            int v_index = 0;
            while (v_index < this.vastaukset.koko()) {
                System.out.println(this.vastaukset.alkio(v_index).toString());
                v_index++;
            }
        }
    }
    
    public void tulostaKetjuPuuna() {
        
    }
    
    @Override
    public String toString() {
        return this.aloitusviesti + " (" + this.vastauksia() + " messages)";
    }
}
