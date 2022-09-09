package service;

import beans.Comment;
import repository.CommentRepository;

public class CommentService {

	private CommentRepository commentRepository = new CommentRepository();
	
	public boolean addNewComment(Comment comment) {
		return commentRepository.addOne(comment);
	}
}
