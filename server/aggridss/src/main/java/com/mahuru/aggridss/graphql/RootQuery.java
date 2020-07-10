package com.mahuru.aggridss.graphql;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.mahuru.aggridss.dao.OlympicWinnerDao;
import com.mahuru.aggridss.domain.OlympicWinner;
import com.mahuru.aggridss.domain.RowGroup;
import com.mahuru.aggridss.domain.SortModel;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RootQuery implements GraphQLQueryResolver {

  private final OlympicWinnerDao olympicWinnerDao;

  public RootQuery(OlympicWinnerDao olympicWinnerDao) {
    this.olympicWinnerDao = olympicWinnerDao;
  }

  public List<OlympicWinner> getRows(int startRow, int endRow, List<SortModel> sorting,
                                     List<RowGroup> rowGroups,
                                     List<String> groupKeys
  ) {
    return olympicWinnerDao.getData(startRow, endRow, sorting, rowGroups, groupKeys);
//    System.out.println("***");
//    System.out.println(startRow);
//    System.out.println(endRow);
//    System.out.println("***");
//    return List.of(
//        new OlympicWinner("Alon", "US", 30, "run", 2010, 2, 1, 0),
//        new OlympicWinner("Anny", "BG", 25, "run", 2010, 1, 1, 3));
  }
}
