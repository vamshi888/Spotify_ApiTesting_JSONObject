package com.bridzelabz;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class SpotifyApiTestingUsingJSONObject {
	String token;
	String userid;
	String playlistid;

	@BeforeTest
	public void getToken() {
		token = "Bearer BQDbmlBsrd9a1G8jWh5eqQuMW8FxQsT_nFuewBhjlmvWb8FTPdlhe29agJrHP2e48ubvvY34E96Ae3V1KadY19G1fCj74ctTKTR0JH47RRRUD5ZzhUDJS_W7LeHk2KZakKpyNuWpR4RT7Jk9TyD9qGSC35GqiXQskeU54fUA_p2RipIUYIdBfy-Q6x5TRQutiZsV-AT93HRV0yzrNw-mwQaTCVFNdFFRwLtSKVlfg-4Emgvi4eLLlyJSLHEtQw5XJCqN";
	}

		// --------------------------------- User Profile --------------------------------

	@Test (priority=1)
	public void Get_Current_Users_Profile() {
			RequestSpecification requestSpecification = RestAssured.given();
			requestSpecification.accept(ContentType.JSON);
			requestSpecification.contentType("application/json");
			requestSpecification.header("Authorization", token);
			Response response = requestSpecification.get("https://api.spotify.com/v1/me");
			System.out.println("Response code: " + response.getStatusCode());
			userid = response.path("id");
			System.out.println("My user ID:" + userid);
			response.prettyPrint();
		int statusCode = response.statusCode();
		Assert.assertEquals(statusCode, 200);
	}

	@Test (priority=2)
	public void Get_Users_Profile() {
			RequestSpecification requestSpecification = RestAssured.given();
			requestSpecification.accept(ContentType.JSON);
			requestSpecification.contentType("application/json");
			requestSpecification.header("Authorization", token);
			Response response = requestSpecification.get("https://api.spotify.com/v1/users/"+userid+"/");
			System.out.println("Response code: " + response.getStatusCode());
			response.prettyPrint();
		int statusCode = response.statusCode();
		Assert.assertEquals(statusCode, 200);
	}

			// --------------------------------- PlayList --------------------------------

	@Test (priority=3)
	public void Create_Playlist1() {
			RequestSpecification requestSpecification = RestAssured.given();
			requestSpecification.accept(ContentType.JSON);
			requestSpecification.contentType("application/json");
			requestSpecification.header("Authorization", token);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("name", "Vamshi");
			jsonObject.put("description", "new playlist description");
			//jsonObject.put("public", "false");
			requestSpecification.body(jsonObject.toString());
			Response response = requestSpecification.post("https://api.spotify.com/v1/users/"+userid+"/playlists");
			System.out.println("Response code: " + response.getStatusCode());
			playlistid = response.path("id");
			System.out.println("My Playlist ID :" + playlistid);
			response.prettyPrint();
		int statusCode = response.statusCode();
		Assert.assertEquals(statusCode, 201);
	}

	@Test (priority=4)
	public void Add_Items_to_Playlist() {
			RequestSpecification requestSpecification = RestAssured.given();
			requestSpecification.accept(ContentType.JSON);
			requestSpecification.contentType("application/json");
			requestSpecification.header("Authorization", token);
			requestSpecification.queryParam("uris", "spotify:track:4sakBPqSpuj6FJti1rPme7,spotify:track:7n5Jcqw85WSfJHrMXsk5N2,spotify:track:1cpaDNciPGlC39qPs4RkMU");
			Response response = requestSpecification.post("https://api.spotify.com/v1/playlists/"+playlistid+"/tracks");
			response.prettyPrint();
		int statusCode = response.statusCode();
		Assert.assertEquals(statusCode, 201);
	}
	@Test(priority=5)
	public void RemovePlaylistItem() {
		RequestSpecification requestSpecification = RestAssured.given();
		requestSpecification.accept(ContentType.JSON);
		requestSpecification.contentType("application/json");
		requestSpecification.header("Authorization", token);
		requestSpecification.body("{ \"tracks\": [{ \"uri\": \"spotify:track:4sakBPqSpuj6FJti1rPme7\" }] }");
		Response response = requestSpecification.delete("https://api.spotify.com/v1/playlists/"+playlistid+"/tracks");
		response.prettyPrint();
		int statusCode = response.statusCode();
		Assert.assertEquals(statusCode, 200);
	}
}
