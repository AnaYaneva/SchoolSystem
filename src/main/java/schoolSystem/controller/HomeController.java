package schoolSystem.controller;

import schoolSystem.annotations.PreAuthenticate;
import schoolSystem.entity.Constants;
import schoolSystem.entity.Subject;
import schoolSystem.entity.Lesson;
import schoolSystem.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import schoolSystem.repository.LessonRepository;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @GetMapping("/")
    public String index(Model model) {

        List<Subject> subjects = this.subjectRepository.findAll();

        model.addAttribute("view", "home/index");
        model.addAttribute("subjects", subjects);

        return "base-layout";
    }

   @GetMapping("/subject/{id}")
   @PreAuthenticate(loggedIn = true)
    public String listArticles(Model model, @PathVariable Integer id){
        if (!this.subjectRepository.existsById(id)){
            return "redirect:/";
        }

        Subject subject =this.subjectRepository.getOne(id);
        List<Lesson> lessons = subject.getLessons();

        model.addAttribute("lessons", lessons);
        model.addAttribute("subject", subject);
        model.addAttribute("view", "home/list-lessons");

       return Constants.BASE_LAYOUT;
    }

     @RequestMapping("/error/403")
    public String accessDenied(Model model){
        model.addAttribute("view", "error/403");

         return Constants.BASE_LAYOUT;
    }
}
