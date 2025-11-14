package com.timetable.timetable.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class InputParserTest {
    @Test
    void 강의_분할() {
        String lecturesToRegister = "CSC-1234,CSC-5678";
        assertThat(InputParser.separateInput(lecturesToRegister)).containsExactly("CSC-1234", "CSC-5678");

        String noLectureDays = "월,수,금";
        assertThat(InputParser.separateInput(noLectureDays)).containsExactly("월", "수", "금");
    }
}