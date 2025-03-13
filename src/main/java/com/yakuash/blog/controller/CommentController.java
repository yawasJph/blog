package com.yakuash.blog.controller;

import java.time.LocalDateTime;

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
import com.yakuash.blog.service.CommentService;
import com.yakuash.blog.service.PostService;
import com.yakuash.blog.service.UserService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {
	
	private final CommentService commentService;
	private final UserService userService;
	private final PostService postService;
	
	@PostMapping("/addComment")
	public String addComment(@RequestParam Long postId,CommentEntity comment,HttpSession session) {
		UserEntity user = userService.getUserById(Long.parseLong(session.getAttribute("user_session_id").toString())).get();
		PostEntity post = postService.getPostById(postId).orElseThrow(() -> new IllegalArgumentException("invalid post id"));
		comment.setCreatedAt(LocalDateTime.now());
		comment.setUser(user);
		comment.setPost(post);
		commentService.createComment(comment);
		return "redirect:/post/postPage/" + postId;
	}
	
	@GetMapping("/edit/{id}")
    public String editComment(@PathVariable Long id ,Model model) {
        CommentEntity comment = commentService.getCommentById(id).orElseThrow(() -> new IllegalArgumentException("¡Invalid comment id!"));
        model.addAttribute("comment",comment);
        return "/post/update-comment";
    }

    @PostMapping("/update")
    public String updateComment(@RequestParam("commentId") Long id, CommentEntity comment) {
        CommentEntity commentDB = commentService.getCommentById(id).orElseThrow(() -> new IllegalArgumentException("¡Invalid comment id!"));
        commentService.updtaeComment(id, comment);
        return "redirect:/post/postPage/" + commentDB.getPost().getId();
    }

    @GetMapping("/cancel/{id}")
    public String cancelEditComment(@PathVariable Long id) {
        CommentEntity comment = commentService.getCommentById(id).orElseThrow(() -> new IllegalArgumentException("¡Invalid comment id!"));
        return "redirect:/post/postPage/" + comment.getPost().getId();
    }

    @GetMapping("/delete/{id}")
    public String deleteComment(@PathVariable Long id) {
        CommentEntity comment = commentService.getCommentById(id).orElseThrow(() -> new IllegalArgumentException("¡Invalid comment id!"));
        commentService.deleteCommentById(id);
        return "redirect:/post/postPage/" + comment.getPost().getId();
    }
}
