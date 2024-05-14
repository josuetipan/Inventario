package com.krakedev.inventario.bdd;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.krakedev.inventarios.entidades.Categoria;
import com.krakedev.inventarios.entidades.Producto;
import com.krakedev.inventarios.entidades.UnidadDeMedida;
import com.krakedev.inventarios.excepciones.KarkeDevExcepcion;
import com.krakedev.inventarios.utils.ConexionBDD;

public class ProductoBDD {
	
	public ArrayList<Producto> buscar(String subcadena) throws KarkeDevExcepcion{
		ArrayList<Producto> productos = new ArrayList<Producto>();
		Connection con = null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		Producto producto = null;
		try {
			con = ConexionBDD.obtenerConexion(); 
			ps=con.prepareStatement("select prod.codigo_pro, prod.nombre as nombre_producto, "
					+ "udm.codigo_udm as nombre_udm, udm.descripcion as descripcion_udm, "
					+ "cast(prod.precio_venta as decimal(6,2)), prod.tiene_iva, "
					+ "cast(prod.coste as decimal(5,4)), "
					+ "prod.categoria,cat.nombre as nombre_categoria, "
					+ "stock "
					+ "from productos prod,unidades_medida udm,categorias cat "
					+ "where prod.udm = udm.codigo_udm "
					+ "and prod.categoria = cat.codigo_cat "
					+ "and upper(prod.nombre) like ?");
			ps.setString(1, "%"+subcadena.toUpperCase()+"%");
			rs = ps.executeQuery();
			
			while(rs.next()) {
				int codigoProducto = rs.getInt("codigo_pro");
				String  nombreProducto = rs.getString("nombre_producto");
				String nombreUnidadMedida = rs.getString("nombre_udm");
				String descripcionUnidadMEdida = rs.getString("descripcion_udm");
				BigDecimal precioVentas = rs.getBigDecimal("precio_venta");
				boolean tieneIva = rs.getBoolean("tiene_iva");
				BigDecimal coste = rs.getBigDecimal("coste");
				int codigoCategoria = rs.getInt("categoria");
				String nombreCategorias = rs.getString("nombre_categoria");
				int stock = rs.getInt("stock");
				
				UnidadDeMedida udm = new UnidadDeMedida();
				udm.setCodigo(nombreUnidadMedida);
				udm.setDescripcion(descripcionUnidadMEdida);
				
				Categoria categoria = new Categoria();
				categoria.setCodigo(codigoCategoria);
				categoria.setNombre(nombreCategorias);
				
				producto = new Producto();
				producto.setCodigo(codigoProducto);
				producto.setNombre(nombreProducto);
				producto.setUnidadMedida(udm);
				producto.setPrecioVenta(precioVentas);
				producto.setTieneIva(tieneIva);
				producto.setCoste(coste);
				producto.setCategoria(categoria);
				producto.setStock(stock);
				
				productos.add(producto);
				
			}
		} catch (KarkeDevExcepcion e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KarkeDevExcepcion("Error al consultar: Detalle:"+e.getMessage());
		}finally {
			if(con!=null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return productos;
	}
	
	
	public void insertar(Producto producto) throws KarkeDevExcepcion {
		Connection con = null;
		PreparedStatement ps=null;
		try {
			con = ConexionBDD.obtenerConexion();
			ps = con.prepareStatement("INSERT INTO public.productos("
					+ "	codigo_pro, nombre, udm, precio_venta, tiene_iva, coste, categoria, stock)"
					+ "	VALUES (?, ?, ?, ?, ?, ?, ?, ?);");
			ps.setInt(1, producto.getCodigo());
			ps.setString(2, producto.getNombre());
			ps.setString(3, producto.getUnidadMedida().getCodigo());
			ps.setBigDecimal(4, producto.getPrecioVenta());
			ps.setBoolean(5, producto.isTieneIva());
			ps.setBigDecimal(6, producto.getCoste());
			ps.setInt(7, producto.getCategoria().getCodigo());
			ps.setInt(8, producto.getStock());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KarkeDevExcepcion("Error al insertar proveedor");
		} catch (KarkeDevExcepcion e) {
			throw e;
		}finally {
			if(con!=null) {	
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
}
