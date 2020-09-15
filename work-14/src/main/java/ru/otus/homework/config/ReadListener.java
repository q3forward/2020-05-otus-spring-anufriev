package ru.otus.homework.config;

import org.springframework.batch.core.ItemReadListener;
import ru.otus.homework.domain.HaveId;
import ru.otus.homework.domain.jpa.Link;

import java.util.List;

public class ReadListener<T extends HaveId<String>> implements ItemReadListener<T> {

    private List<Link> linkList;
    private Class clazz;

    public ReadListener(List<Link> linkList, Class clazz) {
        this.linkList = linkList;
        this.clazz = clazz;
    }

    @Override
    public void beforeRead() {}

    @Override
    public void afterRead(T item) {
        linkList.add(new Link(item.getId(), clazz.getSimpleName()));
    }

    @Override
    public void onReadError(Exception ex) {}
}
