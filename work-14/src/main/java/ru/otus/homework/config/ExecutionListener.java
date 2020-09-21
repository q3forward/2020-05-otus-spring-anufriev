package ru.otus.homework.config;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import ru.otus.homework.domain.jpa.Link;

import java.util.List;

public class ExecutionListener implements StepExecutionListener {

    private List<Link> linkList;

    public ExecutionListener(List<Link> linkList) {
        this.linkList = linkList;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        linkList.clear();
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return null;
    }
}
