package ru.milovanov.SpringLibrarian.ajax;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
//@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ServiceResponse<T> {
    private String status;
    private T data;
    public ServiceResponse(String status,T data){
        this.status=status;
        this.data=data;
    }
}
