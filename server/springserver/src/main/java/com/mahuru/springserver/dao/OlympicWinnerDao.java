package com.mahuru.springserver.dao;

import com.mahuru.springserver.builder.SqlQueryBuilder;
import com.mahuru.springserver.domain.OlympicWinner;
import com.mahuru.springserver.domain.RowGroup;
import com.mahuru.springserver.domain.SortModel;
import com.mahuru.springserver.filter.ColumnFilter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class OlympicWinnerDao {

  private final JdbcTemplate template;

  public OlympicWinnerDao(JdbcTemplate template) {
    this.template = template;
  }

  public List<OlympicWinner> getData(int startRow, int endRow, List<SortModel> sorting, List<RowGroup> rowGroups,
                                     Map<String, ColumnFilter> filterModel, List<String> groupKeys) {
    SqlQueryBuilder queryBuilder = new SqlQueryBuilder(groupKeys, rowGroups, filterModel, sorting, startRow, endRow,
        "olympic_winners");

    String sql = queryBuilder.createSql();

    System.out.println("Execute SQL:");
    System.out.println(sql);

    return template.query(sql, new BeanPropertyRowMapper<>(OlympicWinner.class));
  }

}
