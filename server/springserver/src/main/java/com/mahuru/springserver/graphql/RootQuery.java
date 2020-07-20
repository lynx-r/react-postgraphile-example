package com.mahuru.springserver.graphql;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.mahuru.springserver.dao.OlympicWinnerDao;
import com.mahuru.springserver.domain.OlympicWinner;
import com.mahuru.springserver.domain.RowParams;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RootQuery implements GraphQLQueryResolver {

  private final OlympicWinnerDao olympicWinnerDao;

  public RootQuery(OlympicWinnerDao olympicWinnerDao) {
    this.olympicWinnerDao = olympicWinnerDao;
  }

  public List<OlympicWinner> getRows(RowParams params) {
    return olympicWinnerDao.getData(params.getStartRow(), params.getEndRow(), params.getSorting(),
        params.getRowGroups(), params.getFilterModel(), params.getGroupKeys());
  }


}
