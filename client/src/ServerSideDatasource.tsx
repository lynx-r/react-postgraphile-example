import ApolloClient from 'apollo-boost'
import gql from 'graphql-tag'

class ServerSideDatasource {
  private gridOptions: any
  private client: ApolloClient<any>

  constructor(gridOptions: any) {
    this.gridOptions = {...gridOptions}
    this.client = new ApolloClient({uri: 'http://localhost:8080/graphql/'})
  }

  getRows(params: any) {
    let jsonRequest = JSON.stringify(params.request, null, 2)
    console.log(jsonRequest)

    const columns = this.gridOptions.columnDefs

    // query GraphQL endpoint
    this.client.query(query(params.request, columns))
      .then(response => {
        const rows = response.data.rows

        // determine last row to size scrollbar and last block size correctly
        let lastRow = -1
        if (rows.length <= this.gridOptions.cacheBlockSize) {
          lastRow = params.request.startRow + rows.length
        }

        // pass results to grid
        params.successCallback(rows, lastRow)
      })
      .catch(err => {
        console.error(err)
        params.failCallback()
      })
  }
}

const query = (request: any, columns: any) => {

  const fields = columns.map((col: any) => col.field)
  console.log('fields', fields)

  return {
    query: gql`
        query GetRows(
            $start: Int,
            $end: Int,
            $sortModel: [SortModel],
            $groups: [RowGroup],
            $groupKeys: [String],
            $filterModel: Map
        ) {
            rows(
                startRow: $start,
                endRow: $end,
                sorting: $sortModel,
                rowGroups: $groups,
                groupKeys: $groupKeys,
                filterModel: $filterModel
            ) {
                ${fields}
            }
        }
    `,
    variables: {
      start: request.startRow,
      end: request.endRow,
      sortModel: request.sortModel,
      groups: request.rowGroupCols,
      groupKeys: request.groupKeys,
      filterModel: request.filterModel
    },
  }
}

export default ServerSideDatasource
