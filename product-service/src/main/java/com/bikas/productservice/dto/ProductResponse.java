package com.bikas.productservice.dto;

import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {

	private String id;
	private String name;
	private String description;
	private BigInteger price;

}
