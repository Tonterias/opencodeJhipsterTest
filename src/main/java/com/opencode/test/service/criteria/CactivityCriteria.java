package com.opencode.test.service.criteria;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.opencode.test.domain.Cactivity} entity. This class is used
 * in {@link com.opencode.test.web.rest.CactivityResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /cactivities?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CactivityCriteria implements Serializable, Criteria {

    @Serial
    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter activityName;

    private LongFilter communityId;

    private Boolean distinct;

    public CactivityCriteria() {}

    public CactivityCriteria(CactivityCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.activityName = other.optionalActivityName().map(StringFilter::copy).orElse(null);
        this.communityId = other.optionalCommunityId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public CactivityCriteria copy() {
        return new CactivityCriteria(this);
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

    public StringFilter getActivityName() {
        return activityName;
    }

    public Optional<StringFilter> optionalActivityName() {
        return Optional.ofNullable(activityName);
    }

    public StringFilter activityName() {
        if (activityName == null) {
            setActivityName(new StringFilter());
        }
        return activityName;
    }

    public void setActivityName(StringFilter activityName) {
        this.activityName = activityName;
    }

    public LongFilter getCommunityId() {
        return communityId;
    }

    public Optional<LongFilter> optionalCommunityId() {
        return Optional.ofNullable(communityId);
    }

    public LongFilter communityId() {
        if (communityId == null) {
            setCommunityId(new LongFilter());
        }
        return communityId;
    }

    public void setCommunityId(LongFilter communityId) {
        this.communityId = communityId;
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
        final CactivityCriteria that = (CactivityCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(activityName, that.activityName) &&
            Objects.equals(communityId, that.communityId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, activityName, communityId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CactivityCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalActivityName().map(f -> "activityName=" + f + ", ").orElse("") +
            optionalCommunityId().map(f -> "communityId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
