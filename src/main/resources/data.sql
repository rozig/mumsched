/*
    Role Seed
 */
INSERT INTO role(id, role) VALUES
    (1, 'STUDENT'),
    (2, 'ADMIN'),
    (3, 'FACULTY');


/*
    User Seed
 */
INSERT INTO user(id, active, email, password, activation_token) VALUES
    (1, TRUE, 'gerdenebat@mum.edu', '$2a$10$8NFDu5a1mhVOiHavtl2cbOPXKTQNw2/9PEwJF1xhZvhyBj4mJrY4O', '' ),
    (2, TRUE, 'egalsandorj@mum.edu', '$2a$10$8NFDu5a1mhVOiHavtl2cbOPXKTQNw2/9PEwJF1xhZvhyBj4mJrY4O', '' ),
    (3, FALSE, 'student@mum.edu', '$2a$10$8NFDu5a1mhVOiHavtl2cbOPXKTQNw2/9PEwJF1xhZvhyBj4mJrY4O', 'cc0af8d8-9339-4add-8b36-6167d30c56eb' ),
    (4, TRUE, 'student1@mum.edu', '$2a$10$8NFDu5a1mhVOiHavtl2cbOPXKTQNw2/9PEwJF1xhZvhyBj4mJrY4O', 'cc0af8d8-9339-4add-8b36-6167d30c56eb' ),
    (5, TRUE, 'faculty@mum.edu', '$2a$10$8NFDu5a1mhVOiHavtl2cbOPXKTQNw2/9PEwJF1xhZvhyBj4mJrY4O', '' ),
    (6, TRUE, 'faculty1@mum.edu', '$2a$10$8NFDu5a1mhVOiHavtl2cbOPXKTQNw2/9PEwJF1xhZvhyBj4mJrY4O', '' ),
    (7, TRUE, 'faculty2@mum.edu', '$2a$10$8NFDu5a1mhVOiHavtl2cbOPXKTQNw2/9PEwJF1xhZvhyBj4mJrY4O', '' ),
    (8, TRUE, 'faculty3@mum.edu', '$2a$10$8NFDu5a1mhVOiHavtl2cbOPXKTQNw2/9PEwJF1xhZvhyBj4mJrY4O', '' ),
    (9, TRUE, 'faculty4@mum.edu', '$2a$10$8NFDu5a1mhVOiHavtl2cbOPXKTQNw2/9PEwJF1xhZvhyBj4mJrY4O', '' );


/*
    User-Role Seed
 */
INSERT INTO user_role(user_id, role_id) VALUES
    (1, 2),
    (2, 2),
    (3, 1),
    (4, 1),
    (5, 3),
    (6, 3),
    (7, 3),
    (8, 3),
    (9, 3)
    ;
    
    
/*
    Entry Seed
 */
INSERT INTO entry(id, name, date, mpp_number, fpp_number, opt_number) VALUES
    (1, 'Aug 2017', '2017-08-04', 10, 10, 3),
    (2, 'Oct 2017', '2017-10-26', 10, 8, 2),
    (3, 'Jan 2018', '2018-01-22', 20, 10, 1);


/*
    Block Seed
 */
INSERT INTO block(id, name, start_date, end_date) VALUES
    (1, "November 2017", "2017-11-02 00:00:00", "2017-12-01 00:00:00"),
    (2, "December 2017", "2017-12-02 00:00:00", "2018-01-01 00:00:00"),
    (3, "January 2018", "2017-01-02 00:00:00", "2018-02-01 00:00:00"),
    (4, "February 2018", "2017-02-02 00:00:00", "2018-03-01 00:00:00"),
    (5, "March 2018", "2017-03-02 00:00:00", "2018-04-01 00:00:00"),
    (6, "April 2018", "2017-04-02 00:00:00", "2018-05-01 00:00:00"),
    (7, "May 2018", "2017-05-02 00:00:00", "2018-06-01 00:00:00")
    ;


/*
    Entry_blocks Seed
 */
INSERT INTO entry_blocks(block_id, entry_id) VALUES
    (1, 2),
    (2, 2),
    (3, 2),
    (4, 2),
    (5, 2),
    (6, 2),
    (7, 2)
    ;

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
    (12, 'CS425', 'Software Engineering', 400, 25, null),
    (13, 'CS390', 'Fundamental Programming Practices', 400, 25, null),
    (14, 'CS401', 'Modern Programming Practices', 500, 25, null);


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
    (1, "Mr President mfuckers", "gerdenebat@mum.edu", "Ganzorig", "Erdenebat", 1),
    (2, null, "faculty1@mum.edu", "Faculty1 firstname", "Faculty1 lastname", 5),
    (3, null, "faculty2@mum.edu", "Faculty2 firstname", "Faculty2 lastname", 6),
    (4, null, "faculty3@mum.edu", "Faculty3 firstname", "Faculty3 lastname", 7),
    (5, null, "faculty4@mum.edu", "Faculty4 firstname", "Faculty4 lastname", 8);


/*
    Student Seed
 */
INSERT INTO user_role(user_id, role_id) VALUES
    (1, 2),
    (2, 2),
    (3, 1),
    (4, 3),
    (5, 1)
;


/*
    Section Seed
 */
INSERT INTO section(building, room_number, block_id, course_id, faculty_id) VALUES
    ("Mc Laughlin", "108", 1, 13, 1),
    ("Mc Laughlin", "109", 1, 13, 2),
    ("Mc Laughlin", "208", 1, 14, 3),
    ("Mc Laughlin", "209", 1, 14, 4),
    ("Mc Laughlin", "210", 1, 14, 5),
    
    ("Mc Laughlin", "108", 2, 14, 1),
    ("Mc Laughlin", "109", 2, 14, 2),
    ("Mc Laughlin", "208", 2, 12, 3),
    ("Mc Laughlin", "209", 2, 5, 4),
    ("Mc Laughlin", "210", 2, 6, 5),
    
    ("Mc Laughlin", "108", 3, 1, 1),
    ("Mc Laughlin", "109", 3, 5, 2),
    ("Mc Laughlin", "208", 3, 6, 3),
    ("Mc Laughlin", "209", 3, 2, 4),
    ("Mc Laughlin", "210", 3, 3, 5),
    
    ("Mc Laughlin", "108", 4, 6, 1),
    ("Mc Laughlin", "109", 4, 7, 2),
    ("Mc Laughlin", "208", 4, 8, 3),
    ("Mc Laughlin", "209", 4, 9, 4),
    ("Mc Laughlin", "210", 4, 2, 5),
    
    ("Mc Laughlin", "108", 5, 1, 1),
    ("Mc Laughlin", "109", 5, 10, 2),
    ("Mc Laughlin", "208", 5, 8, 3),
    ("Mc Laughlin", "209", 5, 9, 4),
    ("Mc Laughlin", "210", 5, 11, 5),
    
    ("Mc Laughlin", "108", 6, 2, 1),
    ("Mc Laughlin", "109", 6, 3, 2),
    ("Mc Laughlin", "208", 6, 4, 3)
    ;

/*
    Faculty Course Seed
 */
INSERT INTO faculty_courses(faculty_id, course_id) VALUES
    (2, 12),
    (2, 13);

/*
    Student Seed
 */
INSERT INTO student(id, description, firstname, lastname, birth_date, isusresident, pt_type, track, user_id, entry_id) VALUES
    (1, '', 'Student1', 'John', '1988-01-21', FALSE, TRUE, TRUE, 3, 2)
;