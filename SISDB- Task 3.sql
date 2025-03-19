use sisdb;

select s.student_id, s.first_name, s.last_name, sum(p.amount) as total_payment from students s
join payments p on s.student_id = p.student_id where s.student_id = 110  
group by s.student_id, s.first_name, s.last_name;


select c.course_id, c.course_name, count(e.student_id) as student_count
from courses c left join enrollments e on c.course_id = e.course_id 
group by c.course_id, c.course_name
order by student_count desc;


select s.student_id, s.first_name, s.last_name from students s
left join enrollments e on s.student_id = e.student_id
where e.student_id is null;


select s.first_name, s.last_name, c.course_name from students s
join enrollments e on s.student_id = e.student_id
join courses c on e.course_id = c.course_id;


SELECT t.first_name, t.last_name, c.course_name 
FROM Teacher t
JOIN Courses c ON t.teacher_id = c.teacher_id;



select s.first_name, s.last_name, e.enrollment_date from students s
join enrollments e on s.student_id = e.student_id
join courses c on e.course_id = c.course_id
where c.course_name = 'artificial intelligence'
order by e.enrollment_date;


select s.student_id, s.first_name, s.last_name
from students s
left join payments p on s.student_id = p.student_id
where p.student_id is null;



select c.course_id, c.course_name
from courses c
left join enrollments e on c.course_id = e.course_id
where e.course_id is null;


select e.student_id, s.first_name, s.last_name, count(e.course_id) as num_of_courses
from enrollments e
join students s on e.student_id = s.student_id
group by e.student_id, s.first_name, s.last_name
having num_of_courses > 1;


select t.teacher_id, t.first_name, t.last_name
from teacher t
left join courses c on t.teacher_id = c.teacher_id
where c.teacher_id is null;








