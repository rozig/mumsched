/*
    Student Seed
 */
-- INSERT INTO student(name, email) VALUES
--     ("Ganzorig", "gerdenebat@mum.edu"),
--     ("Enkhbayasgalan", "enk@mum.edu");



/*
    Course Seed
 */
-- INSERT INTO course(id, code, name, description, prereq_course_id) VALUES
--     (1, "CS472", "Web Programming", "WAP", null),
--     (2, "CS525", "Advanced Software Development", "ASD", null),
--     (3, "CS544", " Enterprise Architecture", "EA", null),
--     (4, "CS545", "Web Application Architecture", "WAA", 1),
--     (5, "CS435", "Algorithms", "Algo", null),
--     (6, "CS471", "Parallel Programming", "PP", null),
--     (7, "CS473", "Mobile Device Programming", "MDP", null),
--     (8, "CS522", "Big Data", "BD", null),
--     (9, "CS572", "Modern Web Applications", "MWA", 1),
--     (10, "CS523", "Big Data Technology", "BDT", null),
--     (11, "CS582", "Machine Learning", "ML", null),
--     (12, "CS425", "Software Engineering", "SWE", null);



/*
    Role Seed
 */
INSERT INTO role(role) VALUES
    ("USER"),
    ("ADMIN"),
    ("FACULTY");

/*
    User Seed
 */
INSERT INTO user(active, email, last_name, name, password) VALUES
    (1, "gerdenebat@mum.edu", "Erdenebat", "Ganzorig", "$2a$10$8NFDu5a1mhVOiHavtl2cbOPXKTQNw2/9PEwJF1xhZvhyBj4mJrY4O"),
    (2, "egalsandorj@mum.edu", "Galsandorj", "Enkhbayasgalan", "$2a$10$mL9l.om2APqDfuqIndw9a.XmE67dm/0e5tVMYo9ldAliD9U6CLNa2");

/*
    User-Role Seed
 */
INSERT INTO user_role(user_id, role_id) VALUES
    (1, 2);
