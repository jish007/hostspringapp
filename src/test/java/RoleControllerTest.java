import com.carparking.project.Application;
import com.carparking.project.RoleController;
import com.carparking.project.entities.Role;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,classes = Application.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@ExtendWith(SpringExtension.class)
public class RoleControllerTest {

    @Autowired
    private MockMvc mockMvc;  // MockMvc will be injected by Spring

    @Test
    public void testGetAllRoles() throws Exception {
        MvcResult result = mockMvc.perform(get("/allRoles")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        ObjectMapper mapper = new ObjectMapper();
        List<Role> actualRoles = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<Role>>() {});

        List<Role> expectedRoles = List.of(
                new Role("Auto Entry and Exit", "automated barriers for entry and exit"),
                new Role("Qr code Entry and Exit", "without automated barriers for entry and exit")
        );

        assertEquals(expectedRoles, actualRoles);

    }
}