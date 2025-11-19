package com.timetable.timetable.dto;

import java.util.List;

public record ClientRequest(
        List<String> lectures,
        List<String> days,
        List<String> lecturesForSpare
) {
}
