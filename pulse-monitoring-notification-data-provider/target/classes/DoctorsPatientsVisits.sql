insert into doctors values ('doctor1@gmail.com','doctor1');
insert into doctors values ('doctor2@gmail.com','doctor2');
insert into patients values (123, 'Vasya');
insert into patients values (124, 'Asya');
insert into visits (date, doctor_email, patient_id) values('2023-03-01', 'doctor1@gmail.com', 123);
insert into visits (date, doctor_email, patient_id) values('2023-03-15', 'doctor2@gmail.com', 123);
insert into visits (date, doctor_email, patient_id) values('2023-03-20', 'doctor1@gmail.com', 124);