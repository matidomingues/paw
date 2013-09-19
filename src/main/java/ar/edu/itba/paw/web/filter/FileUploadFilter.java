package ar.edu.itba.paw.web.filter;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.*;

public class FileUploadFilter implements Filter {

    private int maxFileSize;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.maxFileSize = 5*1024*1024;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (servletRequest instanceof HttpServletRequest) {
            // Cast back to HttpServletRequest.
            HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;

            // Parse HttpServletRequest.
            HttpServletRequest parsedRequest = parseRequest(httpRequest);

            // Continue with filter chain.
            filterChain.doFilter(parsedRequest, servletResponse);
        } else {
            // Not a HttpServletRequest.
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    private HttpServletRequest parseRequest(HttpServletRequest request) throws ServletException {

        // Check if the request is actually a multipart/form-data request.
        if (!ServletFileUpload.isMultipartContent(request)) {
            // If not, then return the request unchanged.
            return request;
        }

        // Prepare the multipart request items.
        // I'd rather call the "FileItem" class "MultipartItem" instead or so. What a stupid name ;)
        List<FileItem> multipartItems = null;

        try {
            // Parse the multipart request items.
            multipartItems = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
            // Note: we could use ServletFileUpload#setFileSizeMax() here, but that would throw a
            // FileUploadException immediately without processing the other fields. So we're
            // checking the file size only if the items are already parsed. See processFileField().
        } catch (FileUploadException e) {
            throw new ServletException("Cannot parse multipart request: " + e.getMessage());
        }

        // Prepare the request parameter map.
        Map<String, String[]> parameterMap = new HashMap<String, String[]>();

        // Loop through multipart request items.
        for (FileItem multipartItem : multipartItems) {
            if (multipartItem.isFormField()) {
                // Process regular form field (input type="text|radio|checkbox|etc", select, etc).
                processFormField(multipartItem, parameterMap);
            } else {
                // Process form file field (input type="file").
                processFileField(multipartItem, request);
            }
        }

        // Wrap the request with the parameter map which we just created and return it.
        return wrapRequest(request, parameterMap);
    }

    private HttpServletRequest wrapRequest(HttpServletRequest request, final Map<String, String[]> parameterMap) {
        return new HttpServletRequestWrapper(request) {
            public Map<String, String[]> getParameterMap() {
                return parameterMap;
            }
            public String[] getParameterValues(String name) {
                return parameterMap.get(name);
            }
            public String getParameter(String name) {
                String[] params = getParameterValues(name);
                return params != null && params.length > 0 ? params[0] : null;
            }
            public Enumeration<String> getParameterNames() {
                return Collections.enumeration(parameterMap.keySet());
            }
        };
    }

    private void processFileField(FileItem fileField, HttpServletRequest request) {
        if (fileField.getName().length() <= 0) {
            // No file uploaded.
            request.setAttribute(fileField.getFieldName(), null);
        } else if (maxFileSize > 0 && fileField.getSize() > maxFileSize) {
            // File size exceeds maximum file size.
            request.setAttribute(fileField.getFieldName(), new FileUploadException(
                    "File size exceeds maximum file size of " + maxFileSize + " bytes."));
            // Immediately delete temporary file to free up memory and/or disk space.
            fileField.delete();
        } else {
            // File uploaded with good size.
            request.setAttribute(fileField.getFieldName(), fileField);
        }
    }

    private void processFormField(FileItem formField, Map<String, String[]> parameterMap) {
        String name = formField.getFieldName();
        String value = formField.getString();
        String[] values = parameterMap.get(name);

        if (values == null) {
            // Not in parameter map yet, so add as new value.
            parameterMap.put(name, new String[] { value });
        } else {
            // Multiple field values, so add new value to existing array.
            int length = values.length;
            String[] newValues = new String[length + 1];
            System.arraycopy(values, 0, newValues, 0, length);
            newValues[length] = value;
            parameterMap.put(name, newValues);
        }
    }

    @Override
    public void destroy() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
