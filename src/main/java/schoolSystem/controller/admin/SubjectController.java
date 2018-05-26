package schoolSystem.controller.admin;

import schoolSystem.annotations.PreAuthenticate;
import schoolSystem.bindingModel.SubjectBindingModel;
import schoolSystem.entity.Constants;
import schoolSystem.entity.Lesson;
import schoolSystem.entity.Subject;
import schoolSystem.repository.LessonRepository;
import schoolSystem.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/subject")
public class SubjectController {

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @GetMapping("/")
    @PreAuthenticate(loggedIn = true, inRole = Constants.ADMIN)
    public String list(Model model) {
        List<Subject> subjects = this.subjectRepository.findAll();

        subjects = subjects.stream()
                .sorted(Comparator.comparingInt(Subject::getId))
                .collect(Collectors.toList());

        model.addAttribute("subjects", subjects);
        model.addAttribute("view", "admin/subject/list");

        return Constants.BASE_LAYOUT;
    }

    @GetMapping("/create")
    @PreAuthenticate(loggedIn = true, inRole = Constants.ADMIN)
    public String create(Model model) {
        model.addAttribute("view", "admin/subject/create");

        return Constants.BASE_LAYOUT;
    }

    @PostMapping("/create")
    @PreAuthenticate(loggedIn = true, inRole = Constants.ADMIN)
    public String createProcess(SubjectBindingModel subjectBindingModel) {
        if (StringUtils.isEmpty((subjectBindingModel.getName()))) {
            return "redirect:/admin/subject/create";
        }

        Subject subject = new Subject(subjectBindingModel.getName(), subjectBindingModel.getDescription());

        this.subjectRepository.saveAndFlush(subject);

        return "redirect:/admin/subject/";
    }


    @GetMapping("/edit/{id}")
    @PreAuthenticate(loggedIn = true, inRole = Constants.ADMIN)
    public String edit(Model model, @PathVariable Integer id) {
        if (!this.subjectRepository.existsById(id)) {
            return "redirect:/admin/subject/";
        }

        Subject subject = this.subjectRepository.getOne(id);

        model.addAttribute("subject", subject);
        model.addAttribute("view", "admin/subject/edit");

        return Constants.BASE_LAYOUT;
    }

    @PostMapping("/edit/{id}")
    @PreAuthenticate(loggedIn = true, inRole = Constants.ADMIN)
    public String editProcess(@PathVariable Integer id,
                              SubjectBindingModel subjectBindingModel) {
        if (!this.subjectRepository.existsById(id)) {
            return "redirect:/admin/subject/";
        }

        Subject subject = this.subjectRepository.getOne(id);

        subject.setName(subjectBindingModel.getName());
        subject.setDescription(subjectBindingModel.getDescription());

        this.subjectRepository.saveAndFlush(subject);

        return "redirect:/admin/subject/";
    }

    @GetMapping("/delete/{id}")
    @PreAuthenticate(loggedIn = true, inRole = Constants.ADMIN)
    public String delete(Model model, @PathVariable Integer id) {
        if (!this.subjectRepository.existsById(id)) {
            return "redirect:/admin/subject/";
        }

        Subject subject = this.subjectRepository.getOne(id);

        model.addAttribute("subject", subject);
        model.addAttribute("view", "admin/subject/delete");

        return Constants.BASE_LAYOUT;
    }

    @PostMapping("/delete/{id}")
    @PreAuthenticate(loggedIn = true, inRole = Constants.ADMIN)
    public String deleteProcess(@PathVariable Integer id) {
        if (!this.subjectRepository.existsById(id)) {
            return "redirect:/admin/subject/";
        }
        Subject subject = this.subjectRepository.getOne(id);

        for (Lesson lesson : subject.getLessons()) {
            this.lessonRepository.delete(lesson);
        }

        this.subjectRepository.delete(subject);

        return "redirect:/admin/subject/";
    }
}
