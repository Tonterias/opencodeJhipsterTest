package com.opencode.test.service.criteria;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.opencode.test.domain.Blockuser} entity. This class is used
 * in {@link com.opencode.test.web.rest.BlockuserResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /blockusers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BlockuserCriteria implements Serializable, Criteria {

    @Serial
    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter creationDate;

    private LongFilter blockeduserId;

    private LongFilter blockinguserId;

    private LongFilter cblockeduserId;

    private LongFilter cblockinguserId;

    private Boolean distinct;

    public BlockuserCriteria() {}

    public BlockuserCriteria(BlockuserCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.creationDate = other.optionalCreationDate().map(InstantFilter::copy).orElse(null);
        this.blockeduserId = other.optionalBlockeduserId().map(LongFilter::copy).orElse(null);
        this.blockinguserId = other.optionalBlockinguserId().map(LongFilter::copy).orElse(null);
        this.cblockeduserId = other.optionalCblockeduserId().map(LongFilter::copy).orElse(null);
        this.cblockinguserId = other.optionalCblockinguserId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public BlockuserCriteria copy() {
        return new BlockuserCriteria(this);
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
        final BlockuserCriteria that = (BlockuserCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(creationDate, that.creationDate) &&
            Objects.equals(blockeduserId, that.blockeduserId) &&
            Objects.equals(blockinguserId, that.blockinguserId) &&
            Objects.equals(cblockeduserId, that.cblockeduserId) &&
            Objects.equals(cblockinguserId, that.cblockinguserId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, creationDate, blockeduserId, blockinguserId, cblockeduserId, cblockinguserId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BlockuserCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalCreationDate().map(f -> "creationDate=" + f + ", ").orElse("") +
            optionalBlockeduserId().map(f -> "blockeduserId=" + f + ", ").orElse("") +
            optionalBlockinguserId().map(f -> "blockinguserId=" + f + ", ").orElse("") +
            optionalCblockeduserId().map(f -> "cblockeduserId=" + f + ", ").orElse("") +
            optionalCblockinguserId().map(f -> "cblockinguserId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
