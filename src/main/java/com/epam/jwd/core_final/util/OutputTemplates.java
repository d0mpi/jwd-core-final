package com.epam.jwd.core_final.util;

import lombok.Getter;

public enum OutputTemplates {
    MAIN_MENU("""
            Main menu
            1) Crew menu
            2) Spaceship menu
            3) Mission menu
            4) Planet menu
            5) Exit the program
            """),
    SPACESHIP_MENU("""
            Spaceship menu
            1) Create spaceship
            2) Generate spaceship
            3) Show all spaceships
            4) Show free spaceships
            5) Back to main menu
            """),
    CREW_MEMBER_MENU("""
            Crew member menu
            1) Create crew member
            2) Generate crew member
            3) Show all crew members
            4) Show free crew members
            5) Back to main menu
            """),
    MISSION_MENU("""
            Flight mission menu
            1) Generate flight mission
            2) Show all missions
            3) Print all missions to file
            4) Real-time timetable
            5) Back to main menu
            """),
    PLANET_MENU("""
            Planet menu
            1) Create planet
            2) Generate planet
            3) Show all planets
            4) Back to main menu
            """),
    ROLE_LIST("""
            
            Roles
            1) MISSION_SPECIALIST
            2) FLIGHT_ENGINEER
            3) PILOT
            4) COMMANDER
            """),
    RANK_LIST("""
            
            Rank
            1) TRAINEE
            2) SECOND_OFFICER
            3) FIRST_OFFICER
            4) CAPTAIN
            """);

    @Getter
    private final String text;

    OutputTemplates(String text){
        this.text = text;
    }


}
