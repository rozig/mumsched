package mumsched.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mumsched.entity.Entry;
import mumsched.entity.Schedule;
import mumsched.entity.ScheduleStatus;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
	
	public Schedule findByEntryAndStatus(Entry entry,ScheduleStatus status) ;
	
}
