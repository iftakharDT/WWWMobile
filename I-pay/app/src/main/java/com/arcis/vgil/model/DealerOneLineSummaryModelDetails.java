package com.arcis.vgil.model;

public class DealerOneLineSummaryModelDetails {
	/*  {'InvoiceNo': '0110000816','Date': '12/02/2016','DocumentType': 'DZ','BalanceAmount': '-8685.00'}*/
	
	public String getInvoiceNo() {
		return InvoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		InvoiceNo = invoiceNo;
	}

	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}

	public String getDocumentType() {
		return DocumentType;
	}

	public void setDocumentType(String documentType) {
		DocumentType = documentType;
	}

	public String getBalanceAmount() {
		return BalanceAmount;
	}

	public void setBalanceAmount(String balanceAmount) {
		BalanceAmount = balanceAmount;
	}

	private String InvoiceNo;
	private String Date;
	private String DocumentType;
	private String BalanceAmount;
}
