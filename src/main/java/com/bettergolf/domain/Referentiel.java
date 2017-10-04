package com.bettergolf.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Referentiel.
 */
@Entity
@Table(name = "referentiel")
public class Referentiel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "libelle")
    private String libelle;

    @Column(name = "ordre")
    private Integer ordre;

    @ManyToOne
    private TypeReferentiel typeReferentiel;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public Referentiel code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelle() {
        return libelle;
    }

    public Referentiel libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Integer getOrdre() {
        return ordre;
    }

    public Referentiel ordre(Integer ordre) {
        this.ordre = ordre;
        return this;
    }

    public void setOrdre(Integer ordre) {
        this.ordre = ordre;
    }

    public TypeReferentiel getTypeReferentiel() {
        return typeReferentiel;
    }

    public Referentiel typeReferentiel(TypeReferentiel typeReferentiel) {
        this.typeReferentiel = typeReferentiel;
        return this;
    }

    public void setTypeReferentiel(TypeReferentiel typeReferentiel) {
        this.typeReferentiel = typeReferentiel;
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
        Referentiel referentiel = (Referentiel) o;
        if (referentiel.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), referentiel.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Referentiel{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", libelle='" + getLibelle() + "'" +
            ", ordre='" + getOrdre() + "'" +
            "}";
    }
}
