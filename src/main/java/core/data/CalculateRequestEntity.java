package core.data;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CalculateRequestEntity {
    public CalculateRequestEntity(CalculateRequestEntity cre) {
        this.firstFun = cre.firstFun;
        this.secondFun = cre.secondFun;
        this.iterationCount = cre.iterationCount;
        this.outputMode = cre.outputMode;
    }

    private String firstFun;
    private String secondFun;
    private Integer iterationCount;
    private String outputMode;
}
