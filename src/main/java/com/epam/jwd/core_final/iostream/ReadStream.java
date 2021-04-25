package com.epam.jwd.core_final.iostream;

import com.epam.jwd.core_final.domain.AbstractBaseEntity;
import com.epam.jwd.core_final.domain.CrewMember;

import java.util.List;

public interface ReadStream {
    List<? extends AbstractBaseEntity> readData();
}
