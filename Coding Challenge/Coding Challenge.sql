create database CrimeManagement;
use CrimeManagement;


create table Crime ( 
    CrimeID INT PRIMARY KEY, 
    IncidentType VARCHAR(255), 
    IncidentDate DATE, 
    Location VARCHAR(255), 
    Description TEXT, 
    Status VARCHAR(20) 
);

create table Victim ( 
    VictimID INT PRIMARY KEY, 
    CrimeID INT, 
    Name VARCHAR(255), 
    Age int,
    ContactInfo VARCHAR(255), 
    Injuries VARCHAR(255), 
    FOREIGN KEY (CrimeID) REFERENCES Crime(CrimeID) 
); 


create table Suspect ( 
    SuspectID INT PRIMARY KEY, 
    CrimeID INT, 
    Name VARCHAR(255), 
    Age int,
    Description TEXT,
    CriminalHistory TEXT, 
    FOREIGN KEY (CrimeID) REFERENCES Crime(CrimeID) 
);


INSERT INTO Crime (CrimeID, IncidentType, IncidentDate, Location, Description, Status) VALUES 
(1, 'Robbery', '2023-09-15', '123 Main St, Cityville', 'Armed robbery at a convenience store', 'Open'), 
(2, 'Homicide', '2023-09-20', '456 Elm St, Townsville', 'Investigation into a murder case', 'Under Investigation'), 
(3, 'Theft', '2023-09-10', '789 Oak St, Villagetown', 'Shoplifting incident at a mall', 'Closed'), 
(4, 'Burglary', '2023-09-25', '567 Maple St, Metrocity', 'Break-in at a residential home', 'Open'),
(5, 'Assault', '2023-09-30', '890 Pine St, Cityville', 'Physical altercation outside a bar', 'Under Investigation'),
(6, 'Fraud', '2023-09-12', '101 Cedar St, Villagetown', 'Identity theft and financial fraud case', 'Closed');


INSERT INTO Victim (VictimID, CrimeID, Name,Age, ContactInfo, Injuries) VALUES 
(101, 1, 'Moshina', 30,'moshina@gmail.com', 'Minor injuries'), 
(201, 2, 'Aashwa Damin',27, 'aashwa$damin@gmail.com', 'Deceased'), 
(301, 3, 'John Doe',35, 'johndoe@gmail.com', 'None'),
(401, 4, 'Afrin Banu',37, 'afrinbanu@gmail.com', 'Bruises'),
(501, 5, 'Felci Christina',45, 'felci56@gmail.com', 'Fractured arm'),
(601, 6, 'Kalai Sudar',36, 'kalai78@gmail.com', 'Financial loss');



INSERT INTO Suspect (SuspectID, CrimeID, Name,Age, Description, CriminalHistory) VALUES 
(111, 1, 'Robber 1',33, 'Armed and masked robber', 'Previous robbery convictions'), 
(211, 2, 'Unknown', 45,'Investigation ongoing', NULL), 
(311, 3, 'Suspect 1',34, 'Shoplifting suspect', 'Prior shoplifting arrests'),
(411, 4, 'Suspect 2',50, 'Masked burglar, seen fleeing the scene', 'Previous burglary charges'),
(511, 5, 'Merwin Josh',29, 'Involved in bar fight', 'Multiple assault charges'),
(611, 6, 'Syed Anwar', 38,'Suspected in financial fraud schemes', 'No prior criminal history');


select * from crime where status = 'open';

select  count(*) AS totalincidents from crime;

select distinct incidenttype from crime;

select * from crime where incidentdate between '2023-09-01' and '2023-09-10';


(select name, age from victim) union (select name, age from suspect) order by age desc;

select avg(age) as average_age from (select age from victim union all select age from suspect) as all_persons;

select incidenttype, count(*) as count from crime
where status = 'open'
group by incidenttype;



select name from victim where name like '%doe%'union select name from suspect where name like '%doe%';


select distinct v.name from victim v
join crime c on v.crimeid = c.crimeid
where c.status in ('open', 'closed') union
select distinct s.name
from suspect s
join crime c on s.crimeid = c.crimeid
where c.status in ('open', 'closed');


select distinct c.incidenttype from crime c
join victim v on c.crimeid = v.crimeid
where v.age in (30, 35) union
select distinct c.incidenttype from crime c
join suspect s on c.crimeid = s.crimeid
where s.age in (30, 35);



select distinct v.name from victim v
join crime c on v.crimeid = c.crimeid
where c.incidenttype = 'robbery' union
select distinct s.name from suspect s
join crime c on s.crimeid = c.crimeid
where c.incidenttype = 'robbery';


select incidenttype from crime where status = 'open' group by incidenttype having count(*) > 1;


select distinct c.* from crime c
join suspect s on c.crimeid = s.crimeid
where s.name in (select name from victim);


select c.*, v.name as victim_name, s.name as suspect_name from crime c
left join victim v on c.crimeid = v.crimeid
left join suspect s on c.crimeid = s.crimeid;


select distinct c.* from crime c
join victim v on c.crimeid = v.crimeid
join suspect s on c.crimeid = s.crimeid
where s.age > v.age;                                   


select name, count(*) as incident_count from suspect group by name
having count(*) > 1;


select * from crime where crimeid not in (select distinct crimeid from suspect);


select *
from crime
where incidenttype = 'homicide'
and crimeid in (select crimeid from crime where incidenttype = 'robbery');


select c.*, coalesce(s.name, 'no suspect') as suspect_name
from crime c
left join suspect s on c.crimeid = s.crimeid;


select distinct s.*
from suspect s
join crime c on s.crimeid = c.crimeid
where c.incidenttype in ('robbery', 'assault');


