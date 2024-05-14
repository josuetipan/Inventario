package com.krakedev.inventario.bdd;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import com.krakedev.inventarios.entidades.DetallePedido;
import com.krakedev.inventarios.entidades.Pedido;
import com.krakedev.inventarios.excepciones.KarkeDevExcepcion;
import com.krakedev.inventarios.utils.ConexionBDD;

public class PedidoBDD {
	public void insertar(Pedido pedido) throws KarkeDevExcepcion {
		Connection con = null;
		PreparedStatement ps=null;
		PreparedStatement psDet=null;
		ResultSet rsClave = null;
		int codigoCabecera=0;
		
		Date fechaActual = new Date();
		java.sql.Date fechaSQL = new java.sql.Date(fechaActual.getTime());
		
		try {
			con = ConexionBDD.obtenerConexion();
			ps = con.prepareStatement("INSERT INTO public.cabecera_pedido( "
					+ "	proveedor, fecha, estado) "
					+ "	VALUES (?, ?, ?);",Statement.RETURN_GENERATED_KEYS);
			
			ps.setString(1, pedido.getProveedor().getIdentificador());
			ps.setDate(2, fechaSQL);
			ps.setString(3, "S");
			
			ps.executeUpdate();
			
			rsClave = ps.getGeneratedKeys();
			if(rsClave.next()) {
				codigoCabecera=rsClave.getInt(1);
			}
			
			ArrayList<DetallePedido> detallePedido = pedido.getDetalles();
			DetallePedido det;
			for(int i=0;i<detallePedido.size();i++) {
				det = detallePedido.get(i);
				psDet=con.prepareStatement("INSERT INTO public.detalle_pedido( "
						+ "	 cabecera_pedido, producto, cantidad_solicitada, subtotal, cantidad_recibida) "
						+ "	VALUES ( ?, ?, ?, ?, ?);");
				psDet.setInt(1, codigoCabecera);
				psDet.setInt(2, det.getProducto().getCodigo());
				psDet.setInt(3, det.getCantidadSolicitada());
				
				BigDecimal pv = det.getProducto().getPrecioVenta();
				BigDecimal cantidad = new BigDecimal(det.getCantidadSolicitada());
				BigDecimal subtotal = pv.multiply(cantidad);
				psDet.setBigDecimal(4, subtotal);
				psDet.setInt(5, 0);
				
				psDet.executeUpdate();			}
			
			
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
	
	
	public void actualizar(Pedido pedido) throws KarkeDevExcepcion {
		Connection con = null;
		PreparedStatement ps=null;
		PreparedStatement psDet=null;
		PreparedStatement psHis=null;
		Date fechaActual = new Date();
		Timestamp fechaHoraActual = new Timestamp(fechaActual.getTime());
		
		try {
			con = ConexionBDD.obtenerConexion();
			ps = con.prepareStatement("UPDATE public.cabecera_pedido "
					+ "	SET estado='R' WHERE numero=?;");
			
			ps.setInt(1, pedido.getCodigo());
			ps.executeUpdate();
			
			
			ArrayList<DetallePedido> detallePedido = pedido.getDetalles();
			DetallePedido det;
			for(int i=0;i<detallePedido.size();i++) {
				det = detallePedido.get(i);
				psDet=con.prepareStatement("UPDATE public.detalle_pedido "
						+ "	SET subtotal=?, cantidad_recibida=? "
						+ "	WHERE codigo=?;");

				psDet.setInt(2, det.getCantidadRecibida());
				
				BigDecimal pv = det.getProducto().getPrecioVenta();
				BigDecimal cantidad = new BigDecimal(det.getCantidadRecibida());
				BigDecimal subtotal = pv.multiply(cantidad);
				psDet.setBigDecimal(1, subtotal);
				psDet.setInt(3, det.getCodigo());
				
				psDet.executeUpdate();			
				
				psHis = con.prepareStatement("INSERT INTO public.historial_stock( "
						+ "	fecha, referencia, producto, cantidad) "
						+ "	VALUES (?, ?, ?, ?);",Statement.RETURN_GENERATED_KEYS);
				
				
				psHis.setTimestamp(1,fechaHoraActual);
				psHis.setString(2, "Pedido "+pedido.getCodigo());
				psHis.setInt(3, det.getProducto().getCodigo());
				psHis.setInt(4, det.getCantidadRecibida());
				
				psHis.executeUpdate();
				
			}
			
		
			
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
	
	public void insertarVentas(Pedido pedido) throws KarkeDevExcepcion {
		Connection con = null;
		PreparedStatement ps=null;
		PreparedStatement psDet=null;
		PreparedStatement psCv=null;
		ResultSet rsClave = null;
		int codigoCabecera=0;
		
		Date fechaActual = new Date();
		Timestamp fechaHoraActual = new Timestamp(fechaActual.getTime());
		
		try {
			con = ConexionBDD.obtenerConexion();
			ps = con.prepareStatement("INSERT INTO public.cabecera_ventas(  "
					+ "	fecha, total_sin_iva, iva, total)  "
					+ "	VALUES (?, ?, ?, ?);",Statement.RETURN_GENERATED_KEYS);
			
			ps.setTimestamp(1,fechaHoraActual);
			ps.setBigDecimal(2, BigDecimal.ZERO);
			ps.setBigDecimal(3, BigDecimal.ZERO);
			ps.setBigDecimal(4, BigDecimal.ZERO);
			
			ps.executeUpdate();
			
			rsClave = ps.getGeneratedKeys();
			if(rsClave.next()) {
				codigoCabecera=rsClave.getInt(1);
			}
			
			ArrayList<DetallePedido> detallePedido = pedido.getDetalles();
			DetallePedido det;
			for(int i=0;i<detallePedido.size();i++) {
				det = detallePedido.get(i);
				psDet=con.prepareStatement("INSERT INTO public.detalle_venta( "
						+ "	cabecera_ventas, producto, cantidad, precio_ventas, subtotal, subtotal_con_iva) "
						+ "	VALUES (?, ?, ?, ?, ?, ?);");
				psDet.setInt(1, codigoCabecera);				
				psDet.setInt(2, det.getProducto().getCodigo());
				psDet.setInt(3, det.getCantidadSolicitada());
				psDet.setBigDecimal(4, det.getProducto().getPrecioVenta());
				BigDecimal pv = det.getProducto().getPrecioVenta();
				BigDecimal cantidad = new BigDecimal(det.getCantidadRecibida());
				BigDecimal subtotal = pv.multiply(cantidad);
				double subtotalIvaDouble;
				BigDecimal subtotalIva;
				psDet.setBigDecimal(5, subtotal);
				if(det.getProducto().isTieneIva()) {
					double subtotalDouble = subtotal.doubleValue();
					double ivaDet = subtotalDouble * 0.12;
					subtotalIvaDouble = subtotalDouble + ivaDet;
					subtotalIva = BigDecimal.valueOf(subtotalIvaDouble);
				}else {
					subtotalIva = subtotal;
				}
				psDet.setBigDecimal(5, subtotalIva);
				psDet.executeUpdate();			
			}
			
			BigDecimal totalSinIva = BigDecimal.ZERO;
			BigDecimal iva = BigDecimal.ZERO;
			BigDecimal total = BigDecimal.ZERO;
			
			for(DetallePedido detalle : detallePedido) {
				BigDecimal precioVenta = detalle.getProducto().getPrecioVenta();
				BigDecimal cantidad = new BigDecimal(detalle.getCantidadRecibida());
				BigDecimal subtotal = precioVenta.multiply(cantidad);
				
				totalSinIva = totalSinIva.add(subtotal);
				
				if(detalle.getProducto().isTieneIva()){
					double ivaDet = subtotal.doubleValue()*0.12;
					iva = iva.add(BigDecimal.valueOf(ivaDet));
				}
				
			}
			
			total = totalSinIva.add(iva);
			
			psCv = con.prepareStatement("UPDATE public.cabecera_ventas SET total_sin_iva=?, iva=?, total=?	WHERE codigo = ?;");
			psCv.setBigDecimal(1, totalSinIva);
			psCv.setBigDecimal(2, iva);
			psCv.setBigDecimal(3, total);
			psCv.setInt(4, codigoCabecera);
			
			ps.executeUpdate();
			
			for(DetallePedido det1 : detallePedido) {
				psCv = con.prepareStatement("INSERT INTO public.historial_stock(fecha, referencia, producto, cantidad) "
						+ "	VALUES (?, ?, ?, ?);");
				psCv.setTimestamp(1, fechaHoraActual);
				psCv.setString(2, "Venta "+pedido.getCodigo());
				psCv.setInt(3, det1.getProducto().getCodigo());
				psCv.setInt(4, det1.getCantidadRecibida());
				ps.executeUpdate();
			}
			System.out.println("se crea la historia");
			
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
