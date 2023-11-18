-- Inserting 10 UserEntity records
INSERT INTO UserEntity (FIRSTNAME, LASTNAME, USERNAME, ADDRESS, PASSWORD, ISACTIVE) VALUES
    ('John', 'Doe', 'john.doe', '123 Main St', 'password1', true),
    ('Jane', 'Doe', 'jane.doe', '456 Oak St', 'password2', true),
    ('Alice', 'Smith', 'alice.smith', '789 Elm St', 'password3', true),
    ('Bob', 'Johnson', 'bob.johnson', '101 Pine St', 'password4', true),
    ('Eva', 'Williams', 'eva.williams', '202 Maple St', 'password5', true),
    ('Michael', 'Brown', 'michael.brown', '303 Birch St', 'password6', true),
    ('Olivia', 'Davis', 'olivia.davis', '404 Cedar St', 'password7', true),
    ('Daniel', 'Miller', 'daniel.miller', '505 Walnut St', 'password8', true),
    ('Sophia', 'Wilson', 'sophia.wilson', '606 Spruce St', 'password9', true),
    ('Matthew', 'Moore', 'matthew.moore', '707 Oak St', 'password10', true);

-- Inserting 10 records into the TrainingType table
INSERT INTO TRAININGTYPE(name) VALUES
    ('Boxing'),
    ('Karate'),
    ('Bodybuilding'),
    ('Strict Diet'),
    ('Kickboxing'),
    ('Powerlifting'),
    ('Running'),
    ('Weight Loss'),
    ('Postural and technique corrections'),
    ('High-intensity interval training');

-- Inserting trainee records with the generated user IDs
-- Adjust the user IDs based on the actual IDs generated in the previous step
INSERT INTO TRAINEE (dateOfBirth, USER_ID) VALUES
    ('1990-01-01', 1), -- John Doe
    ('1991-02-02', 2), -- Jane Doe
    ('1992-03-03', 3), -- Alice Smith
    ('1993-04-04', 4), -- Bob Johnson
    ('1994-05-05', 5), -- Eva Williams
    ('1995-06-06', 6), -- Michael Brown
    ('1996-07-07', 7); -- Olivia Davis

-- Associating training types with trainers
-- Assuming that the Trainer entity has a @ManyToMany relationship with TrainingType
-- Adjust column names and data types based on your actual entity mappings
INSERT INTO TRAINER (USER_ID)
VALUES (8), (9), (10);

INSERT INTO TRAINER_TRAININGTYPE(TRAINER_ID, SPECIALIZATIONS_ID) VALUES
-- Daniel Miller with Boxing and Karate
(1, 1), -- Boxing
(1, 2), -- Karate
-- Sophia Wilson with Bodybuilding and Strict Diet
(2, 3), -- Bodybuilding
(2, 4), -- Strict Diet
-- Matthew Moore with Kickboxing and Powerlifting
(3, 5), -- Kickboxing
(3, 6); -- Powerlifting

