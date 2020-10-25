import React, { Component } from "react";

export default class Proizvod extends Component {
  render() {
    const { naziv, jedinicaMere, opis, tipProizvoda } = this.props.proizvod;
    const { grupa, deleteProizvod, openModal } = this.props;
    return (
      <React.Fragment>
        <tr>
          <td>{naziv}</td>
          <td>{jedinicaMere}</td>
          <td>{opis}</td>
          <td>{tipProizvoda}</td>
          <td>{grupa}</td>
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
              onClick={deleteProizvod}
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
