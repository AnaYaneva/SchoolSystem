package schoolSystem.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import schoolSystem.annotations.PreAuthenticate;
import schoolSystem.bindingModel.UserEditBindingModel;
import schoolSystem.entity.Lesson;
import schoolSystem.entity.Role;
import schoolSystem.entity.User;
import schoolSystem.repository.LessonRepository;
import schoolSystem.repository.RoleRepository;
import schoolSystem.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin/users")
public class AdminUserController {
    //ROLE_ADMIN must be added manualy in DB, and a user must be defined as admin in DB users_roles table
    
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LessonRepository lessonRepository;
    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/")
    @PreAuthenticate(loggedIn = true, inRole = "ADMIN")
    public String listUsers(Model model){
        List<User> users = this.userRepository.findAll();

        model.addAttribute("users", users);
        model.addAttribute("view", "admin/user/list");

        return "base-layout";
    }

   @GetMapping("/edit/{id}")
   @PreAuthenticate(loggedIn = true, inRole = "ADMIN")
    public String edit(@PathVariable Integer id, Model model){
        if(!this.userRepository.existsById(id)){
            return "redirect:/admin/users/";
        }

        User user = this.userRepository.getOne(id);
        List<Role> roles = this.roleRepository.findAll();

        model.addAttribute("user", user);
        model.addAttribute("roles", roles);
        model.addAttribute("view", "admin/user/edit");

        return "base-layout";
    }

    @PostMapping("/edit/{id}")
    @PreAuthenticate(loggedIn = true, inRole = "ADMIN")
    public String editProcess(@PathVariable Integer id,
                              UserEditBindingModel userBindingModel){
        if(!this.userRepository.existsById(id)){
            return "redirect:/admin/users/";
        }

        User user = this.userRepository.getOne(id);

        if(!StringUtils.isEmpty(userBindingModel.getPassword())
                && !StringUtils.isEmpty(userBindingModel.getConfirmPassword())){

            if(userBindingModel.getPassword().equals(userBindingModel.getConfirmPassword())){
                BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

                user.setPassword(bCryptPasswordEncoder.encode(userBindingModel.getPassword()));
            }
        }

        user.setFullName(userBindingModel.getFullName());
        user.setEmail(userBindingModel.getEmail());

        Set<Role> roles = new HashSet<>();

        for (Integer roleId : userBindingModel.getRoles()){
            roles.add(this.roleRepository.getOne(roleId));
        }

        user.setRoles(roles);

        this.userRepository.saveAndFlush(user);

        return "redirect:/admin/users/";
    }

    @GetMapping("/delete/{id}")
    @PreAuthenticate(loggedIn = true, inRole = "ADMIN")
    public String delete(@PathVariable Integer id, Model model){
        if(!this.userRepository.existsById(id)){
            return "redirect:/admin/users/";
        }

        User user = this.userRepository.getOne(id);

        model.addAttribute("user", user);
        model.addAttribute("view", "admin/user/delete");

        return "base-layout";
    }

    @PostMapping("/delete/{id}")
    @PreAuthenticate(loggedIn = true, inRole = "ADMIN")
    public String deleteProcess(@PathVariable Integer id){
        if(!this.userRepository.existsById(id)){
            return "redirect:/admin/users/";
        }
        User user = this.userRepository.getOne(id);

        for(Lesson lesson : user.getLessons()){
            this.lessonRepository.delete(lesson);
        }

        this.userRepository.delete(user);

        return "redirect:/admin/users/";
    }
}
