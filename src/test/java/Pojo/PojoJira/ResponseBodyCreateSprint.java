package Pojo.PojoJira;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ResponseBodyCreateSprint {

    private Integer id;
    private String self;
    private String state;
    private String name;
    private String startDate;
    private String endDate;
    private Integer originBoardId;

}
