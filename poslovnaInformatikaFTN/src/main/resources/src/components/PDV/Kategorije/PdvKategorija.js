import React, { Component } from "react";

export default class PdvKategorija extends Component {
  render() {
    const { naziv, stopePDV } = this.props.kategorija;
    const { grupe, deletePdvKategorija, openModal } = this.props;
    var grupeProizvoda = "";
    grupe.forEach((element) => {
      grupeProizvoda +=
        grupeProizvoda === "" ? element.naziv : ", " + element.naziv;
    });
    return (
      <React.Fragment>
        <tr>
          <td>{naziv}</td>
          <td>{grupeProizvoda}</td>
          <td>{stopePDV[0].procenat + "%"}</td>
          <td>
            <button
              onClick={openModal}
              type="button"
              className="btn btn-primary"
            >
              Izmeni
            </button>
          </td>
          <td>
            <button
              onClick={deletePdvKategorija}
              type="button"
              className="btn btn-danger"
            >
              Izbriši
            </button>
          </td>
        </tr>
      </React.Fragment>
    );
  }
}
