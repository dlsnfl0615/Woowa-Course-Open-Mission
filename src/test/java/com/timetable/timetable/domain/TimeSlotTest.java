package com.timetable.timetable.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

class TimeSlotTest {
    @Test
    void 겹치지_않는_강의() {
        TimeSlot timeSlot1 = new TimeSlot(Day.MON, LocalTime.of(13,0,0), LocalTime.of(14,30,0));
        TimeSlot timeSlot2 = new TimeSlot(Day.TUE, LocalTime.of(13,0,0), LocalTime.of(14,30,0));
        TimeSlot timeSlot3 = new TimeSlot(Day.MON, LocalTime.of(14,30,0), LocalTime.of(15,30,0));

        assertThat(timeSlot1.overlapsWith(timeSlot2)).isEqualTo(false);
        assertThat(timeSlot1.overlapsWith(timeSlot3)).isEqualTo(false);
    }

    @Test
    void 겹치는_강의() {
        TimeSlot timeSlot1 = new TimeSlot(Day.MON, LocalTime.of(13,0,0), LocalTime.of(14,30,0));
        TimeSlot timeSlot2 = new TimeSlot(Day.MON, LocalTime.of(13,30,0), LocalTime.of(15,0,0));

        assertThat(timeSlot1.overlapsWith(timeSlot2)).isEqualTo(true);
    }
}