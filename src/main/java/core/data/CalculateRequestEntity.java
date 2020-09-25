package core.data;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CalculateRequestEntity {
    private String firstFun;
    private String secondFun;
    private Integer iterationCount;
    private String outputMode;
}
