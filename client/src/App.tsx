import { GridOptions } from 'ag-grid-community'

import 'ag-grid-community/dist/styles/ag-grid.css'
import 'ag-grid-community/dist/styles/ag-theme-balham.css'
import 'ag-grid-enterprise'
import { LicenseManager } from 'ag-grid-enterprise/main'
import { AgGridReact } from 'ag-grid-react'
import React, { useState } from 'react'

import ServerSideDatasource from './ServerSideDatasource'

LicenseManager.setLicenseKey(process.env.REACT_APP_LICENSE_KEY || '')

const columnDefs = [
  {field: 'athlete'},
  {field: 'country', enableRowGroup: true, rowGroup: true, hide: true},
  {field: 'sport', enableRowGroup: true, rowGroup: true, hide: true},
  {field: 'year'},
  {field: 'age'},
  {field: 'gold', type: 'valueColumn'},
  {field: 'silver', type: 'valueColumn'},
  {field: 'bronze', type: 'valueColumn'},
]

const initGridOptions: GridOptions = {
  columnDefs,

  // use the server-side row model
  rowModelType: 'serverSide',

  // fetch 100 rows per at a time
  cacheBlockSize: 200,

  // only keep 10 blocks of rows
  maxBlocksInCache: 10,

  sideBar: true,

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
  const [gridOptions] = useState(initGridOptions)
  const datasource = new ServerSideDatasource(gridOptions)

  return (
    <div
      className="ag-theme-balham"
      style={{
        height: '500px',
        width: '1400px'
      }}
    >
      <AgGridReact
        gridOptions={gridOptions}
        animateRows={true}
        serverSideDatasource={datasource}
      />
    </div>
  )
}

export default App
