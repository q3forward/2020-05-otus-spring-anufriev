package ru.otus.homework.config;

import org.springframework.batch.core.ItemReadListener;
import ru.otus.homework.domain.HaveId;
import ru.otus.homework.domain.jpa.Link;
import ru.otus.homework.service.TransformService;

import java.util.List;

public class ReadListener<T extends HaveId<String>> implements ItemReadListener<T> {

    private List<Link> linkList;
    private final TransformService transformService;
    private Class clazz;

    public ReadListener(List<Link> linkList, TransformService transformService, Class clazz) {
        this.linkList = linkList;
        this.transformService = transformService;
        this.clazz = clazz;
    }

    @Override
    public void beforeRead() {}

    @Override
    public void afterRead(T item) {
        transformService.addLink(item, linkList, clazz);
    }

    @Override
    public void onReadError(Exception ex) {}
}
