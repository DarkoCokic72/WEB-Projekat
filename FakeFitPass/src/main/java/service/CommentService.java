package service;

import java.util.ArrayList;
import java.util.List;

import beans.Comment;
import repository.CommentRepository;

public class CommentService {

	private CommentRepository commentRepository = new CommentRepository();
	
	public boolean addNewComment(Comment comment) {
		return commentRepository.addOne(comment);
	}
	
	public List<Comment> getAllComments(){
		List<Comment> comments = new ArrayList<Comment>();
		for(Comment comment: commentRepository.getAll()) {
			if(comment.getIsAproved() == false && comment.getIsDenied() == false) {
				comments.add(comment);
			}
		}
		return comments;
	}
	
	public boolean denieComment(String id) {
		Comment comment = commentRepository.getOne(id);
		comment.setIsDenied(true);
		commentRepository.update(id, comment);
		return true;
	}
	
	public boolean aproveComment(String id) {
		Comment comment = commentRepository.getOne(id);
		comment.setIsAproved(true);
		commentRepository.update(id, comment);
		return true;
	}
	
	public List<Comment> getAprovedComments(String sportFacilityName){
		List<Comment> comments = new ArrayList<Comment>();
		for(Comment comment: commentRepository.getAll()) {
			if(comment.getSportFacility().getName().equals(sportFacilityName)) {
				if(comment.getIsAproved() == true) {
					comments.add(comment);
				}
			}
		}
		return comments;
	}
}
