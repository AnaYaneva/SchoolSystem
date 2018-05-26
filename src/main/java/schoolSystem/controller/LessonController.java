package schoolSystem.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import schoolSystem.annotations.PreAuthenticate;
import schoolSystem.bindingModel.LessonBindingModel;

import schoolSystem.entity.*;
import schoolSystem.repository.SubjectRepository;
import schoolSystem.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import schoolSystem.repository.LessonRepository;
import schoolSystem.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class LessonController {
    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private TagRepository tagRepository;


    @GetMapping("/lesson/create")
    @PreAuthenticate(loggedIn = true, inRole = Constants.TEACHER)
    public String create(Model model) {
        List<Subject> subjects = this.subjectRepository.findAll();

        model.addAttribute("subjects", subjects);
        model.addAttribute("view", "lesson/create");

        return Constants.BASE_LAYOUT;
    }

    @PostMapping("/lesson/create")
    @PreAuthenticate(loggedIn = true, inRole = Constants.TEACHER)
    public String createProcess(LessonBindingModel lessonBindingModel) {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        User userEntity = this.userRepository.findByEmail(user.getUsername());
        Subject subject = this.subjectRepository.getOne(lessonBindingModel.getSubjectId());
        HashSet<Tag> tags = this.findTagsFromString(lessonBindingModel.getTagString());

        Lesson lessonEntity = new Lesson(
                lessonBindingModel.getTitle(),
                lessonBindingModel.getContent(),
                userEntity,
                subject,
                tags
        );

        this.lessonRepository.saveAndFlush(lessonEntity);

        return "redirect:/";
    }

    @GetMapping("/lesson/{id}")
    @PreAuthorize("isAuthenticated()")
    public String details(Model model, @PathVariable Integer id) {
        if (!this.lessonRepository.existsById(id)) {
            return "redirect:/";
        }

        if (!(SecurityContextHolder.getContext().getAuthentication()
                instanceof AnonymousAuthenticationToken)) {
            UserDetails principal = (UserDetails) SecurityContextHolder.getContext()
                    .getAuthentication().getPrincipal();

            User entityUser = this.userRepository.findByEmail(principal.getUsername());

            model.addAttribute("user", entityUser);
        }

        Lesson lesson = this.lessonRepository.getOne(id);

        model.addAttribute("lesson", lesson);
        model.addAttribute("view", "lesson/details");

        return Constants.BASE_LAYOUT;
    }

    @GetMapping("/lesson/edit/{id}")
    @PreAuthenticate(loggedIn = true, inRole = Constants.TEACHER)
    public String edit(@PathVariable Integer id, Model model) {
        if (!this.lessonRepository.existsById(id)) {
            return "redirect:/";
        }
        Lesson lesson = this.lessonRepository.getOne(id);

        if (!isUserAuthorOrAdmin(lesson)) {
            return "redirect:/lesson/" + id;
        }

        List<Subject> subjects = this.subjectRepository.findAll();

        String tagString = lesson.getTags().stream()
                .map(Tag::getName)
                .collect(Collectors.joining(", "));

        model.addAttribute("view", "lesson/edit");
        model.addAttribute("lesson", lesson);
        model.addAttribute("subjects", subjects);
        model.addAttribute("tags", tagString);

        return Constants.BASE_LAYOUT;
    }

    @PostMapping("/lesson/edit/{id}")
    @PreAuthenticate(loggedIn = true, inRole = Constants.TEACHER)
    public String editProcess(@PathVariable Integer id, LessonBindingModel lessonBindingModel) {
        if (!this.lessonRepository.existsById(id)) {
            return "redirect:/";
        }

        Lesson lesson = this.lessonRepository.getOne(id);

        if (!isUserAuthorOrAdmin(lesson)) {
            return "redirect:/lesson/" + id;
        }

        Subject subject = this.subjectRepository.getOne(lessonBindingModel.getSubjectId());
        HashSet<Tag> tags = this.findTagsFromString(lessonBindingModel.getTagString());

        lesson.setSubject(subject);
        lesson.setContent(lessonBindingModel.getContent());
        lesson.setTitle(lessonBindingModel.getTitle());
        lesson.setTags(tags);

        this.lessonRepository.saveAndFlush(lesson);

        return "redirect:/lesson/" + lesson.getId();
    }

    @GetMapping("/lesson/delete/{id}")
    @PreAuthenticate(loggedIn = true, inRole = Constants.TEACHER)
    public String delete(Model model, @PathVariable Integer id) {
        if (!this.lessonRepository.existsById(id)) {
            return "redirect:/";
        }

        Lesson lesson = this.lessonRepository.getOne(id);

        if (!isUserAuthorOrAdmin(lesson)) {
            return "redirect:/lesson/" + id;
        }

        model.addAttribute("lesson", lesson);
        model.addAttribute("view", "lesson/delete");

        return Constants.BASE_LAYOUT;
    }

    @PostMapping("/lesson/delete/{id}")
    @PreAuthenticate(loggedIn = true, inRole = Constants.TEACHER)
    public String deleteProcess(@PathVariable Integer id) {
        if (!this.lessonRepository.existsById(id)) {
            return "redirect:/";
        }

        Lesson lesson = this.lessonRepository.getOne(id);

        if (!isUserAuthorOrAdmin(lesson)) {
            return "redirect:/lesson/" + id;
        }

        this.lessonRepository.delete(lesson);

        return "redirect:/";
    }

    private boolean isUserAuthorOrAdmin(Lesson lesson) {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        User userEntity = this.userRepository.findByEmail(user.getUsername());

        return userEntity.isAdmin() || userEntity.isAuthor(lesson);
    }

    private HashSet<Tag> findTagsFromString(String tagString) {
        HashSet<Tag> tags = new HashSet<>();
        String[] tagNames = tagString.split(",\\s*");

        for (String tagName : tagNames) {
            Tag currentTag = this.tagRepository.findByName(tagName);

            if (currentTag == null) {
                currentTag = new Tag(tagName);
                this.tagRepository.saveAndFlush(currentTag);
            }

            tags.add(currentTag);
        }

        return tags;
    }
}
