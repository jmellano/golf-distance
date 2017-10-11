package com.bettergolf.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A PlayerClub.
 */
@Entity
@Table(name = "player_club")
public class PlayerClubDistance extends PlayerClub {

    @OneToMany(mappedBy = "playerClub")
    @Fetch(FetchMode.JOIN)
    private Set<Calibration> distances = new HashSet<>();

}
