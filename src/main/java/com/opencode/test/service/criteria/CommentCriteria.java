package com.opencode.test.service.criteria;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.opencode.test.domain.Comment} entity. This class is used
 * in {@link com.opencode.test.web.rest.CommentResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /comments?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CommentCriteria implements Serializable, Criteria {

    @Serial
    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter creationDate;

    private StringFilter commentText;

    private BooleanFilter isOffensive;

    private LongFilter appuserId;

    private LongFilter postId;

    private Boolean distinct;

    public CommentCriteria() {}

    public CommentCriteria(CommentCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.creationDate = other.optionalCreationDate().map(InstantFilter::copy).orElse(null);
        this.commentText = other.optionalCommentText().map(StringFilter::copy).orElse(null);
        this.isOffensive = other.optionalIsOffensive().map(BooleanFilter::copy).orElse(null);
        this.appuserId = other.optionalAppuserId().map(LongFilter::copy).orElse(null);
        this.postId = other.optionalPostId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public CommentCriteria copy() {
        return new CommentCriteria(this);
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

    public StringFilter getCommentText() {
        return commentText;
    }

    public Optional<StringFilter> optionalCommentText() {
        return Optional.ofNullable(commentText);
    }

    public StringFilter commentText() {
        if (commentText == null) {
            setCommentText(new StringFilter());
        }
        return commentText;
    }

    public void setCommentText(StringFilter commentText) {
        this.commentText = commentText;
    }

    public BooleanFilter getIsOffensive() {
        return isOffensive;
    }

    public Optional<BooleanFilter> optionalIsOffensive() {
        return Optional.ofNullable(isOffensive);
    }

    public BooleanFilter isOffensive() {
        if (isOffensive == null) {
            setIsOffensive(new BooleanFilter());
        }
        return isOffensive;
    }

    public void setIsOffensive(BooleanFilter isOffensive) {
        this.isOffensive = isOffensive;
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

    public LongFilter getPostId() {
        return postId;
    }

    public Optional<LongFilter> optionalPostId() {
        return Optional.ofNullable(postId);
    }

    public LongFilter postId() {
        if (postId == null) {
            setPostId(new LongFilter());
        }
        return postId;
    }

    public void setPostId(LongFilter postId) {
        this.postId = postId;
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
        final CommentCriteria that = (CommentCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(creationDate, that.creationDate) &&
            Objects.equals(commentText, that.commentText) &&
            Objects.equals(isOffensive, that.isOffensive) &&
            Objects.equals(appuserId, that.appuserId) &&
            Objects.equals(postId, that.postId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, creationDate, commentText, isOffensive, appuserId, postId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CommentCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalCreationDate().map(f -> "creationDate=" + f + ", ").orElse("") +
            optionalCommentText().map(f -> "commentText=" + f + ", ").orElse("") +
            optionalIsOffensive().map(f -> "isOffensive=" + f + ", ").orElse("") +
            optionalAppuserId().map(f -> "appuserId=" + f + ", ").orElse("") +
            optionalPostId().map(f -> "postId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
