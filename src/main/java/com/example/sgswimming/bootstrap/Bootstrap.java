package com.example.sgswimming.bootstrap;

import com.example.sgswimming.model.ClientData;
import com.example.sgswimming.model.Instructor;
import com.example.sgswimming.model.Lesson;
import com.example.sgswimming.model.Swimmer;
import com.example.sgswimming.repositories.ClientDataRepository;
import com.example.sgswimming.repositories.InstructorRepository;
import com.example.sgswimming.repositories.LessonRepository;
import com.example.sgswimming.repositories.SwimmerRepository;
import com.example.sgswimming.security.model.Authority;
import com.example.sgswimming.security.model.Role;
import com.example.sgswimming.security.model.User;
import com.example.sgswimming.security.repositories.AuthorityRepository;
import com.example.sgswimming.security.repositories.RoleRepository;
import com.example.sgswimming.security.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

import static com.example.sgswimming.bootstrap.BootstrapHelper.*;

@RequiredArgsConstructor
@Component
public class Bootstrap implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;

    private final ClientDataRepository clientDataRepository;

    private final InstructorRepository instructorRepository;
    private final LessonRepository lessonRepository;
    private final SwimmerRepository swimmerRepository;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthorityRepository authorityRepository;

    @Override
    public void run(String... args) throws Exception {
        loadUserData();
        loadClientData();
        loadModelData();
    }

    private void loadClientData() {
        User employee = userRepository.findByUsername("employee").get();
        ClientData clientData = new ClientData();
        clientData.setFirstName("employee");
        clientData.setLastName("employee");
        ClientData savedClientData = clientDataRepository.save(clientData);
        savedClientData.setUser(employee);
        employee.setClientData(savedClientData);
        userRepository.save(employee);


        User customer = userRepository.findByUsername("customer").get();
        ClientData clientData1 = new ClientData();
        clientData1.setFirstName("customer");
        clientData1.setLastName("customer");
        ClientData savedClientData1 = clientDataRepository.save(clientData1);
        savedClientData1.setUser(employee);
        customer.setClientData(savedClientData1);
        userRepository.save(customer);
    }

    private void loadModelData() {

        List<Lesson> lessons = lessonRepository.saveAll(List.of(createLesson(), createLesson(), createLesson()));


        ClientData instructorClientData = clientDataRepository.findByFirstName("employee");
        Instructor instructor = instructorRepository.save(createInstructor());
        instructor.setLessons(lessons);
        lessons.forEach(l -> l.setInstructor(instructor));
        instructorClientData.setInstructor(instructor);
        instructor.addClientData(instructorClientData);
        clientDataRepository.save(instructorClientData);


        ClientData customerClientData = clientDataRepository.findByFirstName("customer");
        List<Swimmer> swimmers = swimmerRepository.saveAll(List.of(createSwimmer(), createSwimmer(), createSwimmer()));
        swimmers.forEach(s -> s.setLessons(lessons));
        lessons.forEach(l -> l.setSwimmers(swimmers));
        customerClientData.setSwimmers(new HashSet<>(swimmers));
        swimmers.forEach(s -> s.addClientData(customerClientData));
        clientDataRepository.save(customerClientData);


        for (int i = 0; i < 5; i++) {
            lessonRepository.save(createLesson());
        }

        for (int i = 0; i < 3; i++) {
            instructorRepository.save(createInstructor());
        }

        for (int i = 0; i < 10; i++) {
            swimmerRepository.save(createSwimmer());
        }


        System.out.printf(
                "Loaded bootstrap data.\ninstructor count: %d\nlesson count: %d\nswimmer count: %d\n",
                instructorRepository.count(), lessonRepository.count(), swimmerRepository.count());
    }

    private void loadUserData() {

        //lessonPermissions
        Authority lessonCreate = Authority.builder().permission("lesson.create").build();
        Authority lessonRead = Authority.builder().permission("lesson.read").build();
        Authority lessonUpdate = Authority.builder().permission("lesson.update").build();
        Authority lessonDelete = Authority.builder().permission("lesson.delete").build();

        Authority employeeLessonRead = Authority.builder().permission("employee.lesson.read").build();

        Authority customerLessonRead = Authority.builder().permission("customer.lesson.read").build();

        //swimmerPermissions
        Authority swimmerCreate = Authority.builder().permission("swimmer.create").build();
        Authority swimmerRead = Authority.builder().permission("swimmer.read").build();
        Authority swimmerUpdate = Authority.builder().permission("swimmer.update").build();
        Authority swimmerDelete = Authority.builder().permission("swimmer.delete").build();

        Authority employeeSwimmerRead = Authority.builder().permission("employee.swimmer.read").build();

        Authority customerSwimmerRead = Authority.builder().permission("customer.swimmer.read").build();

        //instructorPermissions
        Authority instructorCreate = Authority.builder().permission("instructor.create").build();
        Authority instructorRead = Authority.builder().permission("instructor.read").build();
        Authority instructorUpdate = Authority.builder().permission("instructor.update").build();
        Authority instructorDelete = Authority.builder().permission("instructor.delete").build();

        Authority employeeInstructorRead = Authority.builder().permission("employee.instructor.read").build();

        Authority customerInstructorRead = Authority.builder().permission("customer.instructor.read").build();

        authorityRepository.saveAll(
                List.of(
                        lessonCreate, lessonRead, lessonUpdate, lessonDelete,
                        swimmerCreate, swimmerRead, swimmerUpdate, swimmerDelete,
                        instructorCreate, instructorRead, instructorUpdate, instructorDelete));

        authorityRepository.saveAll(
                List.of(
                        employeeLessonRead,
                        employeeSwimmerRead,
                        employeeInstructorRead));

        authorityRepository.saveAll(
                List.of(
                        customerLessonRead,
                        customerSwimmerRead,
                        customerInstructorRead));

        //roles
        Role adminRole = Role.builder()
                .name("ADMIN")
                .authorities(
                        List.of(
                                lessonCreate, lessonRead, lessonUpdate, lessonDelete,
                                swimmerCreate, swimmerRead, swimmerUpdate, swimmerDelete,
                                instructorCreate, instructorRead, instructorUpdate, instructorDelete
                        )).build();

        Role employeeRole = Role.builder()
                .name("EMPLOYEE")
                .authorities(List.of(
                        employeeLessonRead,
                        employeeSwimmerRead,
                        employeeInstructorRead
                )).build();

        Role customerRole = Role.builder()
                .name("USER")
                .authorities(
                        List.of(
                                customerLessonRead,
                                customerSwimmerRead,
                                customerInstructorRead
                        )).build();

        roleRepository.save(adminRole);
        roleRepository.save(employeeRole);
        roleRepository.save(customerRole);

        User admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                .role(adminRole)
                .build();

        User employee = User.builder()
                .username("employee")
                .password(passwordEncoder.encode("employee"))
                .role(employeeRole)
                .build();

        User customer = User.builder()
                .username("customer")
                .password(passwordEncoder.encode("customer"))
                .role(customerRole)
                .build();

        userRepository.save(admin);
        userRepository.save(employee);
        userRepository.save(customer);
    }
}
