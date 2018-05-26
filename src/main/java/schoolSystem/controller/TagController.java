package schoolSystem.controller;

import schoolSystem.annotations.PreAuthenticate;
import schoolSystem.entity.Constants;
import schoolSystem.entity.Tag;
import schoolSystem.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class TagController {
    @Autowired
    private TagRepository tagRepository;

    @GetMapping("/tag/{name}")
    @PreAuthenticate(loggedIn = true)
    public String articleWithTag(Model model, @PathVariable String name) {
        Tag tag = this.tagRepository.findByName(name);

        if (tag == null) {
            return "redirect:/";
        }

        model.addAttribute("view", "tag/lessons");
        model.addAttribute("tag", tag);

        return Constants.BASE_LAYOUT;
    }
}
