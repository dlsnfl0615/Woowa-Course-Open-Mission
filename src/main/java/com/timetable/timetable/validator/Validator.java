package com.timetable.timetable.validator;

import com.timetable.timetable.domain.Day;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Validator {
    private static final Set<String> validKoreanDays =
            Arrays.stream(Day.values())
                    .map(Day::getKoreanDay)
                    .collect(Collectors.toSet());

    private Validator() {

    }

    public static boolean validateInputLectures(String inputLectures) {
        // 쉼표와 하이픈만 허용
        return inputLectures.matches("^[a-zA-z0-9,-]+$");
    }

    public static boolean containsOnlyComma(String inputDays) {
        return inputDays.matches("^[a-zA-z0-9,]+$");
    }

    public static boolean validateInputDays(List<String> inputDays) {
        for (String inputDay : inputDays) {
            if (!validKoreanDays.contains(inputDay)) {
                return false;
            }
        }

        return true;
    }
}
