package com.opencode.test.service.criteria;

import com.opencode.test.domain.enumeration.NotificationReason;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.opencode.test.domain.Notification} entity. This class is used
 * in {@link com.opencode.test.web.rest.NotificationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /notifications?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NotificationCriteria implements Serializable, Criteria {

    /**
     * Class for filtering NotificationReason
     */
    public static class NotificationReasonFilter extends Filter<NotificationReason> {

        public NotificationReasonFilter() {}

        public NotificationReasonFilter(NotificationReasonFilter filter) {
            super(filter);
        }

        @Override
        public NotificationReasonFilter copy() {
            return new NotificationReasonFilter(this);
        }
    }

    @Serial
    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter creationDate;

    private InstantFilter notificationDate;

    private NotificationReasonFilter notificationReason;

    private StringFilter notificationText;

    private BooleanFilter isDelivered;

    private LongFilter appuserId;

    private Boolean distinct;

    public NotificationCriteria() {}

    public NotificationCriteria(NotificationCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.creationDate = other.optionalCreationDate().map(InstantFilter::copy).orElse(null);
        this.notificationDate = other.optionalNotificationDate().map(InstantFilter::copy).orElse(null);
        this.notificationReason = other.optionalNotificationReason().map(NotificationReasonFilter::copy).orElse(null);
        this.notificationText = other.optionalNotificationText().map(StringFilter::copy).orElse(null);
        this.isDelivered = other.optionalIsDelivered().map(BooleanFilter::copy).orElse(null);
        this.appuserId = other.optionalAppuserId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public NotificationCriteria copy() {
        return new NotificationCriteria(this);
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

    public InstantFilter getNotificationDate() {
        return notificationDate;
    }

    public Optional<InstantFilter> optionalNotificationDate() {
        return Optional.ofNullable(notificationDate);
    }

    public InstantFilter notificationDate() {
        if (notificationDate == null) {
            setNotificationDate(new InstantFilter());
        }
        return notificationDate;
    }

    public void setNotificationDate(InstantFilter notificationDate) {
        this.notificationDate = notificationDate;
    }

    public NotificationReasonFilter getNotificationReason() {
        return notificationReason;
    }

    public Optional<NotificationReasonFilter> optionalNotificationReason() {
        return Optional.ofNullable(notificationReason);
    }

    public NotificationReasonFilter notificationReason() {
        if (notificationReason == null) {
            setNotificationReason(new NotificationReasonFilter());
        }
        return notificationReason;
    }

    public void setNotificationReason(NotificationReasonFilter notificationReason) {
        this.notificationReason = notificationReason;
    }

    public StringFilter getNotificationText() {
        return notificationText;
    }

    public Optional<StringFilter> optionalNotificationText() {
        return Optional.ofNullable(notificationText);
    }

    public StringFilter notificationText() {
        if (notificationText == null) {
            setNotificationText(new StringFilter());
        }
        return notificationText;
    }

    public void setNotificationText(StringFilter notificationText) {
        this.notificationText = notificationText;
    }

    public BooleanFilter getIsDelivered() {
        return isDelivered;
    }

    public Optional<BooleanFilter> optionalIsDelivered() {
        return Optional.ofNullable(isDelivered);
    }

    public BooleanFilter isDelivered() {
        if (isDelivered == null) {
            setIsDelivered(new BooleanFilter());
        }
        return isDelivered;
    }

    public void setIsDelivered(BooleanFilter isDelivered) {
        this.isDelivered = isDelivered;
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
        final NotificationCriteria that = (NotificationCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(creationDate, that.creationDate) &&
            Objects.equals(notificationDate, that.notificationDate) &&
            Objects.equals(notificationReason, that.notificationReason) &&
            Objects.equals(notificationText, that.notificationText) &&
            Objects.equals(isDelivered, that.isDelivered) &&
            Objects.equals(appuserId, that.appuserId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, creationDate, notificationDate, notificationReason, notificationText, isDelivered, appuserId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NotificationCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalCreationDate().map(f -> "creationDate=" + f + ", ").orElse("") +
            optionalNotificationDate().map(f -> "notificationDate=" + f + ", ").orElse("") +
            optionalNotificationReason().map(f -> "notificationReason=" + f + ", ").orElse("") +
            optionalNotificationText().map(f -> "notificationText=" + f + ", ").orElse("") +
            optionalIsDelivered().map(f -> "isDelivered=" + f + ", ").orElse("") +
            optionalAppuserId().map(f -> "appuserId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
