package ru.otus.homework.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.MongoItemReaderBuilder;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.homework.domain.jpa.*;
import ru.otus.homework.domain.mongo.MongoAuthor;
import ru.otus.homework.domain.mongo.MongoBook;
import ru.otus.homework.domain.mongo.MongoComment;
import ru.otus.homework.domain.mongo.MongoGenre;
import ru.otus.homework.repository.*;
import ru.otus.homework.service.ClearService;
import ru.otus.homework.service.TransformService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Configuration
public class JobConfig {
    private static final int CHUNK_SIZE = 5;
    private final Logger logger = LoggerFactory.getLogger("Batch");

    public static final String OUTPUT_FILE_NAME = "outputFileName";
    public static final String INPUT_FILE_NAME = "inputFileName";
    public static final String IMPORT_USER_JOB_NAME = "importUserJob";

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private LinkRepository linkRepository;

    @Autowired
    private ClearService clearService;

    /************ FLOWS **************/

    @Bean
    public Job importUserJob(Flow splitFlow, Step bookStep, Step commentStep) {
        return jobBuilderFactory.get(IMPORT_USER_JOB_NAME)
                .incrementer(new RunIdIncrementer())
                .start(splitFlow)
                .next(bookStep)
                .next(commentStep)
                .build()
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(JobExecution jobExecution) {
                        clearService.clearTargetTables();
                    }

                    @Override
                    public void afterJob(JobExecution jobExecution) {}
                })
                .build();
    }

    @Bean
    public Flow splitFlow(Flow authorFlow, Flow genreFlow, TaskExecutor taskExecutor) {
        return new FlowBuilder<SimpleFlow>("splitFlow")
                .split(taskExecutor)
                .add(authorFlow, genreFlow)
                .build();
    }

    @Bean
    public Flow authorFlow(Step authorStep) {
        return new FlowBuilder<SimpleFlow>("authorFlow")
                .start(authorStep)
                .build();
    }

    @Bean
    public Flow genreFlow(Step genreStep) {
        return new FlowBuilder<SimpleFlow>("genreFlow")
                .start(genreStep)
                .build();
    }

    @Bean
    public TaskExecutor taskExecutor(){
        return new SimpleAsyncTaskExecutor("authorGenreThread");
    }

    /************ READERS **************/
    @StepScope
    @Bean
    public MongoItemReader<MongoAuthor> authorItemReader(MongoTemplate template) {
        return new MongoItemReaderBuilder<MongoAuthor>()
                .name("authorItemReader")
                .template(template)
                .targetType(MongoAuthor.class)
                .jsonQuery("{}")
                .sorts(new HashMap<>())
                .build();
    }

    @StepScope
    @Bean
    public MongoItemReader<MongoGenre> genreItemReader(MongoTemplate template) {
        return new MongoItemReaderBuilder<MongoGenre>()
                .name("genreItemReader")
                .template(template)
                .targetType(MongoGenre.class)
                .jsonQuery("{}")
                .sorts(new HashMap<>())
                .build();
    }

    @StepScope
    @Bean
    public MongoItemReader<MongoBook> bookReader(MongoTemplate template) {
        return new MongoItemReaderBuilder<MongoBook>()
                .name("bookItemReader")
                .template(template)
                .targetType(MongoBook.class)
                .jsonQuery("{}")
                .sorts(new HashMap<>())
                .build();
    }

    @StepScope
    @Bean
    public MongoItemReader<MongoComment> commentReader(MongoTemplate template) {
        return new MongoItemReaderBuilder<MongoComment>()
                .name("commentItemReader")
                .template(template)
                .targetType(MongoComment.class)
                .jsonQuery("{}")
                .sorts(new HashMap<>())
                .build();
    }

    /************ PROCESSORS **************/
    @StepScope
    @Bean
    public ItemProcessor<MongoAuthor, Author> authorProcessor(TransformService transformService) {
        return transformService::getJpaAuthor;
    }

    @StepScope
    @Bean
    public ItemProcessor<MongoGenre, Genre> genreProcessor(TransformService transformService) {
        return transformService::getJpaGenre;
    }

    @StepScope
    @Bean
    public ItemProcessor<MongoBook, Book> bookProcessor(TransformService transformService) {
        return transformService::getJpaBook;
    }

    @StepScope
    @Bean
    public ItemProcessor<MongoComment, Comment> commentProcessor(TransformService transformService) {
        return transformService::getJpaComment;
    }

    /************ WRITERS **************/
    @StepScope
    @Bean
    public RepositoryItemWriter<Author> authorWriter(AuthorRepository authorRepository) {
        return new RepositoryItemWriterBuilder<Author>()
                .repository(authorRepository)
                .methodName("save")
                .build();
    }

    @StepScope
    @Bean
    public RepositoryItemWriter<Genre> genreWriter(GenreRepository genreRepository) {
        return new RepositoryItemWriterBuilder<Genre>()
                .repository(genreRepository)
                .methodName("save")
                .build();
    }

    @StepScope
    @Bean
    public RepositoryItemWriter<Book> bookWriter(BookRepository bookRepository) {
        return new RepositoryItemWriterBuilder<Book>()
                .repository(bookRepository)
                .methodName("save")
                .build();
    }

    @StepScope
    @Bean
    public RepositoryItemWriter<Comment> commentWriter(CommentRepository commentRepository) {
        return new RepositoryItemWriterBuilder<Comment>()
                .repository(commentRepository)
                .methodName("save")
                .build();
    }

    /************ STEPS **************/

    @Bean
    public Step authorStep(RepositoryItemWriter<Author> writer, MongoItemReader<MongoAuthor> reader, ItemProcessor<MongoAuthor, Author> authorProcessor) {
        List<Link> linkList = new ArrayList<>();
        return stepBuilderFactory.get("authorStep")
                .<MongoAuthor, Author>chunk(CHUNK_SIZE)
                .reader(reader)
                .processor(authorProcessor)
                .writer(writer)
                .listener(new ItemReadListener<>() {
                    public void beforeRead() {}

                    public void afterRead(MongoAuthor monAuthor) {
                        linkList.add(new Link(monAuthor.getId(), Author.class.getSimpleName()));
                    }

                    public void onReadError(Exception e) {}
                })
                .listener(new ItemWriteListener<>() {
                    public void beforeWrite(List items) {}

                    public void afterWrite(List items) {
                        for (int i=0; i<items.size(); i++) {
                            linkList.get(i).setJpaId(((Author)items.get(i)).getId());
                        }
                        linkRepository.saveAll(new ArrayList<>(linkList));
                    }

                    public void onWriteError(Exception exception, List items) {}
                })
                .listener(new StepExecutionListener(){
                    @Override
                    public void beforeStep(StepExecution stepExecution) {
                        linkList.clear();
                    }

                    @Override
                    public ExitStatus afterStep(StepExecution stepExecution) {
                        return null;
                    }
                })
                .build();
    }

    @Bean
    public Step genreStep(RepositoryItemWriter<Genre> writer, MongoItemReader<MongoGenre> reader, ItemProcessor<MongoGenre, Genre> genreProcessor) {
        List<Link> linkList = new ArrayList<>();
        return stepBuilderFactory.get("genreStep")
                .<MongoGenre, Genre>chunk(CHUNK_SIZE)
                .reader(reader)
                .processor(genreProcessor)
                .writer(writer)
                .listener(new ItemReadListener<>() {
                    public void beforeRead() {}

                    public void afterRead(MongoGenre monGenre) {
                        linkList.add(new Link(monGenre.getId(), Genre.class.getSimpleName()));
                    }

                    public void onReadError(Exception e) {}
                })
                .listener(new ItemWriteListener<>() {
                    public void beforeWrite(List items) {}

                    public void afterWrite(List items) {
                        for (int i=0; i<items.size(); i++) {
                            linkList.get(i).setJpaId(((Genre)items.get(i)).getId());
                        }
                        linkRepository.saveAll(new ArrayList<>(linkList));
                        linkList.clear();
                    }

                    public void onWriteError(Exception exception, List items) {}
                })
                .listener(new StepExecutionListener(){
                    @Override
                    public void beforeStep(StepExecution stepExecution) {
                        linkList.clear();
                    }

                    @Override
                    public ExitStatus afterStep(StepExecution stepExecution) {
                        return null;
                    }
                })
                .build();
    }

    @Bean
    public Step bookStep(RepositoryItemWriter<Book> writer, MongoItemReader<MongoBook> reader, ItemProcessor<MongoBook, Book> bookProcessor) {
        List<Link> linkList = new ArrayList<>();
        return stepBuilderFactory.get("bookStep")
                .<MongoBook, Book>chunk(CHUNK_SIZE)
                .reader(reader)
                .processor(bookProcessor)
                .writer(writer)
                .listener(new ItemReadListener<>() {
                    public void beforeRead() {}

                    public void afterRead(MongoBook monBook) {
                        linkList.add(new Link(monBook.getId(), Book.class.getSimpleName()));
                    }

                    public void onReadError(Exception e) {}
                })
                .listener(new ItemWriteListener<>() {
                    public void beforeWrite(List items) {}

                    public void afterWrite(List items) {
                        for (int i=0; i<items.size(); i++) {
                            linkList.get(i).setJpaId(((Book)items.get(i)).getId());
                        }
                        linkRepository.saveAll(new ArrayList<>(linkList));
                        linkList.clear();
                    }

                    public void onWriteError(Exception exception, List items) {}
                })
                .listener(new StepExecutionListener(){
                    @Override
                    public void beforeStep(StepExecution stepExecution) {
                        linkList.clear();
                    }

                    @Override
                    public ExitStatus afterStep(StepExecution stepExecution) {
                        return null;
                    }
                })
                .build();
    }

    @Bean
    public Step commentStep(RepositoryItemWriter<Comment> writer, MongoItemReader<MongoComment> reader, ItemProcessor<MongoComment, Comment> commentProcessor) {
        return stepBuilderFactory.get("commentStep")
                .<MongoComment, Comment>chunk(CHUNK_SIZE)
                .reader(reader)
                .processor(commentProcessor)
                .writer(writer)
                .build();
    }

}

