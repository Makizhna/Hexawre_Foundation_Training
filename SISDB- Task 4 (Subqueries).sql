
use sisdb;

select avg(student_count) as avg_students_per_course
from (
    select course_id, count(student_id) as student_count
    from enrollments group by course_id) as course_enrollments;


Select first_name, last_name from Students 
where student_id = (
    select student_id from Payments 
    where amount = (select MAX(amount) from Payments)
);


select c.course_id, c.course_name, count(e.student_id) as enrollment_count from courses c
join enrollments e on c.course_id = e.course_id
group by c.course_id, c.course_name
having count(e.student_id) = (
    select max(student_count)
    from (select count(student_id) as student_count
        from enrollments
        group by course_id) as max_enrollments
);


select t.teacher_id, t.first_name, t.last_name, 
       (select sum(p.amount) from payments p
        join enrollments e on p.student_id = e.student_id
        join courses c on e.course_id = c.course_id
        where c.teacher_id = t.teacher_id) as total_payments
from teacher t;


select s.student_id, s.first_name, s.last_name
from students s
where (select count(distinct course_id) from enrollments where student_id = s.student_id) = (select count(*) from courses);



select teacher_id, first_name, last_name from teacher
where teacher_id not in (select distinct teacher_id from courses);


select avg(age) as average_age 
from (select datediff(curdate(), date_of_birth) / 365 as age 
from students) as age_subquery;



select course_id, course_name from courses
where course_id not in (select distinct course_id from enrollments);



select s.student_id, s.first_name, s.last_name, c.course_name, (select sum(p.amount) 
from payments p where p.student_id = s.student_id) as total_payment
from students s
join enrollments e on s.student_id = e.student_id
join courses c on e.course_id = c.course_id;


select student_id, first_name, last_name
from students
where student_id in (
    select student_id 
    from payments 
    group by student_id
    having count(payment_id) > 1
);


select s.student_id, s.first_name, s.last_name, sum(p.amount) as total_payments
from students s
join payments p on s.student_id = p.student_id
group by s.student_id;



select c.course_name, count(e.student_id) as student_count
from courses c
left join enrollments e on c.course_id = e.course_id
group by c.course_name;





select s.student_id, s.first_name, s.last_name, avg(p.amount) as avg_payment
from students s
join payments p on s.student_id = p.student_id
group by s.student_id;

