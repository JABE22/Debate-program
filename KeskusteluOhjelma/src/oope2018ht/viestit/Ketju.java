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

    public void tulostaPuuna() {
        System.out.println("=\n== " + this.aloitusviesti
                + " (" + this.vastaukset.koko() + " messages)\n===");

        // Tarkistetaan, onko viestejä ketjussa (Aiheviestin lisäksi)
        if (this.vastaukset.koko() > 0) {

            // Ensimmäisen (1) tason viestien (aloitusviestien) tulostus
            Solmu solmu;
            int v_index = 0;
            while (v_index < this.vastaukset.koko()) {
                solmu = (Solmu) this.vastaukset.alkio(v_index);
                Viesti viesti = (Viesti) solmu.alkio();

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
                        solmu = (Solmu) viestit.alkio(v2_index);
                        viesti = (Viesti) solmu.alkio();
                        System.out.println("   " + viesti);
                        

                        // Kolmannen (3) tason viestien tulostus
                        OmaLista viestit2 = viesti.getVastaukset();
                        if (viestit2.koko() > 0) {

                            int v3_index = 0;
                            while (v3_index < viestit2.koko()) {
                                solmu = (Solmu) viestit2.alkio(v3_index);
                                viesti = (Viesti) solmu.alkio();
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
}
