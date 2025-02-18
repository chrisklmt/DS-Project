package gr.hua.dit.ds.dsproject.repositories;

import gr.hua.dit.ds.dsproject.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
}