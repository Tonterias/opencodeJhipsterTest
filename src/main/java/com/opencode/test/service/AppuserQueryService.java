package com.opencode.test.service;

import com.opencode.test.domain.*; // for static metamodels
import com.opencode.test.domain.Appuser;
import com.opencode.test.repository.AppuserRepository;
import com.opencode.test.service.criteria.AppuserCriteria;
import com.opencode.test.service.dto.AppuserDTO;
import com.opencode.test.service.mapper.AppuserMapper;
import jakarta.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Appuser} entities in the database.
 * The main input is a {@link AppuserCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link AppuserDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AppuserQueryService extends QueryService<Appuser> {

    private static final Logger LOG = LoggerFactory.getLogger(AppuserQueryService.class);

    private final AppuserRepository appuserRepository;

    private final AppuserMapper appuserMapper;

    public AppuserQueryService(AppuserRepository appuserRepository, AppuserMapper appuserMapper) {
        this.appuserRepository = appuserRepository;
        this.appuserMapper = appuserMapper;
    }

    /**
     * Return a {@link Page} of {@link AppuserDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AppuserDTO> findByCriteria(AppuserCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Appuser> specification = createSpecification(criteria);
        return appuserRepository.findAll(specification, page).map(appuserMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AppuserCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Appuser> specification = createSpecification(criteria);
        return appuserRepository.count(specification);
    }

    /**
     * Function to convert {@link AppuserCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Appuser> createSpecification(AppuserCriteria criteria) {
        Specification<Appuser> specification = Specification.unrestricted();
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : Specification.unrestricted(),
                buildRangeSpecification(criteria.getId(), Appuser_.id),
                buildRangeSpecification(criteria.getCreationDate(), Appuser_.creationDate),
                buildStringSpecification(criteria.getBio(), Appuser_.bio),
                buildStringSpecification(criteria.getFacebook(), Appuser_.facebook),
                buildStringSpecification(criteria.getTwitter(), Appuser_.twitter),
                buildStringSpecification(criteria.getLinkedin(), Appuser_.linkedin),
                buildStringSpecification(criteria.getInstagram(), Appuser_.instagram),
                buildRangeSpecification(criteria.getBirthdate(), Appuser_.birthdate),
                buildSpecification(criteria.getUserId(), root -> root.join(Appuser_.user, JoinType.LEFT).get(User_.id)),
                buildSpecification(criteria.getBlogId(), root -> root.join(Appuser_.blogs, JoinType.LEFT).get(Blog_.id)),
                buildSpecification(criteria.getCommunityId(), root -> root.join(Appuser_.communities, JoinType.LEFT).get(Community_.id)),
                buildSpecification(criteria.getNotificationId(), root ->
                    root.join(Appuser_.notifications, JoinType.LEFT).get(Notification_.id)
                ),
                buildSpecification(criteria.getCommentId(), root -> root.join(Appuser_.comments, JoinType.LEFT).get(Comment_.id)),
                buildSpecification(criteria.getPostId(), root -> root.join(Appuser_.posts, JoinType.LEFT).get(Post_.id)),
                buildSpecification(criteria.getFollowedId(), root -> root.join(Appuser_.followeds, JoinType.LEFT).get(Follow_.id)),
                buildSpecification(criteria.getFollowingId(), root -> root.join(Appuser_.followings, JoinType.LEFT).get(Follow_.id)),
                buildSpecification(criteria.getBlockeduserId(), root -> root.join(Appuser_.blockedusers, JoinType.LEFT).get(Blockuser_.id)),
                buildSpecification(criteria.getBlockinguserId(), root ->
                    root.join(Appuser_.blockingusers, JoinType.LEFT).get(Blockuser_.id)
                ),
                buildSpecification(criteria.getAppphotoId(), root -> root.join(Appuser_.appphoto, JoinType.LEFT).get(Appphoto_.id)),
                buildSpecification(criteria.getInterestId(), root -> root.join(Appuser_.interests, JoinType.LEFT).get(Interest_.id)),
                buildSpecification(criteria.getActivityId(), root -> root.join(Appuser_.activities, JoinType.LEFT).get(Activity_.id)),
                buildSpecification(criteria.getCelebId(), root -> root.join(Appuser_.celebs, JoinType.LEFT).get(Celeb_.id))
            );
        }
        return specification;
    }
}
