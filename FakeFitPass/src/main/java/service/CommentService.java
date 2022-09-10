package service;

import java.util.ArrayList;
import java.util.List;

import beans.Comment;
import beans.SportFacility;
import repository.CommentRepository;
import repository.SportFacilityRepository;

public class CommentService {

	private CommentRepository commentRepository = new CommentRepository();
	private SportFacilityRepository sportFacilityRepository = new SportFacilityRepository();
	
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
		countAverageScore(comment.getScore(), comment.getSportFacility());
		return true;
	}
	
	public boolean aproveComment(String id) {
		Comment comment = commentRepository.getOne(id);
		comment.setIsAproved(true);
		commentRepository.update(id, comment);
		countAverageScore(comment.getScore(), comment.getSportFacility());
		return true;
	}
	
	private void countAverageScore(int score, SportFacility sportFacility) {
		int sum = 0;
        int counter = 0;
        for (Comment comment : commentRepository.getAll()) {
            if (comment.getSportFacility().getName().equals(sportFacility.getName())) {
                sum += comment.getScore();
                counter++;
            }
        }
        double averageScore = (double)sum / (double)counter;
		sportFacility.setAverageScore(averageScore);
		sportFacilityRepository.update(sportFacility.getName(), sportFacility);
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
	
	public List<Comment> getAprovedComments(){
		List<Comment> comments = new ArrayList<Comment>();
		for(Comment comment: commentRepository.getAll()) {
			if(comment.getIsAproved() == true) {
				comments.add(comment);
			}
		}
		return comments;
	}
	
	public List<Comment> getAprovedAndDeniedComments(){
		List<Comment> comments = new ArrayList<Comment>();
		for(Comment comment: commentRepository.getAll()) {
			if(comment.getIsAproved() == true || comment.getIsDenied() == true) {
				comments.add(comment);
			}
		}
		return comments;
	}
}
