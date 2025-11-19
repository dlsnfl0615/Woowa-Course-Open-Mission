package com.timetable.timetable.dto;

import com.timetable.timetable.domain.Day;
import com.timetable.timetable.domain.TimeSlot;

import java.time.LocalTime;

public record TimeSlotRequest(
        Day day,
        LocalTime start,
        LocalTime end
) {
    public static TimeSlotRequest from(TimeSlot slot) {
        return new TimeSlotRequest(
                slot.getDay(),
                slot.getStart(),
                slot.getEnd()
        );
    }

    public String convertToGridPosition() {
        int column = day.ordinal() + 2;
        int[] row = getGridRow();

        return String.format("grid-column: %d; grid-row: %d / %d", column, row[0], row[1]);
    }

    public int[] getGridRow() {
        int startRow = calculateRow(start);
        int endRow = calculateRow(end);

        return new int[]{startRow, endRow};
    }

    private int calculateRow(LocalTime time) {
        int hour = time.getHour();
        int minute = time.getMinute();

        int startRow = (hour - 9) * 2 + 2;
        startRow += minute / 30;

        return startRow;
    }
}