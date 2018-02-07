package mumsched.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mumsched.entity.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
	
}
