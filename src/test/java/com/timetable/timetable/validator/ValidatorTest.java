package com.timetable.timetable.validator;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ValidatorTest {
    @Test
    void 콤마_분할_성공() {
        String input1 = "CSC-1234,CSE-9876";
        assertThat(Validator.validateInputLectures(input1)).isTrue();

        String input2 = "월,화,수,목";
        assertThat(Validator.containsOnlyComma(input2)).isTrue();
    }

    @Test
    void 요일() {
        List<String> days = List.of("월", "화", "수", "목", "금");
        assertThat(Validator.validateInputDays(days)).isTrue();
    }
}