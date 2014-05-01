package com.nrk.cart.web.domain;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "Product")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Product", propOrder = {"productId", "productName", "unitPrice"})
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long productId;
	private String productName;
	private double unitPrice;

	public Long getProductId() {
		return productId;
	}

	public void setProductId(final Long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(final String productName) {
		this.productName = productName;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(final double unitPrice) {
		this.unitPrice = unitPrice;
	}

}
