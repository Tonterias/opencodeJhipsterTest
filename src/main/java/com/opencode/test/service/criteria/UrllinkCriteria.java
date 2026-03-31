package com.opencode.test.service.criteria;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.opencode.test.domain.Urllink} entity. This class is used
 * in {@link com.opencode.test.web.rest.UrllinkResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /urllinks?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UrllinkCriteria implements Serializable, Criteria {

    @Serial
    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter linkText;

    private StringFilter linkURL;

    private Boolean distinct;

    public UrllinkCriteria() {}

    public UrllinkCriteria(UrllinkCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.linkText = other.optionalLinkText().map(StringFilter::copy).orElse(null);
        this.linkURL = other.optionalLinkURL().map(StringFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public UrllinkCriteria copy() {
        return new UrllinkCriteria(this);
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

    public StringFilter getLinkText() {
        return linkText;
    }

    public Optional<StringFilter> optionalLinkText() {
        return Optional.ofNullable(linkText);
    }

    public StringFilter linkText() {
        if (linkText == null) {
            setLinkText(new StringFilter());
        }
        return linkText;
    }

    public void setLinkText(StringFilter linkText) {
        this.linkText = linkText;
    }

    public StringFilter getLinkURL() {
        return linkURL;
    }

    public Optional<StringFilter> optionalLinkURL() {
        return Optional.ofNullable(linkURL);
    }

    public StringFilter linkURL() {
        if (linkURL == null) {
            setLinkURL(new StringFilter());
        }
        return linkURL;
    }

    public void setLinkURL(StringFilter linkURL) {
        this.linkURL = linkURL;
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
        final UrllinkCriteria that = (UrllinkCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(linkText, that.linkText) &&
            Objects.equals(linkURL, that.linkURL) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, linkText, linkURL, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UrllinkCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalLinkText().map(f -> "linkText=" + f + ", ").orElse("") +
            optionalLinkURL().map(f -> "linkURL=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
