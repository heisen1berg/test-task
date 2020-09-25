package core.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CalculateRequestEntity {
    private String firstFun;
    private String secondFun;
    private Integer iterationCount;
    private String outputMode;
}
