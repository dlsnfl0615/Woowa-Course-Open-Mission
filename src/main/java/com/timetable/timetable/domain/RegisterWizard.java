package com.timetable.timetable.domain;

import java.util.*;

public class RegisterWizard {
    private final List<Lecture> usedLectures = new ArrayList<>();

    public List<TimeTable> makeTimeTable(List<Lecture> lecturesToRegister, List<Day> noLectureDays) {

        int uniqueLectures = divideLectureByType(lecturesToRegister).size(); // 강의 종류 수
        List<TimeTable> timeTables = new ArrayList<>();

        dfsPickingLecture(lecturesToRegister, timeTables, noLectureDays, uniqueLectures);

        return timeTables;
    }

    // 조건에 맞는 모든 강의 시간표 찾기
    public void dfsPickingLecture(List<Lecture> lecturesToRegister,
                                  List<TimeTable> timeTables,
                                  List<Day> noLectureDays,
                                  int uniqueLectures) {
        // 사용자가 원하는 강의 종류 수를 모두 신청하면 종료
        if (usedLectures.size() == uniqueLectures) {
            timeTables.add(new TimeTable(new ArrayList<>(usedLectures)));
            return;
        }

        for (Lecture lecture : lecturesToRegister) {
            if (canAddLecture(lecture, noLectureDays)) {
                usedLectures.add(lecture);
                dfsPickingLecture(lecturesToRegister, timeTables, noLectureDays, uniqueLectures);
                usedLectures.removeLast();
            }
        }

    }

    public boolean canAddLecture(Lecture lecture, List<Day> noLectureDays) {
        if (lecture.conflictsWithNoLectureDays(noLectureDays)) {
            return false;
        }

        if (lecture.isCyberLecture()) {
            return true;
        }

        if (usedLectures.isEmpty()) {
            return true;
        }

        for (Lecture usedLecture : usedLectures) {
            if (usedLecture.overlapsTimeWith(lecture)
                    || usedLecture.overlapsNameWith(lecture)) {
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
