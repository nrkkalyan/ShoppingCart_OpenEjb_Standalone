package com.nrk.cart.rest.domain;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "OrderStatus")
@XmlEnum
public enum OrderStatus {
	UNPAID, PAID, DELIVERED;
}