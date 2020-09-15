package ru.otus.homework.config;

import org.springframework.batch.core.ItemWriteListener;
import ru.otus.homework.domain.HaveId;
import ru.otus.homework.domain.jpa.Link;
import ru.otus.homework.service.TransformService;

import java.util.List;

public class WriteListener<T extends HaveId<Long>> implements ItemWriteListener<HaveId> {

    private List<Link> linkList;
    private final TransformService transformService;

    public WriteListener(List<Link> linkList, TransformService transformService) {
        this.linkList = linkList;
        this.transformService = transformService;
    }

    @Override
    public void beforeWrite(List items) {}

    @Override
    public void afterWrite(List<? extends HaveId> items) {
        transformService.saveLinks(items, linkList);
    }

    @Override
    public void onWriteError(Exception exception, List items) {}

}
