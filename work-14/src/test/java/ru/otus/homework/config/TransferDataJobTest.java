package ru.otus.homework.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.homework.domain.jpa.Author;
import ru.otus.homework.domain.jpa.Book;
import ru.otus.homework.domain.jpa.Comment;
import ru.otus.homework.domain.jpa.Genre;
import ru.otus.homework.repository.AuthorRepository;
import ru.otus.homework.repository.BookRepository;
import ru.otus.homework.repository.CommentRepository;
import ru.otus.homework.repository.GenreRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.otus.homework.config.JobConfig.*;

@SpringBootTest
@SpringBatchTest
class TransferDataJobTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;

    @Autowired
    private AuthorRepository authorRepo;

    @Autowired
    private GenreRepository genreRepo;

    @Autowired
    private BookRepository bookRepo;

    @Autowired
    private CommentRepository commentRepo;

    @BeforeEach
    void clearMetaData() {
        jobRepositoryTestUtils.removeJobExecutions();
    }

    @Test
    void testJob() throws Exception {

        Job job = jobLauncherTestUtils.getJob();
        assertThat(job).isNotNull()
                .extracting(Job::getName)
                .isEqualTo(IMPORT_USER_JOB_NAME);

        JobParameters parameters = new JobParametersBuilder()
                .toJobParameters();
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(parameters);

        assertThat(jobExecution.getExitStatus().getExitCode()).isEqualTo("COMPLETED");
        List<Author> authorJpaList = authorRepo.findAll();
        Author author1 = authorJpaList.get(0);
        assertThat(authorJpaList.size()).isEqualTo(3);
        assertThat(author1.getName()).isEqualTo("Ремарк");

        List<Genre> genreJpaList = genreRepo.findAll();
        Genre genre1 = genreJpaList.get(0);
        assertThat(genreJpaList.size()).isEqualTo(3);
        assertThat(genre1.getName()).isEqualTo("Классическая проза");

        List<Book> bookJpaList = bookRepo.findAll();
        Book book1 = bookJpaList.get(0);
        assertThat(bookJpaList.size()).isEqualTo(4);
        assertThat(book1.getTitle()).isEqualTo("Три товарища");
        assertThat(book1.getAuthor()).isEqualToComparingFieldByField(author1);
        assertThat(book1.getGenre()).isEqualToComparingFieldByField(genre1);

        List<Comment> commentJpaList = commentRepo.findAll();
        Comment comment1 = commentJpaList.get(0);
        assertThat(commentJpaList.size()).isEqualTo(3);
        assertThat(comment1.getText()).isEqualTo("Очень хорошая книга");
        assertThat(comment1.getBook()).isEqualToComparingFieldByField(book1);
    }
}