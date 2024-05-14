package com.krakedev.inventarios.entidades;

import java.math.BigDecimal;

public class DetalleVenta {
	private int codigo;
	private Ventas ventas;
	private Producto producto;
	private int cantidad;
	private BigDecimal precioVentas;
	private BigDecimal subtotal;
	private BigDecimal subtotalConIva;
	public DetalleVenta(int codigo, Ventas ventas, Producto producto, int cantidad, BigDecimal precioVentas,
			BigDecimal subtotal, BigDecimal subtotalConIva) {
		super();
		this.codigo = codigo;
		this.ventas = ventas;
		this.producto = producto;
		this.cantidad = cantidad;
		this.precioVentas = precioVentas;
		this.subtotal = subtotal;
		this.subtotalConIva = subtotalConIva;
	}
	public DetalleVenta() {
		super();
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public Ventas getVentas() {
		return ventas;
	}
	public void setVentas(Ventas ventas) {
		this.ventas = ventas;
	}
	public Producto getProducto() {
		return producto;
	}
	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public BigDecimal getPrecioVentas() {
		return precioVentas;
	}
	public void setPrecioVentas(BigDecimal precioVentas) {
		this.precioVentas = precioVentas;
	}
	public BigDecimal getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}
	public BigDecimal getSubtotalConIva() {
		return subtotalConIva;
	}
	public void setSubtotalConIva(BigDecimal subtotalConIva) {
		this.subtotalConIva = subtotalConIva;
	}
	@Override
	public String toString() {
		return "detalleVenta [codigo=" + codigo + ", ventas=" + ventas + ", producto=" + producto + ", cantidad="
				+ cantidad + ", precioVentas=" + precioVentas + ", subtotal=" + subtotal + ", subtotalConIva="
				+ subtotalConIva + "]";
	}
	
	
	
	
	
	
	
}
