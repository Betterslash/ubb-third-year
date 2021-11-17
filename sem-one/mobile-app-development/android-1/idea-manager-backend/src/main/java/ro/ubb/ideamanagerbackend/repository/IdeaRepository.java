package ro.ubb.ideamanagerbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.ubb.ideamanagerbackend.model.IdeaEntity;

@Repository
public interface IdeaRepository extends JpaRepository<IdeaEntity, Long> {
}
