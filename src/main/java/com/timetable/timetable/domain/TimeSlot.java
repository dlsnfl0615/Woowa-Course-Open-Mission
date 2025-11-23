package com.timetable.timetable.domain;

import java.time.LocalTime;
import java.util.List;

public class TimeSlot {
    private final Day day;
    private final LocalTime start;
    private final LocalTime end;

    public TimeSlot(Day day, LocalTime start, LocalTime end) {
        this.day = day;
        this.start = start;
        this.end = end;
    }

    public boolean overlapsWith(TimeSlot otherTimeSlot) {
        if (this.day != otherTimeSlot.day) {
            return false;
        }

        if (this.end.equals(otherTimeSlot.start)
                || this.start.equals(otherTimeSlot.end)) { // 연강 (한 강의 끝나자마자 다음 강의 시작)인 경우도 시간표 등록할 수 있게
            return false;
        }

        if (otherTimeSlot.start.isBefore(this.start)) { // otherTimeSlot의 시작 시간이 9시인데 thisTimeSlot의 시작시간이 10시일 때
            if (otherTimeSlot.end.isBefore(this.start)) { // otherTimeSlot의 종료 시간이 10시 전이기만 하면 안 겹침
                return false;
            }

            return true; // otherTimeSlot의 종료시간이 10시 이후면 무조건 겹침
        }

        if (otherTimeSlot.start.isAfter(this.end)) { // otherTimeSlot의 시작 시간이 13시인데 thisTimeSlot의 종료 시간이 11시이면
            return false; // 무조건 안 겹침
        }

        return true; // otherTimeSlot의 시작 시간이 thisTime의 시작 시간과 종료 시간 사이에 있으면 무조건 겹침
    }

    public boolean isSameDayWith(List<Day> noLectureDays) {
        for (Day noLectureDay : noLectureDays) {
            if (day.equals(noLectureDay)) {
                return true;
            }
        }

        return false;
    }

    public Day getDay() {
        return day;
    }

    public LocalTime getStart() {
        return start;
    }

    public LocalTime getEnd() {
        return end;
    }
}
