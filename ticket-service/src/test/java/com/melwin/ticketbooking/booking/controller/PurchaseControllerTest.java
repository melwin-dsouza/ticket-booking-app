package com.melwin.ticketbooking.booking.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.melwin.ticketbooking.booking.dto.PurchaseDTO;
import com.melwin.ticketbooking.booking.dto.PurchaseRequest;
import com.melwin.ticketbooking.booking.entity.TicketType;
import com.melwin.ticketbooking.booking.service.PurchaseService;

@WebMvcTest(controllers = PurchaseController.class)
public class PurchaseControllerTest {

    @MockBean
    private PurchaseService purchaseService;
    @Autowired
    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void purchaseTicket() throws Exception {

        when(purchaseService.createPurchase(any(PurchaseRequest.class))).thenReturn(1L);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/purchase")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(new PurchaseRequest(1L,1L,10,TicketType.NORMAL)))
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().is(201));
        Mockito.verify(this.purchaseService, Mockito.times(1))
        .createPurchase(Mockito.any(PurchaseRequest.class));
    }

    @Test
    void getTicket() throws Exception {
    	PurchaseDTO purchase = new PurchaseDTO();
        purchase.setPurchaseId(1L);
        when(purchaseService.getPurchase(anyLong())).thenReturn(purchase);

        mockMvc.perform(MockMvcRequestBuilders
                        .get(String.format("/purchase/%s",1))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Purchase details successfully retrieved."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.purchaseId").value(1L));
    }
    
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
