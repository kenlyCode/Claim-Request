package com.vn;

import com.vn.entities.Project;
import com.vn.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@SpringBootTest
public class ProjectRepositoryTest {

    @Autowired
    ProjectRepository projectRepository;

    @Test
    void ProjectRepository_TC001_success() throws ParseException {
        Project project = new Project();
        project.setName("Project12");
        project.setCode("PROJECT_12");
        project.setFromDate(new SimpleDateFormat("yyyy-MM-dd").parse("2024-05-01"));
        project.setToDate(new SimpleDateFormat("yyyy-MM-dd").parse("2024-08-06"));
        projectRepository.save(project);

    }
}
