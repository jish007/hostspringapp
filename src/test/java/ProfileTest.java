import com.carparking.project.Application;
import com.carparking.project.entities.Slots;
import com.carparking.project.helper.SlotsHelper;
import com.carparking.project.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,classes = Application.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
public class ProfileTest {


    private final String FORM_ID="250874001847053";
    @Autowired
    private MockMvc mockMvc;  // MockMvc will be injected by Spring

    @MockBean
    private ImageService imageService;

    @Mock
    EmailService emailService;


    @Autowired
    ProfileService profileService;

    @BeforeEach
    public void initiateSubmission(){

        String apiKey = "cddafdb394ece027f76898d35eca913d";
        String formId = "250874001847053";
        String url = "https://api.jotform.com/form/" + formId + "/submissions?apiKey=" + apiKey;

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("3", "John Doe"); // Name
        requestBody.put("4", "john.doe@example.com"); // Email
        requestBody.put("5", "KL65H432"); // Vehicle Number
        requestBody.put("6", "9645794547"); // Phone Number


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("APIKEY", apiKey); // Move API key to headers

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
        RestTemplate restTemplate = new RestTemplate();

        // Send POST request
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        // Print response
        System.out.println(response.getBody());
    }


    @Test
    @Order(1)
    public void testprofilecreation() throws Exception {
        String jsonPayload = "{ \"sensorId\": \"123\", \"status\": \"active\" }";
        Map<String,String>  requestmap = new HashMap<>();
        when(imageService.getVehicleNumber()).thenReturn("kl65h432");
        MvcResult result = mockMvc.perform( post("/updateSensor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload)) // Attach JSON body
                .andExpect(status().isOk())
                .andReturn();
        String responseBody = result.getResponse().getContentAsString();
        Assertions.assertTrue(responseBody.contains("up"),responseBody);
        String response = profileService.updateProfile();
        Assertions.assertEquals(response,"Successfully completed");
        System.out.println("Response: " + responseBody);
    }

    @Test
    @Order(2)
    public void testTimer() throws Exception {
        MvcResult result = mockMvc.perform(get("/profiles/timer")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        Assertions.assertNotNull(result.getResponse());

    }

    @Test
    @Order(3)
    public void testLeaveSlot() throws Exception {
        MvcResult result = mockMvc.perform(get("/profiles/leave?slotNumber=A1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println(result.getResponse());
    }
}