package ru.otus.homework.config;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import ru.otus.homework.service.ClearService;

public class ClearTableTasklet implements Tasklet {

    private ClearService clearService;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        clearService.clearTargetTables();
        return RepeatStatus.FINISHED;
    }

    public void setClearService(ClearService clearService) {
        this.clearService = clearService;
    }
}
