package com.timetable.timetable.domain;

import org.junit.jupiter.api.Test;
import com.timetable.timetable.service.TimeTableMaker;

class MakeTimeTableTest {
    @Test
    void 시간표에_등록여부확인() {
        TimeTableMaker timeTableMaker = new TimeTableMaker(new RegisterWizard(), new LectureMatcher());
    }
}