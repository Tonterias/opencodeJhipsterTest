package com.opencode.test.service.criteria;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.opencode.test.domain.Interest} entity. This class is used
 * in {@link com.opencode.test.web.rest.InterestResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /interests?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class InterestCriteria implements Serializable, Criteria {

    @Serial
    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter interestName;

    private LongFilter appuserId;

    private Boolean distinct;

    public InterestCriteria() {}

    public InterestCriteria(InterestCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.interestName = other.optionalInterestName().map(StringFilter::copy).orElse(null);
        this.appuserId = other.optionalAppuserId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public InterestCriteria copy() {
        return new InterestCriteria(this);
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

    public LongFilter getAppuserId() {
        return appuserId;
    }

    public Optional<LongFilter> optionalAppuserId() {
        return Optional.ofNullable(appuserId);
    }

    public LongFilter appuserId() {
        if (appuserId == null) {
            setAppuserId(new LongFilter());
        }
        return appuserId;
    }

    public void setAppuserId(LongFilter appuserId) {
        this.appuserId = appuserId;
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
        final InterestCriteria that = (InterestCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(interestName, that.interestName) &&
            Objects.equals(appuserId, that.appuserId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, interestName, appuserId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InterestCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalInterestName().map(f -> "interestName=" + f + ", ").orElse("") +
            optionalAppuserId().map(f -> "appuserId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
