package com.krakedev.inventarios.servicios;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.krakedev.inventario.bdd.PedidoBDD;
import com.krakedev.inventarios.entidades.Pedido;
import com.krakedev.inventarios.excepciones.KarkeDevExcepcion;

@Path("pedidos")
public class ServiciosPedidos {

	
	@Path("registrar")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response crear(Pedido pedido) {
		PedidoBDD pedBDD = new PedidoBDD();
		try {
			pedBDD.insertar(pedido);
			return Response.ok().build();
		} catch (KarkeDevExcepcion e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}
	
	
	
}
