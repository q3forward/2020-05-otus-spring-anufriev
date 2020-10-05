package ru.otus.homework.integration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import ru.otus.homework.domain.Requirement;
import ru.otus.homework.domain.Concept;
import ru.otus.homework.domain.Program;

import java.util.Collection;

@Configuration
public class Flow {

    @Bean
    public DirectChannel requirementChannel() {
        return MessageChannels.direct().get();
    }

    @Bean
    public DirectChannel developChannel() {
        return MessageChannels.direct().get();
    }

    @Bean
    public DirectChannel testingChannel() {
        return MessageChannels.direct().get();
    }

    @Bean
    public DirectChannel releaseChannel() {
        return MessageChannels.direct().get();
    }


    @Bean
    public IntegrationFlow productionFlow() {
        return IntegrationFlows.from("requirementChannel")
                .split()
                .handle("analyticService", "processRequirement")
                .aggregate()
                .<Collection<Requirement>, Concept>transform(list -> new Concept(list))
                .channel("developChannel")
                .get();
    }

    @Bean
    public IntegrationFlow developFlow() {
        return IntegrationFlows.from("developChannel")
                .handle("developService", "code")
                .channel("testingChannel")
                .get();
    }

    @Bean
    public IntegrationFlow testingFlow() {
        return IntegrationFlows.from("testingChannel")
                .handle("testingService", "testProgram")
                .<Program, Boolean> route(prog -> prog.getTestResult(),
                        m -> m.subFlowMapping(true, s -> s.channel("releaseChannel"))
                                .subFlowMapping(false, s -> s.transform(Program.class, Program::getConcept).channel("developChannel")))
                .get();
    }

    @Bean
    public IntegrationFlow releaseFlow() {
        return IntegrationFlows.from("releaseChannel")
                .handle("documentService", "writeManual")
                .handle("buildService","build")
                .channel("productChannel")
                .get();
    }
}
