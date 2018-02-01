
/*
    Entry Seed
 */
INSERT INTO entry(id, name, date, mpp_number, fpp_number, opt_number) VALUES
    (1, 'Aug 2017', '2017-08-04', 70, 40, 30),
    (2, 'Oct 2017', '2017-10-26', 60, 40, 20),
    (3, 'Jan 2018', '2018-01-22', 40, 20, 10);


/*
    Student Seed
 */
-- INSERT INTO student(name, email) VALUES
--     ('Ganzorig', 'gerdenebat@mum.edu'),
--     ('Enkhbayasgalan', 'enk@mum.edu');



/*
    Course Seed
 */
INSERT INTO course(id, code, name, level, max_student, prereq_course_id) VALUES
    (1, 'CS472',  'Web Programming', 400, 25, null),
    (2, 'CS525',  'Advanced Software Development', 500, 25, null),
    (3, 'CS544',  'Enterprise Architecture', 500, 25, 1),
    (4, 'CS545',  'Web Application Architecture', 500, 25, 1),
    (5, 'CS435',  'Algorithms', 400, 25, null),
    (6, 'CS471',  'Parallel Programming', 400, 25, null),
    (7, 'CS473',  'Mobile Device Programming', 400, 25, null),
    (8, 'CS522',  'Big Data', 500, 25, null),
    (9, 'CS572',  'Modern Web Applications', 500, 25, 1),
    (10, 'CS523', 'Big Data Technology', 500, 25, null),
    (11, 'CS582', 'Machine Learning', 500, 25, null),
    (12, 'CS425', 'Software Engineering', 400, 25, null);



/*
    Role Seed
 */
INSERT INTO role(role) VALUES
    ('USER'),
    ('ADMIN'),
    ('FACULTY');

/*
    User Seed
 */
INSERT INTO user(active, email, last_name, firstname, password) VALUES
    (1, 'gerdenebat@mum.edu', 'Erdenebat', 'Ganzorig', '$2a$10$8NFDu5a1mhVOiHavtl2cbOPXKTQNw2/9PEwJF1xhZvhyBj4mJrY4O'),
    (2, 'egalsandorj@mum.edu', 'Galsandorj', 'Enkhbayasgalan', '$2a$10$8NFDu5a1mhVOiHavtl2cbOPXKTQNw2/9PEwJF1xhZvhyBj4mJrY4O'),
    (3, 'student@mum.edu', 'Doe', 'John', '$2a$10$8NFDu5a1mhVOiHavtl2cbOPXKTQNw2/9PEwJF1xhZvhyBj4mJrY4O'),
    (4, 'faculty@mum.edu', 'Nolle', 'Steve', '$2a$10$8NFDu5a1mhVOiHavtl2cbOPXKTQNw2/9PEwJF1xhZvhyBj4mJrY4O');

/*
    User-Role Seed
 */
INSERT INTO user_role(user_id, role_id) VALUES
    (1, 2),
    (2, 2),
    (3, 1),
    (4, 3);
