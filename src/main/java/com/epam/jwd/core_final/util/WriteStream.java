package com.epam.jwd.core_final.util;

import com.epam.jwd.core_final.domain.AbstractBaseEntity;

import java.io.IOException;
import java.util.List;

public interface WriteStream<T extends AbstractBaseEntity> {
    void writeData(List<T> entities) throws IOException;
}
