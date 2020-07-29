package bootjbmconsultdebtorservice.impl;



import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import bootjbmconsultdebtorservice.dto.AddInfoResponse;
import bootjbmconsultdebtorservice.dto.Data;
import bootjbmconsultdebtorservice.dto.GeneralInfoResponse;
import bootjbmconsultdebtorservice.dto.Response;
import bootjbmconsultdebtorservice.util.AppServiceException;

@Component
public class ConsultdebtorServiceImpl {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public Response processTransaction(String idTransaccion, String operador, String servicio, String accion, String documentoNro) {
		Response response= null;
		AddInfoResponse addInfo=null;
		GeneralInfoResponse generalInfo=null;
		Data data=null;
		
		int validateTransaction = validateNumberTransaction(documentoNro, servicio);
		if(validateTransaction<=5) {
			int statusClient = getStatusClient(documentoNro, servicio);
			if(statusClient<=0) {
				addInfo = new AddInfoResponse("SIN DEUDA");
				
			}else {
				addInfo = new  AddInfoResponse("CON DEUDA");
			}
			generalInfo = new GeneralInfoResponse("0", "Proceso Satisfactorio");
			data = new Data(generalInfo, addInfo);
			response = new Response(data);
			
			saveTransaction(idTransaccion, operador, servicio, documentoNro, accion, addInfo, generalInfo);
		}else {
			throw new AppServiceException("403", "Usted a llegado al limite de consultar diarias");
		}
		
		return response;
	}
	
	private int validateNumberTransaction(String documentoNro, String servicio) {
		int responseValidate=0;
		
		Date createInit = new Date();
		Date createEnd = new Date();
		
		try {
			
			responseValidate = jdbcTemplate.queryForObject("select count(*) from tbl_consultdebtor_transaction c where c.document_nro=? and c.service=? and c.create between (?) and (?) ", Integer.class, documentoNro, servicio, createInit, createEnd);
		} catch (Exception e) {
			throw new AppServiceException("401", "ERROR al consultar numero de transacciones por dia" + e.getMessage());
		}
		
		return responseValidate;
		
	}
	
	private int getStatusClient(String documentoNro, String servicio) {
		int responseStatusClient=0;
		try {
			
			responseStatusClient = jdbcTemplate.queryForObject("select count(*) from consult_debtor_status_client e where e.documento_identidad=? and e.servicio=? and estado in ('PC')", Integer.class, documentoNro, servicio);
		} catch (Exception e) {
			throw new AppServiceException("401", "ERROR al consultar estado del cliente " + e.getMessage());
		}
		
		return responseStatusClient;
	}
	
	private void saveTransaction(String idTransaccion, String operador, String servicio, String documentoNro, String accion, AddInfoResponse addInfo, GeneralInfoResponse generalInfo) {
		try {
			jdbcTemplate.update("insert into tbl_consultdebtor_transaction(id_transaction,operador,service,document_nro,debt_status,action,code)values(?,?,?,?,?,?,?)",
					idTransaccion, operador, servicio, documentoNro, accion, addInfo.getEstadoDeuda(), generalInfo.getCode() );
		} catch (Exception e) {
			throw new AppServiceException("401", "ERROR en registrar la transaccion");
		}
	}
	
}
