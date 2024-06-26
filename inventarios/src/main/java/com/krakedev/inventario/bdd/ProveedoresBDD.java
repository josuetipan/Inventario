package com.krakedev.inventario.bdd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.krakedev.inventarios.entidades.Proveedor;
import com.krakedev.inventarios.entidades.TipoDocumento;
import com.krakedev.inventarios.excepciones.KarkeDevExcepcion;
import com.krakedev.inventarios.utils.ConexionBDD;

public class ProveedoresBDD {
	
	public ArrayList<Proveedor> buscar(String subcadena) throws KarkeDevExcepcion{
		ArrayList<Proveedor> proveedores = new ArrayList<Proveedor>();
		Connection con = null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		Proveedor proveedor = null;
		try {
			con = ConexionBDD.obtenerConexion(); 
			ps=con.prepareStatement("SELECT prov.identificador, prov.tipo_documento,td.descripcion, prov.nombre, prov.telefono, prov.correo, prov.direccion"
					+ "	FROM public.proveedores prov, tipo_documentos td "
					+ "where prov.tipo_documento = td.codigo_td "
					+ "and upper (nombre) like ?");
			ps.setString(1, "%"+subcadena.toUpperCase()+"%");
			rs = ps.executeQuery();
			
			while(rs.next()) {
				String identificador = rs.getString("identificador");
				String codigoTipoDocumento = rs.getString("tipo_documento");
				String descripcionTipoDocumento = rs.getString("descripcion");
				String nombre = rs.getString("nombre");
				String telefono= rs.getString("telefono");
				String correo = rs.getString("correo");
				String direccion = rs.getString("direccion");
				TipoDocumento td = new TipoDocumento(codigoTipoDocumento,descripcionTipoDocumento);
				proveedor = new Proveedor(identificador,td,nombre,telefono,correo,direccion);
				proveedores.add(proveedor);
				
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
		return proveedores;
	}
	
	
	public void insertar(Proveedor proveedor) throws KarkeDevExcepcion {
		Connection con = null;
		PreparedStatement ps=null;
		try {
			con = ConexionBDD.obtenerConexion();
			ps = con.prepareStatement("INSERT INTO public.proveedores (identificador, tipo_documento, nombre, telefono, correo, direccion)"
					+ "	VALUES (?, ?, ?, ?, ?, ?);");
			ps.setString(1, proveedor.getIdentificador());
			ps.setString(2, proveedor.getTipoDocumento().getCodigo());
			ps.setString(3, proveedor.getNombre());
			ps.setString(4, proveedor.getTelefono());
			ps.setString(5, proveedor.getCorreo());
			ps.setString(6, proveedor.getDireccion());
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
