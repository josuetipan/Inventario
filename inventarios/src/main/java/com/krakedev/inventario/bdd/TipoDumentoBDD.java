package com.krakedev.inventario.bdd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.krakedev.inventarios.entidades.TipoDocumento;
import com.krakedev.inventarios.excepciones.KarkeDevExcepcion;
import com.krakedev.inventarios.utils.ConexionBDD;

public class TipoDumentoBDD {

	public ArrayList<TipoDocumento> buscar() throws KarkeDevExcepcion{
		ArrayList<TipoDocumento> tipDocum = new ArrayList<TipoDocumento>();
		Connection con = null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		TipoDocumento tipo = null;
		try {
			con = ConexionBDD.obtenerConexion(); 
			ps=con.prepareStatement("SELECT codigo_td, descripcion"
					+ "	FROM public.tipo_documentos");
			rs = ps.executeQuery();
			
			while(rs.next()) {
				String codigo = rs.getString("codigo_td");
				String descripcion = rs.getString("descripcion");
				
				tipo = new TipoDocumento(codigo,descripcion);
				tipDocum.add(tipo);
				
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
		return tipDocum;
	}
	
}
