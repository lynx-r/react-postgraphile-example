import 'ag-grid-community/dist/styles/ag-grid.css'
import 'ag-grid-community/dist/styles/ag-theme-balham.css'
import { LicenseManager } from 'ag-grid-enterprise/main'
import { AgGridReact } from 'ag-grid-react'
import React from 'react'

import ServerSideDatasource from './ServerSideDatasource'

LicenseManager.setLicenseKey(process.env.REACT_APP_LICENSE_KEY || '')

const columnDefs = [
  {field: 'athlete', /*enableRowGroup: true, rowGroup: true,*/ hide: false},
  {
    field: 'country',
    filter: 'agTextColumnFilter',
    // filterParams: {
    //   values: function (params: any) {
    //     // async update simulated using setTimeout()
    //     setTimeout(function () {
    //       // fetch values from server
    //       const values = ['Russia', 'United States', 'Great Britain', 'Canada']
    //
    //       // supply values to the set filter
    //       params.success(values)
    //     }, 100)
    //   },
    // },
    /*enableRowGroup: true, rowGroup: true,*/ hide: false
  },
  {field: 'sport', /*enableRowGroup: true, rowGroup: true,*/ hide: false},
  {
    field: 'year',
    filter: 'agNumberColumnFilter',
  },
  {
    field: 'age',
    filter: 'agNumberColumnFilter',
  },
  {field: 'gold', type: 'valueColumn'},
  {field: 'silver', type: 'valueColumn'},
  {field: 'bronze', type: 'valueColumn'},
]

const defaultColDef = {
  filterParams: {
    debounceMs: 1000
  }
}

const sideBar = {
  toolPanels: [
    {
      id: 'columns',
      labelDefault: 'Колонки',
      labelKey: 'columns',
      iconKey: 'columns',
      toolPanel: 'agColumnsToolPanel',
    },
    {
      id: 'filters',
      labelDefault: 'Фильтры',
      labelKey: 'filters',
      iconKey: 'filter',
      toolPanel: 'agFiltersToolPanel',
    }
  ],
  defaultToolPanel: 'columns',
}
const gridOptions = {
  defaultColDef,
  columnDefs,
  sideBar,

  // use the server-side row model
  rowModelType: 'serverSide',

  // fetch 100 rows per at a time
  cacheBlockSize: 200,

  // only keep 10 blocks of rows
  maxBlocksInCache: 10,

  enableColResize: true,
  enableSorting: true,
  enableFilter: true,

  columnTypes: {
    dimension: {
      enableRowGroup: true,
      enablePivot: true,
    },
    valueColumn: {
      width: 150,
      aggFunc: 'sum',
      enableValue: true,
      cellClass: 'number',
      allowedAggFuncs: ['avg', 'sum', 'min', 'max']
    }
  },
}

function App() {
  const datasource = new ServerSideDatasource(gridOptions)

  return (
    <div
      className="ag-theme-balham"
      style={{
        height: '250px',
        width: '1400px'
      }}
    >
      <AgGridReact
        gridOptions={gridOptions}
        serverSideDatasource={datasource}
      />
    </div>
  )
}

export default App
