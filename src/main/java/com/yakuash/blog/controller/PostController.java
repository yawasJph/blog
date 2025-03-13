package com.yakuash.blog.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yakuash.blog.entity.CommentEntity;
import com.yakuash.blog.entity.PostEntity;
import com.yakuash.blog.entity.UserEntity;
import com.yakuash.blog.service.PostService;
import com.yakuash.blog.service.UserService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;
	private final UserService userService;

	@GetMapping("/home")
	public String homePage(Model model) {
		model.addAttribute("posts", postService.getAllPost());
		return "/post/home";
	}

	@GetMapping("/new")
	public String newPost() {
		return "/post/create-post";
	}

	@PostMapping("/create")
	public String createPost(PostEntity post, HttpSession session) {
		post.setCreatedAt(LocalDateTime.now());
		UserEntity user = userService.getUserById(Long.parseLong(session.getAttribute("user_session_id").toString())).get();
		post.setUser(user);
		postService.createdPost(post);
		return "redirect:/post/home";
	}
	
	@GetMapping("/postPage/{id}")
	public String postPage(@PathVariable Long id,Model model) {
		PostEntity post = postService.getPostById(id).orElseThrow(() -> new IllegalArgumentException("invalid post id"));
		List<CommentEntity> comments = post.getComments();
		model.addAttribute("post", post);
		model.addAttribute("comments", comments);
		return "/post/post-page";
	}
	
	@GetMapping("/mine")
	public String myPosts(Model model,HttpSession session) {
		Long userId = Long.parseLong(session.getAttribute("user_session_id").toString());
		List<PostEntity> posts = postService.getPostByUserId(userId);
		model.addAttribute("posts", posts);
		return "/post/my-post";
	}
	
	@GetMapping("/edit/{id}")
	public String editPost(@PathVariable Long id,Model model) {
		PostEntity post = postService.getPostById(id).orElseThrow(() -> new IllegalArgumentException("ivalid post id"));
		model.addAttribute("post", post);
		return "/post/update-post";
	}
	
	@PostMapping("/update")
	public String updatePost(@RequestParam Long postId, PostEntity post) {
		postService.updatePost(postId, post);
		return "redirect:/post/mine";
	}
	
	@GetMapping("/delete/{id}")
	public String deletePost(@PathVariable Long id) {
		postService.deletePostById(id);
		return "redirect:/post/mine";
	}
	
	@GetMapping("/search")
	public String serachPost(@RequestParam String tittle,Model model) {
		List<PostEntity> posts = postService.searchPostByTittle(tittle);
		model.addAttribute("posts", posts);
		return "/post/home";
	}
}
