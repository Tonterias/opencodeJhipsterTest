package com.opencode.test.service.criteria;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.opencode.test.domain.Appuser} entity. This class is used
 * in {@link com.opencode.test.web.rest.AppuserResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /appusers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AppuserCriteria implements Serializable, Criteria {

    @Serial
    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter creationDate;

    private StringFilter bio;

    private StringFilter facebook;

    private StringFilter twitter;

    private StringFilter linkedin;

    private StringFilter instagram;

    private InstantFilter birthdate;

    private LongFilter userId;

    private LongFilter blogId;

    private LongFilter communityId;

    private LongFilter notificationId;

    private LongFilter commentId;

    private LongFilter postId;

    private LongFilter followedId;

    private LongFilter followingId;

    private LongFilter blockeduserId;

    private LongFilter blockinguserId;

    private LongFilter appphotoId;

    private LongFilter interestId;

    private LongFilter activityId;

    private LongFilter celebId;

    private Boolean distinct;

    public AppuserCriteria() {}

    public AppuserCriteria(AppuserCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.creationDate = other.optionalCreationDate().map(InstantFilter::copy).orElse(null);
        this.bio = other.optionalBio().map(StringFilter::copy).orElse(null);
        this.facebook = other.optionalFacebook().map(StringFilter::copy).orElse(null);
        this.twitter = other.optionalTwitter().map(StringFilter::copy).orElse(null);
        this.linkedin = other.optionalLinkedin().map(StringFilter::copy).orElse(null);
        this.instagram = other.optionalInstagram().map(StringFilter::copy).orElse(null);
        this.birthdate = other.optionalBirthdate().map(InstantFilter::copy).orElse(null);
        this.userId = other.optionalUserId().map(LongFilter::copy).orElse(null);
        this.blogId = other.optionalBlogId().map(LongFilter::copy).orElse(null);
        this.communityId = other.optionalCommunityId().map(LongFilter::copy).orElse(null);
        this.notificationId = other.optionalNotificationId().map(LongFilter::copy).orElse(null);
        this.commentId = other.optionalCommentId().map(LongFilter::copy).orElse(null);
        this.postId = other.optionalPostId().map(LongFilter::copy).orElse(null);
        this.followedId = other.optionalFollowedId().map(LongFilter::copy).orElse(null);
        this.followingId = other.optionalFollowingId().map(LongFilter::copy).orElse(null);
        this.blockeduserId = other.optionalBlockeduserId().map(LongFilter::copy).orElse(null);
        this.blockinguserId = other.optionalBlockinguserId().map(LongFilter::copy).orElse(null);
        this.appphotoId = other.optionalAppphotoId().map(LongFilter::copy).orElse(null);
        this.interestId = other.optionalInterestId().map(LongFilter::copy).orElse(null);
        this.activityId = other.optionalActivityId().map(LongFilter::copy).orElse(null);
        this.celebId = other.optionalCelebId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public AppuserCriteria copy() {
        return new AppuserCriteria(this);
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

    public StringFilter getBio() {
        return bio;
    }

    public Optional<StringFilter> optionalBio() {
        return Optional.ofNullable(bio);
    }

    public StringFilter bio() {
        if (bio == null) {
            setBio(new StringFilter());
        }
        return bio;
    }

    public void setBio(StringFilter bio) {
        this.bio = bio;
    }

    public StringFilter getFacebook() {
        return facebook;
    }

    public Optional<StringFilter> optionalFacebook() {
        return Optional.ofNullable(facebook);
    }

    public StringFilter facebook() {
        if (facebook == null) {
            setFacebook(new StringFilter());
        }
        return facebook;
    }

    public void setFacebook(StringFilter facebook) {
        this.facebook = facebook;
    }

    public StringFilter getTwitter() {
        return twitter;
    }

    public Optional<StringFilter> optionalTwitter() {
        return Optional.ofNullable(twitter);
    }

    public StringFilter twitter() {
        if (twitter == null) {
            setTwitter(new StringFilter());
        }
        return twitter;
    }

    public void setTwitter(StringFilter twitter) {
        this.twitter = twitter;
    }

    public StringFilter getLinkedin() {
        return linkedin;
    }

    public Optional<StringFilter> optionalLinkedin() {
        return Optional.ofNullable(linkedin);
    }

    public StringFilter linkedin() {
        if (linkedin == null) {
            setLinkedin(new StringFilter());
        }
        return linkedin;
    }

    public void setLinkedin(StringFilter linkedin) {
        this.linkedin = linkedin;
    }

    public StringFilter getInstagram() {
        return instagram;
    }

    public Optional<StringFilter> optionalInstagram() {
        return Optional.ofNullable(instagram);
    }

    public StringFilter instagram() {
        if (instagram == null) {
            setInstagram(new StringFilter());
        }
        return instagram;
    }

    public void setInstagram(StringFilter instagram) {
        this.instagram = instagram;
    }

    public InstantFilter getBirthdate() {
        return birthdate;
    }

    public Optional<InstantFilter> optionalBirthdate() {
        return Optional.ofNullable(birthdate);
    }

    public InstantFilter birthdate() {
        if (birthdate == null) {
            setBirthdate(new InstantFilter());
        }
        return birthdate;
    }

    public void setBirthdate(InstantFilter birthdate) {
        this.birthdate = birthdate;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public Optional<LongFilter> optionalUserId() {
        return Optional.ofNullable(userId);
    }

    public LongFilter userId() {
        if (userId == null) {
            setUserId(new LongFilter());
        }
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
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

    public LongFilter getNotificationId() {
        return notificationId;
    }

    public Optional<LongFilter> optionalNotificationId() {
        return Optional.ofNullable(notificationId);
    }

    public LongFilter notificationId() {
        if (notificationId == null) {
            setNotificationId(new LongFilter());
        }
        return notificationId;
    }

    public void setNotificationId(LongFilter notificationId) {
        this.notificationId = notificationId;
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

    public LongFilter getBlockeduserId() {
        return blockeduserId;
    }

    public Optional<LongFilter> optionalBlockeduserId() {
        return Optional.ofNullable(blockeduserId);
    }

    public LongFilter blockeduserId() {
        if (blockeduserId == null) {
            setBlockeduserId(new LongFilter());
        }
        return blockeduserId;
    }

    public void setBlockeduserId(LongFilter blockeduserId) {
        this.blockeduserId = blockeduserId;
    }

    public LongFilter getBlockinguserId() {
        return blockinguserId;
    }

    public Optional<LongFilter> optionalBlockinguserId() {
        return Optional.ofNullable(blockinguserId);
    }

    public LongFilter blockinguserId() {
        if (blockinguserId == null) {
            setBlockinguserId(new LongFilter());
        }
        return blockinguserId;
    }

    public void setBlockinguserId(LongFilter blockinguserId) {
        this.blockinguserId = blockinguserId;
    }

    public LongFilter getAppphotoId() {
        return appphotoId;
    }

    public Optional<LongFilter> optionalAppphotoId() {
        return Optional.ofNullable(appphotoId);
    }

    public LongFilter appphotoId() {
        if (appphotoId == null) {
            setAppphotoId(new LongFilter());
        }
        return appphotoId;
    }

    public void setAppphotoId(LongFilter appphotoId) {
        this.appphotoId = appphotoId;
    }

    public LongFilter getInterestId() {
        return interestId;
    }

    public Optional<LongFilter> optionalInterestId() {
        return Optional.ofNullable(interestId);
    }

    public LongFilter interestId() {
        if (interestId == null) {
            setInterestId(new LongFilter());
        }
        return interestId;
    }

    public void setInterestId(LongFilter interestId) {
        this.interestId = interestId;
    }

    public LongFilter getActivityId() {
        return activityId;
    }

    public Optional<LongFilter> optionalActivityId() {
        return Optional.ofNullable(activityId);
    }

    public LongFilter activityId() {
        if (activityId == null) {
            setActivityId(new LongFilter());
        }
        return activityId;
    }

    public void setActivityId(LongFilter activityId) {
        this.activityId = activityId;
    }

    public LongFilter getCelebId() {
        return celebId;
    }

    public Optional<LongFilter> optionalCelebId() {
        return Optional.ofNullable(celebId);
    }

    public LongFilter celebId() {
        if (celebId == null) {
            setCelebId(new LongFilter());
        }
        return celebId;
    }

    public void setCelebId(LongFilter celebId) {
        this.celebId = celebId;
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
        final AppuserCriteria that = (AppuserCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(creationDate, that.creationDate) &&
            Objects.equals(bio, that.bio) &&
            Objects.equals(facebook, that.facebook) &&
            Objects.equals(twitter, that.twitter) &&
            Objects.equals(linkedin, that.linkedin) &&
            Objects.equals(instagram, that.instagram) &&
            Objects.equals(birthdate, that.birthdate) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(blogId, that.blogId) &&
            Objects.equals(communityId, that.communityId) &&
            Objects.equals(notificationId, that.notificationId) &&
            Objects.equals(commentId, that.commentId) &&
            Objects.equals(postId, that.postId) &&
            Objects.equals(followedId, that.followedId) &&
            Objects.equals(followingId, that.followingId) &&
            Objects.equals(blockeduserId, that.blockeduserId) &&
            Objects.equals(blockinguserId, that.blockinguserId) &&
            Objects.equals(appphotoId, that.appphotoId) &&
            Objects.equals(interestId, that.interestId) &&
            Objects.equals(activityId, that.activityId) &&
            Objects.equals(celebId, that.celebId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            creationDate,
            bio,
            facebook,
            twitter,
            linkedin,
            instagram,
            birthdate,
            userId,
            blogId,
            communityId,
            notificationId,
            commentId,
            postId,
            followedId,
            followingId,
            blockeduserId,
            blockinguserId,
            appphotoId,
            interestId,
            activityId,
            celebId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppuserCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalCreationDate().map(f -> "creationDate=" + f + ", ").orElse("") +
            optionalBio().map(f -> "bio=" + f + ", ").orElse("") +
            optionalFacebook().map(f -> "facebook=" + f + ", ").orElse("") +
            optionalTwitter().map(f -> "twitter=" + f + ", ").orElse("") +
            optionalLinkedin().map(f -> "linkedin=" + f + ", ").orElse("") +
            optionalInstagram().map(f -> "instagram=" + f + ", ").orElse("") +
            optionalBirthdate().map(f -> "birthdate=" + f + ", ").orElse("") +
            optionalUserId().map(f -> "userId=" + f + ", ").orElse("") +
            optionalBlogId().map(f -> "blogId=" + f + ", ").orElse("") +
            optionalCommunityId().map(f -> "communityId=" + f + ", ").orElse("") +
            optionalNotificationId().map(f -> "notificationId=" + f + ", ").orElse("") +
            optionalCommentId().map(f -> "commentId=" + f + ", ").orElse("") +
            optionalPostId().map(f -> "postId=" + f + ", ").orElse("") +
            optionalFollowedId().map(f -> "followedId=" + f + ", ").orElse("") +
            optionalFollowingId().map(f -> "followingId=" + f + ", ").orElse("") +
            optionalBlockeduserId().map(f -> "blockeduserId=" + f + ", ").orElse("") +
            optionalBlockinguserId().map(f -> "blockinguserId=" + f + ", ").orElse("") +
            optionalAppphotoId().map(f -> "appphotoId=" + f + ", ").orElse("") +
            optionalInterestId().map(f -> "interestId=" + f + ", ").orElse("") +
            optionalActivityId().map(f -> "activityId=" + f + ", ").orElse("") +
            optionalCelebId().map(f -> "celebId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
