package com.krakedev.inventarios.entidades;

public class UnidadDeMedida {
	private String codigo;
	private String descripcion;
	private CategoriaUDM categoriaUdm;
	
	public UnidadDeMedida(String codigo, String descripcion, CategoriaUDM categoriaUdm) {
		super();
		this.codigo = codigo;
		this.descripcion = descripcion;
		this.categoriaUdm = categoriaUdm;
	}
	public UnidadDeMedida() {
		super();
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public CategoriaUDM getCategoriaUdm() {
		return categoriaUdm;
	}
	public void setCategoriaUdm(CategoriaUDM categoriaUdm) {
		this.categoriaUdm = categoriaUdm;
	}
	@Override
	public String toString() {
		return "UnidadDeMedida [codigo=" + codigo + ", descripcion=" + descripcion + ", categoriaUdm=" + categoriaUdm
				+ "]";
	}
	
	
	
}
