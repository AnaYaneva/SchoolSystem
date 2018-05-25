package testSystem.controller.admin;

import testSystem.bindingModel.SubjectBindingModel;
import testSystem.entity.Test;
import testSystem.entity.Subject;
import testSystem.repository.TestRepository;
import testSystem.repository.SubjectRepository;
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
    private TestRepository testRepository;

    @GetMapping("/")
    public String list(Model model) {
        List<Subject> subjects = this.subjectRepository.findAll();

        subjects = subjects.stream()
                .sorted(Comparator.comparingInt(Subject::getId))
                .collect(Collectors.toList());

        model.addAttribute("subjects", subjects);
        model.addAttribute("view", "admin/subject/list");

        return "base-layout";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("view", "admin/subject/create");

        return "base-layout";
    }

    @PostMapping("/create")
    public String createProcess(SubjectBindingModel subjectBindingModel) {
        if (StringUtils.isEmpty((subjectBindingModel.getName()))) {
            return "redirect:/admin/subject/create";
        }

        Subject subject =new Subject(subjectBindingModel.getName());

        this.subjectRepository.saveAndFlush(subject);

        return "redirect:/admin/subject/";
    }


  /*  @GetMapping("/edit/{id}")
    public String edit(Model model,@PathVariable Integer id){
        if(!this.subjectRepository.exists(id)){
            return "redirect:/admin/categories/";
        }

        Subject subject =this.subjectRepository.findOne(id);

        model.addAttribute("subject", subject);
        model.addAttribute("view", "admin/subject/edit");

        return "base-layout";
    }

    @PostMapping("/edit/{id}")
    public String editProcess(@PathVariable Integer id,
                              SubjectBindingModel subjectBindingModel){
        if(!this.subjectRepository.exists(id)){
            return "redirect:/admin/categories/";
        }

        Subject subject = this.subjectRepository.findOne(id);

        subject.setName(subjectBindingModel.getName());

        this.subjectRepository.saveAndFlush(subject);

        return "redirect:/admin/categories/";
    }

    @GetMapping("/delete/{id}")
    public String delete(Model model, @PathVariable Integer id){
        if(!this.subjectRepository.exists(id)){
            return "redirect:/admin/categories/";
        }

        Subject subject = this.subjectRepository.findOne(id);

        model.addAttribute("subject", subject);
        model.addAttribute("view", "admin/subject/delete");

        return "base-layout";
    }

    @PostMapping("/delete/{id}")
    public String deleteProcess(@PathVariable Integer id){
        if(!this.subjectRepository.exists(id)){
            return "redirect:/admin/categories/";
        }
        Subject subject = this.subjectRepository.findOne(id);

        for(Test test : subject.getTests()){
            this.testRepository.delete(test);
        }

        this.subjectRepository.delete(subject);

        return "redirect:/admin/categories/";
    }*/
}
