package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * Should be a builder for {@link com.epam.jwd.core_final.domain.CrewMember} fields
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Slf4j
public class CrewMemberCriteria extends Criteria<CrewMember> {
    @Getter
    private final Role role;
    @Getter
    private final Rank rank;
    @Getter
    private final Boolean isReadyForNextMission;

    CrewMemberCriteria(Long id, String name, Role role, Rank rank, Boolean isReadyForNextMission) {
        super(id, name);
        this.role = role;
        this.rank = rank;
        this.isReadyForNextMission = isReadyForNextMission;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String name;
        private Role role;
        private Rank rank;
        private Boolean isReadyForNextMission;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder role(Role role) {
            this.role = role;
            return this;
        }

        public Builder rank(Rank rank) {
            this.rank = rank;
            return this;
        }

        public Builder isReadyForNextMission(Boolean isReadyForNextMission) {
            this.isReadyForNextMission = isReadyForNextMission;
            return this;
        }

        public CrewMemberCriteria build() {
            return new CrewMemberCriteria(id, name, role, rank, isReadyForNextMission);
        }
    }
}
