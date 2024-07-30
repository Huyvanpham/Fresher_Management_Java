package dev.manage_fresher_app.DTO.Response.Fresher;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FresherStatisticsDTO {
    private String centerName;
    private long fresherCount;
}
