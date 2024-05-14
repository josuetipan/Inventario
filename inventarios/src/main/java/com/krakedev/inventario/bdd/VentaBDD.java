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

import com.krakedev.inventarios.entidades.DetalleVenta;
import com.krakedev.inventarios.entidades.Ventas;
import com.krakedev.inventarios.excepciones.KarkeDevExcepcion;
import com.krakedev.inventarios.utils.ConexionBDD;

public class VentaBDD {
	public void insertar(Ventas ventas) throws KarkeDevExcepcion {
		Connection con = null;
		PreparedStatement ps=null;
		PreparedStatement psDet=null;
		ResultSet rsClave = null;
		int codigoCabecera=0;
		
		Date fechaActual = new Date();
		Timestamp fechaHoraActual = new Timestamp(fechaActual.getTime());
		
		try {
			con = ConexionBDD.obtenerConexion();
			ps = con.prepareStatement("INSERT INTO public.cabecera_ventas( "
					+ "	fecha, total_sin_iva, iva, total) "
					+ "	VALUES (?, ?, ?, ?);",Statement.RETURN_GENERATED_KEYS);
			
			
			ps.setTimestamp(1, fechaHoraActual);
			ps.setInt(2, 0);
			ps.setInt(3, 0);
			ps.setInt(4, 0);
			
			ps.executeUpdate();
			
			rsClave = ps.getGeneratedKeys();
			if(rsClave.next()) {
				codigoCabecera=rsClave.getInt(1);
			}
			System.out.println("aqui LLego>>>>>>");
			ArrayList<DetalleVenta> detalleVenta = ventas.getDetalle();
			DetalleVenta det;
			for(int i=0;i<detalleVenta.size();i++) {
				det = detalleVenta.get(i);
				psDet=con.prepareStatement("INSERT INTO public.detalle_venta( "
						+ "cabecera_ventas, producto, cantidad, precio_ventas, subtotal, subtotal_con_iva) "
						+ "	VALUES (?, ?, ?, ?, ?, ?);");
				psDet.setInt(1, codigoCabecera);
				psDet.setInt(2, det.getProducto().getCodigo());
				psDet.setInt(3, det.getCantidad());
				psDet.setBigDecimal(4, det.getProducto().getPrecioVenta());
				BigDecimal pv = det.getProducto().getPrecioVenta();
				BigDecimal cantidad = new BigDecimal(det.getCantidad());
				BigDecimal subtotal = pv.multiply(cantidad);
				psDet.setBigDecimal(5, subtotal);
				
				boolean condi = det.getProducto().isTieneIva();			
				if(condi==true) {
					
					BigDecimal iva= det.getProducto().getPrecioVenta();
					BigDecimal cantidad1 = new BigDecimal(0.12);
					BigDecimal iva1 = iva.multiply(cantidad1);
					psDet.setBigDecimal(6, iva1);
					
				}else {
					psDet.setBigDecimal(6, subtotal);
					
				}
				
				psDet.executeUpdate();			
				}
			BigDecimal totalSinIva = BigDecimal.ZERO;
			BigDecimal iva = BigDecimal.ZERO;
			BigDecimal total = BigDecimal.ZERO;
			
			for(DetalleVenta detalle : detalleVenta) {
				BigDecimal precioVenta = detalle.getProducto().getPrecioVenta();
				BigDecimal cantidad = new BigDecimal(detalle.getCantidad());
				BigDecimal subtotal = precioVenta.multiply(cantidad);
				
				totalSinIva = totalSinIva.add(subtotal);
				
				if(detalle.getProducto().isTieneIva()){
					double ivaDet = subtotal.doubleValue()*0.12;
					iva = iva.add(BigDecimal.valueOf(ivaDet));
				}
				
			}
			
			total = totalSinIva.add(iva);
			
			ps = con.prepareStatement("UPDATE public.cabecera_ventas SET total_sin_iva=?, iva=?, total=?	WHERE codigo = ?;");
			ps.setBigDecimal(1, totalSinIva);
			ps.setBigDecimal(2, iva);
			ps.setBigDecimal(3, total);
			ps.setInt(4, codigoCabecera);
			
			ps.executeUpdate();
			
			for(DetalleVenta det1 : detalleVenta) {
				ps = con.prepareStatement("INSERT INTO public.historial_stock(fecha, referencia, producto, cantidad) "
						+ "	VALUES (?, ?, ?, ?);");
				ps.setTimestamp(1, fechaHoraActual);
				ps.setString(2, "Venta "+codigoCabecera);
				ps.setInt(3, det1.getProducto().getCodigo());
				ps.setInt(4, det1.getCantidad());
				ps.executeUpdate();
			}
			System.out.println("se crea la historia");
			System.out.println("la cabecera es "+codigoCabecera);
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KarkeDevExcepcion("Error al insertar venta");
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
