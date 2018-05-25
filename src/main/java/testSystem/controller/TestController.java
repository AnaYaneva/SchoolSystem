package testSystem.controller;

import testSystem.entity.Subject;
import testSystem.entity.Tag;
import testSystem.entity.Test;
import testSystem.repository.SubjectRepository;
import testSystem.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import testSystem.bindingModel.TestBindingModel;
import testSystem.entity.User;
import testSystem.repository.TestRepository;
import testSystem.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class TestController {
    @Autowired
    private TestRepository testRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private TagRepository tagRepository;

  /*@GetMapping("/article/create")
    @PreAuthorize("isAuthenticated()")
    public String create(Model model){
          List<Subject> categories=this.subjectRepository.findAll();

        model.addAttribute("categories", categories);
        model.addAttribute("view", "article/create");

        return "base-layout";
    }

    @PostMapping("/article/create")
    @PreAuthorize("isAuthenticated()")
    public String createProcess(TestBindingModel testBindingModel){
        UserDetails user = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        User userEntity = this.userRepository.findByEmail(user.getUsername());
        Subject subject =this.subjectRepository.findOne(testBindingModel.getSubjectId());
        HashSet<Tag> tags=this.findTagsFromString(testBindingModel.getTagString());

        Test testEntity = new Test(
                testBindingModel.getTitle(),
                testBindingModel.getContent(),
                userEntity,
                subject,
                tags
        );

        this.testRepository.saveAndFlush(testEntity);

        return "redirect:/";
    }

    @GetMapping("/article/{id}")
    public String details(Model model, @PathVariable Integer id){
        if (!this.testRepository.exists(id)) {
            return "redirect:/";
        }

        if(!(SecurityContextHolder.getContext().getAuthentication()
                instanceof AnonymousAuthenticationToken)){
            UserDetails principal = (UserDetails) SecurityContextHolder.getContext()
                    .getAuthentication().getPrincipal();

            User entityUser = this.userRepository.findByEmail(principal.getUsername());

            model.addAttribute("user", entityUser);
        }

        Test test = this.testRepository.findOne(id);

        model.addAttribute("article", test);
        model.addAttribute("view", "test/details");

        return "base-layout";
    }

    @GetMapping("/article/edit/{id}")
    @PreAuthorize("isAuthenticated()")
    public String edit(@PathVariable Integer id, Model model){
        if(!this.testRepository.exists(id)){
            return "redirect:/";
        }
        Test test = this.testRepository.findOne(id);

        if(!isUserAuthorOrAdmin(test)){
            return "redirect:/test/" + id;
        }

        List<Subject> categories=this.subjectRepository.findAll();

        String tagString= test.getTags().stream()
                .map(Tag::getName)
                .collect(Collectors.joining(", "));

        model.addAttribute("view", "test/edit");
        model.addAttribute("article", test);
        model.addAttribute("categories", categories);
        model.addAttribute("tags", tagString);

        return "base-layout";
    }

    @PostMapping("/article/edit/{id}")
    @PreAuthorize("isAuthenticated()")
    public String editProcess(@PathVariable Integer id, TestBindingModel testBindingModel){
        if(!this.testRepository.exists(id)){
            return "redirect:/";
        }

        Test test = this.testRepository.findOne(id);

        if(!isUserAuthorOrAdmin(test)){
            return "redirect:/test/" + id;
        }

        Subject subject =this.subjectRepository.findOne(testBindingModel.getSubjectId());
        HashSet<Tag> tags=this.findTagsFromString(testBindingModel.getTagString());

        test.setSubject(subject);
        test.setContent(testBindingModel.getContent());
        test.setTitle(testBindingModel.getTitle());
        test.setTags(tags);

        this.testRepository.saveAndFlush(test);

        return "redirect:/test/" + test.getId();
    }

    @GetMapping("/article/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public String delete(Model model, @PathVariable Integer id){
        if(!this.testRepository.exists(id)){
            return "redirect:/";
        }

        Test test = this.testRepository.findOne(id);

        if(!isUserAuthorOrAdmin(test)){
            return "redirect:/test/" + id;
        }

        model.addAttribute("article", test);
        model.addAttribute("view", "test/delete");

        return "base-layout";
    }

    @PostMapping("/article/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public String deleteProcess(@PathVariable Integer id){
        if (!this.testRepository.exists(id)) {
            return "redirect:/";
        }

        Test test = this.testRepository.findOne(id);

        if(!isUserAuthorOrAdmin(test)){
            return "redirect:/test/" + id;
        }

        this.testRepository.delete(test);

        return "redirect:/";
    }

    private boolean isUserAuthorOrAdmin(Test test){
        UserDetails user = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        User userEntity = this.userRepository.findByEmail(user.getUsername());

        return userEntity.isAdmin() || userEntity.isAuthor(test);
    }

    private HashSet<Tag> findTagsFromString(String tagString){
        HashSet<Tag> tags=new HashSet<>();
        String[] tagNames=tagString.split(",\\s*");

        for (String tagName : tagNames) {
            Tag currentTag=this.tagRepository.findByName(tagName);

            if(currentTag==null){
                currentTag=new Tag(tagName);
                this.tagRepository.saveAndFlush(currentTag);
            }

            tags.add(currentTag);
        }

        return tags;
    }*/
}
