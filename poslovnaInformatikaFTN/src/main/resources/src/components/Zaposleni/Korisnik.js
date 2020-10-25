import React, { Component } from "react";

export default class Korisnik extends Component {
  render() {
    const { ime, prezime, adresa, brojTelefona, uloga } = this.props.korisnik;
    const { deleteKorisnik, openModal } = this.props;
    return (
      <React.Fragment>
        <tr>
          <td>{ime}</td>
          <td>{prezime}</td>
          <td>{adresa}</td>
          <td>{brojTelefona}</td>
          <td>{uloga}</td>
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
              onClick={deleteKorisnik}
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
