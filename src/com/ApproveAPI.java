package com;

//import com.Payment;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig; 
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
* Servlet implementation class PaymentAPI
*/
@WebServlet("/ApproveAPI")
public class ApproveAPI  extends HttpServlet{

	private static final long serialVersionUID = 1L;

	Approve approveObj = new Approve();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ApproveAPI() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, NumberFormatException {
				String CUSID=request.getParameter("CUSID");
				String CUSName=request.getParameter("CUSName");
				String Amount=	request.getParameter("Amount");
				String Bank=request.getParameter("Bank");
				String CardNo=request.getParameter("CardNo");
				String paymentDate=request.getParameter("paymentDate");
				String PayStatus=request.getParameter("PayStatus");
				String ApproveDate=request.getParameter("ApproveDate");
				
				String output = approveObj.insertApprove(CUSID,CUSName,Amount,Bank,CardNo,paymentDate,PayStatus,ApproveDate);
		response.getWriter().write(output);

	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		int AID = Integer.parseInt(request.getParameter("hidApproveIdSave"));
		String CUSID = request.getParameter("CUSID");
		String CUSName = request.getParameter("CUSName");
		String Amount = request.getParameter("Amount");
		String Bank = request.getParameter("Bank");
		String CardNo = request.getParameter("CardNo");
		String paymentDate = request.getParameter("paymentDate");
		String PayStatus = request.getParameter("PayStatus");
		String ApproveDate = request.getParameter("ApproveDate");
		 
		
		String output = approveObj.updateApprove(AID, CUSID,CUSName,Amount,Bank,CardNo,paymentDate,PayStatus,ApproveDate);

		response.getWriter().write(output);
	}


	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

					Map paras = getParasMap(request);
					int AID = Integer.parseInt(paras.get("AID").toString());

		  String output = approveObj.deleteApprove(AID);

		response.getWriter().write(output);
	}

	// Convert request parameters to a Map
	private static Map getParasMap(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			Scanner scanner = new Scanner(request.getInputStream(), "UTF-8");
			String queryString = scanner.hasNext() ? scanner.useDelimiter("\\A").next() : "";
			scanner.close();

			String[] params = queryString.split("&");
			for (String param : params) {

				String[] p = param.split("=");
				map.put(p[0], p[1]);
			}
		} catch (Exception e) {

		}
		return map;

	}
}



	



