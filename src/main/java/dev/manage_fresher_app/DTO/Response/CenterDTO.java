package dev.manage_fresher_app.DTO.Response;

import dev.manage_fresher_app.entities.Center;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CenterDTO {
    private Long id;

    private String name;
    private String address;
    private String phoneNumber;
    private String email;
    private int yearEstablished;
    private List<ManageHistoryDTO> managerHistories;

    public CenterDTO(Center entity){
        if(entity != null){
            this.id = entity.getId();
            this.name = entity.getName();
            this.address = entity.getAddress();
            this.phoneNumber = entity.getPhoneNumber();
            this.email = entity.getEmail();
            this.yearEstablished = entity.getYearEstablished();
            this.managerHistories = entity.getManagerHistories().stream().map(ManageHistoryDTO::new).collect(Collectors.toList());
        }
    }
}
