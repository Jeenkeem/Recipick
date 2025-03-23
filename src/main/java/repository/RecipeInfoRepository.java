package repository;

import com.project.recipick.Entity.RecipeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeInfoRepository extends JpaRepository<RecipeInfo, Long> {

}
