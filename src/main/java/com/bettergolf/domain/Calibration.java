package com.bettergolf.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A Calibration.
 */
@Entity
@Table(name = "calibration")
public class Calibration implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "jhi_force", precision=10, scale=2, nullable = false)
    private BigDecimal force;

    @Column(name = "average", precision=10, scale=2)
    private BigDecimal average;

    @Column(name = "standard_deviation", precision=10, scale=2)
    private BigDecimal standardDeviation;

    @ManyToOne
    private PlayerClub playerClub;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getForce() {
        return force;
    }

    public Calibration force(BigDecimal force) {
        this.force = force;
        return this;
    }

    public void setForce(BigDecimal force) {
        this.force = force;
    }

    public BigDecimal getAverage() {
        return average;
    }

    public Calibration average(BigDecimal average) {
        this.average = average;
        return this;
    }

    public void setAverage(BigDecimal average) {
        this.average = average;
    }

    public BigDecimal getStandardDeviation() {
        return standardDeviation;
    }

    public Calibration standardDeviation(BigDecimal standardDeviation) {
        this.standardDeviation = standardDeviation;
        return this;
    }

    public void setStandardDeviation(BigDecimal standardDeviation) {
        this.standardDeviation = standardDeviation;
    }

    public PlayerClub getPlayerClub() {
        return playerClub;
    }

    public Calibration playerClub(PlayerClub playerClub) {
        this.playerClub = playerClub;
        return this;
    }

    public void setPlayerClub(PlayerClub playerClub) {
        this.playerClub = playerClub;
    }
    // jhipster-needle-entity-add-getters-setters - Jhipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Calibration calibration = (Calibration) o;
        if (calibration.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), calibration.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Calibration{" +
            "id=" + getId() +
            ", force='" + getForce() + "'" +
            ", average='" + getAverage() + "'" +
            ", standardDeviation='" + getStandardDeviation() + "'" +
            "}";
    }
}
