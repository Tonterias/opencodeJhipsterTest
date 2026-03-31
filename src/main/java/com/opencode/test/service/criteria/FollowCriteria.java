package com.opencode.test.service.criteria;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.opencode.test.domain.Follow} entity. This class is used
 * in {@link com.opencode.test.web.rest.FollowResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /follows?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FollowCriteria implements Serializable, Criteria {

    @Serial
    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter creationDate;

    private LongFilter followedId;

    private LongFilter followingId;

    private LongFilter cfollowedId;

    private LongFilter cfollowingId;

    private Boolean distinct;

    public FollowCriteria() {}

    public FollowCriteria(FollowCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.creationDate = other.optionalCreationDate().map(InstantFilter::copy).orElse(null);
        this.followedId = other.optionalFollowedId().map(LongFilter::copy).orElse(null);
        this.followingId = other.optionalFollowingId().map(LongFilter::copy).orElse(null);
        this.cfollowedId = other.optionalCfollowedId().map(LongFilter::copy).orElse(null);
        this.cfollowingId = other.optionalCfollowingId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public FollowCriteria copy() {
        return new FollowCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public Optional<LongFilter> optionalId() {
        return Optional.ofNullable(id);
    }

    public LongFilter id() {
        if (id == null) {
            setId(new LongFilter());
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public InstantFilter getCreationDate() {
        return creationDate;
    }

    public Optional<InstantFilter> optionalCreationDate() {
        return Optional.ofNullable(creationDate);
    }

    public InstantFilter creationDate() {
        if (creationDate == null) {
            setCreationDate(new InstantFilter());
        }
        return creationDate;
    }

    public void setCreationDate(InstantFilter creationDate) {
        this.creationDate = creationDate;
    }

    public LongFilter getFollowedId() {
        return followedId;
    }

    public Optional<LongFilter> optionalFollowedId() {
        return Optional.ofNullable(followedId);
    }

    public LongFilter followedId() {
        if (followedId == null) {
            setFollowedId(new LongFilter());
        }
        return followedId;
    }

    public void setFollowedId(LongFilter followedId) {
        this.followedId = followedId;
    }

    public LongFilter getFollowingId() {
        return followingId;
    }

    public Optional<LongFilter> optionalFollowingId() {
        return Optional.ofNullable(followingId);
    }

    public LongFilter followingId() {
        if (followingId == null) {
            setFollowingId(new LongFilter());
        }
        return followingId;
    }

    public void setFollowingId(LongFilter followingId) {
        this.followingId = followingId;
    }

    public LongFilter getCfollowedId() {
        return cfollowedId;
    }

    public Optional<LongFilter> optionalCfollowedId() {
        return Optional.ofNullable(cfollowedId);
    }

    public LongFilter cfollowedId() {
        if (cfollowedId == null) {
            setCfollowedId(new LongFilter());
        }
        return cfollowedId;
    }

    public void setCfollowedId(LongFilter cfollowedId) {
        this.cfollowedId = cfollowedId;
    }

    public LongFilter getCfollowingId() {
        return cfollowingId;
    }

    public Optional<LongFilter> optionalCfollowingId() {
        return Optional.ofNullable(cfollowingId);
    }

    public LongFilter cfollowingId() {
        if (cfollowingId == null) {
            setCfollowingId(new LongFilter());
        }
        return cfollowingId;
    }

    public void setCfollowingId(LongFilter cfollowingId) {
        this.cfollowingId = cfollowingId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public Optional<Boolean> optionalDistinct() {
        return Optional.ofNullable(distinct);
    }

    public Boolean distinct() {
        if (distinct == null) {
            setDistinct(true);
        }
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final FollowCriteria that = (FollowCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(creationDate, that.creationDate) &&
            Objects.equals(followedId, that.followedId) &&
            Objects.equals(followingId, that.followingId) &&
            Objects.equals(cfollowedId, that.cfollowedId) &&
            Objects.equals(cfollowingId, that.cfollowingId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, creationDate, followedId, followingId, cfollowedId, cfollowingId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FollowCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalCreationDate().map(f -> "creationDate=" + f + ", ").orElse("") +
            optionalFollowedId().map(f -> "followedId=" + f + ", ").orElse("") +
            optionalFollowingId().map(f -> "followingId=" + f + ", ").orElse("") +
            optionalCfollowedId().map(f -> "cfollowedId=" + f + ", ").orElse("") +
            optionalCfollowingId().map(f -> "cfollowingId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
