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

INSERT INTO TRAINING (TRAININGDURATION, TRAINEE_ID, TRAINER_ID, TRAININGDATE, TRAININGTYPE_ID, TRAININGNAME) VALUES
    (60, 1, 2, '2023-01-18', 8, 'Strength training session with trainee: john.doe and trainer: sophia.wilson'),
    (60, 1, 3, '2023-01-25', 9, 'Boxing technique session with trainee: john.doe and trainer: daniel.miller'),
    (60, 2, 2, '2023-01-12', 8, 'Nutrition consultation with trainee: jane.doe and trainer: sophia.wilson'),
    (60, 2, 1, '2023-01-19', 8, 'Karate training session with trainee: jane.doe and trainer: daniel.miller'),
    (60, 3, 2, '2023-01-13', 8, 'Bodybuilding training session with trainee: alice.smith and trainer: sophia.wilson'),
    (60, 3, 3, '2023-01-20', 9, 'Powerlifting technique session with trainee: alice.smith and trainer: daniel.miller'),
    (60, 4, 2, '2023-01-14', 8, 'Weight loss consultation with trainee: bob.johnson and trainer: sophia.wilson'),
    (60, 4, 3, '2023-01-21', 9, 'Kickboxing training session with trainee: bob.johnson and trainer: daniel.miller'),
    (60, 5, 1, '2023-01-15', 8, 'Strict diet consultation with trainee: eva.williams and trainer: daniel.miller'),
    (60, 5, 2, '2023-01-22', 8, 'Running training session with trainee: eva.williams and trainer: sophia.wilson'),
    (60, 6, 3, '2023-01-16', 9, 'Weight loss coaching session with trainee: michael.brown and trainer: daniel.miller'),
    (60, 6, 2, '2023-01-23', 8, 'Postural and technique corrections session with trainee: michael.brown and trainer: sophia.wilson'),
    (60, 7, 3, '2023-01-17', 9, 'High-intensity interval training session with trainee: olivia.davis and trainer: daniel.miller'),
    (60, 7, 1, '2023-01-24', 8, 'Nutrition consultation with trainee: olivia.davis and trainer: daniel.miller');
