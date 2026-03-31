package com.opencode.test.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Cinterest.
 */
@Entity
@Table(name = "cinterest")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Cinterest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 2, max = 40)
    @Column(name = "interest_name", length = 40, nullable = false)
    private String interestName;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_cinterest__community",
        joinColumns = @JoinColumn(name = "cinterest_id"),
        inverseJoinColumns = @JoinColumn(name = "community_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "blogs", "cfolloweds", "cfollowings", "cblockedusers", "cblockingusers", "appuser", "cinterests", "cactivities", "ccelebs",
        },
        allowSetters = true
    )
    private Set<Community> communities = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Cinterest id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInterestName() {
        return this.interestName;
    }

    public Cinterest interestName(String interestName) {
        this.setInterestName(interestName);
        return this;
    }

    public void setInterestName(String interestName) {
        this.interestName = interestName;
    }

    public Set<Community> getCommunities() {
        return this.communities;
    }

    public void setCommunities(Set<Community> communities) {
        this.communities = communities;
    }

    public Cinterest communities(Set<Community> communities) {
        this.setCommunities(communities);
        return this;
    }

    public Cinterest addCommunity(Community community) {
        this.communities.add(community);
        return this;
    }

    public Cinterest removeCommunity(Community community) {
        this.communities.remove(community);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cinterest)) {
            return false;
        }
        return getId() != null && getId().equals(((Cinterest) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Cinterest{" +
            "id=" + getId() +
            ", interestName='" + getInterestName() + "'" +
            "}";
    }
}
