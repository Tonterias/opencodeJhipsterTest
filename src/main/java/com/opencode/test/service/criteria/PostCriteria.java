package com.opencode.test.service.criteria;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.opencode.test.domain.Post} entity. This class is used
 * in {@link com.opencode.test.web.rest.PostResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /posts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PostCriteria implements Serializable, Criteria {

    @Serial
    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter creationDate;

    private InstantFilter publicationDate;

    private StringFilter headline;

    private StringFilter leadtext;

    private StringFilter bodytext;

    private StringFilter quote;

    private StringFilter conclusion;

    private StringFilter linkText;

    private StringFilter linkURL;

    private LongFilter commentId;

    private LongFilter appuserId;

    private LongFilter blogId;

    private LongFilter tagId;

    private LongFilter topicId;

    private Boolean distinct;

    public PostCriteria() {}

    public PostCriteria(PostCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.creationDate = other.optionalCreationDate().map(InstantFilter::copy).orElse(null);
        this.publicationDate = other.optionalPublicationDate().map(InstantFilter::copy).orElse(null);
        this.headline = other.optionalHeadline().map(StringFilter::copy).orElse(null);
        this.leadtext = other.optionalLeadtext().map(StringFilter::copy).orElse(null);
        this.bodytext = other.optionalBodytext().map(StringFilter::copy).orElse(null);
        this.quote = other.optionalQuote().map(StringFilter::copy).orElse(null);
        this.conclusion = other.optionalConclusion().map(StringFilter::copy).orElse(null);
        this.linkText = other.optionalLinkText().map(StringFilter::copy).orElse(null);
        this.linkURL = other.optionalLinkURL().map(StringFilter::copy).orElse(null);
        this.commentId = other.optionalCommentId().map(LongFilter::copy).orElse(null);
        this.appuserId = other.optionalAppuserId().map(LongFilter::copy).orElse(null);
        this.blogId = other.optionalBlogId().map(LongFilter::copy).orElse(null);
        this.tagId = other.optionalTagId().map(LongFilter::copy).orElse(null);
        this.topicId = other.optionalTopicId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public PostCriteria copy() {
        return new PostCriteria(this);
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

    public InstantFilter getPublicationDate() {
        return publicationDate;
    }

    public Optional<InstantFilter> optionalPublicationDate() {
        return Optional.ofNullable(publicationDate);
    }

    public InstantFilter publicationDate() {
        if (publicationDate == null) {
            setPublicationDate(new InstantFilter());
        }
        return publicationDate;
    }

    public void setPublicationDate(InstantFilter publicationDate) {
        this.publicationDate = publicationDate;
    }

    public StringFilter getHeadline() {
        return headline;
    }

    public Optional<StringFilter> optionalHeadline() {
        return Optional.ofNullable(headline);
    }

    public StringFilter headline() {
        if (headline == null) {
            setHeadline(new StringFilter());
        }
        return headline;
    }

    public void setHeadline(StringFilter headline) {
        this.headline = headline;
    }

    public StringFilter getLeadtext() {
        return leadtext;
    }

    public Optional<StringFilter> optionalLeadtext() {
        return Optional.ofNullable(leadtext);
    }

    public StringFilter leadtext() {
        if (leadtext == null) {
            setLeadtext(new StringFilter());
        }
        return leadtext;
    }

    public void setLeadtext(StringFilter leadtext) {
        this.leadtext = leadtext;
    }

    public StringFilter getBodytext() {
        return bodytext;
    }

    public Optional<StringFilter> optionalBodytext() {
        return Optional.ofNullable(bodytext);
    }

    public StringFilter bodytext() {
        if (bodytext == null) {
            setBodytext(new StringFilter());
        }
        return bodytext;
    }

    public void setBodytext(StringFilter bodytext) {
        this.bodytext = bodytext;
    }

    public StringFilter getQuote() {
        return quote;
    }

    public Optional<StringFilter> optionalQuote() {
        return Optional.ofNullable(quote);
    }

    public StringFilter quote() {
        if (quote == null) {
            setQuote(new StringFilter());
        }
        return quote;
    }

    public void setQuote(StringFilter quote) {
        this.quote = quote;
    }

    public StringFilter getConclusion() {
        return conclusion;
    }

    public Optional<StringFilter> optionalConclusion() {
        return Optional.ofNullable(conclusion);
    }

    public StringFilter conclusion() {
        if (conclusion == null) {
            setConclusion(new StringFilter());
        }
        return conclusion;
    }

    public void setConclusion(StringFilter conclusion) {
        this.conclusion = conclusion;
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

    public LongFilter getCommentId() {
        return commentId;
    }

    public Optional<LongFilter> optionalCommentId() {
        return Optional.ofNullable(commentId);
    }

    public LongFilter commentId() {
        if (commentId == null) {
            setCommentId(new LongFilter());
        }
        return commentId;
    }

    public void setCommentId(LongFilter commentId) {
        this.commentId = commentId;
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

    public LongFilter getBlogId() {
        return blogId;
    }

    public Optional<LongFilter> optionalBlogId() {
        return Optional.ofNullable(blogId);
    }

    public LongFilter blogId() {
        if (blogId == null) {
            setBlogId(new LongFilter());
        }
        return blogId;
    }

    public void setBlogId(LongFilter blogId) {
        this.blogId = blogId;
    }

    public LongFilter getTagId() {
        return tagId;
    }

    public Optional<LongFilter> optionalTagId() {
        return Optional.ofNullable(tagId);
    }

    public LongFilter tagId() {
        if (tagId == null) {
            setTagId(new LongFilter());
        }
        return tagId;
    }

    public void setTagId(LongFilter tagId) {
        this.tagId = tagId;
    }

    public LongFilter getTopicId() {
        return topicId;
    }

    public Optional<LongFilter> optionalTopicId() {
        return Optional.ofNullable(topicId);
    }

    public LongFilter topicId() {
        if (topicId == null) {
            setTopicId(new LongFilter());
        }
        return topicId;
    }

    public void setTopicId(LongFilter topicId) {
        this.topicId = topicId;
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
        final PostCriteria that = (PostCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(creationDate, that.creationDate) &&
            Objects.equals(publicationDate, that.publicationDate) &&
            Objects.equals(headline, that.headline) &&
            Objects.equals(leadtext, that.leadtext) &&
            Objects.equals(bodytext, that.bodytext) &&
            Objects.equals(quote, that.quote) &&
            Objects.equals(conclusion, that.conclusion) &&
            Objects.equals(linkText, that.linkText) &&
            Objects.equals(linkURL, that.linkURL) &&
            Objects.equals(commentId, that.commentId) &&
            Objects.equals(appuserId, that.appuserId) &&
            Objects.equals(blogId, that.blogId) &&
            Objects.equals(tagId, that.tagId) &&
            Objects.equals(topicId, that.topicId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            creationDate,
            publicationDate,
            headline,
            leadtext,
            bodytext,
            quote,
            conclusion,
            linkText,
            linkURL,
            commentId,
            appuserId,
            blogId,
            tagId,
            topicId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PostCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalCreationDate().map(f -> "creationDate=" + f + ", ").orElse("") +
            optionalPublicationDate().map(f -> "publicationDate=" + f + ", ").orElse("") +
            optionalHeadline().map(f -> "headline=" + f + ", ").orElse("") +
            optionalLeadtext().map(f -> "leadtext=" + f + ", ").orElse("") +
            optionalBodytext().map(f -> "bodytext=" + f + ", ").orElse("") +
            optionalQuote().map(f -> "quote=" + f + ", ").orElse("") +
            optionalConclusion().map(f -> "conclusion=" + f + ", ").orElse("") +
            optionalLinkText().map(f -> "linkText=" + f + ", ").orElse("") +
            optionalLinkURL().map(f -> "linkURL=" + f + ", ").orElse("") +
            optionalCommentId().map(f -> "commentId=" + f + ", ").orElse("") +
            optionalAppuserId().map(f -> "appuserId=" + f + ", ").orElse("") +
            optionalBlogId().map(f -> "blogId=" + f + ", ").orElse("") +
            optionalTagId().map(f -> "tagId=" + f + ", ").orElse("") +
            optionalTopicId().map(f -> "topicId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
