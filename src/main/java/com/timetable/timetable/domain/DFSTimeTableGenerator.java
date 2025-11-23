package com.timetable.timetable.domain;

import java.util.*;

public class DFSTimeTableGenerator implements TimeTableGenerator {
    @Override
    public List<TimeTable> generate(List<Lecture> lecturesToRegister, List<Day> noLectureDays) {
        List<TimeTable> timeTables = new ArrayList<>();

        Map<String, List<Lecture>> groupedLectures = divideLectureByType(lecturesToRegister);

        List<String> lectures = new ArrayList<>(groupedLectures.keySet());

        dfsPickingLecture(0, lectures, groupedLectures, new ArrayList<>(), timeTables, noLectureDays);

        return timeTables;
    }

    // 조건에 맞는 모든 강의 시간표 찾기
    public void dfsPickingLecture(int depth,
                                  List<String> lectures,
                                  Map<String, List<Lecture>> groupedLectures,
                                  List<Lecture> currentTimetable,
                                  List<TimeTable> result,
                                  List<Day> noLectureDays) {
        // 사용자가 원하는 강의 종류 수를 모두 신청하면 종료
        if (depth == lectures.size()) {
            result.add(new TimeTable(new ArrayList<>(currentTimetable)));
            return;
        }

        String currentSubject = lectures.get(depth);
        List<Lecture> candidates = groupedLectures.get(currentSubject);

        for (Lecture lecture : candidates) {
            // 시간 겹침 및 공강일 체크
            if (canAddLecture(lecture, currentTimetable, noLectureDays)) {
                currentTimetable.add(lecture); // 선택

                // 다음 과목 선택하러 이동 (depth + 1)
                dfsPickingLecture(depth + 1, lectures, groupedLectures, currentTimetable, result, noLectureDays);

                currentTimetable.removeLast();
            }
        }
    }

    public boolean canAddLecture(Lecture lecture, List<Lecture> currentTimetable, List<Day> noLectureDays) {
        if (lecture.conflictsWithNoLectureDays(noLectureDays)) {
            return false;
        }

        if (lecture.isCyberLecture()) {
            return true;
        }

        for (Lecture existing : currentTimetable) {
            if (!existing.isCyberLecture() // 사이버 강의가 아닌데
                    && existing.overlapsTimeWith(lecture)) { // 시간이 겹치면
                return false;
            }
        }

        return true;
    }

    // 동일 종류 강의(이름이 동일한 강의)는 중복 수강신청할 수 없음.
    // 강의명을 key로 정의하여 분반을 하나의 강의로 합쳐 강의 종류 수 셀 수 있음.
    private Map<String, List<Lecture>> divideLectureByType(List<Lecture> lecturesToRegister) {
        Map<String, List<Lecture>> map = new HashMap<>();

        for (Lecture lecture : lecturesToRegister) {
            lecture.divide(map);
        }

        return map;
    }
}
