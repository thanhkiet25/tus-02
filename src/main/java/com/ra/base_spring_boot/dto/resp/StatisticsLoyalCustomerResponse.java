package com.ra.base_spring_boot.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StatisticsLoyalCustomerResponse {
    private long loyalCustomersCount;
    private List<CustomerInfoDTO> loyalCustomers;



    public static class CustomerInfoDTO {
        private String email;
        private long totalTickets;


        public CustomerInfoDTO(String email, long totalTickets) {
            this.email = email;
            this.totalTickets = totalTickets;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public long getTotalTickets() {
            return totalTickets;
        }

        public void setTotalTickets(long totalTickets) {
            this.totalTickets = totalTickets;
        }
    }
}
