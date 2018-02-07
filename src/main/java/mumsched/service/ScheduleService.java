package mumsched.service;

import java.util.List;

import mumsched.entity.Entry;
import mumsched.entity.Schedule;
import mumsched.entity.ScheduleStatus;

public interface ScheduleService {
    public void generate(Entry entry);
    
    public List<Schedule> findAll();
    
    public Schedule findOne(Long id);
    
    public Schedule save(Schedule schedule);
    
    public Schedule findByEntryAndStatus(Entry entry,ScheduleStatus status);
}
