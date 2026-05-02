package com.general.nitin.springjpa.scheduler;

import com.general.nitin.springjpa.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@ConditionalOnProperty(name = "app.scheduler.enabled", havingValue = "true", matchIfMissing = true)
public class StudentStatsScheduler {

    private final StudentRepository studentRepository;

    public StudentStatsScheduler(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Scheduled(
            fixedDelayString = "${app.scheduler.student-stats.fixed-delay-ms:300000}",
            initialDelayString = "${app.scheduler.student-stats.initial-delay-ms:30000}"
    )
    public void logStudentCount() {
        long studentCount = studentRepository.count();
        log.info("[Scheduler:fixed-delay] Total students in DB: {}", studentCount);
    }

    @Scheduled(
            cron = "${app.scheduler.student-stats.cron:-}",
            zone = "${app.scheduler.student-stats.time-zone:UTC}"
    )
    public void logStudentCountByCron() {
        long studentCount = studentRepository.count();
        log.info("[Scheduler:cron] Total students in DB: {}", studentCount);
    }
}


