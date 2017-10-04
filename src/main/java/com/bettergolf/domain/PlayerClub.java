package com.bettergolf.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A PlayerClub.
 */
@Entity
@Table(name = "player_club")
public class PlayerClub implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "jhi_type", nullable = false)
    private String type;

    @NotNull
    @Column(name = "jhi_label", nullable = false)
    private String label;

    @ManyToOne
    private Player player;

    @OneToMany(mappedBy = "playerClub")
    @JsonIgnore
    private Set<Shot> shots = new HashSet<>();

    @OneToMany(mappedBy = "playerClub")
    @JsonIgnore
    private Set<Calibration> isCalibrateds = new HashSet<>();

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public PlayerClub type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLabel() {
        return label;
    }

    public PlayerClub label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Player getPlayer() {
        return player;
    }

    public PlayerClub player(Player player) {
        this.player = player;
        return this;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Set<Shot> getShots() {
        return shots;
    }

    public PlayerClub shots(Set<Shot> shots) {
        this.shots = shots;
        return this;
    }

    public PlayerClub addShots(Shot shot) {
        this.shots.add(shot);
        shot.setPlayerClub(this);
        return this;
    }

    public PlayerClub removeShots(Shot shot) {
        this.shots.remove(shot);
        shot.setPlayerClub(null);
        return this;
    }

    public void setShots(Set<Shot> shots) {
        this.shots = shots;
    }

    public Set<Calibration> getIsCalibrateds() {
        return isCalibrateds;
    }

    public PlayerClub isCalibrateds(Set<Calibration> calibrations) {
        this.isCalibrateds = calibrations;
        return this;
    }

    public PlayerClub addIsCalibrated(Calibration calibration) {
        this.isCalibrateds.add(calibration);
        calibration.setPlayerClub(this);
        return this;
    }

    public PlayerClub removeIsCalibrated(Calibration calibration) {
        this.isCalibrateds.remove(calibration);
        calibration.setPlayerClub(null);
        return this;
    }

    public void setIsCalibrateds(Set<Calibration> calibrations) {
        this.isCalibrateds = calibrations;
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
        PlayerClub playerClub = (PlayerClub) o;
        if (playerClub.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), playerClub.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PlayerClub{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", label='" + getLabel() + "'" +
            "}";
    }
}
