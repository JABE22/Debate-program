/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oope2018ht.viestit;

import oope2018ht.omalista.OmaLista;
import oope2018ht.tiedostot.Kuva;
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

    public void lisaaViesti(String teksti, String tiedosto) {
        Viesti uusi = luoViesti(teksti, null, tiedosto);
        this.vastaukset.lisaa(uusi);
        
    }

    public void lisaaVastaus(int vastattava_id, String teksti, String tiedosto) {
        // Luodaan viite aktiivisen ketjun viestiin, johon vastataan
        Viesti vastattava = (Viesti) this.vastaukset.alkio(vastattava_id);
        Viesti vastaus = luoViesti(teksti, vastattava, tiedosto);
        // Lisätään vastausviesti vastattavan vastaukset -listalle
        vastattava.lisaaVastaus(vastaus);
    }

    private Viesti luoViesti(String teksti, Viesti vastaa, String tiedosto) {
        // Luodaan viestin tunniste ja viesti
        Viesti uusiViesti;
        int vastauksia = this.vastaukset.koko();

        // Jos viestiin ei ole liitetty tiedostoa
        if (tiedosto == null) {
            uusiViesti = new Viesti(vastauksia + 1, teksti, null, null);

            // Muuten luodaan uusi tiedosto
        } else {
            // Lisätään tänne tiedoston luonti
            Tiedosto file = new Kuva("Kuva", 800, 640, 480);
            uusiViesti = new Viesti(vastauksia + 1, teksti, null, file);
        }

        return uusiViesti; // Palautetaan viesti
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("=\n== ").append(this.aloitusviesti);
        
        
        result.append(" (").append(this.vastaukset.koko());
        result.append(" messages)\n===\n");

//        System.out.println("=\n" + "== " + this.vastaukset.alkio(0) + " (" 
//                + this.vastaukset.koko() + "messages)\n===");
        if (this.vastaukset.koko() > 0) {
            int v_index = 0;
            while (v_index < this.vastaukset.koko()) {
                result.append(this.vastaukset.alkio(v_index).toString()).append("\n");
                v_index++;
            }
        }
        return result.toString();
    }
}
