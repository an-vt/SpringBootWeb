package com.vuan.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
// sp trong bill
public class BillProductDTO {
	private int id;
	private long unitPrice;
	private int quantity;
	private BillDTO billDTO;
	private ProductDTO productDTO;
	
	public BillProductDTO(long unitPrice, int quantity, BillDTO billDTO, ProductDTO productDTO) {
		super();
		this.unitPrice = unitPrice;
		this.quantity = quantity;
		this.billDTO = billDTO;
		this.productDTO = productDTO;
	}
	
	
}
