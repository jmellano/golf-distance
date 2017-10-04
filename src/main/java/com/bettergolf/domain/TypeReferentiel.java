package com.bettergolf.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A TypeReferentiel.
 */
@Entity
@Table(name = "type_referentiel")
public class TypeReferentiel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "trf_code", nullable = false)
    private String trfCode;

    @Column(name = "libelle")
    private String libelle;

    @OneToMany(mappedBy = "typeReferentiel")
    @JsonIgnore
    private Set<Referentiel> typeDeReferentiels = new HashSet<>();

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTrfCode() {
        return trfCode;
    }

    public TypeReferentiel trfCode(String trfCode) {
        this.trfCode = trfCode;
        return this;
    }

    public void setTrfCode(String trfCode) {
        this.trfCode = trfCode;
    }

    public String getLibelle() {
        return libelle;
    }

    public TypeReferentiel libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Set<Referentiel> getTypeDeReferentiels() {
        return typeDeReferentiels;
    }

    public TypeReferentiel typeDeReferentiels(Set<Referentiel> referentiels) {
        this.typeDeReferentiels = referentiels;
        return this;
    }

    public TypeReferentiel addTypeDeReferentiel(Referentiel referentiel) {
        this.typeDeReferentiels.add(referentiel);
        referentiel.setTypeReferentiel(this);
        return this;
    }

    public TypeReferentiel removeTypeDeReferentiel(Referentiel referentiel) {
        this.typeDeReferentiels.remove(referentiel);
        referentiel.setTypeReferentiel(null);
        return this;
    }

    public void setTypeDeReferentiels(Set<Referentiel> referentiels) {
        this.typeDeReferentiels = referentiels;
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
        TypeReferentiel typeReferentiel = (TypeReferentiel) o;
        if (typeReferentiel.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), typeReferentiel.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TypeReferentiel{" +
            "id=" + getId() +
            ", trfCode='" + getTrfCode() + "'" +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
