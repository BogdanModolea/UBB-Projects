package com.example.bankaccounts.dto;

import com.example.bankaccounts.entity.Account;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AccountDTO {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("iban")
    private String iban;

    @JsonProperty("sold")
    private String sold;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("bank")
    private String bank;

    @JsonProperty("name")
    private String name;

    public Account toModel() {
        return Account.builder()
                .id(id)
                .iban(iban)
                .sold(sold)
                .currency(currency)
                .bank(bank)
                .name(name)
                .build();
    }
}
