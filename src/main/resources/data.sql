/*
    Student Seed
 */
INSERT INTO student(name, email) VALUES
    ("Ganzorig", "gerdenebat@mum.edu"),
    ("Enkhbayasgalan", "enk@mum.edu");



/*
    Course Seed
 */
INSERT INTO course(code, name, description) VALUES
    ("CS472", "Web Programming", "WAP"),
    ("CS525", "Advanced Software Development", "ASD"),
    ("CS544", " Enterprise Architecture", "EA"),
    ("CS545", "Web Application Architecture", "WAA"),
    ("CS435", "Algorithms", "Algo"),
    ("CS471", "Parallel Programming", "PP"),
    ("CS473", "Mobile Device Programming", "MDP"),
    ("CS522", "Big Data", "BD"),
    ("CS572", "Modern Web Applications", "MWA"),
    ("CS523", "Big Data Technology", "BDT"),
    ("CS582", "Machine Learning", "ML"),
    ("CS425", "Software Engineering", "SWE");



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
    (1, "gerdenebat@mum.edu", "Erdenebat", "Ganzorig", "$2a$10$8NFDu5a1mhVOiHavtl2cbOPXKTQNw2/9PEwJF1xhZvhyBj4mJrY4O");

/*
    User-Role Seed
 */
INSERT INTO user_role(user_id, role_id) VALUES
    (1, 2);
