package com.krakedev.inventarios.entidades;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

public class Ventas {
	private int codigo;
	private Date fecha;
	private BigDecimal totalSinIVa;
	private BigDecimal iva;
	private BigDecimal total;
	private ArrayList<DetalleVenta> detalle;
	
	public Ventas(int codigo, Date fecha, BigDecimal totalSinIVa, BigDecimal iva, BigDecimal total) {
		super();
		this.codigo = codigo;
		this.fecha = fecha;
		this.totalSinIVa = totalSinIVa;
		this.iva = iva;
		this.total = total;
	}
	public Ventas() {
		super();
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public BigDecimal getTotalSinIVa() {
		return totalSinIVa;
	}
	public void setTotalSinIVa(BigDecimal totalSinIVa) {
		this.totalSinIVa = totalSinIVa;
	}
	public BigDecimal getIva() {
		return iva;
	}
	public void setIva(BigDecimal iva) {
		this.iva = iva;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	
	
	public ArrayList<DetalleVenta> getDetalle() {
		return detalle;
	}
	public void setDetalle(ArrayList<DetalleVenta> detalle) {
		this.detalle = detalle;
	}
	@Override
	public String toString() {
		return "Ventas [codigo=" + codigo + ", fecha=" + fecha + ", totalSinIVa=" + totalSinIVa + ", iva=" + iva
				+ ", total=" + total + "]";
	}
	
	
	
}
