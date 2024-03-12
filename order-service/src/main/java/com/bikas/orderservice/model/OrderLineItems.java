package com.bikas.orderservice.model;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="t_order_line_item")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderLineItems {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String skuCode;
	private BigDecimal price;
	private Integer quantity;
}
