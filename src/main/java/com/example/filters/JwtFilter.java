package com.example.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.google.gson.Gson;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebFilter(urlPatterns = {"/api/search", "/api/profile"})
public class JwtFilter implements Filter {
    private static final Dotenv dotenv = Dotenv.load();

    private static final String JWT_SECRET_KEY = dotenv.get("JWT_SECRET_KEY");

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        PrintWriter out = resp.getWriter();
        Gson gson = new Gson();
        Map<String, String> errors = new HashMap<>();

        String authHeader = req.getHeader("Authorization");
        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            errors.put("authHeader", "Authorization header is null or Bearer is not defined!");
            out.print(gson.toJson(errors));
            return;
        }
        String token = authHeader.substring(7);

        try{
            Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET_KEY);
            JWT.require(algorithm)
                    .withIssuer("CraftoriaS")
                    .build()
                    .verify(token);
            chain.doFilter(request,response);
        } catch(Exception e) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            errors.put("Token", "Nevalidan ili istekao token, molim vas ulogujte se ponovo!");
            out.print(gson.toJson(errors));
            return;
        }
    }
}
