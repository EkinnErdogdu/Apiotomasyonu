package org.example.tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class BookingTest {
    private final String BASE_URL = "https://restful-booker.herokuapp.com/";
    private final String credentials = "{\n" +
            "  \"username\": \"admin\",\n" +
            "  \"password\": \"password123\"\n" +
            "}";

        @Test
        public void createBookingTest() {
            String bookingDetails = "{\n" +
                    "  \"firstname\": \"Ekin\",\n" +
                    "  \"lastname\": \"Erdogdu\",\n" +
                    "  \"totalprice\": 1000,\n" +
                    "  \"depositpaid\": true,\n" +
                    "  \"bookingdates\": {\n" +
                    "    \"checkin\": \"2024-12-05\",\n" +
                    "    \"checkout\": \"2024-12-06\"\n" +
                    "  },\n" +
                    "  \"additionalneeds\": \"Dinner\"\n" +
                    "}";

            Response response = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(bookingDetails)
                    .post(BASE_URL + "booking");

            assertEquals(200, response.getStatusCode(), "Rezervasyon oluşturulmadı");
            System.out.println("Rezervasyon Oluşturuldu: " + response.asString());
        }

    @Test
    public void getBookingTest() {
        int bookingId = 1;

        Response response = RestAssured.given()
                .get(BASE_URL + "booking/" + bookingId);

        assertEquals(200, response.getStatusCode(), "Rezervasyon yok");
        System.out.println("Rezervasyon alındı: " + response.asString());
    }
    @Test
    public void updateBookingTest() {
        int bookingId = 1;

        String updatedBookingDetails = "{\n" +
                "  \"firstname\": \"Ayşe\",\n" +
                "  \"lastname\": \"Eroglu\",\n" +
                "  \"totalprice\": 1500,\n" +
                "  \"depositpaid\": true,\n" +
                "  \"bookingdates\": {\n" +
                "    \"checkin\": \"2024-12-06\",\n" +
                "    \"checkout\": \"2025-01-22\"\n" +
                "  },\n" +
                "  \"additionalneeds\": \"Breakfast\"\n" +
                "}";

        Response authResponse = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(credentials)
                .post(BASE_URL + "auth");

        String token = authResponse.jsonPath().getString("token");

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Cookie", "token=" + token)
                .body(updatedBookingDetails)
                .put(BASE_URL + "booking/" + bookingId);

        assertEquals(200, response.getStatusCode(), "Rezervasyon güncellenmedi");
        System.out.println("Rezervasyon güncellendi: " + response.asString());
    }
    @Test
    public void deleteBookingTest() {
        int bookingId = 1;

        Response authResponse = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(credentials)
                .post(BASE_URL + "auth");

        String token = authResponse.jsonPath().getString("token");

        Response response = RestAssured.given()
                .header("Cookie", "token=" + token)
                .delete(BASE_URL + "booking/" + bookingId);

        assertEquals(201, response.getStatusCode(), "Rezervasyon silinemedi");
        System.out.println("Rezervasyon Silindi: " + response.asString());
    }


}

