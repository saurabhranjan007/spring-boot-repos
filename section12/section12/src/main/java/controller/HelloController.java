package controller;

import model.Student;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;


@Controller
public class HelloController {

//    this controller will forward the request to a template (here "hello.html")
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello() {
        return "hello";
    }

//    Function to render simple dynamic string data through Thymeleaf templating..
    @RequestMapping(value = "/sendData",method = RequestMethod.GET)
    public ModelAndView sendData() {
        ModelAndView mav = new ModelAndView("data");
        mav.addObject("message", "Take up one idea and make it your life.");
        return mav;
    }

//    Function to send Object Data to be rendered dynamically using - Thymeleaf templating..
    @RequestMapping(value = "/student", method = RequestMethod.GET)
    public ModelAndView getStudent() {
        ModelAndView mav = new ModelAndView("student");

        Student student = new Student();
        student.setName("Saurabh");
        student.setScore(98);

        mav.addObject("student", new Student());
        return mav;
    }

//    Function to send multiple student records, in above function we're sending only one student record.
    @RequestMapping(value = "/students", method = RequestMethod.GET)
    public ModelAndView getStudents() {
        ModelAndView mav = new ModelAndView("studentList");

        Student student = new Student();
        student.setName("Saurabh R.");
        student.setScore(98);

        Student student2 = new Student();
        student2.setName("Saurabh Ranjan");
        student2.setScore(96);

//        to return multiple student entry, here we're bundling up student entries as an Array list.
        List<Student> students = Arrays.asList(student, student2);

        mav.addObject("students", students);
        return mav;
    }

//    Function to render the Student form as a "Thymeleaf Template"..
    @RequestMapping(value = "/studentForm", method = RequestMethod.GET)
    public ModelAndView displayStudentForm() {
        ModelAndView mav = new ModelAndView("studentForm");
        Student student = new Student();
//        sending an empty Student entry..
        mav.addObject("student", student);
        return mav;
    }

//    The above method, defines the form and populates it, but there is no defined action here.
//    To define certain action, we need to create a function for it.
    @RequestMapping(value = "/saveStudent", method = RequestMethod.GET)
    public ModelAndView saveStudents(@ModelAttribute Student student) {
        ModelAndView mav = new ModelAndView("result");
        System.out.println("Name: "+student.getName());
        System.out.println("Score: "+student.getScore());
        return mav;
    }

}
