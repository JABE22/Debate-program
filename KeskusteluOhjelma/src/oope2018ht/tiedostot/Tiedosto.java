/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oope2018ht.tiedostot;

import oope2018ht.apulaiset.Getteri;
import oope2018ht.apulaiset.Setteri;

/**
 *
 * @author jarnomata
 */
public abstract class Tiedosto {
    
    protected String tiedosto_nimi;
    protected int koko_tavua;
    
    public Tiedosto(String nimi, int koko) throws IllegalArgumentException {
        if (koko < 1 || nimi.isEmpty()) {
            throw new IllegalArgumentException("Tiedostonimi tai koko ei kelpaa");
        } else {
            this.koko_tavua = koko;
        }
        this.tiedosto_nimi = nimi;
    }
    
    @Getteri
    public String getTiedostonimi() {
        return this.tiedosto_nimi;
    }
    
    @Getteri
    public int getKokoTavuina() {
        return this.koko_tavua;
    }
    
    @Setteri
    public void setNimi(String nimi) throws IllegalArgumentException {
        if (nimi.isEmpty()) {
            throw new IllegalArgumentException("Annettu tiedostonimi ei kelpaa");
        } else {
            this.tiedosto_nimi = nimi;
        }
    }
    
    @Setteri
    public void setKoko(int koko) throws IllegalArgumentException {
        if (koko < 1) {
            throw new IllegalArgumentException("Annettu tiedostokoko ei kelpaa");
        } else {
            this.koko_tavua = koko;
        }
    }
    
    @Override
    public String toString() {
        return this.tiedosto_nimi + " " + this.koko_tavua + " B";
    }
}
