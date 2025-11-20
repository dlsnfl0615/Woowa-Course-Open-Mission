package com.timetable.timetable.validator;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ValidatorTest {
    @Test
    void 틀린_학수번호() {
        List<String> lectureCodes = List.of("a1", "acd1234", "CSC1234", "CSC-", "CS-1234", "CSC-12", "CSC-12345", "");
        for (String code : lectureCodes) {
            assertThrows(IllegalArgumentException.class, () -> {
                Validator.validateLectureCode(code);
            });
        }
    }
}