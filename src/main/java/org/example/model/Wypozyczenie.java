package org.example.model;

import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import org.example.exceptions.WypozyczenieException;
import java.util.Date;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;
@Entity
@CqlName("wypozyczenie")
public class Wypozyczenie {

    private UUID wypozyczenieId;

    private Wypozyczajacy wypozyczajacy;

    private Wolumin wolumin;

    private Date dataOd;

    private Date dataDo;

    //private UUID uuid;

    // Konstruktor z walidacją
    public Wypozyczenie(Wypozyczajacy wypozyczajacy, Wolumin wolumin) {
        if (wypozyczajacy == null) {
            throw new WypozyczenieException("Błędny wypożyczający");
        }
        if (wolumin == null) {
            throw new WypozyczenieException("Błędny wolumin");
        }
        this.wypozyczenieId = UUID.randomUUID();
        this.wypozyczajacy = wypozyczajacy;
        this.wolumin = wolumin;
        this.dataOd = new Date();  // Ustawienie daty początkowej na teraz
        //this.uuid = UUID.randomUUID();  // Generowanie UUID
    }
    public Wypozyczenie() {}

    public UUID getWypozyczenieId() {
        return wypozyczenieId;
    }

    public void setWypozyczenieId(UUID id) {
        this.wypozyczenieId = id;
    }

    // Gettery
    public Wypozyczajacy getWypozyczajacy() {
        return wypozyczajacy;
    }

    public Wolumin getWolumin() {
        return wolumin;
    }

    public Date getDataOd() {
        return dataOd;
    }

    public Date getDataDo() {
        return dataDo;
    }


    public void setDataOd(Date dataOd) {
        this.dataOd = dataOd;
    }

    public void setDataDo(Date dataDo) {
        this.dataDo = dataDo;
    }

    /*public UUID getUuid() {
        return uuid;
    }*/

    // Settery z walidacją
    public void setWypozyczajacy(Wypozyczajacy wypozyczajacy) {
        if (wypozyczajacy == null) {
            throw new WypozyczenieException("Błędny wypożyczający");
        }
        this.wypozyczajacy = wypozyczajacy;
    }

    public void setWolumin(Wolumin wolumin) {
        if (wolumin == null) {
            throw new WypozyczenieException("Błędny wolumin");
        }
        this.wolumin = wolumin;
    }

    // Zakończenie wypożyczenia - ustawienie daty zwrotu
    public void koniecWypozyczenia() {
        this.dataDo = new Date();
    }

    // Obliczanie długości wypożyczenia w dniach
    public double dlugoscWypozyczenia() {
        if (dataDo == null) {
            return 0;
        }
        long diff = dataDo.getTime() - dataOd.getTime();
        return diff / (1000.0 * 60 * 60 * 24);
    }

    // Obliczanie kary na podstawie długości wypożyczenia
    public double obliczKare() {
        return wypozyczajacy.getTypWypozyczajacy().getKara() * dlugoscWypozyczenia();
    }
}
