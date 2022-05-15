package com;

import com.Billing;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ProjectAPI
 */
@WebServlet("/billingAPI")
public class billingAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Billing projectObj = new Billing();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public billingAPI() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String output = projectObj.insertProject(request.getParameter("Month"), 
				request.getParameter("Customer_name"),
				request.getParameter("Meter_reading"),
				request.getParameter("Charge_for_this_month"),
				request.getParameter("Total_Bill_amount"));
		
		// String output = BillingObj.insertBilling(Month, Customer_name, Meter_reading, Charge_for_this_month, Total_Bill_amount);


		response.getWriter().write(output);

	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map paras = getParasMap(request);

		String output = projectObj.updateProject(paras.get("hidProjectIDSave").toString(),
				paras.get("Month").toString(), 
				paras.get("Customer_name").toString(),
				paras.get("Meter_reading").toString(),
				paras.get("Charge_for_this_month").toString(),
				paras.get("Total_Bill_amount").toString()
				

		);

		response.getWriter().write(output);
	}


	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Map paras = getParasMap(request);

		String output = projectObj.deleteProject(paras.get("billingId").toString());

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
