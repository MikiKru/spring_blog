package pl.sda.mysimpleblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sda.mysimpleblog.model.Post;
import pl.sda.mysimpleblog.repository.PostsRepository;

import java.util.List;
@Service
public class PostsService {
    PostsRepository postsRepository;
    @Autowired
    public PostsService(PostsRepository postsRepository) {
        this.postsRepository = postsRepository;
    }
    public List<Post> getAllPosts(){
        return postsRepository.findAll();
    }
    public Post getPostById(Long post_id){
        if(postsRepository.findById(post_id).isPresent()) {
            return postsRepository.getOne(post_id);
        }
        return new Post();
    }
}
