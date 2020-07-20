package com.mahuru.springserver.domain;

import com.mahuru.springserver.filter.ColumnFilter;
import graphql.schema.DataFetchingEnvironment;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@NoArgsConstructor
@Data
public class RowParams {
  int startRow;
  int endRow;
  List<SortModel> sorting;
  List<RowGroup> rowGroups;
  Map<String, ColumnFilter> filterModel;
  List<String> groupKeys;

  public Map<String, ColumnFilter> getFilterModel(DataFetchingEnvironment environment) {
    var filterModel = environment.getArgument("filterModel");
    System.out.println(filterModel);
    return Map.of();
  }
}
