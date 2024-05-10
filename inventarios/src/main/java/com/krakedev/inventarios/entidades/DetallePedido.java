package com.krakedev.inventarios.entidades;

import java.math.BigDecimal;

public class DetallePedido {
	private int codigo;
	private Pedido pedido;
	private Producto producto;
	private int cantidadSolicitada;
	private BigDecimal subtotal;
	private int cantidadRecibida;
	public DetallePedido(int codigo, Pedido pedido, Producto producto, int cantidadSolicitada, BigDecimal subtotal,
			int cantidadRecibida) {
		super();
		this.codigo = codigo;
		this.pedido = pedido;
		this.producto = producto;
		this.cantidadSolicitada = cantidadSolicitada;
		this.subtotal = subtotal;
		this.cantidadRecibida = cantidadRecibida;
	}
	public DetallePedido() {
		super();
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public Pedido getPedido() {
		return pedido;
	}
	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}
	public Producto getProducto() {
		return producto;
	}
	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	public int getCantidadSolicitada() {
		return cantidadSolicitada;
	}
	public void setCantidadSolicitada(int cantidadSolicitada) {
		this.cantidadSolicitada = cantidadSolicitada;
	}
	public BigDecimal getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}
	public int getCantidadRecibida() {
		return cantidadRecibida;
	}
	public void setCantidadRecibida(int cantidadRecibida) {
		this.cantidadRecibida = cantidadRecibida;
	}
	@Override
	public String toString() {
		return "DetallePedido [codigo=" + codigo + ", pedido=" + pedido + ", producto=" + producto
				+ ", cantidadSolicitada=" + cantidadSolicitada + ", subtotal=" + subtotal + ", cantidadRecibida="
				+ cantidadRecibida + "]";
	}
	
	
	
}
