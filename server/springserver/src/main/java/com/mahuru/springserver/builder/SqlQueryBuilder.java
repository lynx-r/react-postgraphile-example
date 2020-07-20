package com.mahuru.springserver.builder;

import com.mahuru.springserver.domain.RowGroup;
import com.mahuru.springserver.domain.SortModel;
import com.mahuru.springserver.filter.ColumnFilter;
import com.mahuru.springserver.filter.NumberColumnFilter;
import com.mahuru.springserver.filter.SetColumnFilter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

import static com.google.common.collect.Streams.zip;
import static java.lang.String.format;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Stream.concat;

public class SqlQueryBuilder {

  private final List<String> groupKeys;
  private final List<RowGroup> rowGroups;
  private final List<SortModel> sorting;
  private final int startRow;
  private final int endRow;
  private final String tableName;
  private final Map<String, ColumnFilter> filterModel;
  private final Map<String, String> operatorMap = new HashMap<>() {{
    put("equals", "=");
    put("notEqual", "<>");
    put("lessThan", "<");
    put("lessThanOrEqual", "<=");
    put("greaterThan", ">");
    put("greaterThanOrEqual", ">=");
  }};

  public String createSql() {
    return selectSql() + fromSql(tableName) + whereSql() + groupBySql() + orderBySql() + limitSql();
  }

  private String selectSql() {
    if (isGrouping()) {
      var groupsToUse = rowGroups.stream()
          .skip(groupKeys.size())
          .limit(groupKeys.size() + 1)
          .map(RowGroup::getField).collect(joining(", "));

      if (!groupsToUse.isEmpty()) {
        return "SELECT " + groupsToUse
            + ", sum(gold) as gold, sum(silver) as silver, sum(bronze) as bronze ";
      }
    }
    return "SELECT *";
  }

  private String fromSql(String tableName) {
    return format(" FROM %s", tableName);
  }

  public SqlQueryBuilder(List<String> groupKeys, List<RowGroup> rowGroups,
                         Map<String, ColumnFilter> filterModel, List<SortModel> sorting,
                         int startRow, int endRow, String tableName) {
    this.groupKeys = groupKeys != null ? groupKeys : new ArrayList<>();
    this.rowGroups = rowGroups != null ? rowGroups : new ArrayList<>();
    this.sorting = sorting != null ? sorting : new ArrayList<>();
    this.startRow = startRow;
    this.endRow = endRow;
    this.tableName = tableName;
    this.filterModel = filterModel;
  }

  private String groupBySql() {
    if (isGrouping()) {
      var groupsToUse = rowGroups.stream()
          .skip(groupKeys.size())
          .limit(groupKeys.size() + 1)
          .map(RowGroup::getField).collect(joining(", "));
      if (!groupsToUse.isEmpty()) {
        return " GROUP BY " + groupsToUse;
      }
    }
    return "";
  }

  private String orderBySql() {
    if (!sorting.isEmpty()) {
      String ordering = sorting.stream().map(s -> format("%s %s", s.getColId(), s.getSort())).collect(joining(", "));
      return " ORDER BY " + ordering;
    }
    return "";
  }

  private String whereSql() {
    String whereFilters = concat(getGroupColumns(), getFilters())
        .collect(joining(" AND "));
    return whereFilters.isEmpty() ? "" : format(" WHERE %s AND %s", whereFilters);
  }

  private String limitSql() {
    return " OFFSET " + startRow + " LIMIT " + (endRow - startRow + 1);
  }

  private Stream<String> getGroupColumns() {
    return zip(groupKeys.stream(), rowGroups.stream(), (key, group) -> group.getField() + " = '" + key + "'");
  }

  private Stream<String> getFilters() {
    Function<Map.Entry<String, ColumnFilter>, String> applyFilters = entry -> {
      String columnName = entry.getKey();
      ColumnFilter filter = entry.getValue();

      if (filter instanceof SetColumnFilter) {
        return setFilter().apply(columnName, (SetColumnFilter) filter);
      }

      if (filter instanceof NumberColumnFilter) {
        return numberFilter().apply(columnName, (NumberColumnFilter) filter);
      }

      return "";
    };

    return filterModel.entrySet().stream().map(applyFilters);
  }

  private BiFunction<String, SetColumnFilter, String> setFilter() {
    return (String columnName, SetColumnFilter filter) ->
        columnName + (filter.getValues().isEmpty() ? " IN ('') " : " IN " + asString(filter.getValues()));
  }

  private BiFunction<String, NumberColumnFilter, String> numberFilter() {
    return (String columnName, NumberColumnFilter filter) -> {
      Integer filterValue = filter.getFilter();
      String filerType = filter.getType();
      String operator = operatorMap.get(filerType);

      return columnName + (filerType.equals("inRange") ?
          " BETWEEN " + filterValue + " AND " + filter.getFilterTo() : " " + operator + " " + filterValue);
    };
  }

  private boolean isGrouping() {
    return !rowGroups.isEmpty();
  }

  private String asString(List<String> l) {
    return "(" + l.stream().map(s -> "'" + s + "'").collect(joining(", ")) + ")";
  }
}
