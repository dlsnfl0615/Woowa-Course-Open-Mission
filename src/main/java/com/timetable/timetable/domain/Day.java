package com.timetable.timetable.domain;

public enum Day {
    MON("월"),
    TUE("화"),
    WED("수"),
    THU("목"),
    FRI("금");
    
    private final String koreanDay;
    
    Day(String koreanDay) {
        this.koreanDay = koreanDay;
    }

    public String getKoreanDay() {
        return koreanDay;
    }
    
    public static Day getDay(String koreanDay) {
        for (Day day : Day.values()) {
            if (day.koreanDay.equals(koreanDay)) {
                return day;
            }
        }

        return null;
    }
}
