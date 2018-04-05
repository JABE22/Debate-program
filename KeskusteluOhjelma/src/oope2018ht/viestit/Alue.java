/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oope2018ht.viestit;

import fi.uta.csjola.oope.lista.Solmu;
import oope2018ht.omalista.OmaLista;

/**
 *
 * @author jarnomata
 */
public class Alue {

    private OmaLista viestiketjut;
    private Ketju aktiv_vk;

    public Alue() {
        this.viestiketjut = new OmaLista();
        this.aktiv_vk = null;
    }

    // Lisää uuden ketjun ja ensimmäisen viestin (Ketju == 1. viesti)
    public void lisaaKetju(String teksti, String tiedosto) {
        // Toistaiseksi kuitataan tiedostot null -arvolla
        Viesti aloitusviesti = new Viesti(viestiketjut.koko() + 1, teksti, null, null);
        Ketju viestiketju = new Ketju(aloitusviesti);
        this.viestiketjut.lisaa(viestiketju);
        this.aktiv_vk = viestiketju;
    }

    public void lisaaViesti(String teksti, String tiedosto) {
        if (this.viestiketjut.koko() == 0) {
            System.out.println("Yhtään viestiketjua ei ole vielä luotu");
        } else {
            this.aktiv_vk.lisaaViesti(teksti, tiedosto);
            System.out.println("Viesti lisätty");
        }
    }

    public void lisaaVastaus(int vastattava_id, String teksti, String tiedosto) {
        this.aktiv_vk.lisaaVastaus(vastattava_id, teksti, tiedosto);
    }

    public OmaLista getViestiketjut() {
        return this.viestiketjut;
    }

    public Viesti getViestiketju(int paikka) {
        return (Viesti) this.viestiketjut.alkio(paikka);
    }

    public void aktivoiKetju(String ketju) {
        if (ketju == null) {
            this.aktiv_vk = (Ketju) this.viestiketjut.alkio(0);
        } else {
            int ketju_nro = Integer.parseInt(ketju);
            ketju_nro-=1;
            if (ketju_nro < 0 || ketju_nro > this.viestiketjut.koko() - 1) {
                System.out.println("Viestiketjua ei ole luotu annetulla arvolla");
            } else {
                if (this.viestiketjut.alkio(ketju_nro) != null) {
                    Solmu solmu = (Solmu) this.viestiketjut.alkio(ketju_nro);
                    this.aktiv_vk = (Ketju)solmu.alkio();
                    System.out.println("Viestiketju aktivoitu " + this.aktiv_vk.getAloitusviesti().getTunniste());
                }
            }
        }
    }

    public void tulostaAlueListana() {
        if (!this.viestiketjut.onkoTyhja()) {
            int ketju_index = 0;

            while (ketju_index < this.viestiketjut.koko()) {
                System.out.println(this.viestiketjut.alkio(ketju_index));
                ketju_index++;
            }
        }

    }

    public void tulostaPuuna() {

    }
}
