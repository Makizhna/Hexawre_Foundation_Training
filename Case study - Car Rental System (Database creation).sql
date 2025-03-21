
/*Case study*/
create database carrentalsystem;
use carrentalsystem;


create table vehicle (
    vehicle_id int auto_increment primary key,
    make varchar(50) not null,
    model varchar(50) not null,
    year int not null,
    daily_rate decimal(10,2) not null,
    status varchar(20) not null,  
    passenger_capacity int not null,
    engine_capacity decimal(5,2) not null
);


create table customer (
    customer_id int primary key,
    first_name varchar(50) not null,
    last_name varchar(50) not null,
    email varchar(100) unique not null,
    phone_number varchar(15) unique not null
);


create table lease (
    lease_id int primary key,
    vehicle_id int not null,
    customer_id int not null,
    start_date date not null,
    end_date date not null,
    type varchar(20) not null, 
    foreign key (vehicle_id) references vehicle(vehicle_id),
    foreign key (customer_id) references customer(customer_id)
);


create table payment (
    payment_id int primary key,
    lease_id int not null,
    payment_date date not null,
    amount decimal(10,2) not null,
    foreign key (lease_id) references lease(lease_id)
);



insert into vehicle (make, model, year, daily_rate, status, passenger_capacity, engine_capacity) values
('Maruti', 'Swift', 2018, 35.00, 'available', 5, 1.2),
('Hyundai', 'i20', 2020, 40.00, 'not_available', 5, 1.4),
('Kia', 'Sonet', 2020, 48.00, 'available', 5, 1.0),
('Mahindra', 'XUV700', 2023, 95.00, 'available', 7, 2.2),
('Audi', 'A4', 2021, 85.00, 'not_available', 5, 2.0),
('Renault', 'Kiger', 2021, 42.00, 'available', 5, 1.0),
('BMW', 'X5', 2022, 100.00, 'available', 7, 3.0),
('Ford', 'Focus', 2021, 55.00, 'not_available', 5, 2.0),
('Toyota', 'Fortuner', 2022, 90.00, 'not_available', 7, 2.8),
('Tata', 'Nexon', 2019, 50.00, 'available', 5, 1.5);

select * from vehicle;

insert into customer (customer_id, first_name, last_name, email, phone_number) values
(111,'Meera', 'Jasmine', 'meera_jas@gmail.com', '8774567890'),
(211,'Amaina', 'Banu', 'aamina.banu@gmail.com', '6787654321'),
(311,'Rakshana', 'Shre', 'rakshana@gmail.com', '5678978908'),
(411,'Reethiga', 'Shre', 'reethi123@gmail.com', '8657838299'),
(511,'Senthil', 'Velan', 'senthil$345@gmail.com', '98654364876'),
(611,'Ram', 'kumar', 'ramkumar@gmail.com', '8674845923'),
(711,'Siva', 'Ranjani', 'sivaranjani@gmail.com', '8895467835'),
(811,'Moshina', 'Rahmath', 'moshina24@gmail.com', '8957428673'),
(911,'Indhu', 'Mathi', 'indhumathi@gmail.com', '7343267658'),
(1011,'Syed', 'Anwar', 'syedanwar@gmail.com', '8899934643');


select * from customer;

insert into lease (lease_id, vehicle_id, customer_id, start_date, end_date, type) values
(1001, 1, 111, '2024-01-10', '2024-01-20', 'daily_lease'),
(1002, 2, 211, '2024-02-05', '2024-03-05', 'daily_lease'),
(1003, 3, 311, '2024-03-01', '2024-03-10', 'monthly_lease'),
(1004, 4, 411, '2024-03-15', '2024-04-15', 'monthly_lease'),
(1005, 5, 511, '2024-04-01', '2024-04-07', 'daily_lease'),
(1006, 6, 611, '2024-04-10', '2024-05-10', 'monthly_lease'),
(1007, 7, 711, '2024-05-01', '2024-05-15', 'daily_lease'),
(1008, 8, 811, '2024-05-20', '2024-06-20', 'monthly_lease'),
(1009, 9, 911, '2024-06-01', '2024-06-10', 'monthly_lease'),
(1010, 10, 1011, '2024-06-15', '2024-07-15', 'daily_lease');


select * from lease;

insert into payment (payment_id, lease_id, payment_date, amount) values
(201, 1001, '2024-01-20', 3500.00),
(202, 1002, '2024-03-05', 12000.00),
(203, 1003, '2024-03-10', 4800.00),
(204, 1004, '2024-04-15', 15000.00),
(205, 1005, '2024-04-07', 4200.00),
(206, 1006, '2024-05-10', 13500.00),
(207, 1007, '2024-05-15', 6500.00),
(208, 1008, '2024-06-20', 16000.00),
(209, 1009, '2024-06-10', 4800.00),
(210, 1010, '2024-07-15', 18000.00);


select * from payment;
