package pl.sda.mysimpleblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sda.mysimpleblog.model.Comment;
import pl.sda.mysimpleblog.model.Post;
import pl.sda.mysimpleblog.model.User;
import pl.sda.mysimpleblog.repository.CommentRepository;
import pl.sda.mysimpleblog.repository.PostsRepository;
import pl.sda.mysimpleblog.repository.UserRepository;

import java.util.List;
@Service
public class PostsService {
    PostsRepository postsRepository;
    UserRepository userRepository;
    CommentRepository commentRepository;
    @Autowired
    public PostsService(PostsRepository postsRepository, UserRepository userRepository, CommentRepository commentRepository) {
        this.postsRepository = postsRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
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
    public void addComment(Comment comment, Long post_id, Long user_id){
        User user = userRepository.getOne(user_id);
        Post post = postsRepository.getOne(post_id);
        comment.setPost(post);
        comment.setUser(user);
        commentRepository.save(comment);
    }
    public void savePost(Post post){
        post.setUser(userRepository.getOne(4L));
        postsRepository.save(post);
    }
    public void deletePost(Long post_id){
        postsRepository.deleteById(post_id);
    }
    public void updatePost(Long post_id, Post updatedPost){
        Post post = postsRepository.getOne(post_id);
        // przepisanie pól
        post.setTitle(updatedPost.getTitle());
        post.setContent(updatedPost.getContent());
        post.setCategory(updatedPost.getCategory());
        postsRepository.save(post);
    }



}
