package com.example.EMS.LogFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


@Component
@Order(1)
public class RequestResponseLoggingFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger("API_LOGGER");
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                     HttpServletResponse response,
                                     FilterChain filterChain) throws ServletException, IOException {

        // Wrap request/response so the body can be read AND still be passed on normally
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

        long startTime = System.currentTimeMillis();

        try {
            filterChain.doFilter(wrappedRequest, wrappedResponse);
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            logApiDetails(wrappedRequest, wrappedResponse, duration);
            // MUST be called, otherwise the response body will never reach the actual client
            wrappedResponse.copyBodyToResponse();
        }
    }

    private void logApiDetails(ContentCachingRequestWrapper request,
                                ContentCachingResponseWrapper response,
                                long durationMs) {

        String apiName = request.getMethod();
        String apiUrl = buildFullUrl(request);
        String requestBody = prettyPrint(getRequestBody(request));
        String responseBody = prettyPrint(getResponseBody(response));

        StringBuilder log = new StringBuilder();
        log.append("\n---------------------------- API CALL LOG ----------------------------\n");
        log.append("API Name       : ").append(apiName).append('\n');
        log.append("API URL        : ").append(apiUrl).append('\n');
        log.append("Request Body   : ").append(requestBody.isEmpty() ? "N/A" : requestBody).append('\n');
        log.append("Response Body  : ").append(responseBody.isEmpty() ? "N/A" : responseBody).append('\n');
        log.append("------------------------------------------------------------------------");

        logger.info(log.toString());
    }

    private String prettyPrint(String body) {
        if (body == null || body.isEmpty()) {
            return body;
        }
        try {
            JsonNode json = objectMapper.readTree(body);
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
        } catch (Exception e) {
            return body;
        }
    }

    private String buildFullUrl(HttpServletRequest request) {
        StringBuilder url = new StringBuilder(request.getRequestURL().toString());
        String queryString = request.getQueryString();
        if (queryString != null) {
            url.append('?').append(queryString);
        }
        return url.toString();
    }

    private String getRequestBody(ContentCachingRequestWrapper request) {
        byte[] buf = request.getContentAsByteArray();
        if (buf.length == 0) {
            return "";
        }
        try {
            String encoding = request.getCharacterEncoding() != null
                    ? request.getCharacterEncoding() : StandardCharsets.UTF_8.name();
            return new String(buf, 0, buf.length, encoding);
        } catch (Exception e) {
            return "[unreadable request body]";
        }
    }

    private String getResponseBody(ContentCachingResponseWrapper response) {
        byte[] buf = response.getContentAsByteArray();
        if (buf.length == 0) {
            return "";
        }
        try {
            String encoding = response.getCharacterEncoding() != null
                    ? response.getCharacterEncoding() : StandardCharsets.UTF_8.name();
            return new String(buf, 0, buf.length, encoding);
        } catch (Exception e) {
            return "[unreadable response body]";
        }
    }
}