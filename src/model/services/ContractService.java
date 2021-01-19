package model.services;

import java.util.Calendar;
import java.util.Date;

import model.entities.Contract;
import model.entities.Installments;

public class ContractService {
	
	private OnlinePaymentService onlinePaymentServivce;

	public ContractService(OnlinePaymentService onlinePaymentServivce) {
		
		this.onlinePaymentServivce = onlinePaymentServivce;
	}
	
	public void processContract(Contract contract, int months) {
		
		double basicQuota = contract.getValue() / months;
		for(int i=1;i<=months;i++) {
			double updatedQuota = basicQuota + onlinePaymentServivce.interest(basicQuota, i);
			double fullQuota = updatedQuota + onlinePaymentServivce.paymentFee(updatedQuota);
			Date dueDate = addMonths(contract.getDate(),i);
			contract.getInstallments().add(new Installments(dueDate, fullQuota));
		}
		
	}
	
	private Date addMonths(Date date, int N) {
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, N);
		return calendar.getTime();
		
	}
	
	

}
