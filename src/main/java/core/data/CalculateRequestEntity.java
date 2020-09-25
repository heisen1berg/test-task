package core.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CalculateRequestEntity {
    private String firstFun;
    private String secondFun;
    private Integer iterationCount;
    private String outputMode;
}
