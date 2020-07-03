insert into genres(id, name)
values (1, 'Test genre'), (2, 'Test genre 2'), (3, 'Test genre 3');

insert into authors(id, name)
values (1, 'Test author'), (2, 'Test author 2'), (3, 'Test author 3');

insert into books(id, title, genre_id, author_id)
values (1, 'Book title', 1, 1), (2, 'Book title 2', 2, 2);
