package mumsched.service;

import java.util.List;

import mumsched.entity.Entry;
import mumsched.entity.Schedule;

public interface ScheduleService {
    public void generate(Entry entry);
    
    public List<Schedule> findAll();
    
    public Schedule findOne(Long id);
    
    public Schedule save(Schedule schedule);
}
