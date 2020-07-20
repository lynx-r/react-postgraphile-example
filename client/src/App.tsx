import { GridOptions } from 'ag-grid-community'

import 'ag-grid-community/dist/styles/ag-grid.css'
import 'ag-grid-community/dist/styles/ag-theme-balham.css'
import 'ag-grid-enterprise'
import { LicenseManager } from 'ag-grid-enterprise/main'
import { AgGridReact } from 'ag-grid-react'
import React, { useState } from 'react'

import ServerSideDatasource from './ServerSideDatasource'

LicenseManager.setLicenseKey(process.env.REACT_APP_LICENSE_KEY || '')

const initGridOptions: GridOptions = {
  columnDefs: [
    {
      field: 'athlete',
      filter: 'agTextColumnFilter',
      minWidth: 220,
      filterParams: {
        apply: true,
        suppressSorting: true,
        suppressRemoveEntries: true,
        suppressAndOrCondition: true,
      },
    },
    {
      field: 'year',
      filter: 'agNumberColumnFilter',
      filterParams: {
        apply: true,
        suppressSorting: true,
        suppressRemoveEntries: true,
        suppressAndOrCondition: true,
      },
    },
    {
      field: 'gold',
      type: 'number',
    },
    {
      field: 'silver',
      type: 'number',
    },
    {
      field: 'bronze',
      type: 'number',
    },
  ],
  defaultColDef: {
    minWidth: 100,
    // menuTabs: ['filterMenuTab'],
  },
  enableFilter: true,
  floatingFilter: true,
  columnTypes: {
    number: {
      filter: 'agNumberColumnFilter',
      filterParams: {
        apply: true,
      }
    }
  },
  rowModelType: 'serverSide',
}

function App() {
  const [gridOptions] = useState(initGridOptions)
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
        animateRows={true}
        serverSideDatasource={datasource}
      />
    </div>
  )
}

export default App
