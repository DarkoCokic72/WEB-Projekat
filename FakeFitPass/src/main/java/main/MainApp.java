package main;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.put;
import static spark.Spark.delete;
import static spark.Spark.before;
import static spark.Spark.halt;
import static spark.Spark.staticFiles;

import java.io.File;
import java.security.Key;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import beans.Coach;
import beans.Comment;
import beans.IdGenerator;
import beans.Manager;
import beans.SportFacility;
import beans.User;
import beans.Workout;
import beans.WorkoutHistory;
import dto.CommentDTO;
import dto.CustomerAndWorkoutDTO;
import dto.LoggedInUserDTO;
import dto.ScheduledWorkoutDTO;
import dto.SchedulingWorkoutDTO;
import dto.SportFacilityDTO;
import dto.WorkoutDTO;
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
import repository.WorkoutHistoryRepository;
import repository.WorkoutRepository;
import service.CommentService;
import service.ManagerService;
import service.MembershipDTOService;
import service.MembershipService;
import service.ScheduledWorkoutService;
import service.SportFacilityService;
import service.UserService;
import service.WorkoutHistoryService;
import service.WorkoutService;

public class MainApp {
	private static Gson gson = new Gson();
	private static SportFacilityRepository sportFacilityRepository = new SportFacilityRepository();
	private static UserService userService = new UserService();
	private static CustomerRepository customerRepository = new CustomerRepository();
	private static CoachRepository coachRepository = new CoachRepository();
	private static ManagerRepository managerRepository = new ManagerRepository();
	private static ManagerService managerService = new ManagerService();
	private static AdministratorRepository administratorRepository = new AdministratorRepository();
	private static SportFacilityService sportFacilityService = new SportFacilityService();
	private static WorkoutHistoryService workoutHistoryService = new WorkoutHistoryService();
	private static WorkoutHistoryRepository workoutHistoryRepository = new WorkoutHistoryRepository();
	private static WorkoutRepository workoutRepository = new WorkoutRepository();
	private static WorkoutService workoutService = new WorkoutService();
	private static ScheduledWorkoutService scheduledWorkoutService = new ScheduledWorkoutService();
	private static MembershipDTOService membershipDTOService = new MembershipDTOService();
	private static MembershipService membershipService = new MembershipService();
	private static CommentService commentService = new CommentService();
	private static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	private static IdGenerator idGenerator = new IdGenerator();

	
	private static String getUsername(String auth) {
		if (auth == null)
			return "";
		String jwt = auth;
		Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt);
		return claims.getBody().getSubject();
	}
	
	private static String getRole(String auth) {
        if (auth == null)
            return "";
        String jwt = auth;
        Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt);
        return (String) claims.getBody().get("role");
	}

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		port(8080);

		staticFiles.externalLocation(new File("./static").getCanonicalPath());
		
		before("/admin/*", (request, response) -> {
            String auth = request.headers("Authorization");
            String username = getUsername(auth);
            String role = getRole(auth);
            if (username.equals("") || !role.equals("Administrator"))
                halt(401, "Go Away!");
           });
		
		before("/manager/*", (request, response) -> {
            String auth = request.headers("Authorization");
            String username = getUsername(auth);
            String role = getRole(auth);
            if (username.equals("") || !role.equals("Manager"))
                halt(401, "Go Away!");
           });
		
		before("/coach/*", (request, response) -> {
            String auth = request.headers("Authorization");
            String username = getUsername(auth);
            String role = getRole(auth);
            if (username.equals("") || !role.equals("Coach"))
                halt(401, "Go Away!");
           });
		
		before("/customer/*", (request, response) -> {
            String auth = request.headers("Authorization");
            String username = getUsername(auth);
            String role = getRole(auth);
            if (username.equals("") || !role.equals("Customer"))
                halt(401, "Go Away!");
           });
		
		post("/login", (req, res) -> {
            String username = req.queryParams("username");
            String password = req.queryParams("password");
            String jws = null;
            LoggedInUserDTO dto = new LoggedInUserDTO();
            

            User user = userService.findByID(username);
            if (user == null || !user.getPassword().equals(password)) {
                jws = "-1";
                dto.setJwt(jws);
            }  else {

                Map<String, Object> claims = new HashMap<>();
                claims.put("role", user.getRoleName());


                jws = Jwts.builder()
                        .setClaims(claims)
                        .setSubject(username)
                        .setExpiration(new Date(new Date().getTime() + 100000 * 10L)).setIssuedAt(new Date())
                        .signWith(key).compact();
                dto.setJwt(jws);
                dto.setRole(user.getRoleName());
            }

            return gson.toJson(dto);

        });
		
		post("/registration", (req, res) -> {
			Gson gsonReg = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			User user = gsonReg.fromJson(req.body(), User.class);
			if (!userService.register(user)) {
				return false;
			}
			
			String facilityName = req.queryParams("facility");
			
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
			String username = getUsername(req.headers("Authorization"));
			User user = userService.findByID(username);
			return gsonReg.toJson(user);
		});
		
		get("/manager/obtainContent", (req, res) -> {
			Gson gsonReg = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			String username = getUsername(req.headers("Authorization"));
			Manager manager = managerRepository.getOne(username);
			String facilityName = manager.getSportFacility().getName();
			
			List<Workout> content = sportFacilityRepository.getContentByFacilityName(facilityName);
			return gsonReg.toJson(content);
		});
		
		get("/manager/obtainSingleContent", (req, res) -> {
			Gson gsonReg = new GsonBuilder().create();
			String username = getUsername(req.headers("Authorization"));
			String contentName = req.queryParams("name");
			
			Manager manager = managerRepository.getOne(username);
			String facilityName = manager.getSportFacility().getName();
			
			Workout workout = sportFacilityRepository.getByContentName(facilityName, contentName);
			
			DateTimeFormatter formater = DateTimeFormatter.ISO_DATE_TIME;
			
			WorkoutDTO workoutDTO = new WorkoutDTO(workout.getName(), workout.getType(), workout.getSportFacility(), workout.getDuration().format(formater), workout.getCoach().getUsername(), workout.getDescription(), workout.getImage());
			
			return gsonReg.toJson(workoutDTO);
		});
		
		put("/editData", (req, res) -> {
			String username = getUsername(req.headers("Authorization"));

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
		
		get("/admin/allUsers", (req, res) -> {
			List<User> unfiltered = userService.getAll();
			String nameSearch = req.queryParams("nameSearch");
			String surnameSearch = req.queryParams("surnameSearch");
			String usernameSearch = req.queryParams("usernameSearch");
			if (nameSearch == null && surnameSearch == null && usernameSearch == null) {
				return gson.toJson(unfiltered);
			}
			return gson.toJson(userService.filterUsers(unfiltered, nameSearch, surnameSearch, usernameSearch));
		});
		
		get("/admin/freeManagers", (req, res) -> {
			return gson.toJson(managerRepository.getFreeManagersUsernames());
		});
		
		post("/admin/newFacility", (req, res) -> {
			SportFacilityDTO sportFacilityDTO = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm").create().fromJson(req.body(), SportFacilityDTO.class);
			SportFacility sportFacility = new SportFacility(sportFacilityDTO.getName(), sportFacilityDTO.getType(), true, sportFacilityDTO.getLocation(), sportFacilityDTO.getImage(), 0.0, 
					LocalDateTime.parse(sportFacilityDTO.getStartTime()), LocalDateTime.parse(sportFacilityDTO.getEndTime()));
			if (!sportFacilityRepository.addOne(sportFacility)) {
				return false;
			}
			if (req.queryParams("manager") != null) {
				managerRepository.setFacilityForManagerUsername(sportFacility, req.queryParams("manager"));
			}
			return true;
		});
		
		post("/manager/registerContent", (req, res) -> {
			Gson gsonReg = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm").create();
			WorkoutDTO workoutDTO = gsonReg.fromJson(req.body(), WorkoutDTO.class);
			
			Coach c;
			if(workoutDTO.getCoach().equals("")) {
				c= new Coach();
			} else {
				c = coachRepository.getCoachByUsername(workoutDTO.getCoach());
			}
			
			Workout workout = new Workout(idGenerator.generateRandomKey(4),workoutDTO.getName(),
					workoutDTO.getType(),
					null,
					LocalDateTime.parse(workoutDTO.getDuration()),
					c,
					workoutDTO.getDescription(),
					workoutDTO.getImage());
			
			String username = getUsername(req.headers("Authorization"));
			String facilityName = managerRepository.getFacilityName(username);
			
			if(sportFacilityRepository.getWorkout(facilityName, workout.getName()) != null) {
				return false;
			}
			sportFacilityRepository.addWorkoutToContent(facilityName, workout);
			return true;
		});
		
		put("/manager/editContent", (req, res) -> {
			String username = getUsername(req.headers("Authorization"));
			
			Gson gsonReg = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm").create();
			WorkoutDTO workoutDTO = gsonReg.fromJson(req.body(), WorkoutDTO.class);
			
			Coach c;
			if(workoutDTO.getCoach().equals("")) {
				c= new Coach();
			} else {
				c = coachRepository.getCoachByUsername(workoutDTO.getCoach());
			}
			
			Workout workout = new Workout(idGenerator.generateRandomKey(4),workoutDTO.getName(),
					workoutDTO.getType(),
					null,
					LocalDateTime.parse(workoutDTO.getDuration()),
					c,
					workoutDTO.getDescription(),
					workoutDTO.getImage());
			
			String facilityName = managerRepository.getFacilityName(username);
			
			sportFacilityRepository.putEditedContent(facilityName, workout);
			return true;
		});
		
		get("/manager/allCoaches", (req, res) -> {
			return gson.toJson(coachRepository.getAllCoachesUsernames());
		});
		
		get("/customer/allWorkouts", (req, res) -> {
			res.type("application/json");
			return gson.toJson(workoutHistoryService.getAllWorkoutsInLastMonth(getUsername(req.headers("Authorization"))));
		});
		
		get("/coach/allWorkouts", (req, res) -> {
			res.type("application/json");
			return gson.toJson(workoutService.getAllWorkoutsForCoach(getUsername(req.headers("Authorization"))));
		});
		
		get("/manager/allWorkouts", (req, res) -> {
			res.type("application/json");
			return gson.toJson(workoutService.getAllWorkoutsForManager(managerService.findSportFacilityByManager(getUsername(req.headers("Authorization")))));
		});
		
		get("/customer/allPersonalWorkouts", (req, res) -> {
			res.type("application/json");
			return gson.toJson(workoutService.getAllPersonalWorkouts());
		});
		
		post("/customer/scheduleTraining", (req, res) -> {
			res.type("application/json");
			Gson gson = new GsonBuilder().create();
			SchedulingWorkoutDTO schedulingWorkoutDTO = gson.fromJson(req.body(), SchedulingWorkoutDTO.class);
			ScheduledWorkoutDTO scheduledWorkoutDTO = new ScheduledWorkoutDTO(idGenerator.generateRandomKey(4), getUsername(req.headers("Authorization")),
					userService.findByID(getUsername(req.headers("Authorization"))).getName(), userService.findByID(getUsername(req.headers("Authorization"))).getSurname(), 
					workoutService.findWorkoutById(schedulingWorkoutDTO.getId()), LocalDateTime.parse(schedulingWorkoutDTO.getDateTimeOfWorkout()));
			if(scheduledWorkoutService.findWorkoutByCoachAndTime(workoutService.findCoachOfWorkoutById(schedulingWorkoutDTO.getId()), scheduledWorkoutDTO.getDateTimeOfWorkout()) == true) {
				return false;
			}
			scheduledWorkoutService.addScheduledWorkout(scheduledWorkoutDTO);
			return true;
			
		});
		
		get("/coach/allScheduledWorkouts", (req, res) -> {
			res.type("application/json");
			return gson.toJson(scheduledWorkoutService.getAllScheduledWorkouts(getUsername(req.headers("Authorization"))));
		});
		
		delete("/coach/cancelWorkout", (req, res) -> {
			res.type("application/json");
			String id = req.queryParams("id");
			return gson.toJson(scheduledWorkoutService.cancelWorkout(id));
		});
		
		get("/manager/getSportFacility", (req, res) -> {
			res.type("application/json");
			return gson.toJson(managerRepository.getSportFacilityByUsername(getUsername(req.headers("Authorization"))));
		});
		
		get("/manager/getCoaches", (req, res) -> {
			res.type("application/json");
			return gson.toJson(workoutService.getCoachesBySportFacilityName(managerRepository.getSportFacilityByUsername(getUsername(req.headers("Authorization")))));
		});
		
		get("/manager/getCustomers", (req, res) -> {
			res.type("application/json");
			return gson.toJson(workoutHistoryService.getCustomersBySportFacilityName(managerRepository.getSportFacilityByUsername(getUsername(req.headers("Authorization")))));
		});
		
		get("/allWorkouts", (req, res) -> {
			res.type("application/json");
			return gson.toJson(workoutHistoryService.getAllScheduledAndWorkoutHistoryWorkouts());
		});
		
		get("/searchWorkouts", (req, res) -> {
			res.type("application/json");
			String sportFacilityNameSearch = req.queryParams("sportFacilityNameSearch");
			String dateFrom = req.queryParams("dateFrom");
			String dateTo = req.queryParams("dateTo");

			if (sportFacilityNameSearch == null && dateFrom == null && dateTo == null) {
				return gson.toJson(workoutHistoryService.getAllScheduledAndWorkoutHistoryWorkouts());
			}
			return gson.toJson(workoutHistoryService.searchWorkouts(workoutHistoryService.getAllScheduledAndWorkoutHistoryWorkouts(), sportFacilityNameSearch, dateFrom, dateTo));
		});
		
		get("/filterWorkouts", (req, res) -> {
			res.type("application/json");
			String typeFacilityFilter = req.queryParams("typeFacilityFilter");
			String typeWorkoutFilter = req.queryParams("typeWorkoutFilter");

			if (typeFacilityFilter == "" && typeWorkoutFilter == "") {
				return gson.toJson(workoutHistoryService.getAllScheduledAndWorkoutHistoryWorkouts());
			}
			return gson.toJson(workoutHistoryService.filterWorkouts(workoutHistoryService.getAllScheduledAndWorkoutHistoryWorkouts(), typeFacilityFilter, typeWorkoutFilter));
		});
		
		get("/sortDates", (req, res) -> {
			res.type("application/json");
			return gson.toJson(workoutHistoryService.sortWorkoutsByDateTime());
		});
		
		get("/customer/allMemberships", (req, res) -> {
			res.type("application/json");
			return gson.toJson(membershipDTOService.getAll());
		});
		
		get("/customer/displayMembership", (req, res) -> {
			res.type("application/json");
			return gson.toJson(membershipDTOService.findMembershipById(req.queryParams("membershipId")));
		});
		
		post("/customer/buyMembership", (req, res) -> {
			res.type("application/json");
			return gson.toJson(customerRepository.addMembershipId(getUsername(req.headers("Authorization")), membershipService.addMembership(getUsername(req.headers("Authorization")), membershipDTOService.findMembershipById(req.queryParams("membershipId")))));
		});
		
		get("/manager/allWorkouts", (req, res) -> {
			res.type("application/json");
			return gson.toJson(workoutService.getWorkoutsBySportFacilityName(managerRepository.getSportFacilityByUsername(getUsername(req.headers("Authorization")))));
		});
		
		get("/manager/allCustomers", (req, res) -> {
			res.type("application/json");
			return gson.toJson(customerRepository.getAll());
		});
		
		put("/manager/changeStatus", (req, res) -> {
			res.type("application/json");
			return gson.toJson(membershipService.changeStatus());
		});
		
		post("/manager/defineTerm", (req, res) -> {
			res.type("application/json");
			Gson gson = new GsonBuilder().create();
			CustomerAndWorkoutDTO customerAndWorkoutDTO = gson.fromJson(req.body(), CustomerAndWorkoutDTO.class);
			if(membershipService.findMembershipById(customerAndWorkoutDTO.getCustomerId()).getStatus() == false || membershipService.findMembershipById(customerAndWorkoutDTO.getCustomerId()).getNumberOfAppointments() <= 0) {
				customerRepository.setTypeOfCustomer(workoutHistoryService.findNumberOfUsedTerms(customerRepository.getOne(customerAndWorkoutDTO.getCustomerId())), customerRepository.getOne(customerAndWorkoutDTO.getCustomerId()));
				return false;
			}
			membershipService.decreaseNumberOfAppointments(membershipService.findMembershipById(customerAndWorkoutDTO.getCustomerId()));
			return gson.toJson(workoutHistoryService.defineTerm(customerRepository.getOne(customerAndWorkoutDTO.getCustomerId()), workoutService.findWorkoutById(customerAndWorkoutDTO.getWorkoutId())));
		});
		
		get("/customer/customerMembership", (req, res) -> {
			res.type("application/json");
			return gson.toJson(membershipService.findMembershipById(getUsername(req.headers("Authorization"))));
		});
		
		put("/manager/decreaseNumberOfAppointments", (req, res) -> {
			res.type("application/json");
			return gson.toJson(membershipService.decreaseNumberOfAppointmentsByOne());
		});
		
		get("/customer/getCustomer", (req, res) -> {
			res.type("application/json");
			return gson.toJson(customerRepository.getOne(getUsername(req.headers("Authorization"))));
		});
		
		get("/customer/allVisitedSportFacilities", (req, res) -> {
			res.type("application/json");
			return gson.toJson(workoutHistoryService.getAllVisitedSportFacilities(getUsername(req.headers("Authorization"))));
		});
		
		post("/customer/createComment", (req, res) -> {
			res.type("application/json");
			Gson gson = new GsonBuilder().create();
			return gson.toJson(commentService.addNewComment(new Comment(idGenerator.generateRandomKey(4), gson.fromJson(req.body(), CommentDTO.class))));
		});
		
		get("/admin/allComments", (req, res) -> {
			res.type("application/json");
			return gson.toJson(commentService.getAllComments());
		});
		
		put("/admin/denieComment", (req, res) -> {
			res.type("application/json");
			return gson.toJson(commentService.denieComment(req.queryParams("id")));
		});
		
		put("/admin/aproveComment", (req, res) -> {
			res.type("application/json");
			return gson.toJson(commentService.aproveComment(req.queryParams("id")));
		});
		
		get("/getAprovedComments", (req, res) -> {
			res.type("application/json");
			return gson.toJson(commentService.getAprovedComments(req.queryParams("sportFacilityName")));
		});
		
		get("/customer/getAprovedComments", (req, res) -> {
			res.type("application/json");
			return gson.toJson(commentService.getAprovedComments());
		});
	}

}
