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
    private String status,user;
    private T data;
    public ServiceResponse(String status,String user,T data){
        this.user=user;
        this.status=status;
        this.data=data;
    }
}
