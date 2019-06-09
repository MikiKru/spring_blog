package pl.sda.mysimpleblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.mysimpleblog.model.Post;

@Repository
public interface PostsRepository extends JpaRepository<Post,Long> {
}
