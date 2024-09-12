
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Servlet implementation class QuoteCalculator
 */
@WebServlet("/QuoteCalculator")
public class QuoteCalculator extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final double TAX_PERCENT = 0.18;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public QuoteCalculator() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.sendRedirect("index.html");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// Reading form data
		String socketType = request.getParameter("SocketType");
		String quantityStr = request.getParameter("quantity");
		String name = request.getParameter("cname");
		String email = request.getParameter("cemail");

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		int quantity = 0;
		// using try block to check validity of entered input
		try {
			quantity = Integer.parseInt(quantityStr);
			if(!isValidEmail(email)) {
				throw new Exception("Invalid Email");
			}
			if(quantity <= 0) {
				throw new NumberFormatException("Quantity negative");
			}
			// Calculating total price
			double pricePerUnit = calculatePrice(socketType);
			double totalPrice = pricePerUnit * quantity;
			double taxAmount = TAX_PERCENT * totalPrice;

			// HTML response for Quote
			out.println(
					"<html><body style='font-family: Arial, sans-serif; background-color: #f9f9f9; padding: 20px;'>");
			out.println("<h2 style='color: #333;'>Quote for Socket Request</h2>");
			out.println("<p style='font-size: 16px; color: #555;'>Thank you, " + name + " (" + email + ")</p>");
			out.println("<p style='font-size: 16px; color: #555;'>Socket Type: " + socketType + "</p>");
			out.println("<p style='font-size: 16px; color: #555;'>Price Per Unit: " + pricePerUnit + "</p>");
			out.println("<p style='font-size: 16px; color: #555;'>Quantity: " + quantity + "</p>");
			out.println("<p style='font-size: 18px; color: #333;'>Total Price: $" + totalPrice + "</p>");
			out.println("<p style='font-size: 18px; color: #333;'>Tax Amount: $" + taxAmount + "</p>");
			out.println("<p style='font-size: 18px; font-weight: bold; color: #333;'>Total Amount After Tax: $"
					+ (totalPrice + taxAmount) + "</p>");
			out.println("</body></html>");
				
		}catch(NumberFormatException e) {
			// HTML response for Error
			out.println("<html><body>");
			out.println("<h2>Error: Invalid Quantity!</h2>");
			out.println("<p>Please enter a valid numeric value.</p>");
			out.println("</body></html>");
		}
		catch (Exception e) {
			// HTML response for Error
			out.println("<html><body>");
			out.println("<h2>Error: Invalid input!</h2>");
			out.println("<p>Please enter a valid email.</p>");
			out.println("</body></html>");
		}

		
	}

	// Method To return corresponding cost for type of socket
	private double calculatePrice(String socketType) {
		switch (socketType) {
		case "Type A":
			return 15.00;
		case "Type B":
			return 12.50;
		case "Type C":
			return 10.00;
		default:
			return 0.00;
		}
	}
	// Method to check validity of email
	private boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
