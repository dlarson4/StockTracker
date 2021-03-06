package com.stocktracker.http;

public class HttpTaskResponse
{
    public static int HTTP_OK = 200;
    
    public enum Status
    {
        Success, InvalidUrl, InternalServerError, ServerUnavailable, ErrorReadingResponse;
    }
    
    public static HttpTaskResponse INVALID_URL = new HttpTaskResponse(Status.InvalidUrl);
    public static HttpTaskResponse SERVER_UNAVAILABLE = new HttpTaskResponse(Status.ServerUnavailable);
    public static HttpTaskResponse INTERNAL_SERVER_ERROR = new HttpTaskResponse(Status.InternalServerError);
    public static HttpTaskResponse ERROR_READING_RESPONSE = new HttpTaskResponse(Status.ErrorReadingResponse);
    
    private Status status;
    private String data;

    private HttpTaskResponse(final Status status)
    {
        this.status = status;
    }
    
    private HttpTaskResponse(final Status status, final String data)
    {
        this.status = status;
        this.data = data;
    }
    
    public static HttpTaskResponse createSuccessResponse(final String content)
    {
        return new HttpTaskResponse(Status.Success, content);
    }

    public boolean isSuccess()
    {
        return status == Status.Success;
    }

    public boolean isError()
    {
        return !isSuccess();
    }

    public Status getStatus()
    {
        return status;
    }

    public String getData()
    {
        return data;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("HttpTaskResponse{");
        sb.append("status=").append(status);
        sb.append(", data='").append(data).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
