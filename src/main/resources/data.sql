/*
    Student Seed
 */
INSERT INTO student(name, email) VALUES
    ("Ganzorig", "gerdenebat@mum.edu"),
    ("Enkhbayasgalan", "enk@mum.edu");



/*
    Course Seed
 */
INSERT INTO course(name, description) VALUES
    ("Web Programming", "Web Programming fundamentals"),
    ("Advanced Software Development", "Web Programming fundamentals"),
    (" Enterprise Architecture", "Web Programming fundamentals"),
    ("Web Application Architecture", "Web Programming fundamentals"),
    ("Algorithms", "Web Programming fundamentals"),
    ("Parallel Programming", "Web Programming fundamentals"),
    ("Mobile Device Programming", "Web Programming fundamentals"),
    ("Big Data", "Web Programming fundamentals"),
    ("Modern Web Applications", "Web Programming fundamentals"),
    ("Big Data Technology", "Web Programming fundamentals"),
    ("Machine Learning", "Web Programming fundamentals"),
    ("Software Engineering", "Web Programming fundamentals");



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