package main;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.staticFiles;

import java.io.File;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.Context;

import com.google.gson.Gson;


import beans.SportFacility;
import beans.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.ServletContext;
import repository.AdministratorRepository;
import repository.CoachRepository;
import repository.CustomerRepository;
import repository.ManagerRepository;
import repository.SportFacilityRepository;
import service.UserService;

public class MainApp {
	private static Gson gson = new Gson();
	private static SportFacilityRepository sportFacilityRepository = new SportFacilityRepository();
	private static UserService userService = new UserService();
	private static CustomerRepository customerRepository = new CustomerRepository();
	private static CoachRepository coachRepository = new CoachRepository();
	private static ManagerRepository managerRepository = new ManagerRepository();
	private static AdministratorRepository administratorRepository = new AdministratorRepository();
	private static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

	
	private static String getUsername(String auth) {
		if (auth == null)
			return "";
		String jwt = auth;
		Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt);
		return claims.getBody().getSubject();
	}

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		port(8080);

		staticFiles.externalLocation(new File("./static").getCanonicalPath());
		
		get("/allFacilities", (req, res) -> {
			res.type("application/json");
			return gson.toJson(sportFacilityRepository.getAll());
		});
		
		post("/login", (req, res) -> {
			String username = req.queryParams("username");
			String password = req.queryParams("password");
			String jws = null;
			List<String> response = new ArrayList<String>();
			
			User user = userService.findByID(username);
			if (user == null || !user.getPassword().equals(password)) {
				jws = "-1";
				response.add(jws);
			}  else {
				jws = Jwts.builder().setSubject(username)
						.setExpiration(new Date(new Date().getTime() + 100000 * 10L)).setIssuedAt(new Date())
						.signWith(key).compact();
				response.add(jws);
				response.add(user.getRoleName());
			}

			return gson.toJson(response);
			
		});
		
		get("/checkJWT", (req, res) -> {
			String auth = req.headers("Authorization");
			String username = getUsername(auth);
			if (username.equals(""))
				return false;
			return true;
		});
	}

}