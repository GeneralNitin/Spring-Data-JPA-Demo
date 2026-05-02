package com.general.nitin.springjpa.scheduler;

import com.general.nitin.springjpa.repository.StudentRepository;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class StudentStatsSchedulerTest {

    @Test
    void logStudentCount_readsStudentCountFromRepository() {
        StudentRepository studentRepository = mock(StudentRepository.class);
        when(studentRepository.count()).thenReturn(3L);

        StudentStatsScheduler scheduler = new StudentStatsScheduler(studentRepository);
        scheduler.logStudentCount();

        verify(studentRepository, times(1)).count();
    }

    @Test
    void logStudentCountByCron_readsStudentCountFromRepository() {
        StudentRepository studentRepository = mock(StudentRepository.class);
        when(studentRepository.count()).thenReturn(5L);

        StudentStatsScheduler scheduler = new StudentStatsScheduler(studentRepository);
        scheduler.logStudentCountByCron();

        verify(studentRepository, times(1)).count();
    }
}

