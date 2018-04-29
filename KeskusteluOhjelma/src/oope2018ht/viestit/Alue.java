package oope2018ht.viestit;

import oope2018ht.omalista.OmaLista;
import oope2018ht.tiedostot.Tiedosto;

/**
 *
 * @author Jarno Matarmaa
 *
 * Alue -luokka on ohjelman pää-luokka, jonka kautta hallitaan koko viestialueen
 * toimintoja. Alue kapseloi OmaListan joka sisältää alueen kaikki ketjut. Tämä
 * luokka vastaa osittain myös tulostuksista, jotka kohdistuvat aktiiviseen
 * ketjuun (Vain "Error!" tulostuksia).
 *
 */
public class Alue {

    private final OmaLista viestiketjut;
    private Ketju aktiv_vk; // Viite alueen aktiivisena olevaan viestiin
    
    // Näitä saa käyttää ainoastaan getterillä
    private int ketju_id;  // Ketjutunnisteen generointiin
    private int viesti_id; // Viestitunnisteen generointiin

    /**
     * Konstruktori alustaa OmaLista -tyyppisen olion, jonne lisätään
     * viestiketjuja. Aktiivinen viestiketju (Ketju -olio) on aluksi null ja ja
     * omalista on tyhjä.
     */
    public Alue() {
        this.viestiketjut = new OmaLista();
        this.aktiv_vk = null;
        
        this.ketju_id = 1;
        this.viesti_id = 1;
    }

    /**
     *
     * @return aktiivisen ketjun viestien lukumäärä
     */
    public int getAktivViestiLkm() {
        if (this.aktiv_vk != null) {
            return this.aktiv_vk.vastauksia();
        } else {
            return 0;
        }
    }
    
    /**
     * Ketju ID laskuri generoi tunnuksen uusille ketjuille. Uuden ketjun tunnus
     * on viimeisimmän lisätyn ketjun tunnus + 1.
     * 
     * @return generoitu uusi uniikki tunnus ketju_id
     */
    private int generoiKetjuID() {
        return this.ketju_id++;
    }
    
    /**
     * Viesti ID laskuri generoi tunnuksen uusille viesteille. Uuden viestin 
     * tunnus on viimeisimmän lisätyn viestin tunnus + 1.
     * 
     * @return generoitu uusi uniikki tunnus viesti_id
     */
    private int generoiViestiID() {
        return this.viesti_id++;
    }
    
    /**
     * Hakee parametrina annettua tunnistetta vastaavan ketjun luokkamuutujan
     * ketjut varastoivalta (this.viestiketjut) OmaListalta.
     * 
     * @param ketju_id tunniste jolla haetaan ketjua
     * @return palauttaa ketjun jos löytyi, muuten null
     */
    private Ketju haeKetju(int ketju_id) {
        int indeksi = 0;
        while (indeksi < this.viestiketjut.koko()) {
            Ketju ketju = (Ketju)this.viestiketjut.alkio(indeksi);
            if (ketju.getTunniste() == ketju_id) {
                return ketju;
            }
            indeksi++;
        }
        return null;
    }

    /**
     * Lisää uuden ketjun ja ensimmäisen viestin. (Ketju == 1. viesti)
     *
     * @param teksti ketjun aiheteksti
     * @param tiedosto aloitusviestiin/aiheeseen liittyvä mahdollinen tiedosto
     * voi olla null.
     */
    public void lisaaKetju(String teksti, Tiedosto tiedosto) {
        // Toistaiseksi kuitataan tiedostot null -arvolla
        Viesti aloitusviesti = new Viesti(generoiKetjuID(), teksti, null, tiedosto);
        Ketju viestiketju = new Ketju(aloitusviesti);
        this.viestiketjut.lisaa(viestiketju);
        if (this.aktiv_vk == null) {
            this.aktiv_vk = viestiketju;
        }
    }

    /**
     * Lisää uuden viestin ketjuun (EI vastausviesti, komento "new" käyttää
     * tätä).
     *
     * @param teksti uuden viestin teksti
     * @param tiedosto mahdollinen uusi tiedosto. Voi olla myös null
     */
    public void lisaaViesti(String teksti, Tiedosto tiedosto) {
        if (this.aktiv_vk != null && this.viestiketjut.koko() > 0) {
            this.aktiv_vk.lisaaViesti(generoiViestiID(), teksti, tiedosto);
        } else {
            this.viesti_id--; // Pitää muistaa aina palauttaa tunniste
            System.out.println("Error!");
        }
    }

    /**
     * Lisää vastauksen aiemmin luotuun viestiin (komento "reply").
     *
     * @param vastattava_id
     * @param teksti
     * @param tiedosto
     * @return palautaa true, jos vastauksen lisääminen onnistuu ja false, jos
     * epäonnistuu
     */
    public boolean lisaaVastaus(int vastattava_id, String teksti, Tiedosto tiedosto) {
        if (this.aktiv_vk == null || this.aktiv_vk.vastauksia() == 0) {
            return false;
        }  else {
            if (!aktiv_vk.lisaaVastaus(generoiViestiID(), vastattava_id, 
                                                            teksti, tiedosto)) {
                this.viesti_id--;
                return false;
            }
            return true;
        } 
    }

    /**
     * Palautaa viestiketjun kapseloiman viesti -olion halutusta paikasta.
     *
     * @param paikka indeksi / tunniste haettavalla ketjulle
     * @return indeksiä vastaavan ketjun kapseloima aloitus/aihe -viesti
     */
    public Viesti getViestiketju(int paikka) {
        if (this.viestiketjut.koko() > 0) {
            return (Viesti) this.viestiketjut.alkio(paikka);
        }
        return null;
    }

    /**
     * Aktivoi parametria vastaavan ketjun.
     *
     * @param ketju_nro omalistan ketjuindeksi
     */
    public void aktivoiKetju(int ketju_nro) {
        Ketju aktivoitava = haeKetju(ketju_nro);
        if (aktivoitava != null) {
            this.aktiv_vk = aktivoitava;
        } else {
            System.out.println("Error!");
        }
    }

    /**
     * Tyhjentää aktiivisen viestiketjun viestin, joka vastaa metodin
     * parametria.
     *
     * @param id tyhjennettävän viestin tunniste (käytetään myös indeksinä).
     */
    public void tyhjennaViesti(int id) {
        if (this.aktiv_vk != null) {
            if (!this.aktiv_vk.tyhjennaViesti(id)) {
                System.out.println("Error!");
            }          
        } else {
            System.out.println("Error!");
        }
    }

    /**
     * Kokoa listan, joka sisältää kaikki viestit, joissa hakusana esiintyy.
     * Tulostaa lopuksi listan käyttäen apumetodia.
     *
     * @param hakusana merkkijono, jota verrataan viestien sisältöön
     */
    public void tulostaHakusanalla(String hakusana) {
        if (this.aktiv_vk != null) {
            // Uusi lista osumille ja hakualueelle
            OmaLista loydetyt = new OmaLista();
            OmaLista hakualue = this.aktiv_vk.getVastaukset(); // Siisti koodi

            if (hakualue.koko() > 0) {

                int v_index = 0;
                while (v_index < hakualue.koko()) {
                    // Napataan viesti muuttujaan jotta vältetään luettavuusongelmat
                    // Menetetään tosin metodin universaalius (virhe jos muunnos ei
                    // onnistu)
                    Viesti viesti = (Viesti) hakualue.alkio(v_index);

                    if (viesti.toString().contains(hakusana)) {
                        loydetyt.lisaa(viesti); // Jos match, lisätään listalle
                    }
                    v_index++;
                }
            }
            // Tulostetaan lopuksi löydetyt käyttäen apumetodia
            tulostaLista(loydetyt);
        }
    }

    /* Loput ovat tulostusmetodeita. */
    /**
     * Aktiivisen ketjun tulostus listana
     */
    public void tulostaAktiivinenKetjuListana() {
        if (this.aktiv_vk != null) {
            this.aktiv_vk.tulostaListana();
        }
    }

    /**
     * Aktiivisen ketjun tulostus hierarkkisesti
     */
    public void tulostaAktiivinenKetjuPuuna() {
        if (this.aktiv_vk != null) {
            this.aktiv_vk.tulostaPuunaV2A();
        }
    }

    /**
     * Aktiivisen viestiketjun vanhimpien viestien tulostus. Virheelliset arvot
     * käsitellään tulostaLista() -metodissa, jota tässä kutsutaan.
     *
     * @param lkm tulostettavien viestien lukumäärä
     */
    public void tulostaAktiivisenKetjunVanhat(int lkm) {
        if (this.aktiv_vk != null) {
            if (lkm > 0 && lkm <= this.aktiv_vk.vastauksia()) {
                tulostaLista(this.aktiv_vk.getVanhatViestit(lkm));
            } else {
                System.out.println("Error!");
            }
        }
    }

    /**
     * Aktiivisen viestiketjun uusimpien viestien tulostus. Virheelliset arvot
     * käsitellään tulostaLista() -metodissa, jota tässä kutsutaan
     *
     * @param lkm tulostettavien viestien lukumäärä
     */
    public void tulostaAktiivisenKetjunUudet(int lkm) {
        if (this.aktiv_vk != null) {
            if (lkm > 0 && lkm <= this.aktiv_vk.vastauksia()) {
                tulostaLista(this.aktiv_vk.getUudetViestit(lkm));
            } else {
                System.out.println("Error!");
            }
        }
    }

    /**
     * Esivalitun (Metodin parametri) listan tulostus. Käytetään yleismetodina
     * kaikkeen OmaLista -tyyppisten olioiden tulostamiseen listana muissa
     * metodeissa
     *
     * @param lista tulostettava omalista -olio
     */
    public void tulostaLista(OmaLista lista) {
        if (lista != null) {
            int indeksi = 0;
            while (indeksi < lista.koko()) {
                System.out.println(lista.haeSolmu(indeksi));
                indeksi++;
            }

//            Solmu solmu = lista.haeSolmu(0);
//            while (solmu != null) {
//                System.out.println(solmu);
//                solmu = solmu.seuraava();
//            }
        }
        // Tähän voidaan lisätä Error tulostus myöhemmin, jos tarpeen
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        int ketju_index = 0;
        while (ketju_index < this.viestiketjut.koko()) {
            result.append(this.viestiketjut.alkio(ketju_index));
            result.append("\n");
            ketju_index++;
        }

        return result.toString();
    }
}
