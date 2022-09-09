import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.TreeSet;
import java.util.Vector;



//json.simple 1.1
// import org.json.simple.JSONObject;
// import org.json.simple.JSONArray;

// Alternate implementation of JSON modules.
import org.json.JSONObject;
import org.json.JSONArray;

public class GetData{

    static String prefix = "project3.";

    // You must use the following variable as the JDBC connection
    Connection oracleConnection = null;

    // You must refer to the following variables for the corresponding
    // tables in your database

    String cityTableName = null;
    String userTableName = null;
    String friendsTableName = null;
    String currentCityTableName = null;
    String hometownCityTableName = null;
    String programTableName = null;
    String educationTableName = null;
    String eventTableName = null;
    String participantTableName = null;
    String albumTableName = null;
    String photoTableName = null;
    String coverPhotoTableName = null;
    String tagTableName = null;

    // This is the data structure to store all users' information
    // DO NOT change the name
    JSONArray users_info = new JSONArray();		// declare a new JSONArray


    // DO NOT modify this constructor
    public GetData(String u, Connection c) {
	super();
	String dataType = u;
	oracleConnection = c;
	// You will use the following tables in your Java code
	cityTableName = prefix+dataType+"_CITIES";
	userTableName = prefix+dataType+"_USERS";
	friendsTableName = prefix+dataType+"_FRIENDS";
	currentCityTableName = prefix+dataType+"_USER_CURRENT_CITIES";
	hometownCityTableName = prefix+dataType+"_USER_HOMETOWN_CITIES";
	programTableName = prefix+dataType+"_PROGRAMS";
	educationTableName = prefix+dataType+"_EDUCATION";
	eventTableName = prefix+dataType+"_USER_EVENTS";
	albumTableName = prefix+dataType+"_ALBUMS";
	photoTableName = prefix+dataType+"_PHOTOS";
	tagTableName = prefix+dataType+"_TAGS";
    }




    //implement this function

    @SuppressWarnings("unchecked")
    public JSONArray toJSON() throws SQLException{

    	JSONArray users_info = new JSONArray();

	// Your implementation goes here....
	try (Statement stmt = oracleConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)){

		ResultSet rst = stmt.executeQuery(
		//"SELECT COUNT(*) FROM " + userTableName);
			"SELECT * FROM " + userTableName);
		while (rst.next()) {
			JSONObject test1 = new JSONObject();
			           // from all users //rst.getLong(1), rst.getString(2), rst.getString(3))
			test1.put("user_id", rst.getInt(1));
			test1.put("first_name", rst.getString(2));
			test1.put("last_name", rst.getString(3));
			test1.put("YOB", rst.getInt(4));
			test1.put("MOB", rst.getInt(5));
			test1.put("DOB", rst.getInt(6));
			test1.put("gender", rst.getString(7));
			users_info.put(test1);

			int cityID = 0;
			try (Statement stmt2 = oracleConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)){
					ResultSet rst2 = stmt2.executeQuery("SELECT * FROM " + currentCityTableName +
					" WHERE User_ID = "+ rst.getInt(1));
					while (rst2.next()) {
					cityID = rst2.getInt(2);
					}
					//test1.put("hometown", ht);
			}

			//test1.put("CityID", cityID);

			int homeID = 0;
			try (Statement stmt2 = oracleConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)){
					ResultSet rst2 = stmt2.executeQuery("SELECT * FROM " + hometownCityTableName +
					" WHERE User_ID = "+ rst.getInt(1));
					while (rst2.next()) {
					homeID = rst2.getInt(2);
					}
					//test1.put("hometown", ht);
			}

			//test1.put("HomeID", homeID);


			JSONObject current = new JSONObject();
			try (Statement stmt2 = oracleConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)){
					ResultSet rst2 = stmt2.executeQuery("SELECT * FROM " + cityTableName +
					" WHERE CITY_ID = "+ cityID);
					while (rst2.next()) {
					current.put("country",rst2.getString(4));
					current.put("city",rst2.getString(2));
					current.put("state",rst2.getString(3));
					}
					//test1.put("hometown", ht);
			}
			test1.put("current", current);

			JSONObject home = new JSONObject();
			try (Statement stmt2 = oracleConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)){
					ResultSet rst2 = stmt2.executeQuery("SELECT * FROM " + cityTableName +
					" WHERE CITY_ID = "+ homeID);
					while (rst2.next()) {
					home.put("country",rst2.getString(4));
					home.put("city",rst2.getString(2));
					home.put("state",rst2.getString(3));
					}
					//test1.put("hometown", ht);
			}
			test1.put("hometown", home);



			JSONArray fList = new JSONArray();
			try (Statement stmt2 = oracleConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)){
					ResultSet rst2 = stmt2.executeQuery("SELECT * FROM " + friendsTableName +
					" WHERE User1_ID = "+ rst.getInt(1));
					while (rst2.next()) {

						fList.put(rst2.getInt(2));
						//fList.put(rst2.getInt(3));
					}

		}

		test1.put("friends", fList);


	}//try1

		//JSONObject test = new JSONObject();
		//test.put("name", "john smith");
		//test.put("user_id", ID);
		//users_info.put(test);

		return users_info;


	}

	}//end of implementation***************************************************

    // This outputs to a file "output.json"
    public void writeJSON(JSONArray users_info) {
	// DO NOT MODIFY this function
	try {
	    FileWriter file = new FileWriter(System.getProperty("user.dir")+"/output.json");
	    file.write(users_info.toString());
	    file.flush();
	    file.close();

	} catch (IOException e) {
	    e.printStackTrace();
	}

    }
}
