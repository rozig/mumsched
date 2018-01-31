package mumsched.entity;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class Entry {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "{notBlank.message}")
    private String name;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;

    private int mppNumber;

    private int fppNumber;

    private int optNumber;

    @Override
    public String toString() {
        return getName();
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMppNumber() {
        return mppNumber;
    }

    public void setMppNumber(int mppNumber) {
        this.mppNumber = mppNumber;
    }

    public int getFppNumber() {
        return fppNumber;
    }

    public void setFppNumber(int fppNumber) {
        this.fppNumber = fppNumber;
    }

    public int getOptNumber() {
        return optNumber;
    }

    public void setOptNumber(int optNumber) {
        this.optNumber = optNumber;
    }
}
