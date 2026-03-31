package com.opencode.test.service.criteria;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.opencode.test.domain.Community} entity. This class is used
 * in {@link com.opencode.test.web.rest.CommunityResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /communities?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CommunityCriteria implements Serializable, Criteria {

    @Serial
    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter creationDate;

    private StringFilter communityName;

    private StringFilter communityDescription;

    private BooleanFilter isActive;

    private LongFilter blogId;

    private LongFilter cfollowedId;

    private LongFilter cfollowingId;

    private LongFilter cblockeduserId;

    private LongFilter cblockinguserId;

    private LongFilter appuserId;

    private LongFilter cinterestId;

    private LongFilter cactivityId;

    private LongFilter ccelebId;

    private Boolean distinct;

    public CommunityCriteria() {}

    public CommunityCriteria(CommunityCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.creationDate = other.optionalCreationDate().map(InstantFilter::copy).orElse(null);
        this.communityName = other.optionalCommunityName().map(StringFilter::copy).orElse(null);
        this.communityDescription = other.optionalCommunityDescription().map(StringFilter::copy).orElse(null);
        this.isActive = other.optionalIsActive().map(BooleanFilter::copy).orElse(null);
        this.blogId = other.optionalBlogId().map(LongFilter::copy).orElse(null);
        this.cfollowedId = other.optionalCfollowedId().map(LongFilter::copy).orElse(null);
        this.cfollowingId = other.optionalCfollowingId().map(LongFilter::copy).orElse(null);
        this.cblockeduserId = other.optionalCblockeduserId().map(LongFilter::copy).orElse(null);
        this.cblockinguserId = other.optionalCblockinguserId().map(LongFilter::copy).orElse(null);
        this.appuserId = other.optionalAppuserId().map(LongFilter::copy).orElse(null);
        this.cinterestId = other.optionalCinterestId().map(LongFilter::copy).orElse(null);
        this.cactivityId = other.optionalCactivityId().map(LongFilter::copy).orElse(null);
        this.ccelebId = other.optionalCcelebId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public CommunityCriteria copy() {
        return new CommunityCriteria(this);
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

    public StringFilter getCommunityName() {
        return communityName;
    }

    public Optional<StringFilter> optionalCommunityName() {
        return Optional.ofNullable(communityName);
    }

    public StringFilter communityName() {
        if (communityName == null) {
            setCommunityName(new StringFilter());
        }
        return communityName;
    }

    public void setCommunityName(StringFilter communityName) {
        this.communityName = communityName;
    }

    public StringFilter getCommunityDescription() {
        return communityDescription;
    }

    public Optional<StringFilter> optionalCommunityDescription() {
        return Optional.ofNullable(communityDescription);
    }

    public StringFilter communityDescription() {
        if (communityDescription == null) {
            setCommunityDescription(new StringFilter());
        }
        return communityDescription;
    }

    public void setCommunityDescription(StringFilter communityDescription) {
        this.communityDescription = communityDescription;
    }

    public BooleanFilter getIsActive() {
        return isActive;
    }

    public Optional<BooleanFilter> optionalIsActive() {
        return Optional.ofNullable(isActive);
    }

    public BooleanFilter isActive() {
        if (isActive == null) {
            setIsActive(new BooleanFilter());
        }
        return isActive;
    }

    public void setIsActive(BooleanFilter isActive) {
        this.isActive = isActive;
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

    public LongFilter getCblockeduserId() {
        return cblockeduserId;
    }

    public Optional<LongFilter> optionalCblockeduserId() {
        return Optional.ofNullable(cblockeduserId);
    }

    public LongFilter cblockeduserId() {
        if (cblockeduserId == null) {
            setCblockeduserId(new LongFilter());
        }
        return cblockeduserId;
    }

    public void setCblockeduserId(LongFilter cblockeduserId) {
        this.cblockeduserId = cblockeduserId;
    }

    public LongFilter getCblockinguserId() {
        return cblockinguserId;
    }

    public Optional<LongFilter> optionalCblockinguserId() {
        return Optional.ofNullable(cblockinguserId);
    }

    public LongFilter cblockinguserId() {
        if (cblockinguserId == null) {
            setCblockinguserId(new LongFilter());
        }
        return cblockinguserId;
    }

    public void setCblockinguserId(LongFilter cblockinguserId) {
        this.cblockinguserId = cblockinguserId;
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

    public LongFilter getCinterestId() {
        return cinterestId;
    }

    public Optional<LongFilter> optionalCinterestId() {
        return Optional.ofNullable(cinterestId);
    }

    public LongFilter cinterestId() {
        if (cinterestId == null) {
            setCinterestId(new LongFilter());
        }
        return cinterestId;
    }

    public void setCinterestId(LongFilter cinterestId) {
        this.cinterestId = cinterestId;
    }

    public LongFilter getCactivityId() {
        return cactivityId;
    }

    public Optional<LongFilter> optionalCactivityId() {
        return Optional.ofNullable(cactivityId);
    }

    public LongFilter cactivityId() {
        if (cactivityId == null) {
            setCactivityId(new LongFilter());
        }
        return cactivityId;
    }

    public void setCactivityId(LongFilter cactivityId) {
        this.cactivityId = cactivityId;
    }

    public LongFilter getCcelebId() {
        return ccelebId;
    }

    public Optional<LongFilter> optionalCcelebId() {
        return Optional.ofNullable(ccelebId);
    }

    public LongFilter ccelebId() {
        if (ccelebId == null) {
            setCcelebId(new LongFilter());
        }
        return ccelebId;
    }

    public void setCcelebId(LongFilter ccelebId) {
        this.ccelebId = ccelebId;
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
        final CommunityCriteria that = (CommunityCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(creationDate, that.creationDate) &&
            Objects.equals(communityName, that.communityName) &&
            Objects.equals(communityDescription, that.communityDescription) &&
            Objects.equals(isActive, that.isActive) &&
            Objects.equals(blogId, that.blogId) &&
            Objects.equals(cfollowedId, that.cfollowedId) &&
            Objects.equals(cfollowingId, that.cfollowingId) &&
            Objects.equals(cblockeduserId, that.cblockeduserId) &&
            Objects.equals(cblockinguserId, that.cblockinguserId) &&
            Objects.equals(appuserId, that.appuserId) &&
            Objects.equals(cinterestId, that.cinterestId) &&
            Objects.equals(cactivityId, that.cactivityId) &&
            Objects.equals(ccelebId, that.ccelebId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            creationDate,
            communityName,
            communityDescription,
            isActive,
            blogId,
            cfollowedId,
            cfollowingId,
            cblockeduserId,
            cblockinguserId,
            appuserId,
            cinterestId,
            cactivityId,
            ccelebId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CommunityCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalCreationDate().map(f -> "creationDate=" + f + ", ").orElse("") +
            optionalCommunityName().map(f -> "communityName=" + f + ", ").orElse("") +
            optionalCommunityDescription().map(f -> "communityDescription=" + f + ", ").orElse("") +
            optionalIsActive().map(f -> "isActive=" + f + ", ").orElse("") +
            optionalBlogId().map(f -> "blogId=" + f + ", ").orElse("") +
            optionalCfollowedId().map(f -> "cfollowedId=" + f + ", ").orElse("") +
            optionalCfollowingId().map(f -> "cfollowingId=" + f + ", ").orElse("") +
            optionalCblockeduserId().map(f -> "cblockeduserId=" + f + ", ").orElse("") +
            optionalCblockinguserId().map(f -> "cblockinguserId=" + f + ", ").orElse("") +
            optionalAppuserId().map(f -> "appuserId=" + f + ", ").orElse("") +
            optionalCinterestId().map(f -> "cinterestId=" + f + ", ").orElse("") +
            optionalCactivityId().map(f -> "cactivityId=" + f + ", ").orElse("") +
            optionalCcelebId().map(f -> "ccelebId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
