package org.adaschool.Weather.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.adaschool.Weather.data.WeatherApiResponse;
import org.adaschool.Weather.data.WeatherApiResponse.Main;
import org.adaschool.Weather.data.WeatherReport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

public class WeatherReportServiceTest {

    @InjectMocks
    private WeatherReportService weatherReportService;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetWeatherReport() {
        // Arrange
        double latitude = 40.7128;
        double longitude = -74.0060;

        WeatherApiResponse mockResponse = new WeatherApiResponse();
        Main main = new Main();
        main.setTemperature(25.0);
        main.setHumidity(60);
        mockResponse.setMain(main);

        String url = "https://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&appid=71f5e1770198397de5b16024a85c10b6";

        when(restTemplate.getForObject(url, WeatherApiResponse.class)).thenReturn(mockResponse);

        // Act
        WeatherReport result = weatherReportService.getWeatherReport(latitude, longitude);

        // Assert
        assertNotNull(result);
        assertEquals(25.0, result.getTemperature());
        assertEquals(60, result.getHumidity());

        verify(restTemplate).getForObject(url, WeatherApiResponse.class);
    }
}
