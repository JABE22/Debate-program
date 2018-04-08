/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oope2018ht.tiedostot;

import oope2018ht.apulaiset.Getteri;

/**
 *
 * @author jarnomata
 */
public class Video extends Tiedosto {
    
    private final double pituus;
    
    
    public Video(String nimi, int koko, double pituus) {
        super(nimi, koko);
        this.pituus = pituus;
    }
    
    @Getteri
    public double getPituus() {
        return this.pituus;
    }

    @Override
    public String toString() {
        return super.toString() + " " + this.pituus + " s";
    }
}
