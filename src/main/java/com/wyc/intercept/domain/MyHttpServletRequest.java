package com.wyc.intercept.domain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpUpgradeHandler;
import javax.servlet.http.Part;

import com.wyc.wx.domain.AccessTokenBean;
import com.wyc.wx.domain.Authorize;
import com.wyc.wx.domain.Button;
import com.wyc.wx.domain.JsapiTicketBean;
import com.wyc.wx.domain.Result;
import com.wyc.wx.domain.UserInfo;

public class MyHttpServletRequest implements HttpServletRequest{
    private HttpServletRequest httpServletRequest;
    private UserInfo userInfo;
    private Result result;
    private JsapiTicketBean jsapiTicketBean;
    private AccessTokenBean accessTokenBean;
    private Authorize authorize;
    private Button button;
    public void setHttpServletRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }
    public UserInfo getUserInfo() {
        return userInfo;
    }
    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
    public Result getResult() {
        return result;
    }
    public void setResult(Result result) {
        this.result = result;
    }
    public JsapiTicketBean getJsapiTicketBean() {
        return jsapiTicketBean;
    }
    public void setJsapiTicketBean(JsapiTicketBean jsapiTicketBean) {
        this.jsapiTicketBean = jsapiTicketBean;
    }
    public AccessTokenBean getAccessTokenBean() {
        return accessTokenBean;
    }
    public void setAccessTokenBean(AccessTokenBean accessTokenBean) {
        this.accessTokenBean = accessTokenBean;
    }
    public Authorize getAuthorize() {
        return authorize;
    }
    public void setAuthorize(Authorize authorize) {
        this.authorize = authorize;
    }
    public Button getButton() {
        return button;
    }
    public void setButton(Button button) {
        this.button = button;
    }
    public MyHttpServletRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }
    @Override
    public AsyncContext getAsyncContext() {
        // TODO Auto-generated method stub
        return httpServletRequest.getAsyncContext();
    }

    @Override
    public Object getAttribute(String arg0) {
        // TODO Auto-generated method stub
        return httpServletRequest.getAttribute(arg0);
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        // TODO Auto-generated method stub
        return httpServletRequest.getAttributeNames();
    }

    @Override
    public String getCharacterEncoding() {
        // TODO Auto-generated method stub
        return httpServletRequest.getCharacterEncoding();
    }

    @Override
    public int getContentLength() {
        // TODO Auto-generated method stub
        return httpServletRequest.getContentLength();
    }

    @Override
    public long getContentLengthLong() {
        // TODO Auto-generated method stub
        return httpServletRequest.getContentLengthLong();
    }

    @Override
    public String getContentType() {
        // TODO Auto-generated method stub
        return httpServletRequest.getContentType();
    }

    @Override
    public DispatcherType getDispatcherType() {
        // TODO Auto-generated method stub
        return httpServletRequest.getDispatcherType();
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        // TODO Auto-generated method stub
        return httpServletRequest.getInputStream();
    }

    @Override
    public String getLocalAddr() {
        // TODO Auto-generated method stub
        return httpServletRequest.getLocalAddr();
    }

    @Override
    public String getLocalName() {
        // TODO Auto-generated method stub
        return httpServletRequest.getLocalName();
    }

    @Override
    public int getLocalPort() {
        // TODO Auto-generated method stub
        return httpServletRequest.getLocalPort();
    }

    @Override
    public Locale getLocale() {
        // TODO Auto-generated method stub
        return httpServletRequest.getLocale();
    }

    @Override
    public Enumeration<Locale> getLocales() {
        // TODO Auto-generated method stub
        return httpServletRequest.getLocales();
    }

    @Override
    public String getParameter(String arg0) {
        // TODO Auto-generated method stub
        return httpServletRequest.getParameter(arg0);
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        // TODO Auto-generated method stub
        return httpServletRequest.getParameterMap();
    }

    @Override
    public Enumeration<String> getParameterNames() {
        // TODO Auto-generated method stub
        return httpServletRequest.getAttributeNames();
    }

    @Override
    public String[] getParameterValues(String arg0) {
        // TODO Auto-generated method stub
        return httpServletRequest.getParameterValues(arg0);
    }

    @Override
    public String getProtocol() {
        // TODO Auto-generated method stub
        return httpServletRequest.getProtocol();
    }

    @Override
    public BufferedReader getReader() throws IOException {
        // TODO Auto-generated method stub
        return httpServletRequest.getReader();
    }

    @Override
    public String getRealPath(String arg0) {
        // TODO Auto-generated method stub
        return httpServletRequest.getRealPath(arg0);
    }

    @Override
    public String getRemoteAddr() {
        // TODO Auto-generated method stub
        return httpServletRequest.getRemoteAddr();
    }

    @Override
    public String getRemoteHost() {
        // TODO Auto-generated method stub
        return httpServletRequest.getRemoteHost();
    }

    @Override
    public int getRemotePort() {
        // TODO Auto-generated method stub
        return httpServletRequest.getRemotePort();
    }

    @Override
    public RequestDispatcher getRequestDispatcher(String arg0) {
        // TODO Auto-generated method stub
        return httpServletRequest.getRequestDispatcher(arg0);
    }

    @Override
    public String getScheme() {
        // TODO Auto-generated method stub
        return httpServletRequest.getScheme();
    }

    @Override
    public String getServerName() {
        // TODO Auto-generated method stub
        return httpServletRequest.getServerName();
    }

    @Override
    public int getServerPort() {
        // TODO Auto-generated method stub
        return httpServletRequest.getServerPort();
    }

    @Override
    public ServletContext getServletContext() {
        // TODO Auto-generated method stub
        return httpServletRequest.getServletContext();
    }

    @Override
    public boolean isAsyncStarted() {
        // TODO Auto-generated method stub
        return httpServletRequest.isAsyncStarted();
    }

    @Override
    public boolean isAsyncSupported() {
        // TODO Auto-generated method stub
        return httpServletRequest.isAsyncSupported();
    }

    @Override
    public boolean isSecure() {
        // TODO Auto-generated method stub
        return httpServletRequest.isSecure();
    }

    @Override
    public void removeAttribute(String arg0) {
        httpServletRequest.removeAttribute(arg0);
        
    }

    @Override
    public void setAttribute(String arg0, Object arg1) {
        httpServletRequest.setAttribute(arg0, arg1);
        
    }

    @Override
    public void setCharacterEncoding(String arg0)
            throws UnsupportedEncodingException {
        httpServletRequest.setCharacterEncoding(arg0);
        
    }

    @Override
    public AsyncContext startAsync() throws IllegalStateException {
        // TODO Auto-generated method stub
        return httpServletRequest.startAsync();
    }

    @Override
    public AsyncContext startAsync(ServletRequest arg0, ServletResponse arg1)
            throws IllegalStateException {
        // TODO Auto-generated method stub
        return httpServletRequest.startAsync();
    }

    @Override
    public boolean authenticate(HttpServletResponse arg0) throws IOException,
            ServletException {
        // TODO Auto-generated method stub
        return httpServletRequest.authenticate(arg0);
    }

    @Override
    public String changeSessionId() {
        // TODO Auto-generated method stub
        return httpServletRequest.changeSessionId();
    }

    @Override
    public String getAuthType() {
        // TODO Auto-generated method stub
        return httpServletRequest.getAuthType();
    }

    @Override
    public String getContextPath() {
        // TODO Auto-generated method stub
        return httpServletRequest.getContextPath();
    }

    @Override
    public Cookie[] getCookies() {
        // TODO Auto-generated method stub
        return httpServletRequest.getCookies();
    }

    @Override
    public long getDateHeader(String arg0) {
        // TODO Auto-generated method stub
        return httpServletRequest.getDateHeader(arg0);
    }

    @Override
    public String getHeader(String arg0) {
        // TODO Auto-generated method stub
        return httpServletRequest.getHeader(arg0);
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        // TODO Auto-generated method stub
        return httpServletRequest.getHeaderNames();
    }

    @Override
    public Enumeration<String> getHeaders(String arg0) {
        // TODO Auto-generated method stub
        return httpServletRequest.getHeaders(arg0);
    }

    @Override
    public int getIntHeader(String arg0) {
        // TODO Auto-generated method stub
        return httpServletRequest.getIntHeader(arg0);
    }

    @Override
    public String getMethod() {
        // TODO Auto-generated method stub
        return httpServletRequest.getMethod();
    }

    @Override
    public Part getPart(String arg0) throws IOException, ServletException {
        // TODO Auto-generated method stub
        return httpServletRequest.getPart(arg0);
    }

    @Override
    public Collection<Part> getParts() throws IOException, ServletException {
        // TODO Auto-generated method stub
        return httpServletRequest.getParts();
    }

    @Override
    public String getPathInfo() {
        // TODO Auto-generated method stub
        return httpServletRequest.getPathInfo();
    }

    @Override
    public String getPathTranslated() {
        // TODO Auto-generated method stub
        return httpServletRequest.getPathTranslated();
    }

    @Override
    public String getQueryString() {
        // TODO Auto-generated method stub
        return httpServletRequest.getQueryString();
    }

    @Override
    public String getRemoteUser() {
        // TODO Auto-generated method stub
        return httpServletRequest.getRemoteUser();
    }

    @Override
    public String getRequestURI() {
        // TODO Auto-generated method stub
        return httpServletRequest.getRequestURI();
    }

    @Override
    public StringBuffer getRequestURL() {
        // TODO Auto-generated method stub
        return httpServletRequest.getRequestURL();
    }

    @Override
    public String getRequestedSessionId() {
        // TODO Auto-generated method stub
        return httpServletRequest.getRequestedSessionId();
    }

    @Override
    public String getServletPath() {
        // TODO Auto-generated method stub
        return httpServletRequest.getServletPath();
    }

    @Override
    public HttpSession getSession() {
        // TODO Auto-generated method stub
        return httpServletRequest.getSession();
    }

    @Override
    public HttpSession getSession(boolean arg0) {
        // TODO Auto-generated method stub
        return httpServletRequest.getSession(arg0);
    }

    @Override
    public Principal getUserPrincipal() {
        // TODO Auto-generated method stub
        return httpServletRequest.getUserPrincipal();
    }

    @Override
    public boolean isRequestedSessionIdFromCookie() {
        // TODO Auto-generated method stub
        return httpServletRequest.isRequestedSessionIdFromCookie();
    }

    @Override
    public boolean isRequestedSessionIdFromURL() {
        // TODO Auto-generated method stub
        return httpServletRequest.isRequestedSessionIdFromURL();
    }

    @Override
    public boolean isRequestedSessionIdFromUrl() {
        // TODO Auto-generated method stub
        return httpServletRequest.isRequestedSessionIdFromURL();
    }

    @Override
    public boolean isRequestedSessionIdValid() {
        // TODO Auto-generated method stub
        return httpServletRequest.isRequestedSessionIdValid();
    }

    @Override
    public boolean isUserInRole(String arg0) {
        // TODO Auto-generated method stub
        return httpServletRequest.isUserInRole(arg0);
    }

    @Override
    public void login(String arg0, String arg1) throws ServletException {
       httpServletRequest.login(arg0, arg1);
        
    }

    @Override
    public void logout() throws ServletException {
       httpServletRequest.logout();
        
    }

    @Override
    public <T extends HttpUpgradeHandler> T upgrade(Class<T> arg0)
            throws IOException, ServletException {
        // TODO Auto-generated method stub
        return httpServletRequest.upgrade(arg0);
    }
   
}
