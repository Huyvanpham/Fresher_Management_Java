package dev.manage_fresher_app.DTO.Response;

import dev.manage_fresher_app.entities.Center;
import dev.manage_fresher_app.entities.CenterDirector;
import dev.manage_fresher_app.entities.ManageHistory;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@AllArgsConstructor
@Getter
@Setter
public class ManageHistoryDTO {
    private Long id;
    private Date startTime;
    private Date endTime;
    private String position;
    private CenterDirector centerDirector;

    public  ManageHistoryDTO(ManageHistory entity){
        this.id = entity.getId();
        this.startTime = entity.getStartTime();
        this.endTime = entity.getEndTime();
        this.position = entity.getPosition();
        this.centerDirector = entity.getCenterDirector();
    }
}