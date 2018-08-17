package br.com.vtr;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.protobuf.Message;

@ControllerAdvice
@RestController
public class BlogController {
	
	@Autowired
	BlogRepository blogRepository;
	
	@GetMapping("/blog")
	public List<Blog> index() {
		return blogRepository.findAll();
	}

	@GetMapping("/blog/{id}")
	public Blog show(@PathVariable String id) {
		int blogId = Integer.parseInt(id);
		return blogRepository.findOne(blogId);
	}
	
	public List<Blog> search(@RequestBody Map<String, String> body) {
		String searchTerm = body.get("text");
		return blogRepository.findByTitleContainingOrContentContaining(searchTerm, searchTerm);
	}
	
	@PostMapping("/blog")
	public Blog create(@RequestBody Map<String, String> body) {
		int id = Integer.parseInt(body.get("id"));
		String title = body.get("title");
		String content = body.get("content");
		// TODO REMOVER O ID SE DER PROBLEMA
		return blogRepository.save(new Blog(id, title, content));
	}
	
    @PutMapping("/blog/{id}")
	public Blog update(@PathVariable String id, @RequestBody Map<String, String> body) {
		int blogId = Integer.parseInt(id);
		Blog blog = blogRepository.findOne(blogId);
		blog.setTitle(body.get("title"));
		blog.setContent(body.get("content"));
		return blogRepository.save(blog);
	}
	
	@DeleteMapping("/blog/{id}")
	public boolean delete(@PathVariable String id) {
		int blogId = Integer.parseInt(id);
		blogRepository.delete(blogId);
		return true;
	}
}
