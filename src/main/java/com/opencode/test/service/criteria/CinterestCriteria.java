package com.opencode.test.service.criteria;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.opencode.test.domain.Cinterest} entity. This class is used
 * in {@link com.opencode.test.web.rest.CinterestResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /cinterests?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CinterestCriteria implements Serializable, Criteria {

    @Serial
    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter interestName;

    private LongFilter communityId;

    private Boolean distinct;

    public CinterestCriteria() {}

    public CinterestCriteria(CinterestCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.interestName = other.optionalInterestName().map(StringFilter::copy).orElse(null);
        this.communityId = other.optionalCommunityId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public CinterestCriteria copy() {
        return new CinterestCriteria(this);
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

    public StringFilter getInterestName() {
        return interestName;
    }

    public Optional<StringFilter> optionalInterestName() {
        return Optional.ofNullable(interestName);
    }

    public StringFilter interestName() {
        if (interestName == null) {
            setInterestName(new StringFilter());
        }
        return interestName;
    }

    public void setInterestName(StringFilter interestName) {
        this.interestName = interestName;
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
        final CinterestCriteria that = (CinterestCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(interestName, that.interestName) &&
            Objects.equals(communityId, that.communityId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, interestName, communityId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CinterestCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalInterestName().map(f -> "interestName=" + f + ", ").orElse("") +
            optionalCommunityId().map(f -> "communityId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
