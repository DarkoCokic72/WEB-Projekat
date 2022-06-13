package main;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.staticFiles;

import java.io.File;
import java.util.List;

import com.google.gson.Gson;

import beans.SportFacility;
import repository.SportFacilityRepository;

public class MainApp {
	private static Gson gson = new Gson();
	private static SportFacilityRepository sportFacilityRepository = new SportFacilityRepository();

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		port(8080);
		staticFiles.externalLocation(new File("./static").getCanonicalPath());
		
		get("/sviRestorani", (req, res) -> {
			List<SportFacility> unfiltered = sportFacilityRepository.getAll();
			
			return gson.toJson(unfiltered);
		});
	}

}
