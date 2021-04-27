package com.epam.jwd.core_final.util;

import com.epam.jwd.core_final.domain.FlightMission;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface WriteStream {
    void writeMissionsResult(List<FlightMission> flightMissions) throws IOException;
}
