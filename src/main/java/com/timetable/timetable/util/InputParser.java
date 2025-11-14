package com.timetable.timetable.util;

import com.timetable.timetable.domain.Day;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class InputParser {
    private static final Map<String, Day> dayMap = Map.of(
            "월", Day.MON,
            "화", Day.TUE,
            "수", Day.WED,
            "목", Day.THU,
            "금", Day.FRI
    );

    private InputParser() {

    }

    public static List<String> separateInput(String input) {
        return Arrays.stream(input.split(",")).toList();
    }

    // 한글로 월, 화, 수, ... 로 입력된 문자열을 Day 자료형으로 변환
    public static List<Day> convertToDay(List<String> koreanDays) {
        return koreanDays.stream()
                .map(koreanDay -> {
                    return Day.getDay(koreanDay);
                })
                .toList();
    }
}
