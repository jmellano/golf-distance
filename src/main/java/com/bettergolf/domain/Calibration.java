package com.bettergolf.domain;


import com.bettergolf.repository.ShotRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.inject.Inject;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;
import java.util.OptionalDouble;

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
    @Column(name = "jhi_force", precision = 10, scale = 2, nullable = false)
    private BigDecimal force;

    @Column(name = "average", precision = 10, scale = 2)
    private BigDecimal average;

    @Column(name = "standard_deviation", precision = 10, scale = 2)
    private BigDecimal standardDeviation;

    @Transient
    @JsonProperty
    private String standardDeviationResult;

    @ManyToOne
    @JsonIgnore
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


    public String getStandardDeviationResult() {
        if (standardDeviation.compareTo(BigDecimal.ONE) <0 ) {
           return StandardDeviationResultEnum.EXCEPTIONNAL.toString();
        }
        if (standardDeviation.compareTo(new BigDecimal(2)) <0 ) {
            return StandardDeviationResultEnum.GOOD.toString();
        }
        if (standardDeviation.compareTo(new BigDecimal(3)) <0 ) {
            return StandardDeviationResultEnum.NORMAL.toString();
        }
        if (standardDeviation.compareTo(new BigDecimal(4)) <0 ) {
            return StandardDeviationResultEnum.BAD.toString();
        }
        else {
            return StandardDeviationResultEnum.AWFUL.toString();
        }
    }

    public void setStandardDeviationResult(String standardDeviationResult) {
        this.standardDeviationResult = standardDeviationResult;
    }

    public void updateCalibrationPlayerClub(List<Shot> shots) {

        final double average = shots.stream().mapToDouble(a -> a.getDistance().doubleValue()).average().orElse(0);
        final double rawSum =
            shots.stream().mapToDouble((x) -> Math.pow(x.getDistance().doubleValue() - average,
                    2.0))
                .sum();
        if(shots.size() > 1){
            final double standardDeviation = Math.sqrt(rawSum / (shots.size() - 1));
            this.standardDeviation = new BigDecimal(standardDeviation, MathContext.DECIMAL64).setScale(2, RoundingMode.HALF_UP);
        }

        this.average = new BigDecimal(average, MathContext.DECIMAL64);
    }


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
