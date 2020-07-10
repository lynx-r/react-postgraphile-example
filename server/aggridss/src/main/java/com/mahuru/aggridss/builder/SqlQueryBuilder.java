package com.mahuru.aggridss.builder;

import com.mahuru.aggridss.domain.RowGroup;
import com.mahuru.aggridss.domain.SortModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static com.google.common.collect.Streams.zip;
import static java.lang.String.format;
import static java.util.stream.Collectors.joining;

public class SqlQueryBuilder {

  private final List<String> groupKeys;
  private final List<RowGroup> rowGroups;
  private final List<SortModel> sorting;
  private final int startRow;
  private final int endRow;
  private final String tableName;

  public SqlQueryBuilder(List<String> groupKeys, List<RowGroup> rowGroups,
                         List<SortModel> sorting,
                         int startRow, int endRow, String tableName) {
    this.groupKeys = groupKeys != null ? groupKeys : new ArrayList<>();
    this.rowGroups = rowGroups != null ? rowGroups : new ArrayList<>();
    this.sorting = sorting != null ? sorting : new ArrayList<>();
    this.startRow = startRow;
    this.endRow = endRow;
    this.tableName = tableName;
  }

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

  private String whereSql() {
    String whereFilters = getGroupColumns().collect(joining(" AND "));
    return whereFilters.isEmpty() ? "" : format(" WHERE %s", whereFilters);
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

  private String limitSql() {
    return " OFFSET " + startRow + " LIMIT " + (endRow - startRow + 1);
  }

  private Stream<String> getGroupColumns() {
    return zip(groupKeys.stream(), rowGroups.stream(), (key, group) -> group.getField() + " = '" + key + "'");
  }

  private boolean isGrouping() {
    return !rowGroups.isEmpty();
  }
}
