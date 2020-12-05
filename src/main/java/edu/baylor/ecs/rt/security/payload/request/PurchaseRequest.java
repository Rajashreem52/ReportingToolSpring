
package edu.baylor.ecs.rt.security.payload.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import java.math.BigInteger;
import java.util.Set;

public class PurchaseRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;
 
    @NotBlank
    @Size(max = 50)
    @Email
    private BigInteger typel;

    @NotBlank
    private BigInteger price;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public BigInteger getTypel() {
		return typel;
	}

	public void setTypel(BigInteger typel) {
		this.typel = typel;
	}

	public BigInteger getPrice() {
		return price;
	}

	public void setPrice(BigInteger price) {
		this.price = price;
	}

   
   
}
