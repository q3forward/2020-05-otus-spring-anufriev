package ru.otus.homework.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import static ru.otus.homework.config.JobConfig.*;

@RequiredArgsConstructor
@ShellComponent
public class BatchCommands {

    private final Job importUserJob;

    private final JobLauncher jobLauncher;
    private final JobOperator jobOperator;
    private final JobExplorer jobExplorer;

    @ShellMethod(value = "(re)startMigrationJob", key = "run")
    public void startMigrationJobWithJobLauncher() throws Exception {
        if (jobExplorer.getLastJobInstance(IMPORT_USER_JOB_NAME)!=null) {
            jobOperator.startNextInstance(IMPORT_USER_JOB_NAME);
        } else {
            JobExecution execution = jobLauncher.run(importUserJob, new JobParametersBuilder()
                    .addLong("run.id", 1L)
                    .toJobParameters());
            System.out.println(execution);
        }
    }

    @ShellMethod(value = "showInfo", key = "i")
    public void showInfo() {
        System.out.println(jobExplorer.getJobNames());
        System.out.println(jobExplorer.getLastJobInstance(IMPORT_USER_JOB_NAME));
    }
}
