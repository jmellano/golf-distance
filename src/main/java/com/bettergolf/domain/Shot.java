package com.bettergolf.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A Shot.
 */
@Entity
@Table(name = "shot")
public class Shot implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "jhi_force", precision=10, scale=2, nullable = false)
    private BigDecimal force;

    @NotNull
    @Column(name = "distance", precision=10, scale=2, nullable = false)
    private BigDecimal distance;

    @Column(name = "vent", precision=10, scale=2, nullable = false)
    private BigDecimal vent;

    @Column(name = "temperature", precision=10, scale=2, nullable = false)
    private BigDecimal temperature;

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

    public Shot force(BigDecimal force) {
        this.force = force;
        return this;
    }

    public void setForce(BigDecimal force) {
        this.force = force;
    }

    public BigDecimal getDistance() {
        return distance;
    }

    public Shot distance(BigDecimal distance) {
        this.distance = distance;
        return this;
    }

    public void setDistance(BigDecimal distance) {
        this.distance = distance;
    }

    public BigDecimal getVent() {
        return vent;
    }

    public void setVent(BigDecimal vent) {
        this.vent = vent;
    }

    public BigDecimal getTemperature() {
        return temperature;
    }

    public void setTemperature(BigDecimal temperature) {
        this.temperature = temperature;
    }

    public PlayerClub getPlayerClub() {
        return playerClub;
    }

    public Shot playerClub(PlayerClub playerClub) {
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
        Shot shot = (Shot) o;
        if (shot.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), shot.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Shot{" +
            "id=" + getId() +
            ", force='" + getForce() + "'" +
            ", distance='" + getDistance() + "'" +
            "}";
    }
}
