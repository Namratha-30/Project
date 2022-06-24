package pos.model.form;

public class ApiError {
    private Integer status;
    private String message;
    public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	private String path;

    public ApiError(int status,String message,String path) {
        super();
        this.status=status;
        this.message=message;
        this.path=path;
    }
}
