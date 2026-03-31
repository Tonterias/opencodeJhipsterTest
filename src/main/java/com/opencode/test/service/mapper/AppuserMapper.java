package com.opencode.test.service.mapper;

import com.opencode.test.domain.Activity;
import com.opencode.test.domain.Appuser;
import com.opencode.test.domain.Celeb;
import com.opencode.test.domain.Interest;
import com.opencode.test.domain.User;
import com.opencode.test.service.dto.ActivityDTO;
import com.opencode.test.service.dto.AppuserDTO;
import com.opencode.test.service.dto.CelebDTO;
import com.opencode.test.service.dto.InterestDTO;
import com.opencode.test.service.dto.UserDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Appuser} and its DTO {@link AppuserDTO}.
 */
@Mapper(componentModel = "spring")
public interface AppuserMapper extends EntityMapper<AppuserDTO, Appuser> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    @Mapping(target = "interests", source = "interests", qualifiedByName = "interestInterestNameSet")
    @Mapping(target = "activities", source = "activities", qualifiedByName = "activityActivityNameSet")
    @Mapping(target = "celebs", source = "celebs", qualifiedByName = "celebCelebNameSet")
    AppuserDTO toDto(Appuser s);

    @Mapping(target = "interests", ignore = true)
    @Mapping(target = "removeInterest", ignore = true)
    @Mapping(target = "activities", ignore = true)
    @Mapping(target = "removeActivity", ignore = true)
    @Mapping(target = "celebs", ignore = true)
    @Mapping(target = "removeCeleb", ignore = true)
    Appuser toEntity(AppuserDTO appuserDTO);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);

    @Named("interestInterestName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "interestName", source = "interestName")
    InterestDTO toDtoInterestInterestName(Interest interest);

    @Named("interestInterestNameSet")
    default Set<InterestDTO> toDtoInterestInterestNameSet(Set<Interest> interest) {
        return interest.stream().map(this::toDtoInterestInterestName).collect(Collectors.toSet());
    }

    @Named("activityActivityName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "activityName", source = "activityName")
    ActivityDTO toDtoActivityActivityName(Activity activity);

    @Named("activityActivityNameSet")
    default Set<ActivityDTO> toDtoActivityActivityNameSet(Set<Activity> activity) {
        return activity.stream().map(this::toDtoActivityActivityName).collect(Collectors.toSet());
    }

    @Named("celebCelebName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "celebName", source = "celebName")
    CelebDTO toDtoCelebCelebName(Celeb celeb);

    @Named("celebCelebNameSet")
    default Set<CelebDTO> toDtoCelebCelebNameSet(Set<Celeb> celeb) {
        return celeb.stream().map(this::toDtoCelebCelebName).collect(Collectors.toSet());
    }
}
