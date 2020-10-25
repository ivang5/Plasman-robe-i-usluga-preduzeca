import React, { Component } from "react";
import styled from "styled-components";

export default class Tabela extends Component {
  render() {
    return (
      <TableWrapper className="col-11 mx-auto mt-5 table">
        <thead>
          <tr>
            <th style={{ width: "2%" }}>Red br.</th>
            <th style={{ width: "25%" }}>NAZIV Dobra / Usluge</th>
            <th style={{ width: "6%" }}>JM</th>
            <th style={{ width: "10%" }}>Izdata količina</th>
            <th style={{ width: "9%" }}>Cena po kg</th>
            <th style={{ width: "12%" }}>Poreska osnovica</th>
            <th style={{ width: "8%" }}>Stopa PDV</th>
            <th style={{ width: "10%" }}>Iznos PDV</th>
            <th style={{ width: "18%" }}>Ukupna naknada</th>
          </tr>
        </thead>
      </TableWrapper>
    );
  }
}

const TableWrapper = styled.table`
  th {
    text-align: center;
    vertical-align: middle !important;
    padding: 1px !important;
    height: 2rem;
  }
`;
