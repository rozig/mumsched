/*
    Entry Seed
 */
INSERT INTO entry(id, name, date, mpp_number, fpp_number, opt_number) VALUES
    (1, 'Aug 2017', '2017-08-04', 70, 40, 30),
    (2, 'Oct 2017', '2017-10-26', 60, 40, 20),
    (3, 'Jan 2018', '2018-01-22', 40, 20, 10);



/*
    Course Seed
 */
INSERT INTO course(id, code, name, level, max_student, prereq_course_id) VALUES
    (1, 'CS390', 'Fundamental Programming Practices', 400, 25, null),
    (2, 'CS401', 'Modern Programming Practices', 400, 25, null),
    (3, 'CS472',  'Web Programming', 400, 25, null),
    (4, 'CS525',  'Advanced Software Development', 500, 25, null),
    (5, 'CS544',  'Enterprise Architecture', 500, 25, 3),
    (6, 'CS545',  'Web Application Architecture', 500, 25, 3),
    (7, 'CS435',  'Algorithms', 400, 25, null),
    (8, 'CS471',  'Parallel Programming', 400, 25, null),
    (9, 'CS473',  'Mobile Device Programming', 400, 25, null),
    (10, 'CS522',  'Big Data', 500, 25, null),
    (11, 'CS572',  'Modern Web Applications', 500, 25, 3),
    (12, 'CS523', 'Big Data Technology', 500, 25, null),
    (13, 'CS582', 'Machine Learning', 500, 25, null),
    (14, 'CS425', 'Software Engineering', 400, 25, null);



/*
    Role Seed
 */
INSERT INTO role(role) VALUES
    ('STUDENT'),
    ('ADMIN'),
    ('FACULTY');



/*
    User Seed
 */
INSERT INTO user(id, active, email, password, activation_token) VALUES
    (1, TRUE, 'gerdenebat@mum.edu', '$2a$10$8NFDu5a1mhVOiHavtl2cbOPXKTQNw2/9PEwJF1xhZvhyBj4mJrY4O', '' ),
    (2, TRUE, 'egalsandorj@mum.edu', '$2a$10$8NFDu5a1mhVOiHavtl2cbOPXKTQNw2/9PEwJF1xhZvhyBj4mJrY4O', '' ),
    (3, TRUE, 'student@mum.edu', '$2a$10$8NFDu5a1mhVOiHavtl2cbOPXKTQNw2/9PEwJF1xhZvhyBj4mJrY4O', 'cc0af8d8-9339-4add-8b36-6167d30c56eb' ),
    (4, TRUE, 'faculty@mum.edu', '$2a$10$8NFDu5a1mhVOiHavtl2cbOPXKTQNw2/9PEwJF1xhZvhyBj4mJrY4O', '' ),
    (5, FALSE, 'student2@mum.edu', '$2a$10$8NFDu5a1mhVOiHavtl2cbOPXKTQNw2/9PEwJF1xhZvhyBj4mJrY4O', 'cc0af8d8-9339-4add-8b36-6167d30c56eb' )
;



/*
    Faculty Seed
 */
INSERT INTO faculty(id, description, email, firstname, lastname, user_id) VALUES
    (1,'Mr President', 'gerdenebat@mum.edu', 'Ganzorig', 'Erdenebat', 1),
    (2,'Associate Chairman and Associate Professor of Computer Science\nB.A., University of Missouri\nM.S., University of Kansas', 'snolle@mum.edu', 'Steve', 'Nolle', 4);



/*
    User-Role Seed
 */
INSERT INTO user_role(user_id, role_id) VALUES
    (1, 2),
    (2, 2),
    (3, 1),
    (4, 3),
    (5, 1)
;



/*
    Block Seed
 */
INSERT INTO block(name, start_date, end_date) VALUES
    ('Test Block 1', '2018-02-01 00:00:00', '2018-03-01 00:00:00');



/*
    Section Seed
 */
INSERT INTO section(building, room_number, block_id, course_id, faculty_id) VALUES
    ('Mc Laughlin', '109', 1, 1, 1);

/*
    Faculty Course Seed
 */
INSERT INTO faculty_courses(faculty_id, course_id) VALUES
    (2, 12),
    (2, 13);

/*
    Student Seed
 */
INSERT INTO student(id, description, email, firstname, lastname, birth_date, isusresident, pt_type, track, user_id, entry_id) VALUES
    (1, '', 'student@mum.edu', 'Student1', 'John', '1988-01-21', FALSE, TRUE, TRUE, 3, 2)
;

