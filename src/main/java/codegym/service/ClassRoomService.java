package codegym.service;

import codegym.model.ClassRoom;
import codegym.repository.ClassRoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassRoomService implements IClassRoomService{
    @Autowired
    ClassRoomRepo classRoomRepo;
    @Override
    public List<ClassRoom> fillAll() {
        return (List<ClassRoom>) classRoomRepo.findAll();
    }
}
