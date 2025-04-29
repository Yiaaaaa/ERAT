package org.example.erat.service;

import org.example.erat.model.ExperimentFile;
import org.example.erat.model.Student;

import java.util.*;

public class AnalysisService {
    public Map<String, Integer> analyzeMissingByStudent(List<Student> students, List<ExperimentFile> reports) {
        Map<String, Integer> missingCount = new HashMap<>();
        Set<String> allExps = new HashSet<>();

        for (ExperimentFile report : reports) {
            allExps.add(report.getExperimentId());
        }

        for (Student student : students) {
            int missing = 0;
            for (String exp : allExps) {
                boolean hasReport = reports.stream()
                        .anyMatch(r -> r.getStudentId().equals(student.getStudentId()) && r.getExperimentId().equals(exp));
                if (!hasReport) missing++;
            }
            missingCount.put(student.getStudentId(), missing);
        }

        return missingCount;
    }

    public Map<String, Integer> analyzeMissingByExperiment(List<Student> students, List<ExperimentFile> reports) {
        Map<String, Integer> missingPerExp = new HashMap<>();
        Set<String> allExps = new HashSet<>();

        for (ExperimentFile report : reports) {
            allExps.add(report.getExperimentId());
        }

        for (String exp : allExps) {
            int count = 0;
            for (Student student : students) {
                boolean hasReport = reports.stream()
                        .anyMatch(r -> r.getStudentId().equals(student.getStudentId()) && r.getExperimentId().equals(exp));
                if (!hasReport) count++;
            }
            missingPerExp.put(exp, count);
        }

        return missingPerExp;
    }
}