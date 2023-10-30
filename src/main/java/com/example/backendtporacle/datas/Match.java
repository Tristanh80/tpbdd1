package com.example.backendtporacle.datas;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Match {
    String CodeMatch;
    Integer NbreButsClubA;
    Integer NbreButsClubB;
    Integer NbreSpectateurs;
    String CodeArbitre;
    String CodeStade;
}
