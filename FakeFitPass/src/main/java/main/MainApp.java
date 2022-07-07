package main;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.put;
import static spark.Spark.staticFiles;

import java.io.File;
import java.security.Key;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import beans.SportFacility;
import beans.SportFacilityTemp;
import beans.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import repository.AdministratorRepository;
import repository.CoachRepository;
import repository.CustomerRepository;
import repository.ManagerRepository;
import repository.SportFacilityRepository;
import service.SportFacilityService;
import service.UserService;

public class MainApp {
	private static Gson gson = new Gson();
	private static SportFacilityRepository sportFacilityRepository = new SportFacilityRepository();
	private static UserService userService = new UserService();
	private static CustomerRepository customerRepository = new CustomerRepository();
	private static CoachRepository coachRepository = new CoachRepository();
	private static ManagerRepository managerRepository = new ManagerRepository();
	private static AdministratorRepository administratorRepository = new AdministratorRepository();
	private static SportFacilityService sportFacilityService = new SportFacilityService();
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
		
		post("/registration", (req, res) -> {
			Gson gsonReg = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			User user = gsonReg.fromJson(req.body(), User.class);
			if (!userService.register(user)) {
				return false;
			}
			
			String facilityName = req.queryParams("facility");
			System.out.println(facilityName);
			if (!facilityName.equals("null")) {
				managerRepository.setFacilityForManagerUsername(facilityName,
						managerRepository.getOne(user.getUsername()));
			}
			return true;
		});
		
		get("/allFacilities", (req, res) -> {
			res.type("application/json");
			List<SportFacility> unfiltered = sportFacilityRepository.getAll();
			String nameSearch = req.queryParams("nameSearch");
			String locationSearch = req.queryParams("locationSearch");
			String typeSearch = req.queryParams("typeSearch");
			String scoreSearch = req.queryParams("scoreSearch");

			if(nameSearch == null && locationSearch == null && typeSearch == null && scoreSearch == null) {
				return gson.toJson(unfiltered);
			}
			
			List<SportFacility> filtered = sportFacilityService.filterFacilities(unfiltered, nameSearch, locationSearch, scoreSearch, typeSearch);
			return gson.toJson(filtered);
		});
		
		get("/facilityByName", (req, res) -> {
			return gson.toJson(sportFacilityRepository.getOne(req.queryParams("name")));
		});
		
		get("/obtainData", (req, res) -> {
			Gson gsonReg = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			String username = getUsername(req.queryParams("jwt"));
			User user = userService.findByID(username);
			return gsonReg.toJson(user);
		});
		
		put("/editData", (req, res) -> {
			String jwt = req.queryParams("jwt");
			String username = getUsername(jwt);

			Gson gsonReg = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			User editedUser = gsonReg.fromJson(req.body(), User.class);
			User user = userService.findByID(username);

			user.setDateOfBirth(editedUser.getDateOfBirth());
			user.setName(editedUser.getName());
			user.setPassword(editedUser.getPassword());
			user.setGender(editedUser.getGender());
			user.setSurname(editedUser.getSurname());

			userService.update(user);
			return true;
		});
		
		get("/allUsers", (req, res) -> {
			List<User> unfiltered = userService.getAll();
			String nameSearch = req.queryParams("nameSearch");
			String surnameSearch = req.queryParams("surnameSearch");
			String usernameSearch = req.queryParams("usernameSearch");
			if (nameSearch == null && surnameSearch == null && usernameSearch == null) {
				return gson.toJson(unfiltered);
			}
			return gson.toJson(userService.filterUsers(unfiltered, nameSearch, surnameSearch, usernameSearch));
		});
		
		get("/freeManagers", (req, res) -> {
			return gson.toJson(managerRepository.getFreeManagersUsernames());
		});
		
		post("/newFacility", (req, res) -> {
			SportFacilityTemp sportFacilityTemp = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm").create().fromJson(req.body(), SportFacilityTemp.class);
			SportFacility sportFacility = new SportFacility(sportFacilityTemp.getName(), sportFacilityTemp.getType(), null, true, sportFacilityTemp.getLocation(), sportFacilityTemp.getImage(), 0.0, 
					LocalDateTime.parse(sportFacilityTemp.getStartTime()), LocalDateTime.parse(sportFacilityTemp.getEndTime()));
			if (!sportFacilityRepository.addOne(sportFacility)) {
				return false;
			}
			if (req.queryParams("manager") != null) {
				managerRepository.setFacilityForManagerUsername(sportFacility, req.queryParams("manager"));
			}
			return true;
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
