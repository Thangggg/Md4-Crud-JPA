package codegym.controller;

import codegym.model.ClassRoom;
import codegym.model.Student;
import codegym.service.IClassRoomService;
import codegym.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;

@Controller
public class StudentController {
    @Autowired
    IClassRoomService classRoomService;

    @Autowired
    IStudentService studentService;

    @GetMapping("/students")
    public ModelAndView showAll(){
        ModelAndView modelAndView = new ModelAndView("show");
        modelAndView.addObject("students",studentService.fillAll());
        return modelAndView;
    }

    @GetMapping ("/create")
    public ModelAndView showCreate(){
        ModelAndView modelAndView = new ModelAndView("create");
        modelAndView.addObject("student",new Student());
        modelAndView.addObject("classList",classRoomService.fillAll());
        return modelAndView;
    }

    @PostMapping("/create")
    public String create(@ModelAttribute (value = "student") Student student, @RequestParam long idClassRoom, @RequestParam MultipartFile upImg){
        ClassRoom classRoom = new ClassRoom();
        classRoom.setId(idClassRoom);
        student.setClassRoom(classRoom);

        String nameFile = upImg.getOriginalFilename();
        try {
            FileCopyUtils.copy(upImg.getBytes(),new File("C:\\Users\\Admin\\Md4-CRUD\\src\\main\\webapp\\WEB-INF\\img\\" + nameFile));
            student.setImg("/img/"+nameFile);
            studentService.save(student);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:students";
    }

    @GetMapping("/edit")
    public ModelAndView showEdit(@RequestParam long id) {
        ModelAndView modelAndView = new ModelAndView("/edit");
        modelAndView.addObject("student", studentService.finById(id));
        modelAndView.addObject("classList", classRoomService.fillAll());
        return modelAndView;
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute(value = "student") Student student, MultipartFile upImg, @RequestParam long idClassRoom) {
        ClassRoom classRoom = new ClassRoom();
        classRoom.setId(idClassRoom);
        student.setClassRoom(classRoom);
        if (upImg.getSize() != 0) {
            String imgFile = upImg.getOriginalFilename();
            try {
                FileCopyUtils.copy(upImg.getBytes(), new File("C:\\Users\\Admin\\Md4-CRUD\\src\\main\\webapp\\WEB-INF\\img\\" + imgFile));
                if (student.getImg()!=null){
                    File file = new File("C:\\Users\\Admin\\Md4-CRUD\\src\\main\\webapp\\WEB-INF\\" + student.getImg());
                    file.delete();
                }
                student.setImg("/img/"+imgFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        studentService.save(student);
        return "redirect:/students";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam long id) {
        if (studentService.finById(id).getImg()!=null){
            File file = new File("C:\\Users\\Admin\\Md4-CRUD\\src\\main\\webapp\\WEB-INF\\" +studentService.finById(id).getImg());
            file.delete();
        }
        studentService.delete(id);
        return "redirect:/students";
    }

}
