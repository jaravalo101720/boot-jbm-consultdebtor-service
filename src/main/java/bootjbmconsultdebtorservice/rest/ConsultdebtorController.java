package bootjbmconsultdebtorservice.rest;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bootjbmconsultdebtorservice.dto.Data;
import bootjbmconsultdebtorservice.dto.GeneralInfoResponse;
import bootjbmconsultdebtorservice.dto.Response;
import bootjbmconsultdebtorservice.impl.ConsultdebtorServiceImpl;
import bootjbmconsultdebtorservice.util.AppServiceException;

@RestController
@RequestMapping("/operador")
public class ConsultdebtorController {


	@Autowired
	private ConsultdebtorServiceImpl serviceImpl;
	
	@GetMapping(value = "/consult-internal-debtor", produces = MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Response> getExecute(
			@RequestParam(name = "idTransaccion") String idTransaccion,
			@RequestParam(name = "accion")String accion,
			@RequestParam(name = "canal") String canal,
			@RequestParam(name = "operador") String operador,
			@RequestParam(name = "servicio") String servicio,
			@RequestParam(name = "tipoDocumento") String tipoDocumento,
			@RequestParam(name = "nroDocumento") String documentoNro			
			) {
		
		Response responseData= null;
		ResponseEntity<Response> response= null;
		
		try {
			
			this.validate(idTransaccion);
			responseData = serviceImpl.processTransaction(idTransaccion, operador, servicio, accion, documentoNro);
			response = new ResponseEntity<>(responseData, HttpStatus.OK);
			
			
		} catch (AppServiceException serviceException) {
			int httpStatus=403;
			responseData = fillResponseError(serviceException.getCode(), serviceException.getMessage());
			response = new ResponseEntity<Response>(responseData, HttpStatus.valueOf(httpStatus));
		}
		
		return response;
	}
	
	public Response fillResponseError(String codigo, String descripcion) {
		Response response=null;
		GeneralInfoResponse generalInfo=null;
		Data data = new Data();
		generalInfo = new GeneralInfoResponse(codigo, descripcion);
		data.setGeneralInfo(generalInfo);
		response = new Response(data);
		
		return response;
		
	}
	
	public void validate(String idTransaccion) {
		if(idTransaccion==null ||  idTransaccion.isEmpty() ||  idTransaccion.length() != 32) {
			throw new AppServiceException("409", "el parametro idTransaccion NO valido, debe contener 32 caracteres");
		}
		
		
	}
}




