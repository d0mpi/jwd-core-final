package com.epam.jwd.core_final.util.iostreamImpl;

import com.epam.jwd.core_final.domain.AbstractBaseEntity;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.util.WriteStream;

import java.util.List;

public class CrewWriteConsoleStream implements WriteStream<CrewMember> {
    private static class SingletonHolder {
        private static final CrewWriteConsoleStream instance = new CrewWriteConsoleStream();
    }

    public static CrewWriteConsoleStream getInstance() {
        return CrewWriteConsoleStream.SingletonHolder.instance;
    }

    private CrewWriteConsoleStream() {
    }

    @Override
    public  void writeData(List<CrewMember> entities) {
        for (CrewMember crewMember : entities) {
            System.out.println(crewMember.toString());
        }
        System.out.println();
    }
}
