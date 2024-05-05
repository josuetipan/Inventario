package com.krakedev.inventarios.servicios;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.krakedev.inventario.bdd.TipoDumentoBDD;
import com.krakedev.inventarios.entidades.TipoDocumento;
import com.krakedev.inventarios.excepciones.KarkeDevExcepcion;
@Path("tipoDocumento")
public class ServiciosTiposDocumentos {

	@Path("recuperar")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscar(){
		TipoDumentoBDD tipBDD = new TipoDumentoBDD();
		ArrayList<TipoDocumento> documento =null;
		try {
			documento = tipBDD.buscar();
			return Response.ok(documento).build();
		} catch (KarkeDevExcepcion e) {
			e.printStackTrace();
			return Response.serverError().build();	
		}
	}
	
}
