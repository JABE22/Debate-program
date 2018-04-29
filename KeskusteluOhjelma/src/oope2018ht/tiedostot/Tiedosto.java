
package oope2018ht.tiedostot;

import oope2018ht.apulaiset.Getteri;
import oope2018ht.apulaiset.Setteri;

/**
 *
 * @author Jarno Matarmaa
 */
public abstract class Tiedosto {
    
    private String tiedosto_nimi;
    private int koko_tavua;
    
    /**
     *
     * @param nimi
     * @param koko
     * @throws IllegalArgumentException
     */
    public Tiedosto(String nimi, int koko) throws IllegalArgumentException {
        if (koko < 1 || nimi == null) {
            throw new IllegalArgumentException();
        } else {
            this.koko_tavua = koko;
        }
        this.tiedosto_nimi = nimi;
    }
    
    /**
     *
     * @return
     */
    @Getteri
    public String getTiedostonimi() {
        return this.tiedosto_nimi;
    }
    
    /**
     *
     * @return
     */
    @Getteri
    public int getKokoTavuina() {
        return this.koko_tavua;
    }
    
    /**
     *
     * @param nimi
     * @throws IllegalArgumentException
     */
    @Setteri
    public void setNimi(String nimi) throws IllegalArgumentException {
        if (nimi.isEmpty()) {
            throw new IllegalArgumentException();
        } else {
            this.tiedosto_nimi = nimi;
        }
    }
    
    /**
     *
     * @param koko
     * @throws IllegalArgumentException
     */
    @Setteri
    public void setKoko(int koko) throws IllegalArgumentException {
        if (koko < 1) {
            throw new IllegalArgumentException();
        } else {
            this.koko_tavua = koko;
        }
    }
    
    @Override
    public String toString() {
        return this.tiedosto_nimi + " " + this.koko_tavua + " B";
    }
}
