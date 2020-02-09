package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.services.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CustomerControllerTest {

    public static final Long ID = 2L;
    public static final String FIRSTNAME = "David";
    public static final String LASTNAME = "Winter";
    public static final String CUSTOMERURL = "/shop/customers/"+ID;

    @Mock
    CustomerService customerService;

    @InjectMocks
    CustomerController customerController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();

    }

    @Test
    public void testListCustomers() throws Exception {
        CustomerDTO c1 = new CustomerDTO();
        c1.setFirstname(FIRSTNAME);
        c1.setLastname(LASTNAME);

        CustomerDTO c2 = new CustomerDTO();
        c2.setFirstname(FIRSTNAME);
        c2.setLastname(LASTNAME);

        List<CustomerDTO> customerDTOS = Arrays.asList(c1, c2);

        when(customerService.getAllCustomers()).thenReturn(customerDTOS);

        mockMvc.perform(get("/api/v1/customers/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(2)));
    }

    @Test
    public void testGetByFirstName() throws Exception {
        CustomerDTO c1 = new CustomerDTO();
        c1.setFirstname(FIRSTNAME);
        c1.setLastname(LASTNAME);

        when(customerService.getCustomerById(anyLong())).thenReturn(c1);

        mockMvc.perform(get("/api/v1/customers/"+ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo(FIRSTNAME)));
    }
}