package repository;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.reflect.TypeToken;

import beans.Comment;

public class CommentRepository extends Repository<Comment, String>{

	@Override
	protected String getKey(Comment comment) {
		
		return comment.getId();
	}

	@Override
	protected Type getTokenType() {
		
		return new TypeToken<ArrayList<LogicalEntity<Comment>>>(){}.getType();
	}
}
