package ru.otus.homework.production;

import org.springframework.stereotype.Service;
import ru.otus.homework.domain.Requirement;

/**
 * Проработка требования аналитиком
 */

@Service
public class AnalyticService {

    public Requirement processRequirement(Requirement requirement) {
        requirement.setAnalyticDescription("руководство разработки для "+requirement);
        System.out.println("Проработано "+requirement.getContent());
        return requirement;
    }
}
