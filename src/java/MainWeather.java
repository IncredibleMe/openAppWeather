/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.aksingh.owmjapis.CurrentWeather;
import net.aksingh.owmjapis.DailyForecast;
import net.aksingh.owmjapis.OpenWeatherMap;
import org.json.JSONException;

/**
 *
 * @author Jack
 */
public class MainWeather extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, JSONException {
        response.setContentType("text/html;charset=UTF-8");
        String days = "";
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>\n"
                    + "<html lang=\"en\">\n"
                    + "  <head>\n"
                    + "    <meta charset=\"utf-8\">\n"
                    + "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n"
                    + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n"
                    + "\n"
                    + "    <title>Weather Forecast</title>\n"
                    + "\n"
                    + "    <meta name=\"description\" content=\"Source code generated using layoutit.com\">\n"
                    + "    <meta name=\"author\" content=\"LayoutIt!\">\n"
                    + "\n"
                    + "     <!-- Material Design fonts -->\n"
                    + "  <link rel=\"stylesheet\" type=\"text/css\" href=\"//fonts.googleapis.com/css?family=Roboto:300,400,500,700\">\n"
                    + "  <link rel=\"stylesheet\" type=\"text/css\" href=\"//fonts.googleapis.com/icon?family=Material+Icons\">\n"
                    + "\n"
                    + "  <!-- Bootstrap -->\n"
                    + "  <link rel=\"stylesheet\" type=\"text/css\" href=\"//maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\">\n"
                    + "\n"
                    + "  <!-- Bootstrap Material Design -->\n"
                    + "  <link rel=\"stylesheet\" type=\"text/css\" href=\"dist/css/bootstrap-material-design.css\">\n"
                    + "  <link rel=\"stylesheet\" type=\"text/css\" href=\"dist/css/ripples.min.css\">"
                    + "    <link href=\"css/style.css\" rel=\"stylesheet\">\n"
                    + " <link href=\"https://cdnjs.cloudflare.com/ajax/libs/bootstrap-material-design/4.0.2/bootstrap-material-design.css\">\n"
                    + "<link href=\"https://cdnjs.cloudflare.com/ajax/libs/bootstrap-material-design/4.0.2/bootstrap-material-design.iife.js\">\n"
                    + "<link href=\"https://cdnjs.cloudflare.com/ajax/libs/bootstrap-material-design/4.0.2/bootstrap-material-design.iife.js.map\">\n"
                    + "<link href=\"https://cdnjs.cloudflare.com/ajax/libs/bootstrap-material-design/4.0.2/bootstrap-material-design.iife.min.js\">\n"
                    + "<link href=\"https://cdnjs.cloudflare.com/ajax/libs/bootstrap-material-design/4.0.2/bootstrap-material-design.min.css\">\n"
                    + "<link href=\"https://cdnjs.cloudflare.com/ajax/libs/bootstrap-material-design/4.0.2/bootstrap-material-design.umd.js\">\n"
                    + "<link href=\"https://cdnjs.cloudflare.com/ajax/libs/bootstrap-material-design/4.0.2/bootstrap-material-design.umd.js.map\">\n"
                    + "<link href=\"https://cdnjs.cloudflare.com/ajax/libs/bootstrap-material-design/4.0.2/bootstrap-material-design.umd.min.js\">\n"
                    + "<link href=\"https://cdnjs.cloudflare.com/ajax/libs/bootstrap-material-design/4.0.2/bootstrap-material-design.umd.min.js.map\">\n"
                    + "  </head>");
            out.println("<body background=\"background-2.jpg\">\n"
                    + "\n"
                    + "    <div class=\"container-fluid\">\n"
                    + "	<div class=\"row\">\n"
                    + "		<div class=\"col-md-12\">\n"
                    + "			<h3 style=\"color:white;\">\n");

            days = request.getParameter("optradio");

            out.println(String.valueOf(days));
            out.println("-day weather forecast for the city of : <span style=\"color:#9abff9;\"><b>");
            out.println(request.getParameter("city"));
            if (!request.getParameter("country").equals("")) {
                out.println(" (");
                out.println(request.getParameter("country"));
                out.println(")");
            }
            out.println("</b></span>			</h3>\n"
                    + "		</div>\n"
                    + "	</div>");

            out.println();

            OpenWeatherMap owm = new OpenWeatherMap("5de0dcb33886f5f73822c2f06f9ecc94");

            DailyForecast df;

            df = owm.dailyForecastByCityName(request.getParameter("city"),request.getParameter("country"), Byte.parseByte(days));

            //CurrentWeather cwd2 = owm.currentWeatherByCityCode(Long.parseLong(request.getParameter("country")));
            for (int i = 0; i < Integer.parseInt(days); i++) {

                out.println("<div class=\"panel panel-info\">\n"
                        + "  <div class=\"panel-heading\">");
                out.println(df.getForecastInstance(i).getDateTime());
                out.println("</div>\n"
                        + "  <div class=\"panel-body\">\n");
                out.println("Temprature: ");
                DecimalFormat df1 = new DecimalFormat();
                df1.setMaximumFractionDigits(2);
                out.println((df1.format((df.getForecastInstance(i).getTemperatureInstance().getMinimumTemperature() - 32) * 5 / 9)) + " to " + (df1.format((df.getForecastInstance(i).getTemperatureInstance().getMaximumTemperature() - 32) * 5 / 9)));
                out.println(" (Celcius Scale)");
                out.println("<br>Humidity: ");
                out.println(df.getForecastInstance(i).getHumidity() + " %");
                out.println("<br>Pressure: ");
                out.println(df.getForecastInstance(i).getPressure() + "mbar");
                out.println("<br>Clouds: ");
                out.println(df.getForecastInstance(i).getPercentageOfClouds() + "%");
                out.println("<br>Wind Speed: ");
                out.println(df.getForecastInstance(i).getWindSpeed() + "m/sec (");

                float speed = df.getForecastInstance(i).getWindSpeed();
                if (speed >= 0 && speed <= 0.2) {
                    out.println("0");
                }
                if (speed >= 0.3 && speed <= 1.5) {
                    out.println("1");
                }
                if (speed >= 1.6 && speed <= 3.3) {
                    out.println("2");
                }
                if (speed >= 3.4 && speed <= 5.4) {
                    out.println("3");
                }
                if (speed >= 5.5 && speed <= 7.9) {
                    out.println("4");
                }
                if (speed >= 8 && speed <= 10.7) {
                    out.println("5");
                }
                if (speed >= 10.8 && speed <= 13.8) {
                    out.println("6");
                }
                if (speed >= 13.9 && speed <= 17.1) {
                    out.println("7");
                }
                if (speed >= 17.2 && speed <= 20.7) {
                    out.println("8");
                }
                if (speed >= 20.8 && speed <= 24.4) {
                    out.println("9");
                }

                out.println(" Beaufort) with direction");
                float dif = df.getForecastInstance(i).getWindDegree();

                if (dif > 348.75 && dif < 11.25) {
                    out.println("N");
                }
                if (dif >= 11.25 && dif < 33.75) {
                    out.println("NNE");
                }
                if (dif >= 33.75 && dif < 56.25) {
                    out.println("NE");
                }
                if (dif >= 56.25 && dif < 78.75) {
                    out.println("ENE");
                }
                if (dif >= 78.75 && dif < 101.25) {
                    out.println("E");
                }
                if (dif >= 101.25 && dif < 123.75) {
                    out.println("ESE");
                }
                if (dif >= 123.75 && dif < 146.25) {
                    out.println("SE");
                }
                if (dif >= 123.75 && dif < 146.25) {
                    out.println("SE");
                }
                if (dif >= 146.25 && dif < 168.75) {
                    out.println("SSE");
                }
                if (dif >= 168.75 && dif < 191.25) {
                    out.println("S");
                }
                if (dif >= 191.25 && dif < 213.75) {
                    out.println("SSW");
                }
                if (dif >= 213.75 && dif < 236.25) {
                    out.println("SW");
                }
                if (dif >= 236.25 && dif < 258.75) {
                    out.println("WSW");
                }
                if (dif >= 258.75 && dif < 281.25) {
                    out.println("W");
                }
                if (dif >= 281.25 && dif < 303.75) {
                    out.println("WNW");
                }
                if (dif >= 303.75 && dif < 326.25) {
                    out.println("NW");
                }
                if (dif >= 326.25 && dif < 348.75) {
                    out.println("NNW");
                }
                out.println("  </div>\n"
                        + "</div>");
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (JSONException ex) {
            Logger.getLogger(MainWeather.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (JSONException ex) {
            Logger.getLogger(MainWeather.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
