import { useQuery } from '@apollo/react-hooks'
import 'ag-grid-community/dist/styles/ag-grid.css'
import 'ag-grid-community/dist/styles/ag-theme-alpine.css'
import { AgGridReact } from 'ag-grid-react'
import { gql } from 'apollo-boost'
import React, { useState } from 'react'
import './App.css'

const GET_JS_FRAMEWORKS = gql`
  {
  jsFrameworks {
    edges {
      node {
        name
      }
    }
  }
}
`

function App() {

  const [columns] = useState([
    {
      headerName: 'JavaScript Framework', field: 'node.name'
    },
  ])

  const {loading, error, data} = useQuery(GET_JS_FRAMEWORKS)

  if (loading) {
    return <div>Loading...</div>
  }
  if (error) {
    return <div>Error! {error.message}</div>
  }

  return (
    <div
      className="ag-theme-alpine"
      style={{
        height: '250px',
        width: '600px'
      }}
    >
      <AgGridReact
        columnDefs={columns}
        rowData={data.jsFrameworks.edges}>
      </AgGridReact>
    </div>
  )
}

export default App
