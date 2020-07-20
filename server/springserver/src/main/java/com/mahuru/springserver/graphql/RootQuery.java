package com.mahuru.springserver.graphql;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.mahuru.springserver.dao.OlympicWinnerDao;
import com.mahuru.springserver.domain.OlympicWinner;
import com.mahuru.springserver.domain.RowGroup;
import com.mahuru.springserver.domain.SortModel;
import com.mahuru.springserver.filter.ColumnFilter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class RootQuery implements GraphQLQueryResolver {

  private final OlympicWinnerDao olympicWinnerDao;

  public RootQuery(OlympicWinnerDao olympicWinnerDao) {
    this.olympicWinnerDao = olympicWinnerDao;
  }

  public List<OlympicWinner> getRows(int startRow, int endRow, List<SortModel> sorting, List<RowGroup> rowGroups,
                                     List<String> groupKeys, Map<String, ColumnFilter> filterModel) {
    return olympicWinnerDao.getData(startRow, endRow, sorting, rowGroups, filterModel, groupKeys);
  }

}
